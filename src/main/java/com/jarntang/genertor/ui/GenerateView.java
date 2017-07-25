package com.jarntang.genertor.ui;

import com.jarntang.genertor.DataStorage.Configuration;
import java.util.List;

/**
 * code generate view.
 *
 * @author qinmu
 * @since 2017/07/20 14:24
 */
public interface GenerateView {

  void updateInputText(Configuration configuration);

  Configuration getDatabaseInput();

  void showTableSelectView(List<String> tables);

  boolean validateConfiguration();

}
