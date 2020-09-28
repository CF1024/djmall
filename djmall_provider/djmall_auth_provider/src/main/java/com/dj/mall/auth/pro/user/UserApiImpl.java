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
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.api.user.MailBoxApi;
import com.dj.mall.auth.api.user.UserApi;
import com.dj.mall.auth.bo.user.UserBO;
import com.dj.mall.auth.dto.address.AreaDTO;
import com.dj.mall.auth.dto.address.UserAddressDTO;
import com.dj.mall.auth.dto.cart.ShoppingCartDTO;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.auth.dto.user.UserTokenDTO;
import com.dj.mall.auth.entity.address.AreaEntity;
import com.dj.mall.auth.entity.address.UserAddressEntity;
import com.dj.mall.auth.entity.cart.ShoppingCartEntity;
import com.dj.mall.auth.entity.user.User;
import com.dj.mall.auth.entity.user.LastLoginTime;
import com.dj.mall.auth.entity.user.UserRole;
import com.dj.mall.auth.mapper.user.UserMapper;
import com.dj.mall.auth.service.address.AreaService;
import com.dj.mall.auth.service.address.UserAddressService;
import com.dj.mall.auth.service.cart.ShoppingCartService;
import com.dj.mall.auth.service.user.LastLoginTimeService;
import com.dj.mall.auth.service.user.UserRoleService;
import com.dj.mall.cmpt.RedisApi;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;
import com.dj.mall.model.contant.*;
import com.dj.mall.model.statement.Statement;
import com.dj.mall.model.util.*;
import com.dj.mall.product.api.sku.SkuApi;
import com.dj.mall.product.dto.sku.SkuDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author chengf
 * @date 2020/6/3 14:59
 * 用户api实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserApiImpl extends ServiceImpl<UserMapper, User> implements UserApi {
    /**
     * 用户角色service 不暴露服务
     */
    @Autowired
    private UserRoleService userRoleService;
    /**
     * 时报service 不暴露服务
     */
    @Autowired
    private LastLoginTimeService lastLoginTimeService;
    /**
     * 购物车Service 不暴露服务
     */
    @Autowired
    private ShoppingCartService shoppingCartService;
    /**
     * 地区Service 不暴露服务
     */
    @Autowired
    private AreaService areaService;
    /**
     * 收货地址Service 不暴露服务
     */
    @Autowired
    private UserAddressService userAddressService;
    @Reference
    private SkuApi skuApi;

    /**
     * 邮箱api
     */
    @Reference
    private MailBoxApi mailBoxApi;
    /**
     * redisApi
     */
    @Reference
    private RedisApi redisApi;
    /**
     * rabbitMQ
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

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
        UserRole userRole = userRoleService.getOne(new QueryWrapper<UserRole>().eq("user_id", userDTO.getUserId()));
        if (AuthConstant.GENERAL_USER.equals(userRole.getRoleId())) {
            throw new BusinessException("角色不匹配");
        }
        if (AuthConstant.DEFAULT_ROLE.equals(userRole.getRoleId())) {
            throw new BusinessException("请联系管理员进行授权");
        }
        userDTO.setUserRole(userRole.getRoleId());
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
        //角色：普通用户
        if (StringUtils.isEmpty(userDTO.getUserRole())) {
            userDTO.setUserRole(AuthConstant.GENERAL_USER);
        }
        //新增 激活状态：已激活 创建时间 ：当前时间 状态：未删除
        User user = DozerUtil.map(userDTO, User.class).toBuilder().userStatus(DictConstant.HAVE_ACTIVATE).createTime(LocalDateTime.now()).isDel(DictConstant.NOT_DEL).build();
        //昵称:DJ+随机6位数
        if (StringUtils.isEmpty(userDTO.getNickName())) {
            user.setNickName("DJ" + MessageVerifyUtils.getNewCode());
        }
        //如果是商家 激活状态：未激活
        if (AuthConstant.BUSINESS.equals(userDTO.getUserRole())) {
            user.setUserStatus(DictConstant.NOT_ACTIVATE);
        }
        getBaseMapper().insert(user);

        //新增用户角色表用户以及角色的ID
        userRoleService.save(new UserRole().toBuilder().roleId(userDTO.getUserRole()).userId(user.getId()).build());
        // 发送消息
        JSONObject message = new JSONObject();
        message.put("userId", user.getId());
        message.put("userRole", userDTO.getUserRole());
        message.put("userStatus", user.getUserStatus());
        message.put("email", user.getUserEmail());
        rabbitTemplate.convertAndSend("emailExchange", "emailQueue", message.toJSONString());
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

    /**
     * 普通用户登录
     * @param userName 用户名
     * @param userPwd 密码
     * @return  UserDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public UserTokenDTO findUserTokenByNameAndPwd(String userName, String userPwd) throws Exception, BusinessException {
        //账号 手机号 邮箱登录
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.nested(i ->i.eq("user_name", userName)
                .or().eq("user_phone", userName)
                .or().eq("user_email", userName));
        User user = this.getOne(queryWrapper);
        //有效性校验
        if (StringUtils.isEmpty(user)) {
            throw new BusinessException("该用户不存在");
        }
        if (!PasswordSecurityUtil.checkPassword(userPwd, user.getUserPwd(), user.getSalt())) {
            throw new BusinessException("用户名或密码错误");
        }
        UserDTO userDTO = DozerUtil.map(user, UserDTO.class);
        //查询用户已关联角色 角色的判断：是否为普通用户
        UserRole userRole = userRoleService.getOne(new QueryWrapper<UserRole>().eq("user_id", userDTO.getUserId()));
        if (!AuthConstant.GENERAL_USER.equals(userRole.getRoleId())) {
            throw new BusinessException("角色不匹配");
        }
        //生成token uuID唯一标识 存redis 22天失效
        String token = UUID.randomUUID().toString().replace("-", "");
        redisApi.set(RedisConstant.USER_TOKEN + token, userDTO, 22 * 24 * 60 * 60);
        //将需要的token信息存入UserTokenDTO中
        UserTokenDTO userTokenDTO = new UserTokenDTO().toBuilder().userName(userName).nickName(userDTO.getNickName()).token(token).build();
        //最后登录时间
        lastLoginTimeService.save(new LastLoginTime().toBuilder().userId(userDTO.getUserId()).lastLoginTime(LocalDateTime.now()).build());
        return userTokenDTO;
    }

    /**
     * 退出登录
     * @param TOKEN token
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public void deleteToken(String TOKEN) throws Exception, BusinessException {
        redisApi.del(RedisConstant.USER_TOKEN + TOKEN);
    }

    /**
     * 修改普通用户
     * @param userDTO userDTO
     * @return  UserTokenDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public UserTokenDTO updateGeneralUser(UserDTO userDTO, byte[] file) throws Exception, BusinessException {
        this.updateById(DozerUtil.map(userDTO, User.class));
        User user = getById(userDTO.getUserId());
        redisApi.set(RedisConstant.USER_TOKEN + userDTO.getToken(), DozerUtil.map(user, UserDTO.class), 22 * 24 * 60 * 60);
        //将需要的token信息存入UserTokenDTO中
        UserTokenDTO userTokenDTO = new UserTokenDTO().toBuilder().userId(user.getId()).userName(user.getUserName()).nickName(user.getNickName()).token(userDTO.getToken()).build();
        //七牛云删除之前的以及上传新的
        if (!StringUtils.isEmpty(file)) {
            QiniuUtils.delFile(userDTO.getRemoveImg());
            InputStream inputStream = new ByteArrayInputStream(file);
            QiniuUtils.uploadByInputStream(inputStream, userDTO.getUserImg());
        }
        return userTokenDTO;
    }


    /*===========================================================收货地址==============================================================*/

    /**
     * 收货地址展示
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return AreaDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public List<UserAddressDTO> findAddressAll(String TOKEN) throws Exception, BusinessException {
        //得到当前登录用户
        UserDTO userDTO = redisApi.get(RedisConstant.USER_TOKEN + TOKEN);
        return DozerUtil.mapList(userAddressService.findAddressAll(userDTO.getUserId()), UserAddressDTO.class);
    }

    /**
     * 三级联动 根据父级id查数据
     * @param parentId 父级id
     * @return AreaDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public List<AreaDTO> getAreaByParentId(Integer parentId) throws Exception, BusinessException {
        return DozerUtil.mapList(areaService.list(new QueryWrapper<AreaEntity>().eq("area_parent_id", parentId)), AreaDTO.class);
    }

    /**
     * 新增收货地址
     * @param userAddressDTO 收货地址 dto
     * @param TOKEN 令牌密钥 用户唯一标识
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public void newShippingAddress(UserAddressDTO userAddressDTO, String TOKEN) throws Exception, BusinessException {
        //得到当前登录用户
        UserDTO userDTO = redisApi.get(RedisConstant.USER_TOKEN + TOKEN);
        userAddressDTO.setUserId(userDTO.getUserId());
        userAddressDTO.setIsDel(DictConstant.NOT_DEL);
        userAddressService.save(DozerUtil.map(userAddressDTO, UserAddressEntity.class));
    }

    /**
     * 根据id查
     * @param addressId 地址id
     * @return AreaDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public UserAddressDTO findAddressById(Integer addressId) throws Exception, BusinessException {
        return DozerUtil.map(userAddressService.findAddressById(addressId), UserAddressDTO.class);
    }

    /**
     * 查全国地区全部数据
     * @return AreaDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public List<AreaDTO> findAreaAll(String isDel) throws Exception, BusinessException {
        List<AreaDTO> areaList = redisApi.getHashValues(isDel);
        if (StringUtils.isEmpty(areaList) || AuthConstant.ZERO.equals(areaList.size())) {
            areaList = DozerUtil.mapList(areaService.list(new QueryWrapper<AreaEntity>().eq("is_del", isDel)), AreaDTO.class);
            areaList.forEach(area -> redisApi.pushHash(area.getIsDel(), String.valueOf(area.getId()), area));
        }
        return areaList;
    }

    /**
     * 修改收货地址
     * @param userAddressDTO 收货地址 dto
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public void updateAddressById(UserAddressDTO userAddressDTO) throws Exception, BusinessException {
        userAddressService.updateById(DozerUtil.map(userAddressDTO, UserAddressEntity.class));
    }

    /**
     * 删除
     * @param id 地址id
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public void removeAddressById(Integer id) throws Exception, BusinessException {
        UserAddressEntity addressEntity = userAddressService.getById(id);
        addressEntity.setIsDel(DictConstant.HAVE_DEL);
        userAddressService.updateById(addressEntity);
    }

    /*========================================================购物车==============================================================*/
    /**
     * 添加购物车
     * @param shoppingCartDTO shoppingCartDTO
     * @param TOKEN 令牌密钥 用户唯一标识
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public Integer addToShoppingCart(ShoppingCartDTO shoppingCartDTO, String TOKEN, Integer buyNow) throws Exception, BusinessException {
        SkuDTO sku = skuApi.findSkuBySkuId(shoppingCartDTO.getSkuId());
        if (AuthConstant.ZERO.equals(sku.getSkuCount()) || AuthConstant.ZERO > sku.getSkuCount()) {
            throw new BusinessException("库存不足给您带来不便，请选择库存充足得商品");
        }
        //得到当前登录用户 默认未选中
        UserDTO userDTO = redisApi.get(RedisConstant.USER_TOKEN + TOKEN);
        shoppingCartDTO.setUserId(userDTO.getUserId());
        shoppingCartDTO.setChecked(ProductConstant.NOT_CHECKED);
        //如果状态不为空 并且为2：立即购买
        if (null != buyNow && AuthConstant.BUY_NOW.equals(buyNow)) {
            shoppingCartDTO.setChecked(ProductConstant.HAVE_CHECKED_BUY_NOW);
        }
        //查到购物车所有数据 判断购物车所有数据是否有跟skuId和用户id相同的数据以及是否为选中状态为 立即购买做准备 直接修改购买数量 如果购买数量大于200 直接返回购买最大额
        List<ShoppingCartEntity> shoppingCartEntityList = shoppingCartService.list();
        for (ShoppingCartEntity shoppingCartEntity : shoppingCartEntityList) {
            if (shoppingCartEntity.getSkuId().equals(shoppingCartDTO.getSkuId()) && shoppingCartEntity.getUserId().equals(userDTO.getUserId())
                    && ProductConstant.NOT_CHECKED.equals(shoppingCartDTO.getChecked())) {
                shoppingCartEntity.setQuantity(Math.min(shoppingCartEntity.getQuantity() + shoppingCartDTO.getQuantity(), ProductConstant.MAX_QUANTITY));
                shoppingCartService.updateById(shoppingCartEntity);
                break;
            }
        }
        ShoppingCartEntity cartEntity = DozerUtil.map(shoppingCartDTO, ShoppingCartEntity.class);
        shoppingCartService.save(cartEntity);
        return cartEntity.getId();
    }

    /**
     * 批量新增购物车
     * @param cartList 购物车数据集合
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public void saveCartBatch(List<ShoppingCartDTO> cartList) throws Exception, BusinessException {
        //根据用户id得到数据
        List<ShoppingCartEntity> shoppingCartEntityList = shoppingCartService.list(new QueryWrapper<ShoppingCartEntity>().eq("user_id", cartList.get(0).getUserId()));
        //新增 修改集合
        List<ShoppingCartEntity> addList = new ArrayList<>();
        List<ShoppingCartEntity> updateList = new ArrayList<>();
        cartList.forEach(shoppingCartDTO ->{
            for (ShoppingCartEntity shoppingCartEntity : shoppingCartEntityList) {
                if (shoppingCartEntity.getSkuId().equals(shoppingCartDTO.getSkuId()) && shoppingCartEntity.getUserId().equals(shoppingCartDTO.getUserId())) {
                    shoppingCartEntity.setQuantity(Math.min(shoppingCartEntity.getQuantity() + shoppingCartDTO.getQuantity(), ProductConstant.MAX_QUANTITY));
                    updateList.add(shoppingCartEntity);
                    return;
                }
            }
            addList.add(DozerUtil.map(shoppingCartDTO, ShoppingCartEntity.class));
        });
        if (OrderConstant.ZERO < addList.size()) {
            shoppingCartService.saveBatch(addList);
        }
        if (OrderConstant.ZERO < updateList.size()) {
            shoppingCartService.saveBatch(updateList);
        }
    }

    /**
     * 获取每日用户登录量
     * @param january 一月
     * @return List<Statement>
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    @Override
    public List<Statement> getUserLoginNum(Integer january) throws Exception, BusinessException {
        return lastLoginTimeService.getUserLoginNum(january);
    }

}
