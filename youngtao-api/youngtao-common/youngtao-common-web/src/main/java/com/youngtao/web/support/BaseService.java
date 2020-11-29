package com.youngtao.web.support;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * The class Base service.
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
	public T selectById(Serializable id) {
		return mapper.selectById(id);
	}

	/**
	 * Select by key t.
	 *
	 * @param ids the key
	 *
	 * @return the t
	 */
	@Override
	public List<T> listByIds(Collection<? extends Serializable> ids) {
		return mapper.selectBatchIds(ids);
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
	 * Delete by key int.
	 *
	 * @param id the key
	 *
	 * @return the bool
	 */
	@Override
	public Boolean deleteById(Serializable id) {
		return mapper.deleteById(id) > 0;
	}

	/**
	 * Delete by example int.
	 *
	 * @param ids the id list
	 *
	 * @return the int
	 */
	@Override
	public Boolean deleteByIds(Collection<? extends Serializable> ids) {
		return mapper.deleteBatchIds(ids) > 0;
	}

}
