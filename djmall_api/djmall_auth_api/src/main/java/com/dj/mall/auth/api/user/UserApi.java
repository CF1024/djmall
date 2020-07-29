/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_auth_api
 * 类名：UserApi
 * 版权所有(C), 2020. 所有权利保留
 */

package com.dj.mall.auth.api.user;

import com.dj.mall.auth.dto.address.AreaDTO;
import com.dj.mall.auth.dto.address.UserAddressDTO;
import com.dj.mall.auth.dto.cart.ShoppingCartDTO;
import com.dj.mall.auth.dto.resource.ResourceDTO;
import com.dj.mall.auth.dto.role.RoleResourceDTO;
import com.dj.mall.auth.dto.user.UserDTO;
import com.dj.mall.auth.dto.user.UserTokenDTO;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.PageResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengf
 * @date 2020/6/3 14:59
 * 用户api接口
 */
public interface UserApi {
    /**
     * 用户登录
     * @param userName
     * @param userPwd
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    UserDTO findUserByNameAndPwd(String userName, String userPwd) throws Exception, BusinessException;

    /**
     * 去重
     * @param userDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    Boolean deDuplicate(UserDTO userDTO) throws Exception, BusinessException;

    /**
     * 新增用户
     * @param userDTO
     * @throws Exception
     * @throws BusinessException
     */
    void addUser(UserDTO userDTO) throws Exception, BusinessException;
    /**
     * 修改激活状态 邮箱激活
     * @param id
     * @throws Exception
     * @throws BusinessException
     */
    void updateUserStatus(Integer id) throws Exception, BusinessException;

    /**
     * 展示用户 分页 模糊查 查询
     * @param userDTO
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    PageResult findAll(UserDTO userDTO) throws Exception, BusinessException;

    /**
     * 根局id查用户信息
     * @param userId
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    UserDTO findUserById(Integer userId) throws Exception, BusinessException;

    /**
     * 修改
     * @param userDTO
     * @throws Exception
     * @throws BusinessException
     */
    void updateUser(UserDTO userDTO) throws Exception, BusinessException;

    /**
     * 重置密码
     * @param userDTO
     * @throws Exception
     * @throws BusinessException
     */
    void resetPwd(UserDTO userDTO) throws Exception, BusinessException;

    /**
     * 强制修改密码 根据账号查
     * @param userName
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    UserDTO findUserByName(String userName) throws Exception, BusinessException;

    /**
     * 强制修改密码
     * @param userDTO
     * @throws Exception
     * @throws BusinessException
     */
    void forceUpdatePwd(UserDTO userDTO) throws Exception, BusinessException;

    /**
     * 批量删除
     * @param ids
     * @param isDel
     * @throws Exception
     * @throws BusinessException
     */
    void removeUser(ArrayList<Integer> ids, String isDel) throws Exception, BusinessException;

    /**
     * 获取验证码
     * @param userPhone
     * @throws Exception
     * @throws BusinessException
     */
    void sendCode(String userPhone) throws Exception, BusinessException;

    /**
     * 根据手机号验证码 查用户信息
     * @param userPhone 手机号
     * @param verifyCode 验证码
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    UserDTO findUserByPhoneAndCode(String userPhone, String verifyCode) throws Exception, BusinessException;

    /**
     * 根据手机号更改密码
     * @param userDTO 用户dto对象
     * @throws Exception
     * @throws BusinessException
     */
    void updatePwdByPhone(UserDTO userDTO) throws Exception, BusinessException;


/*========================================================普通用户==============================================================*/


    /**
     * 普通用户登录
     * @param userName 用户名
     * @param userPwd 密码
     * @return  UserTokenDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    UserTokenDTO findUserTokenByNameAndPwd(String userName, String userPwd) throws Exception, BusinessException;

    /**
     * 退出登录
     * @param TOKEN token
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    void deleteToken(String TOKEN) throws Exception, BusinessException;

    /**
     * 修改普通用户
     * @param userDTO userDTO
     * @return  UserTokenDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    UserTokenDTO updateGeneralUser(UserDTO userDTO, byte[] file) throws Exception, BusinessException;


    /*===========================================================收货地址==============================================================*/

    /**
     * 收货地址展示
     * @param TOKEN 令牌密钥 用户唯一标识
     * @return AreaDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    List<UserAddressDTO> findAddressAll(String TOKEN) throws Exception, BusinessException;

    /**
     * 三级联动 根据父级id查数据
     * @param parentId 父级id
     * @return AreaDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    List<AreaDTO> getAreaByParentId(Integer parentId) throws Exception, BusinessException;

    /**
     * 新增收货地址
     * @param userAddressDTO 收货地址 dto
     * @param TOKEN 令牌密钥 用户唯一标识
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    void newShippingAddress(UserAddressDTO userAddressDTO, String TOKEN) throws Exception, BusinessException;

    /**
     * 根据id查
     * @param id 地址id
     * @return UserAddressDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    UserAddressDTO findAddressById(Integer id) throws Exception, BusinessException;

    /**
     * 查全部数据
     * @return AreaDTO
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    List<AreaDTO> findAreaAll(String isDel) throws Exception, BusinessException;

    /**
     * 修改收货地址
     * @param userAddressDTO 收货地址 dto
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    void updateAddressById(UserAddressDTO userAddressDTO) throws Exception, BusinessException;

    /**
     * 删除
     * @param id 地址id
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    void removeAddressById(Integer id) throws Exception, BusinessException;

    /*========================================================购物车==============================================================*/


    /**
     * 添加购物车
     * @param shoppingCartDTO shoppingCartDTO
     * @param TOKEN 令牌密钥 用户唯一标识
     * @throws Exception 异常
     * @throws BusinessException 自定义异常
     */
    void addToShoppingCart(ShoppingCartDTO shoppingCartDTO, String TOKEN) throws Exception, BusinessException;


}
