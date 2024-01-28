package pl.helwan.fci.vp.plugin.node;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.ProjectManager;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IStereotype;
import com.vp.plugin.model.factory.IModelElementFactory;
import pl.helwan.fci.vp.plugin.UIHelper;
import pl.helwan.fci.model.PlatformInfo;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class UmlProfileReader {

    private static UmlProfileReader INSTANCE;

    private UmlProfileReader() {

    }

    public Set<String> taggedValuesDefinedInProfile(PlatformInfo platformInfo) {
        ProjectManager projectManager = ApplicationManager.instance().getProjectManager();
        Set<String> taggedValues = new HashSet<>();
        IModelElement[] stereotypes = Stream.of(projectManager
                .getProject()
                .getLinkedProjects())
                .filter(lp -> lp.getName().equals(platformInfo.getUmlProfileName()))
                .findFirst()
                .get()
                .toModelElementArray(IModelElementFactory.MODEL_TYPE_STEREOTYPE);

        Stream.of(stereotypes)
                .map(modelElement -> (IStereotype) modelElement)
                .filter(stereotype -> stereotype.getTaggedValueDefinitions() != null && stereotype.getDescription().equals(platformInfo.getDeploymentPlatform().name()))
                .map(stereotype -> stereotype.getTaggedValueDefinitions())
                .map(taggedValueDefinitionContainer -> taggedValueDefinitionContainer.toTaggedValueDefinitionArray())
                .forEach(taggedValuesDefinitions -> Stream.of(taggedValuesDefinitions)
                        .forEach(taggedValue -> taggedValues.add(taggedValue.getName())));
        UIHelper.logMessage("stereos length "+       Stream.of(stereotypes)
                .map(modelElement -> (IStereotype) modelElement)
                .filter(stereotype -> stereotype.getTaggedValueDefinitions() != null && stereotype.getDescription().equals(platformInfo.getDeploymentPlatform().name()))
                .map(stereotype -> stereotype.getTaggedValueDefinitions())
                .map(taggedValueDefinitionContainer -> taggedValueDefinitionContainer.toTaggedValueDefinitionArray()).count());

        return taggedValues;
    }

    public static UmlProfileReader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UmlProfileReader();
        }

        return INSTANCE;
    }

}
