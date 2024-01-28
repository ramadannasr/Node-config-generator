package pl.helwan.fci.model;

import pl.helwan.fci.manager.corda.CordaNodeManager;
import pl.helwan.fci.manager.quorum.QuorumNodeManager;
import pl.helwan.fci.manager.sawtooth.SawtoothNodeManager;
import pl.helwan.fci.service.NodeManager;

/**
 * <p>
 * Title: DeploymentPlatformEnum.java
 *
 * @author <a href="mailto:ramadannasr40@gmail">Ramadan Nasr</a>
 * @version 1.0
 * @date 15/09/2022
 */
public enum DeploymentPlatformEnum {
    QUORUM, CORDA, SAWTOOTH;

    public INodeManager getInstance() {
        switch (this) {
            case QUORUM:
                return QuorumNodeManager.getInstance();
            case SAWTOOTH:
                return SawtoothNodeManager.getInstance();
            case CORDA:
                return CordaNodeManager.getInstance();
            default:
                return NodeManager.getInstance();
        }
    }
}
