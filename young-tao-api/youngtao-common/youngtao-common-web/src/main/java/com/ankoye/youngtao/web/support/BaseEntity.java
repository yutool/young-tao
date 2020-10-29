package com.ankoye.youngtao.web.support;


import java.io.Serializable;
import java.util.Date;

/**
 * @author ankoye@qq.com
 */
public abstract class BaseEntity implements Serializable {
    /** 自增ID */
    protected Integer id;

    /** 创建时间 */
    protected Date createTime;

    /** 修改时间 */
    protected Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
