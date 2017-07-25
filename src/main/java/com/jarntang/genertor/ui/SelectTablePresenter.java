package com.jarntang.genertor.ui;

import static com.jarntang.genertor.ui.SelectTablePresenter.SelectEnum.ALL_OVERRIDE;
import static com.jarntang.genertor.ui.SelectTablePresenter.SelectEnum.ALL_SKIP;
import static com.jarntang.genertor.ui.SelectTablePresenter.SelectEnum.OVERRIDE;

import com.jarntang.genertor.core.model.CodeInfo;
import java.util.Set;

/**
 * select table presenter.
 *
 * @author qinmu
 * @since 2017/07/20 16:47
 */
public class SelectTablePresenter {

  private SelectTableView view;
  private SelectTableModel model;

  public SelectTablePresenter(SelectTableView view) {
    this.view = view;
    this.model = new SelectTableModel();
  }

  void generateCode() {
    SelectEnum select = OVERRIDE;

    Set<CodeInfo> codes = model.genCode(view.getUserInputConfiguration(), view.getSelectTables());
    for (CodeInfo code : codes) {

      if (model.exist(code.getFileName()) && select != ALL_OVERRIDE && select != ALL_SKIP) {
        int input = view.showFileExistConfirmDialog(code.getFileName());
        select = SelectEnum.from(input);
      }

      switch (select) {
        case OVERRIDE:
        case ALL_OVERRIDE:
          model.writeToFile(code);
          break;

        case SKIP:
        case ALL_SKIP:
        default:
      }
    }
    view.dispose();
  }

  enum SelectEnum {
    ALL_OVERRIDE(0), ALL_SKIP(1), SKIP(2), OVERRIDE(3);

    int value;

    SelectEnum(int value) {
      this.value = value;
    }

    static SelectEnum from(int input) {
      for (SelectEnum selectEnum : values()) {
        if (selectEnum.value == input) {
          return selectEnum;
        }
      }
      return SelectEnum.ALL_SKIP;
    }

  }

}
