package pl.helwan.fci.vp.plugin.node;

import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.model.INode;
import com.vp.plugin.model.IPackage;
import pl.helwan.fci.vp.plugin.UIHelper;
import pl.helwan.fci.vp.plugin.config.PluginConfiguration;
import pl.helwan.fci.vp.plugin.node.config.DefaultActionController;

import java.awt.event.ActionEvent;
import java.util.Collection;

public class NodesGeneratorContextActionController extends DefaultActionController implements VPContextActionController {

    @Override
    public void performAction(VPAction vpAction, VPContext vpContext, ActionEvent actionEvent) {
        UIHelper.logMessage("Starting nodes config generation..");
        PluginConfiguration pluginConfiguration = readPluginConfig(vpContext);
        Collection<INode> nodes = NodeRetriver.getInstance().getNodes((IPackage) vpContext.getModelElement());
        NodeConfigGenerator.getInstance().generateNodesConfiguration(pluginConfiguration.getDestination(),pluginConfiguration.getDeploymentPlatformInfo(), nodes);
    }

    @Override
    public void update(VPAction vpAction, VPContext vpContext) {

    }
}
