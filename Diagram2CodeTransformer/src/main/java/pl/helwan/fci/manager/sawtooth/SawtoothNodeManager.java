package pl.helwan.fci.manager.sawtooth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.helwan.fci.model.Destination;
import pl.helwan.fci.model.node.INodeObject;
import pl.helwan.fci.service.NodeManager;

import java.util.Collection;

import static pl.helwan.fci.consts.Consts.TOML_FILE_EXTENSION;
import static pl.helwan.fci.service.TemplateManager.NODE_CONFIG_TEMPLATE;
import static pl.helwan.fci.service.TemplateManager.NODE_VALIDATOR_CLI_TEMPLATE;
import static pl.helwan.fci.service.TemplateManager.NODE_VALIDATOR_REST_API_TEMPLATE;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SawtoothNodeManager extends NodeManager {

    private static SawtoothNodeManager INSTANCE;
    private static final String SAWTOOTH_TEMPLATE_PATH_PREFIX = "templates/sawtooth/";
    public static final String SAWTOOTH_REST_API_CONFIG_FILE_POSTFIX = " rest_api.toml";
    public static final String SAWTOOTH_CLI_CONFIG_FILE_POSTFIX = " cli.toml";

    @Override
    public void generateConfigFiles(Destination destination, Collection<INodeObject> nodes) {
        nodes.forEach(nodeObject -> {
            String configTemplatePath = SAWTOOTH_TEMPLATE_PATH_PREFIX + NODE_CONFIG_TEMPLATE;
            generateNodeConfigFiles(destination, nodeObject, configTemplatePath, TOML_FILE_EXTENSION);
            configTemplatePath = SAWTOOTH_TEMPLATE_PATH_PREFIX + NODE_VALIDATOR_REST_API_TEMPLATE;
            generateNodeConfigFiles(destination, nodeObject, configTemplatePath, SAWTOOTH_REST_API_CONFIG_FILE_POSTFIX);
            configTemplatePath = SAWTOOTH_TEMPLATE_PATH_PREFIX + NODE_VALIDATOR_CLI_TEMPLATE;
            generateNodeConfigFiles(destination, nodeObject, configTemplatePath, SAWTOOTH_CLI_CONFIG_FILE_POSTFIX);
        });
    }

    public static SawtoothNodeManager getInstance() {
        if (INSTANCE == null)
            return new SawtoothNodeManager();
        else return INSTANCE;
    }
}
