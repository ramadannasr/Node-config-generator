package pl.helwan.fci.vp.plugin.logging;

import pl.helwan.fci.logger.Logger;
import pl.helwan.fci.vp.plugin.UIHelper;

import java.io.PrintWriter;
import java.io.StringWriter;

public class VPLogger implements Logger {

    @Override
    public void logInfo(String msg) {
        UIHelper.logMessage(msg);
    }

    @Override
    public void logError(Exception e, String msg) {
        UIHelper.showPopupMessage(msg);
        PrintWriter pw = new PrintWriter(new StringWriter());
        e.printStackTrace(pw);
        UIHelper.logMessage(pw.toString());
    }

    @Override
    public void logError(String msg) {
        UIHelper.showPopupMessage(msg);
    }
}
