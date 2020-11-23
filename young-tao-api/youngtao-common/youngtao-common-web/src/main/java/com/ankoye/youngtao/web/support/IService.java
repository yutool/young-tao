package com.ankoye.youngtao.web.support;

import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 通用接口
 * @author ankoye@qq.com
 */
public interface IService<T> {
	/**
	 * 根据主键字段进行查询, 方法参数必须包含完整的主键属性, 查询条件使用等号
	 *
	 * @param id the key
	 *
	 * @return the t
	 */
	T selectById(Serializable id);

	/**
	 * 根据主键字段进行查询, 方法参数必须包含完整的主键属性, 查询条件使用等号
	 *
	 * @param ids the key
	 *
	 * @return the t
	 */
	List<T> listByIds(Collection<? extends Serializable> ids);

	/**
	 * 保存一个实体, null的属性不会保存, 会使用数据库默认值  @param record the record
	 *
	 * @param record the record
	 *
	 * @return the bool
	 */
	Boolean save(T record);

	/**
	 * 批量保存
	 *
	 * @param list the list
	 *
	 * @return the int
	 */
	@Transactional(rollbackFor = Exception.class)
	Boolean batchSave(List<T> list);

	/**
	 * 根据主键更新属性不为null的值
	 *
	 * @param entity the entity
	 *
	 * @return the int
	 */
	Boolean updateById(T entity);

	/**
	 * 根据主键字段进行删除, 方法参数必须包含完整的主键属性
	 *
	 * @param id the key
	 *
	 * @return the bool
	 */
	Boolean deleteById(Serializable id);

	/**
	 * 根据Example条件删除数据
	 *
	 * @param ids the id list
	 *
	 * @return the int
	 */
	Boolean deleteByIds(Collection<? extends Serializable> ids);

}
