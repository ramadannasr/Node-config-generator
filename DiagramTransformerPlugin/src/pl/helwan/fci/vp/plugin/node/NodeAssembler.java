package pl.helwan.fci.vp.plugin.node;

import com.vp.plugin.model.INode;
import com.vp.plugin.model.ITaggedValue;
import pl.helwan.fci.vp.plugin.UIHelper;
import pl.helwan.fci.model.PlatformInfo;
import pl.helwan.fci.model.node.Node;

import java.util.Set;

public class NodeAssembler {


    private static NodeAssembler INSTANCE = new NodeAssembler();

    private NodeAssembler() {

    }

    public Node buildNode(INode vpNode, Node node, PlatformInfo platformInfo) {
        Set<String> taggedValues = UmlProfileReader.getInstance().taggedValuesDefinedInProfile(platformInfo);
        taggedValues.forEach(tv -> node.getProperties().put(tv, readTaggedValue(tv, vpNode)));
        node.setName(vpNode.getName());
        UIHelper.logMessage("taggedValues size : "+taggedValues.size());
        return node;
    }

    private Object readTaggedValue(String key, INode vpNode) {
        ITaggedValue vpTaggedValue = vpNode.getTaggedValues().getTaggedValueByName(key);
        if (vpTaggedValue == null) {
            return null;
        }

        return vpTaggedValue.getValue();
    }

    public static NodeAssembler getInstance() {
        return INSTANCE;
    }
}
