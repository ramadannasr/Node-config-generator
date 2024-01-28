package pl.helwan.fci.vp.plugin.node;

import com.vp.plugin.model.INode;
import com.vp.plugin.model.IPackage;
import com.vp.plugin.model.factory.IModelElementFactory;
import pl.helwan.fci.vp.plugin.UIHelper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class NodeRetriver {

    private static NodeRetriver INSTANCE;

    private NodeRetriver() {

    }

    public Collection<INode> getNodes(IPackage vpPackage) {
        UIHelper.logMessage("Collecting nodes details...");

        Iterable<INode> nodesIterable = () -> vpPackage.childIterator(IModelElementFactory.MODEL_TYPE_NODE);

        List<INode> nodes = StreamSupport
                .stream(nodesIterable.spliterator(), false)
                .collect(Collectors.toList());

        UIHelper.logMessage("Nodes search finished (" + nodes.size() + " nodes found)");

        return nodes;
    }

    public static NodeRetriver getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NodeRetriver();
        }

        return INSTANCE;
    }

}
