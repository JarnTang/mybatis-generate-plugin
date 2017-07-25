package com.jarntang.genertor.ui;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;
import com.jarntang.genertor.DataStorage;
import com.jarntang.genertor.DataStorage.Configuration;
import com.jarntang.genertor.Env;
import com.jarntang.genertor.util.DialogUtils;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GeneratorDialog extends JDialog implements GenerateView {

  private JPanel contentPane;
  private JButton buttonOK;
  private JButton buttonCancel;
  private JTabbedPane tabbedPanel;
  private JTextField ipTextField;
  private JTextField userNameTextField;
  private JTextField passwordTextField;
  private JTextField portTextField;
  private JTextField dbTextField;

  private TextFieldWithBrowseButton outputDirTextField;

  private GeneratePresenter presenter;

  private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorDialog.class);
  private final FileChooserDescriptor chooseFolderOnlyDescriptor = FileChooserDescriptorFactory
      .createSingleFolderDescriptor();


  public GeneratorDialog() {

    setContentPane(contentPane);
    setModal(true);
    getRootPane().setDefaultButton(buttonOK);

    buttonOK.addActionListener(e -> onOK());

    buttonCancel.addActionListener(e -> onCancel());

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        onCancel();
      }
    });

    contentPane
        .registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    outputDirTextField.addActionListener(e -> {
      chooseFolderOnlyDescriptor.setTitle("Select Path");
      chooseFolderOnlyDescriptor.setDescription(
          "Select Path To Generate, generally we choose the biz path to generate");
      VirtualFile file = FileChooser.chooseFile(chooseFolderOnlyDescriptor, Env.project, null);
      if (file != null) {
        outputDirTextField.setText(file.getPath());
      }
    });
    presenter = new GeneratePresenter(this);
  }

  private void onCancel() {
    dispose();
  }

  private void onOK() {
    presenter.selectTables();
  }

  public void showTableSelectView(List<String> tables) {
    dispose();
    SelectTableDialog dialog = new SelectTableDialog(tables, getDatabaseInput());
    dialog.pack();
    DialogUtils.showOnActiveScreen(dialog);
    dialog.setVisible(true);
  }

  public boolean validateConfiguration() {
    if (StringUtils.isEmpty(ipTextField.getText())) {
      setInputTextBorderRedColor(ipTextField);
      return false;
    }
    if (StringUtils.isEmpty(userNameTextField.getText())) {
      setInputTextBorderRedColor(userNameTextField);
      return false;
    }
    if (StringUtils.isEmpty(passwordTextField.getText())) {
      setInputTextBorderRedColor(passwordTextField);
      return false;
    }
    if (!StringUtils.isNumeric(portTextField.getText())) {
      setInputTextBorderRedColor(portTextField);
      return false;
    }
    if (StringUtils.isEmpty(dbTextField.getText())) {
      setInputTextBorderRedColor(dbTextField);
      return false;
    }
    if (StringUtils.isEmpty(outputDirTextField.getText())) {
      setInputTextBorderRedColor(outputDirTextField);
      return false;
    }
    return true;
  }

  public DataStorage.Configuration getDatabaseInput() {
    return Configuration.builder()
        .address(this.ipTextField.getText())
        .userName(this.userNameTextField.getText())
        .password(this.passwordTextField.getText())
        .port(this.portTextField.getText())
        .database(this.dbTextField.getText())
        .outputDir(outputDirTextField.getText()).build();
  }

  @Override
  public void updateInputText(Configuration configuration) {
    if(configuration == null){
      return;
    }
    setInputFieldText(ipTextField, configuration.getAddress());
    setInputFieldText(userNameTextField, configuration.getUserName());
    setInputFieldText(passwordTextField, configuration.getPassword());
    setInputFieldText(portTextField, configuration.getPort());
    setInputFieldText(dbTextField, configuration.getDatabase());
    setInputFieldText(outputDirTextField, configuration.getOutputDir());
  }

  private void setInputFieldText(JTextField fieldText, String text) {
    if (StringUtils.isNotEmpty(text)) {
      fieldText.setText(text);
    }
  }

  private void setInputFieldText(TextFieldWithBrowseButton fieldText, String text) {
    if (StringUtils.isNotEmpty(text)) {
      fieldText.setText(text);
    }
  }

  private void setInputTextBorderRedColor(JTextField field) {
    Border border = BorderFactory.createLineBorder(Color.RED, 1);
    field.setBorder(border);
  }

  private void setInputTextBorderRedColor(TextFieldWithBrowseButton field) {
    Border border = BorderFactory.createLineBorder(Color.RED, 1);
    field.setBorder(border);
  }

}
