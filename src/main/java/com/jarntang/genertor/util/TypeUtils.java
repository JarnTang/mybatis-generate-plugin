package com.jarntang.genertor.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.JDBCType;
import java.sql.Time;

/**
 * java type utils.
 *
 * @author qinmu
 * @since 2017/07/25 15:53
 */
public class TypeUtils {

  private TypeUtils() {
  }

  /**
   * convert jdbc type to java type.
   *
   * @param jdbcType jdbcType
   * @return java type
   */
  public static Class sqlType2JavaType(JDBCType jdbcType) {
    switch (jdbcType) {
      case BIT:
        return Boolean.class;
      case TINYINT:
        return Boolean.class;
      case SMALLINT:
        return Short.class;
      case INTEGER:
        return Integer.class;
      case BIGINT:
        return Long.class;
      case TIMESTAMP:
        return Long.class;
      case TIMESTAMP_WITH_TIMEZONE:
        return Long.class;
      case FLOAT:
        return Float.class;
      case DECIMAL:
        return BigDecimal.class;
      case NUMERIC:
        return BigDecimal.class;
      case REAL:
        return Double.class;
      case DOUBLE:
        return Double.class;
      case VARCHAR:
        return String.class;
      case CHAR:
        return String.class;
      case NCHAR:
        return String.class;
      case NVARCHAR:
        return String.class;
      case LONGVARCHAR:
        return String.class;
      case LONGNVARCHAR:
        return String.class;
      case BINARY:
        return Byte[].class;
      case VARBINARY:
        return Byte[].class;
      case LONGVARBINARY:
        return Byte[].class;
      case DATE:
        return Date.class;
      case TIME:
        return Time.class;
      default:
        return Void.class;
    }
  }

}
