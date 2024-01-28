package pl.helwan.fci.manager.corda;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import pl.helwan.fci.consts.Consts;
import pl.helwan.fci.dictionary.TemplateTagsEnum;
import pl.helwan.fci.model.Destination;
import pl.helwan.fci.model.corda.NotaryCordaNode;
import pl.helwan.fci.model.node.INodeObject;
import pl.helwan.fci.model.node.Node;
import pl.helwan.fci.service.NodeManager;
import pl.helwan.fci.service.TemplateManager;
import pl.helwan.fci.service.TemplateTags;

import java.util.Collection;

import static pl.helwan.fci.consts.Consts.CORDA_CONFIG_FILE_EXTENSION;
import static pl.helwan.fci.service.TemplateManager.DEPLOY_NODES_TASK_NODE_NOTARY_TEMPLATE;
import static pl.helwan.fci.service.TemplateManager.DEPLOY_NODES_TASK_NODE_TEMPLATE;
import static pl.helwan.fci.service.TemplateManager.DEPLOY_NODES_TASK_TEMPLATE;
import static pl.helwan.fci.service.TemplateManager.NODE_CONFIG_TEMPLATE;
import static pl.helwan.fci.service.TemplateManager.NODE_NOTARY_CONFIG_TEMPLATE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CordaNodeManager extends NodeManager {
    private static CordaNodeManager INSTANCE;
    private static final String CORDA_TEMPLATE_PATH_PREFIX = "templates/corda/";
    public static final String CORDA_GRADLE_RESULT_FILE = "build.gradle";

    @Override
    public void generateConfigFiles(Destination destination, Collection<INodeObject> nodes) {
        StringBuilder nodesStr = new StringBuilder(StringUtils.EMPTY);
        nodes.forEach(nodeObject -> {
            String deployNodeTemplatePath = (nodeObject instanceof NotaryCordaNode) ? DEPLOY_NODES_TASK_NODE_NOTARY_TEMPLATE :
                    DEPLOY_NODES_TASK_NODE_TEMPLATE;
            String currentNode = populateTemplateWithTags(
                    TemplateManager.getInstance().readTemplate(CORDA_TEMPLATE_PATH_PREFIX + deployNodeTemplatePath),
                    ((Node) nodeObject).getProperties());
            String configTemplatePath = (nodeObject instanceof NotaryCordaNode) ? NODE_NOTARY_CONFIG_TEMPLATE : NODE_CONFIG_TEMPLATE;
            configTemplatePath = CORDA_TEMPLATE_PATH_PREFIX + configTemplatePath;
            nodesStr.append(Consts.NEW_LINE);
            nodesStr.append(currentNode);

            generateNodeConfigFiles(destination, nodeObject, configTemplatePath, CORDA_CONFIG_FILE_EXTENSION);
        });
        String deployNodesTask = TemplateManager.getInstance().readTemplate(CORDA_TEMPLATE_PATH_PREFIX + DEPLOY_NODES_TASK_TEMPLATE);
        destination
                .store(deployNodesTask.replaceAll(TemplateTags.getInstance().buildTemplateTag(TemplateTagsEnum.nodes), nodesStr.toString()),
                        CORDA_GRADLE_RESULT_FILE);
    }

    public static CordaNodeManager getInstance() {
        if (INSTANCE == null)
            return new CordaNodeManager();
        else return INSTANCE;
    }
}
