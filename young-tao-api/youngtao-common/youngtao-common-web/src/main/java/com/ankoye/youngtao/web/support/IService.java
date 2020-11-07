package com.ankoye.youngtao.web.support;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 通用接口
 *
 * @param <T> the type parameter
 *
 * @author ankoye@qq.com
 */
public interface IService<T> {
	/**
	 * 根据实体中的属性值进行查询, 查询条件使用等号
	 *
	 * @param record the record
	 *
	 * @return the list
	 */
	List<T> selectList(T record);

	/**
	 * 根据构造条件查询
	 * @param wrapper the wrapper
	 * @return the list
	 */
	List<T> selectList(Wrapper<T> wrapper);

	/**
	 * 根据主键字段进行查询, 方法参数必须包含完整的主键属性, 查询条件使用等号
	 *
	 * @param id the key
	 *
	 * @return the t
	 */
	T selectById(Object id);

	/**
	 * 查询全部结果, select(null)方法能达到同样的效果
	 *
	 * @return the list
	 */
	List<T> selectAll();

	/**
	 * 根据实体中的属性进行查询, 只能有一个返回值, 有多个结果是抛出异常, 查询条件使用等号
	 *
	 * @param wrapper the wrapper
	 *
	 * @return the t
	 */
	T selectOne(Wrapper<T> wrapper);

	/**
	 * 根据实体中的属性查询总数, 查询条件使用等号
	 *
	 * @param wrapper the wrapper
	 *
	 * @return the int
	 */
	Integer selectCount(Wrapper<T> wrapper);

	/**
	 * 根据实体属性和RowBounds进行分页查询
	 *
	 * @param record    the record
	 * @param page the row bounds
	 * @param size
	 *
	 * @return the list
	 */
	List<T> selectByRowBounds(T record, Integer page, Integer size);

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
	 * 根据wrapper更新值
	 *
	 * @param record the record
	 * @param wrapper the wrapper
	 *
	 * @return the int
	 */
	Boolean update(T record, Wrapper<T> wrapper);

	/**
	 * 根据实体属性作为条件进行删除, 查询条件使用等号
	 *
	 * @param record the record
	 *
	 * @return the bool
	 */
	Boolean delete(T record);

	/**
	 * 根据实体属性作为条件进行删除, 查询条件使用等号
	 *
	 * @param wrapper the wrapper
	 *
	 * @return the bool
	 */
	Boolean delete(Wrapper<T> wrapper);

	/**
	 * 少量批量删除  @param list the list
	 *
	 * @param list the list
	 *
	 * @return the bool
	 */
	@Transactional(rollbackFor = Exception.class)
	Boolean batchDelete(List<T> list);

	/**
	 * 根据主键字段进行删除, 方法参数必须包含完整的主键属性
	 *
	 * @param id the key
	 *
	 * @return the bool
	 */
	Boolean deleteById(Object id);

	/**
	 * 根据Example条件删除数据
	 *
	 * @param ids the id list
	 *
	 * @return the int
	 */
	Boolean batchDeleteById(List<Object> ids);

}
