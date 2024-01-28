package pl.helwan.fci.vp.plugin.node;

import com.vp.plugin.model.INode;
import pl.helwan.fci.vp.plugin.UIHelper;
import pl.helwan.fci.dictionary.StereotypesEnum;
import pl.helwan.fci.model.Destination;
import pl.helwan.fci.model.node.INodeObject;
import pl.helwan.fci.model.PlatformInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NodeConfigGenerator {

    private static NodeConfigGenerator INSTANCE;

    private NodeConfigGenerator() {

    }

    public void generateNodesConfiguration(Destination destination, PlatformInfo deploymentPlatformInfo, Collection<INode> vpNodes) {
        Collection<INodeObject> nodes = new ArrayList<>();
        Stream.of(StereotypesEnum.values()).forEach(nodeType -> {
            nodes.addAll(vpNodes
                    .stream()
                    .filter(vpNode -> vpNode.hasStereotype(nodeType.name()))
                    .map(vpNode -> NodeAssembler.getInstance().buildNode(vpNode, nodeType.getInstance(), deploymentPlatformInfo))
                    .collect(Collectors.toList()));
        });

        UIHelper.logMessage("Generating config files for platform " + deploymentPlatformInfo.getDeploymentPlatform().name() + "...");
        try {
            deploymentPlatformInfo.getDeploymentPlatform().getInstance().generateConfigFiles(destination, nodes);
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.showPopupMessage(
                    "Error occured during " + deploymentPlatformInfo.getDeploymentPlatform().name() + " configuration generation");
            UIHelper.logMessage(e.getMessage());
        }

        UIHelper.showPopupMessage(deploymentPlatformInfo.getDeploymentPlatform().name() + " configuration generated successfully.");
    }


    public static NodeConfigGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NodeConfigGenerator();
        }

        return INSTANCE;
    }
}
