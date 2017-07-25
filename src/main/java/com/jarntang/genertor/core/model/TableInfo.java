package com.jarntang.genertor.core.model;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * table info.
 *
 * @author qinmu
 * @since 2017/07/07 17:37
 */
@Data
public class TableInfo {

  String name;
  String comment;
  List<ColumnInfo> columns = new ArrayList<>();

  public void addColumn(ColumnInfo column) {
    columns.add(column);
  }

  @Getter
  @AllArgsConstructor
  @ToString
  public static class ColumnInfo {

    private String name;
    private JDBCType type;
    private String comment;
  }
}
