package com.jarntang.genertor.ui;

import com.jarntang.genertor.core.model.TableInfo;
import com.jarntang.genertor.util.DbUtils;
import java.util.List;
import java.util.stream.Collectors;

/**
 * generate model.
 *
 * @author qinmu
 * @since 2017/07/20 16:24
 */
public class GenerateModel {

  public List<String> listTables(String address, String username, String password) {
    List<TableInfo> tableList = DbUtils.getAllTableInfo(address, username, password);
    return tableList.stream().map(TableInfo::getName).collect(Collectors.toList());
  }


}
