package com.jarntang.genertor.util;


import com.jarntang.genertor.core.model.TableInfo;
import com.jarntang.genertor.core.model.TableInfo.ColumnInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * database util
 *
 * @author Qinmu
 * @since 16/10/12 11:41
 */
public class DbUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(DbUtils.class);

  static {
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (Exception e) {
      LOGGER.error("load mysql driver error.", e);
    }
  }

  public static List<TableInfo> getAllTableInfo(String address, String userName, String password) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(address, userName, password);

      DatabaseMetaData metaData = connection.getMetaData();
      List<TableInfo> tables = new ArrayList<>();
      ResultSet tablesSet = metaData.getTables(null, "%", "%", new String[]{"TABLE"});
      while (tablesSet.next()) {

        TableInfo table = new TableInfo();
        String tableName = tablesSet.getString("TABLE_NAME");

        String comment = tablesSet.getString("REMARKS");
        ResultSet columnsSet = metaData.getColumns(null, null, tableName, "%");

        table.setComment(comment);
        table.setName(tableName);

        while (columnsSet.next()) {
          String dataType = columnsSet.getString("DATA_TYPE");
          String columnComment = columnsSet.getString("REMARKS");
          String columnName = columnsSet.getString("COLUMN_NAME");
          ColumnInfo column = new ColumnInfo(columnName, JDBCType.valueOf(Integer.parseInt(dataType)), columnComment);
          table.addColumn(column);
        }
        tables.add(table);
      }
      return tables;
    } catch (Exception e) {
      LOGGER.error("fetch table info error", e);
      return new ArrayList<>();
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (Exception e) {
        LOGGER.error("close connection error", e);
      }
    }
  }

  public static void main(String[] args) {
    List<TableInfo> tableInfo = getAllTableInfo("jdbc:mysql://10.13.33.23:3315/arctic", "test",
        "test123");
    System.out.println(tableInfo);
  }

}
