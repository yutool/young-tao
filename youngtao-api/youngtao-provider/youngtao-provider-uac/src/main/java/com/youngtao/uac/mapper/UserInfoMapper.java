package com.youngtao.uac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youngtao.uac.model.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 新增数据
     *
     * @param userInfo 实例对象
     * @return 影响行数
     */
    int insert0(UserInfo userInfo);

    UserInfo login(@Param("account") String account, @Param("password") String password);

    UserInfo selectByEmail(String email);

    int resetPassword(@Param("email") String email, @Param("password") String password);
}