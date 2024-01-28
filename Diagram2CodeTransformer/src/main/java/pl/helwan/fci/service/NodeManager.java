package pl.helwan.fci.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.helwan.fci.model.Destination;
import pl.helwan.fci.model.INodeManager;
import pl.helwan.fci.model.node.INodeObject;
import pl.helwan.fci.model.node.Node;

import java.util.Collection;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NodeManager implements INodeManager {

    private static NodeManager INSTANCE;

    protected void generateNodeConfigFiles(Destination destination, INodeObject node, String templatePath, String fileExtension) {
        String nodeConfig =
                populateTemplateWithTags(TemplateManager.getInstance().readTemplate(templatePath), ((Node) node).getProperties());
        destination.store(nodeConfig, ((Node) node).getName() + fileExtension);
    }

    protected String populateTemplateWithTags(String template, Map<String, Object> tagsAndValues) {
        for (Map.Entry<String, Object> entry : tagsAndValues.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String tag = TemplateTags.getInstance().buildTemplateTag(key);
            template = template.replace(tag, getValueAsString(tag, value));
        }

        return template;
    }

    private String getValueAsString(String tag, Object object) {
        if (object == null) {
            return tag;
        }

        return object.toString().trim();
    }

    public static NodeManager getInstance() {
        if (INSTANCE == null)
            return new NodeManager();
        else
            return INSTANCE;
    }

    @Override
    public void generateConfigFiles(Destination destination, Collection<INodeObject> objects) {
    }
}
