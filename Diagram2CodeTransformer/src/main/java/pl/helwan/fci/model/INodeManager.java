package pl.helwan.fci.model;

import pl.helwan.fci.model.node.INodeObject;

import java.util.Collection;

public interface INodeManager {
    void generateConfigFiles(Destination destination, Collection<INodeObject> objects);
}
