package pl.helwan.fci.manager.quorum;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import pl.helwan.fci.consts.Consts;
import pl.helwan.fci.dictionary.TemplateTagsEnum;
import pl.helwan.fci.model.Destination;
import pl.helwan.fci.model.node.INodeObject;
import pl.helwan.fci.model.node.Node;
import pl.helwan.fci.model.quorum.DLTQuorumNode;
import pl.helwan.fci.model.quorum.TxManagerQuorumNode;
import pl.helwan.fci.service.NodeManager;
import pl.helwan.fci.service.TemplateManager;
import pl.helwan.fci.service.TemplateTags;

import java.util.Collection;

import static pl.helwan.fci.consts.Consts.TOML_FILE_EXTENSION;
import static pl.helwan.fci.service.TemplateManager.DEPLOY_NODES_TASK_NODE_TEMPLATE;
import static pl.helwan.fci.service.TemplateManager.DEPLOY_NODES_TASK_TEMPLATE;
import static pl.helwan.fci.service.TemplateManager.NODE_CONFIG_TEMPLATE;
import static pl.helwan.fci.service.TemplateManager.NODE_TX_MANAGER_HTTP_CONFIG_TEMPLATE;
import static pl.helwan.fci.service.TemplateManager.NODE_TX_MANAGER_IPC_CONFIG_TEMPLATE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuorumNodeManager extends NodeManager {
    private static QuorumNodeManager INSTANCE;
    private static final String QUORUM_TEMPLATE_PATH_PREFIX = "templates/quorum/";
    public static final String QUORUM_IPC_CONFIG_FILE_POSTFIX = " ipc-config.toml";
    public static final String QUORUM_HTTP_CONFIG_FILE_POSTFIX = " http-config.toml";
    public static final String QUORUM_TRUFFLE_RESULT_FILE = "truffle-config.js";

    @Override
    public void generateConfigFiles(Destination destination, Collection<INodeObject> nodes) {
        StringBuilder nodesStr = new StringBuilder(StringUtils.EMPTY);
        nodes.forEach(nodeObject -> {
            String currentNode = "";
            String configTemplatePath = null;
            if (nodeObject instanceof DLTQuorumNode) {
                String deployNodeTemplatePath = QUORUM_TEMPLATE_PATH_PREFIX + DEPLOY_NODES_TASK_NODE_TEMPLATE;
                currentNode = populateTemplateWithTags(TemplateManager.getInstance().readTemplate(deployNodeTemplatePath),
                        ((Node) nodeObject).getProperties());
                configTemplatePath = QUORUM_TEMPLATE_PATH_PREFIX + NODE_CONFIG_TEMPLATE;
                generateNodeConfigFiles(destination, nodeObject, configTemplatePath, TOML_FILE_EXTENSION);
            } else if (nodeObject instanceof TxManagerQuorumNode) {
                configTemplatePath = QUORUM_TEMPLATE_PATH_PREFIX + NODE_TX_MANAGER_IPC_CONFIG_TEMPLATE;
                generateNodeConfigFiles(destination, nodeObject, configTemplatePath, QUORUM_IPC_CONFIG_FILE_POSTFIX);
                configTemplatePath = QUORUM_TEMPLATE_PATH_PREFIX + NODE_TX_MANAGER_HTTP_CONFIG_TEMPLATE;
                generateNodeConfigFiles(destination, nodeObject, configTemplatePath, QUORUM_HTTP_CONFIG_FILE_POSTFIX);
            }
            nodesStr.append(Consts.NEW_LINE);
            nodesStr.append(currentNode);
        });
        String deployNodesTask = TemplateManager.getInstance().readTemplate(QUORUM_TEMPLATE_PATH_PREFIX + DEPLOY_NODES_TASK_TEMPLATE);
        destination.store(deployNodesTask.replaceAll(TemplateTags.getInstance().buildTemplateTag(TemplateTagsEnum.nodes), nodesStr.toString()),
                QUORUM_TRUFFLE_RESULT_FILE);
    }

    public static QuorumNodeManager getInstance() {
        if (INSTANCE == null)
            return new QuorumNodeManager();
        else
            return INSTANCE;
    }
}
