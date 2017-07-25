package com.jarntang.genertor.ui;

import com.jarntang.genertor.DataStorage.Configuration;
import com.jarntang.genertor.util.ConfigurationUtils;
import java.util.List;

/**
 * generate presenter.
 *
 * @author qinmu
 * @since 2017/07/19 18:16
 */
class GeneratePresenter {

  private GenerateView view;
  private GenerateModel model;

  GeneratePresenter(GenerateView view) {
    this.view = view;
    this.model = new GenerateModel();
    initConfiguration();
  }

  void initConfiguration() {
    view.updateInputText(ConfigurationUtils.loadConfiguration());
  }

  void selectTables() {
    boolean validated = view.validateConfiguration();
    if (validated) {
      Configuration input = view.getDatabaseInput();
      ConfigurationUtils.saveConfiguration(input);

      List<String> tables = model
          .listTables(input.getDbProtocolAddress(), input.getUserName(), input.getPassword());
      view.showTableSelectView(tables);
    }
  }

}
