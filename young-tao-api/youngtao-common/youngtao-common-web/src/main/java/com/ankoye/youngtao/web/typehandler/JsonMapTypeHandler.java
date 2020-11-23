package com.ankoye.youngtao.web.typehandler;

import com.ankoye.youngtao.core.lang.JsonMap;
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
@MappedTypes(JsonMap.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JsonMapTypeHandler extends BaseTypeHandler<JsonMap> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JsonMap parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toJson());
    }

    @Override
    public JsonMap getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return JsonMap.fromJson(json);
    }

    @Override
    public JsonMap getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return JsonMap.fromJson(json);
    }

    @Override
    public JsonMap getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return JsonMap.fromJson(json);
    }
}
