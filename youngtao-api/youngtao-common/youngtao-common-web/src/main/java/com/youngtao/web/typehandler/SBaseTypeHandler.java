package com.youngtao.web.typehandler;

import com.youngtao.core.type.SBaseType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SBaseTypeHandler<E extends SBaseType> extends BaseTypeHandler<E> {

	private Class<E> type;

	public SBaseTypeHandler(Class<E> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.type = type;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.getCode());
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String code = rs.getString(columnName);
		if (rs.wasNull()) {
			return null;
		}
		return SBaseType.valueOf(type, code);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String code = rs.getString(columnIndex);
		if (rs.wasNull()) {
			return null;
		}
		return SBaseType.valueOf(type, code);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String code = cs.getString(columnIndex);
		if (cs.wasNull()) {
			return null;
		}
		return SBaseType.valueOf(type, code);
	}

}
