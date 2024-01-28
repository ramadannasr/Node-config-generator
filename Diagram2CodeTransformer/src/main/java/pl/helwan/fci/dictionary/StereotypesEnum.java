package pl.helwan.fci.dictionary;

import pl.helwan.fci.model.corda.DLTCordaNode;
import pl.helwan.fci.model.corda.NetworkMapCordaNode;
import pl.helwan.fci.model.corda.NotaryCordaNode;
import pl.helwan.fci.model.corda.OracleCordaNode;
import pl.helwan.fci.model.node.Node;
import pl.helwan.fci.model.quorum.DLTQuorumNode;
import pl.helwan.fci.model.quorum.TxManagerQuorumNode;
import pl.helwan.fci.model.sawtooth.DLTValidatorNode;

public enum StereotypesEnum {
    // Quorum Nodes
    QuorumNode,
    TxManagerNode,
    // Sawtooth Nodes
    ValidatorNode,
    // R3 Corda Nodes
    DLTNode, OracleNode, NetworkMapNode, NotaryNode;
    public Node getInstance() {
        switch (this) {
            case QuorumNode:
                return new DLTQuorumNode();
            case TxManagerNode:
                return new TxManagerQuorumNode();
            case ValidatorNode:
                return new DLTValidatorNode();
            case NotaryNode:
                return new NotaryCordaNode();
            case OracleNode:
                return new OracleCordaNode();
            case NetworkMapNode:
                return new NetworkMapCordaNode();
            case DLTNode:
                return new DLTCordaNode();
            default:
                return new Node();
        }
    }
}
