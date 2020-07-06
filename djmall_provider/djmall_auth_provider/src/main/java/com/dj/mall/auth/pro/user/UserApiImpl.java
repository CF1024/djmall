/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_provider
 * 类名：UserApiImpl
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.pro.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.api.user.MailBoxApi;
import com.dj.mall.auth.api.user.UserApi;
import com.dj.mall.auth.bo.user.UserBO;
import com.dj.mall.auth.dto.resource.ResourceDTO;
import com.dj.mall.auth.dto.role.RoleResourceDTO;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.auth.entity.resource.Resource;
import com.dj.mall.auth.entity.user.User;
import com.dj.mall.auth.entity.user.LastLoginTime;
import com.dj.mall.auth.entity.user.UserRole;
import com.dj.mall.auth.mapper.user.UserMapper;
import com.dj.mall.auth.service.user.LastLoginTimeService;
import com.dj.mall.auth.service.user.UserRoleService;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.contant.AuthConstant;
import com.dj.mall.model.contant.DictConstant;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.LocalDateTimeUtils;
import com.dj.mall.model.util.MessageVerifyUtils;
import com.dj.mall.model.util.PasswordSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author chengf
 * @date 2020/6/3 14:59
 * 用户api实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserApiImpl extends ServiceImpl<UserMapper, User> implements UserApi {
    /**
     * 用户角色service
     */
    @Autowired
    private UserRoleService userRoleService;
    /**
     * 时报service
     */
    @Autowired
    private LastLoginTimeService lastLoginTimeService;
    /**
     * 邮箱api
     */
    @Reference
    private MailBoxApi mailBoxApi;
    /**
     * 用户登录
     * @param userName
     * @param userPwd
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public UserDTO findUserByNameAndPwd(String userName, String userPwd) throws Exception, BusinessException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("user_name", userName);
        User user = getBaseMapper().selectOne(queryWrapper);
        if (null == user) {
            throw new BusinessException("该用户不存在");
        }
        if (!PasswordSecurityUtil.checkPassword(userPwd, user.getUserPwd(), user.getSalt())) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getIsDel().equals(DictConstant.HAVE_DEL)) {
            throw new BusinessException("该用户已注销");
        }
        if (user.getUserStatus().equals(DictConstant.NOT_ACTIVATE)) {
            throw new BusinessException("该用户未激活");
        }
        if (user.getIsDel().equals(DictConstant.FORCE_UPDATE_PWD)) {
            throw new BusinessException(AuthConstant.FORCE_UPDATE_PWD_INT, "请修改密码后再进行登录");
        }
        UserDTO userDTO = DozerUtil.map(user, UserDTO.class);
        //用户已关联角色
        userDTO.setUserRole(userRoleService.getOne(new QueryWrapper<UserRole>().eq("user_id", userDTO.getUserId())).getRoleId());
        //最后登录时间
        lastLoginTimeService.save(new LastLoginTime().toBuilder().userId(userDTO.getUserId()).lastLoginTime(LocalDateTime.now()).build());
        return userDTO;
    }

    /**
     * 去重
     * @param userDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public Boolean deDuplicate(UserDTO userDTO) throws Exception, BusinessException {
        User user = DozerUtil.map(userDTO, User.class);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(user.getId())) {
            queryWrapper.ne("id", user.getId());
        }
        if (!StringUtils.isEmpty(user.getUserName())) {
            queryWrapper.eq("user_name", user.getUserName());
        }
        if (!StringUtils.isEmpty(user.getNickName())) {
            queryWrapper.eq("nick_name", user.getNickName());
        }
        if (!StringUtils.isEmpty(user.getUserPhone())) {
            queryWrapper.eq("user_phone", user.getUserPhone());
        }
        if (!StringUtils.isEmpty(user.getUserEmail())) {
            queryWrapper.eq("user_email", user.getUserEmail());
        }
        return getBaseMapper().selectOne(queryWrapper) == null;
    }

    /**
     * 新增用户
     * @param userDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void addUser(UserDTO userDTO) throws Exception, BusinessException {
        //新增 激活状态：已激活 创建时间 ：当前时间 状态：未删除
        User user = DozerUtil.map(userDTO, User.class).toBuilder()
                .userStatus(DictConstant.HAVE_ACTIVATE)
                .createTime(LocalDateTime.now())
                .isDel(DictConstant.NOT_DEL).build();
        //如果是商家 激活状态：未激活
        if (AuthConstant.BUSINESS.equals(userDTO.getUserRole())) {
            user.setUserStatus(DictConstant.NOT_ACTIVATE);
        }
        getBaseMapper().insert(user);
        //如果激活状态为：未激活 并且是商家 则发送邮件
        if (DictConstant.NOT_ACTIVATE.equals(user.getUserStatus()) && AuthConstant.BUSINESS.equals(userDTO.getUserRole())) {
            //邮箱验证
            String mailHTML = "<html>"
                    + "<META http-equiv=Content-Type content='text/html; charset=UTF-8'>"
                    + "<body>"
                    + "<a href='http://127.0.0.1:8081/admin/auth/user/toValidate/"+user.getId()+"'>马上验证邮箱</a><br />"
                    + "如果您无法点击以上链接，请复制以下网址到浏览器里直接打开：<br />"
                    + "http://127.0.0.1:8081/admin/auth/user/toValidate/"+user.getId()+"<br />"
                    + "如果您没有注册，请忽略此邮件"
                    + "</body>"
                    + "</html>";
            mailBoxApi.sendMailHTML(user.getUserEmail(), AuthConstant.ACTIVATE, mailHTML);
        }
        //新增用户角色表用户以及角色的ID
        userRoleService.save(new UserRole().toBuilder().roleId(userDTO.getUserRole()).userId(user.getId()).build());
    }

    /**
     * 修改激活状态 邮箱激活
     * @param id
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void updateUserStatus(Integer id) throws Exception, BusinessException {
        //修改激活状态：已激活
        User user = getBaseMapper().selectById(id);
        if (DictConstant.HAVE_ACTIVATE.equals(user.getUserStatus())) {
            throw new BusinessException("该用户已激活、请激活未激活的用户");
        }else {
            user.setUserStatus(DictConstant.HAVE_ACTIVATE);
        }
        getBaseMapper().updateById(user);
    }

    /**
     * 展示用户 分页 模糊查 查询
     *
     * @param userDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public PageResult findAll(UserDTO userDTO) throws Exception, BusinessException {
        //用户状态：未删除
        userDTO.setIsDel(DictConstant.NOT_DEL);
        IPage<UserBO> iPage = getBaseMapper().findAll(
                new Page<>(userDTO.getPageNo(), userDTO.getPageSize()),
                DozerUtil.map(userDTO, UserBO.class));
        return new PageResult().toBuilder().pages(iPage.getPages()).
                list(DozerUtil.mapList(iPage.getRecords(), UserDTO.class)).build();
    }

    /**
     * 根局id查用户信息
     *
     * @param userId
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public UserDTO findUserById(Integer userId) throws Exception, BusinessException {
        return DozerUtil.map(getBaseMapper().selectById(userId), UserDTO.class);
    }

    /**
     * 修改
     * @param userDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void updateUser(UserDTO userDTO) throws Exception, BusinessException {
        getBaseMapper().updateById(DozerUtil.map(userDTO, User.class));
    }

    /**
     * 重置密码
     * @param userDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void resetPwd(UserDTO userDTO) throws Exception, BusinessException {
        //生成6位随机数
        String random = PasswordSecurityUtil.generateRandom(AuthConstant.RANDOM_SIX);
        //生成密码盐 （时间毫秒值+随机数[6位]）
        String salt = PasswordSecurityUtil.generateSalt();
        //获取用户信息 并修改密码 盐 以及用户状态：强制修改密码
        User user = getBaseMapper().selectById(userDTO.getUserId()).toBuilder()
                .userPwd(PasswordSecurityUtil.generatePassword(PasswordSecurityUtil.enCode32(random), salt))
                .salt(salt).isDel(DictConstant.FORCE_UPDATE_PWD).build();
        getBaseMapper().updateById(user);
        //时间格式转换
        String dateTime = DateTimeFormatter.ofPattern("yyyy年MM月dd日HH时mm分ss秒").format(LocalDateTime.now());
        //重置密码
        String mailHTML = "<html>"
                + "<META http-equiv=Content-Type content='text/html; charset=UTF-8'>"
                + "<body>"
                + "尊敬的"+user.getNickName()+"，您的密码已被管理员"+userDTO.getSessionUser()+"，<br />"
                + "于"+dateTime+"重置为"+random+"。为了您的账户安全，请及时修改。<br />"
                + "<a href='http://127.0.0.1:8081/admin/auth/user/toLogin'>点我去登录</a><br />"
                + "</body>"
                + "</html>";
        mailBoxApi.sendMailHTML(user.getUserEmail(), AuthConstant.RESET_PWD, mailHTML);
    }

    /**
     * 强制修改密码 根据账号查
     * @param userName
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public UserDTO findUserByName(String userName) throws Exception, BusinessException {
        return DozerUtil.map(getBaseMapper().selectOne(new QueryWrapper<User>().eq("user_name", userName)), UserDTO.class);
    }

    /**
     * 强制修改密码
     * @param userDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void forceUpdatePwd(UserDTO userDTO) throws Exception, BusinessException {
        //修改用户状态：未删除 密码
        getBaseMapper().updateById(DozerUtil.map(userDTO.toBuilder().isDel(DictConstant.NOT_DEL).build(), User.class));
    }

    /**
     * 批量删除
     * @param ids
     * @param isDel
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void removeUser(ArrayList<Integer> ids, String isDel) throws Exception, BusinessException {
        getBaseMapper().removeUser(ids, isDel);
        //删除对应用户角色
        userRoleService.remove(new QueryWrapper<UserRole>().in("user_id", ids));
    }

    /**
     * 获取验证码
     * @param userPhone
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void sendCode(String userPhone) throws Exception, BusinessException {
        User user = getBaseMapper().selectOne(new QueryWrapper<User>().eq("user_phone", userPhone));
        if (StringUtils.isEmpty(user)) {
            throw new BusinessException("该用户不存在，请检查手机号后重新获取验证码");
        }
        //6位随机数
        String code = MessageVerifyUtils.getNewCode();
        //失效时间5分钟
        LocalDateTime failureTime = LocalDateTimeUtils.plus(LocalDateTime.now(), AuthConstant.FAILURE_TIME, ChronoUnit.MINUTES);
        //发送短信
        MessageVerifyUtils.sendSms(userPhone, code);
        user.setVerifyCode(code);
        user.setInvalidateTime(failureTime);
        getBaseMapper().updateById(user);
    }

    /**
     * 根据手机号验证码 查用户信息
     * @param userPhone 手机号
     * @param verifyCode 验证码
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public UserDTO findUserByPhoneAndCode(String userPhone, String verifyCode) throws Exception, BusinessException {
        User user = getBaseMapper().selectOne(new QueryWrapper<User>().eq("user_phone", userPhone));
        if (StringUtils.isEmpty(user)) {
            throw new BusinessException("用户不存在");
        }
        if (!user.getVerifyCode().equals(verifyCode)) {
            throw new BusinessException("验证码不正确,请检查验证码是否输入有误");
        }
        if (user.getInvalidateTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException("验证码已失效,请重新获取验证码");
        }
        UserDTO userDTO = DozerUtil.map(user, UserDTO.class);
        //最后登录时间
        lastLoginTimeService.save(new LastLoginTime().toBuilder().userId(userDTO.getUserId()).lastLoginTime(LocalDateTime.now()).build());
        return userDTO;
    }

    /**
     * 根据手机号更改密码
     * @param userDTO 用户dto对象
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void updatePwdByPhone(UserDTO userDTO) throws Exception, BusinessException {
        User user = getBaseMapper().selectOne(new QueryWrapper<User>().eq("user_phone", userDTO.getUserPhone()));
        if (StringUtils.isEmpty(user)) {
            throw new BusinessException("用户不存在");
        }
        if (!user.getVerifyCode().equals(userDTO.getVerifyCode())) {
            throw new BusinessException("验证码不正确,请检查验证码是否输入有误");
        }
        if (user.getInvalidateTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException("验证码已失效,请重新获取验证码");
        }
        //修改密码 盐
        user.setUserPwd(userDTO.getUserPwd());
        user.setSalt(userDTO.getSalt());
        getBaseMapper().updateById(user);
        //时间转换
        String dateTime = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒").format(LocalDateTime.now());
        //忘记密码邮件
        String sendMail = "您的账户"+user.getUserName()+"，于"+dateTime+"进行密码修改成功。";
        mailBoxApi.sendMail(user.getUserEmail(), AuthConstant.FORGET_PWD, sendMail);
    }


}
