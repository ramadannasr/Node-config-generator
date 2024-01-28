package pl.helwan.fci.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PlatformInfo {

    private static PlatformInfo INSTANCE;
    private DeploymentPlatformEnum deploymentPlatform;
    private String platformVersion;
    private String umlProfileName;

    public static PlatformInfo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlatformInfo();
        }

        return INSTANCE;
    }

}
