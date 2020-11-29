package com.youngtao.web.typehandler;

import com.youngtao.core.lang.JsonList;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ankoye@qq.com
 * @date 2020/11/22
 */
@MappedTypes(JsonList.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JsonListTypeHandler extends BaseTypeHandler<JsonList> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JsonList parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toJson());
    }

    @Override
    public JsonList getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return JsonList.fromJson(json);
    }

    @Override
    public JsonList getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return JsonList.fromJson(json);
    }

    @Override
    public JsonList getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return JsonList.fromJson(json);
    }
}
