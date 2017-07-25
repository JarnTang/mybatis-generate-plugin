package com.jarntang.genertor.util;

import com.intellij.openapi.wm.IdeFocusManager;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Window;

/**
 * dialog utils
 *
 * @author Qinmu
 * @since 2016/10/27 18:34
 */
public class DialogUtils {

  public static void showOnActiveScreen(Window frame) {
    Component owner = IdeFocusManager.getGlobalInstance().getFocusOwner();
    Rectangle monitorBounds = null;
    if (owner == null) {
      monitorBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
          .getDefaultConfiguration().getBounds();

    } else {
      monitorBounds = owner.getGraphicsConfiguration().getBounds();
    }
    final int x = monitorBounds.x + (monitorBounds.width - frame.getWidth()) / 2;
    final int y = monitorBounds.y + (monitorBounds.height - frame.getHeight()) / 2;
    frame.setLocation(x, y);
  }
}
