package com.ankoye.youngtao.web.support;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class Base service.
 *
 * @param <T> the type parameter
 *
 * @author ankoye@qq.com
 */
public abstract class BaseService<T extends Serializable> implements IService<T> {

	/**
	 * The Logger.
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * The Mapper.
	 */
	@Autowired
	protected BaseMapper<T> mapper;

	/**
	 * Gets mapper.
	 *
	 * @return the mapper
	 */
	public BaseMapper<T> getMapper() {
		return mapper;
	}

	/**
	 * Select by key t.
	 *
	 * @param id the key
	 *
	 * @return the t
	 */
	@Override
	public T selectById(Object id) {
		return mapper.selectById((Serializable) id);
	}

	/**
	 * Select one t.
	 *
	 * @param wrapper the wrapper
	 *
	 * @return the t
	 */
	@Override
	public T selectOne(Wrapper<T> wrapper) {
		return mapper.selectOne(wrapper);
	}

	/**
	 * Select list.
	 *
	 * @param record the record
	 *
	 * @return the list
	 */
	@Override
	public List<T> selectList(T record) {
		return mapper.selectList(Wrappers.query(record));
	}

	@Override
	public List<T> selectList(Wrapper<T> wrapper) {
		return mapper.selectList(wrapper);
	}

	/**
	 * Select all list.
	 *
	 * @return the list
	 */
	@Override
	public List<T> selectAll() {
		return mapper.selectList(null);
	}

	/**
	 * Select count int.
	 *
	 * @param wrapper the wrapper
	 *
	 * @return the int
	 */
	@Override
	public Integer selectCount(Wrapper<T> wrapper) {
		return mapper.selectCount(wrapper);
	}

	/**
	 * Select by row bounds list.
	 *
	 * @param record    the record
	 * @param page
	 * @param size
	 * @return the list
	 */
	@Override
	public List<T> selectByRowBounds(T record, Integer page, Integer size) {
		PageHelper.startPage(page, size);
		return mapper.selectList(Wrappers.query(record));
	}

	/**
	 * Save int.
	 *
	 * @param record the record
	 *
	 * @return the int
	 */
	@Override
	public Boolean save(T record) {
		return mapper.insert(record) > 0;
	}

	/**
	 * Batch save int.
	 *
	 * @param list the list
	 *
	 * @return the bool
	 */
	@Override
	public Boolean batchSave(List<T> list) {
		int count = 0;
		for (T record : list) {
			int res = mapper.insert(record);
			count += res;
		}
		return count == list.size();
	}

	/**
	 * Update int.
	 *
	 * @param entity the entity
	 *
	 * @return the bool
	 */
	@Override
	public Boolean updateById(T entity) {
		return mapper.updateById(entity) > 0;
	}

	/**
	 * 根据wrapper更新值
	 *
	 * @param record the record
	 * @param wrapper the wrapper
	 *
	 * @return the int
	 */
	@Override
	public Boolean update(T record, Wrapper<T> wrapper) {
		return mapper.update(record, wrapper) > 0;
	}

	/**
	 * Delete int.
	 *
	 * @param record the record
	 *
	 * @return the int
	 */
	@Override
	public Boolean delete(T record) {
		return mapper.delete(Wrappers.update(record)) > 0;
	}

	/**
	 * Delete int.
	 *
	 * @param wrapper the wrapper
	 *
	 * @return the int
	 */
	@Override
	public Boolean delete(Wrapper<T> wrapper) {
		return mapper.delete(wrapper) > 0;
	}

	/**
	 * Delete by key int.
	 *
	 * @param id the key
	 *
	 * @return the bool
	 */
	@Override
	public Boolean deleteById(Object id) {
		return mapper.deleteById((Serializable) id) > 0;
	}

	/**
	 * Batch delete int.
	 *
	 * @param list the list
	 *
	 * @return the bool
	 */
	@Override
	public Boolean batchDelete(List<T> list) {
		int count = 0;
		for (T record : list) {
			int res = mapper.delete(new QueryWrapper<>(record));
			count += res;
		}
		return count == list.size();
	}

	/**
	 * Delete by example int.
	 *
	 * @param ids the id list
	 *
	 * @return the int
	 */
	@Override
	public Boolean batchDeleteById(List<Object> ids) {
		List<Serializable> id = ids.stream().map(item -> (Serializable)item).collect(Collectors.toList());
		return mapper.deleteBatchIds(id) > 0;
	}

}
