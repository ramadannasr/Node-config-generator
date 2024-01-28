package pl.helwan.fci.vp.plugin.node.config;

import com.vp.plugin.action.VPContext;
import com.vp.plugin.model.INode;
import com.vp.plugin.model.IPackage;
import pl.helwan.fci.vp.plugin.UIHelper;
import pl.helwan.fci.vp.plugin.config.PluginConfiguration;

public abstract class DefaultActionController {

    protected PluginConfiguration readPluginConfig(VPContext context) {
        return new PluginConfiguration(extractEnvironment(context));
    }

    private String extractEnvironment(VPContext context) {
        if (context.getModelElement() instanceof IPackage) {
            return context.getModelElement().getName();
        } else if (context.getModelElement() instanceof INode) {
            return context.getModelElement().getParent().getName();
        }

        UIHelper.showPopupMessage("Action can be executed only on Node and Package");
        throw new IllegalArgumentException("Action can be executed only on Node and Package");
    }

}
