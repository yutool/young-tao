package com.youngtao.web.support;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ankoye@qq.com
 */
public abstract class BaseEntity implements Serializable {
    /** 自增ID */
    @TableId(type = IdType.AUTO)
    protected Long id;

    /** 创建时间 */
    protected Date createTime;

    /** 修改时间 */
    protected Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
