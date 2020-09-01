package priv.wmc.main.config.mybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import priv.wmc.main.base.enums.EnumDefine;

/**
 * mybatis enum serialize and deserialize handler
 *
 * @author Wang Mincong
 * @date 2020-07-28 17:36:26
 */
@MappedTypes(EnumDefine.class)
@MappedJdbcTypes(value = JdbcType.TINYINT, includeNullJdbcType = true)
public class EnumHandler<T extends Enum<T> & EnumDefine> extends BaseTypeHandler<T> {

    private final Class<T> type;

    public EnumHandler(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType)
        throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);

        return rs.wasNull() ? null : getByValue(this.type.getEnumConstants(), code);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return rs.wasNull() ? null : getByValue(this.type.getEnumConstants(), code);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return cs.wasNull() ? null : getByValue(this.type.getEnumConstants(), code);
    }

    public T getByValue(T[] ts, int value) {
        for (T t : ts) {
            if (t.getValue() == value) {
                return t;
            }
        }
        return null;
    }
}