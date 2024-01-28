package pl.helwan.fci.model.node;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@EqualsAndHashCode
public class Node implements INodeObject {

    @Getter @Setter private String name;
    @Getter private Map<String, Object> properties = new HashMap<>();
}
