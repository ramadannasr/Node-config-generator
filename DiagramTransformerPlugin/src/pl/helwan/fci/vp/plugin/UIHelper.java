package pl.helwan.fci.vp.plugin;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.ViewManager;

import javax.swing.*;
import java.awt.*;

public class UIHelper {

    private UIHelper() {

    }

    public static void showPopupMessage(String message) {
        ViewManager viewManager = ApplicationManager.instance().getViewManager();
        Component parentFrame = viewManager.getRootFrame();

        viewManager.showMessageDialog(parentFrame, message);
    }

    public static void logMessage(String message) {
        ApplicationManager.instance().getViewManager().showMessage(message);
    }

    public static String selectFile(int mode, String promptMsg) {
        ViewManager viewManager = ApplicationManager.instance().getViewManager();
        Component parentFrame = viewManager.getRootFrame();

        JFileChooser fileChooser = ApplicationManager.instance().getViewManager().createJFileChooser();
        fileChooser.setDialogTitle(promptMsg);
        fileChooser.setFileSelectionMode(mode);
        fileChooser.setAcceptAllFileFilterUsed(false);
        if (fileChooser.showSaveDialog(parentFrame) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }

        return null;
    }

}
