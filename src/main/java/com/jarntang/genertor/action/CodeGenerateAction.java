package com.jarntang.genertor.action;


import static com.jarntang.genertor.util.DialogUtils.showOnActiveScreen;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.jarntang.genertor.Env;
import com.jarntang.genertor.ui.GeneratorDialog;
import java.awt.Dimension;

/**
 * code generate action.
 *
 * @author Qinmu
 * @since 16/10/20 15:12
 */
public class CodeGenerateAction extends AnAction {

  @Override
  public void actionPerformed(AnActionEvent e) {
    Env.project = e.getProject();
    GeneratorDialog dialog = new GeneratorDialog();
    dialog.setPreferredSize(new Dimension(450, 350));
    dialog.pack();

    showOnActiveScreen(dialog);
    dialog.setResizable(false);
    dialog.setVisible(true);
  }

}
