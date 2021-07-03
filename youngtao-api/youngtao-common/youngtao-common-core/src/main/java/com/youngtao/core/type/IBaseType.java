package com.youngtao.core.type;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public abstract class IBaseType implements Serializable {

	private static final long serialVersionUID = -7072312788070724374L;

	private static final Cache<Class<? extends IBaseType>, List<? extends IBaseType>> cache =
			Caffeine.newBuilder()
					.expireAfterAccess(1, TimeUnit.MINUTES)
					.initialCapacity(60)
					.maximumSize(100)
					.build();

	/** 存入数据库的值 */
	private Integer code;

	private String desc;

	protected IBaseType() {
	}

	protected IBaseType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@SuppressWarnings("unchecked")
	public static <T extends IBaseType> List<T> getAll(Class<T> clazz) {
		List<T> types = (List<T>) cache.getIfPresent(clazz);
		if (types == null) synchronized (clazz) {
			if (types == null) {
				types = new ArrayList<>();
				try {
					Field[] fields = clazz.getFields();
					for (Field field : fields) {
						if (field.getType().isAssignableFrom(clazz)) {
							types.add((T) field.get(null));
						}
					}
					cache.put(clazz, types);
				} catch (Exception ignored) {
					// do nothing
				}
			}
		}
		return types;
	}

	public static <T extends IBaseType> T valueOf(Class<T> clazz, Integer code) {
		List<T> list = getAll(clazz);
		for (T t: list) {
			if (t.getCode().equals(code)) {
				return t;
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IBaseType type = (IBaseType) obj;
		if (!Objects.equals(code, type.code))
			return false;
		return true;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
