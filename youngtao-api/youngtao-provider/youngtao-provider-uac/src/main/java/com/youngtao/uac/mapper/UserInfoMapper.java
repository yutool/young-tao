package com.youngtao.uac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youngtao.uac.model.domain.UserInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoDO> {

    /**
     * 新增数据
     *
     * @param userInfoDO 实例对象
     * @return 影响行数
     */
    int insert0(UserInfoDO userInfoDO);

    UserInfoDO login(@Param("account") String account, @Param("password") String password);

    UserInfoDO selectByEmail(String email);

    int resetPassword(@Param("email") String email, @Param("password") String password);
}