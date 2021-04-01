package com.youngtao.uac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youngtao.uac.model.domain.UserAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
@Mapper
public interface UserAuthMapper extends BaseMapper<UserAuth> {

    /**
     * 新增数据
     *
     * @param userAuth 实例对象
     * @return 影响行数
     */
    int insert0(UserAuth userAuth);

    UserAuth login(@Param("account") String account, @Param("password") String password);

}