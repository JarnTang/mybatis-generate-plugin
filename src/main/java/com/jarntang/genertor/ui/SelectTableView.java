package com.jarntang.genertor.ui;

import com.jarntang.genertor.DataStorage.Configuration;
import java.util.Set;

/**
 * select table view.
 *
 * @author qinmu
 * @since 2017/07/20 16:46
 */
public interface SelectTableView {

  void dispose();

  Set<String> getSelectTables();

  Configuration getUserInputConfiguration();

  int showFileExistConfirmDialog(String name);

}
