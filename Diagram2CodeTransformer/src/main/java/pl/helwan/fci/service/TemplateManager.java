package pl.helwan.fci.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import pl.helwan.fci.consts.Consts;

import java.io.File;
import java.nio.charset.Charset;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TemplateManager {

    public static final String DEPLOY_NODES_TASK_TEMPLATE = "deployNodesTask.template";
    public static final String DEPLOY_NODES_TASK_NODE_TEMPLATE = "deployNodesTask_node.template";
    public static final String NODE_CONFIG_TEMPLATE = "nodeConfig.template";

    // specific templates for Corda Notary Node
    public static final String DEPLOY_NODES_TASK_NODE_NOTARY_TEMPLATE = "deployNodesTask_notaryNode.template";
    public static final String NODE_NOTARY_CONFIG_TEMPLATE = "notaryNodeConfig.template";

    // specific templates for Quorum TxManager  Node
    public static final String NODE_TX_MANAGER_IPC_CONFIG_TEMPLATE = "txManagerNode_ipcConfig.template";
    public static final String NODE_TX_MANAGER_HTTP_CONFIG_TEMPLATE = "txManagerNode_httpConfig.template";

    // specific templates for sawtooth validator  Node
    public static final String NODE_VALIDATOR_REST_API_TEMPLATE = "nodeConfig_rest_api.template";
    public static final String NODE_VALIDATOR_CLI_TEMPLATE = "nodeConfig_cli.template";

    private static TemplateManager INSTANCE = new TemplateManager();

    @SneakyThrows
    public String readTemplate(String template) {
        File file = new File(getClass().getClassLoader().getResource(template).getFile());
        return FileUtils.readFileToString(file, Charset.forName(Consts.UTF_8));
    }

    public static TemplateManager getInstance() {
        return INSTANCE;
    }
}
