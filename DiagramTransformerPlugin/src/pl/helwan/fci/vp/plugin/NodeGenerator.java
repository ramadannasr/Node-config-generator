package pl.helwan.fci.vp.plugin;

import com.vp.plugin.VPPlugin;
import com.vp.plugin.VPPluginInfo;
import pl.helwan.fci.vp.plugin.logging.VPLogger;
import pl.helwan.fci.logger.Logger;
import pl.helwan.fci.logger.CustomLoggerFactory;

public class NodeGenerator implements VPPlugin {

    private static Logger VP_LOGGER;

    @Override
    public void loaded(VPPluginInfo vpPluginInfo) {
        VP_LOGGER = new VPLogger();
        CustomLoggerFactory.getInstance().setLogger(VP_LOGGER);

        UIHelper.logMessage("Config Generator loaded");
    }

    @Override
    public void unloaded() {
        UIHelper.logMessage("Config Generator unloaded");
    }

    public static Logger getLogger() {
        return VP_LOGGER;
    }
}
