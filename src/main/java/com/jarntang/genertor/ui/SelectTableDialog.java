package com.jarntang.genertor.ui;

import com.intellij.ui.table.JBTable;
import com.jarntang.genertor.DataStorage.Configuration;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

public class SelectTableDialog extends JDialog implements SelectTableView {

  private JPanel contentPane;
  private JButton okButton;
  private JPanel tablePanel;
  private CheckAbleTable checkAbleTable;
  private SelectTablePresenter presenter;
  private Configuration userInputConfiguration;

  SelectTableDialog(List<String> tableNames, Configuration userInputConfiguration) {
    setContentPane(contentPane);
    setModal(true);
    getRootPane().setDefaultButton(okButton);
    setPreferredSize(new Dimension(450, 350));

    okButton.addActionListener(e -> onOK());

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        onCancel();
      }
    });

    contentPane.registerKeyboardAction(
        e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    checkAbleTable = new CheckAbleTable(createJtableData(tableNames));
    JTable table = new JBTable(checkAbleTable);
    JScrollPane scrollPane = new JScrollPane(table);
    tablePanel.add(scrollPane, BorderLayout.CENTER);

    presenter = new SelectTablePresenter(this);
    this.userInputConfiguration = userInputConfiguration;
  }

  private void onOK() {
    presenter.generateCode();
  }

  @Override
  public int showFileExistConfirmDialog(String name) {
    Object[] options = {"全部覆盖", "全部跳过", "跳过", "覆盖"};
    return JOptionPane
        .showOptionDialog(this, name + " 文件存在，是否覆盖？", "文件已经存在",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
  }

  @Override
  public Set<String> getSelectTables() {
    Set<String> selectTableNames = new HashSet<>();
    int rowCount = checkAbleTable.getRowCount();
    for (int row = 0; row < rowCount; row++) {
      Boolean checked = (Boolean) checkAbleTable.getValueAt(row, 1);
      if (checked != null && checked) {
        selectTableNames.add((String) checkAbleTable.getValueAt(row, 0));
      }
    }
    return selectTableNames;
  }

  @Override
  public Configuration getUserInputConfiguration() {
    return this.userInputConfiguration;
  }

  private void onCancel() {
    dispose();
  }

  private static Object[][] createJtableData(List<String> tableNames) {
    Object[][] data = new Object[tableNames.size()][2];
    for (int index = 0; index < tableNames.size(); index++) {
      data[index][0] = tableNames.get(index);
      data[index][1] = Boolean.FALSE;
    }
    return data;
  }

}
