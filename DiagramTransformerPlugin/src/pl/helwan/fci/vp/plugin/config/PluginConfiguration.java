package pl.helwan.fci.vp.plugin.config;

import com.vp.plugin.ApplicationManager;
import pl.helwan.fci.vp.plugin.UIHelper;
import pl.helwan.fci.model.DeploymentPlatformEnum;
import pl.helwan.fci.model.Destination;
import pl.helwan.fci.model.PlatformInfo;
import pl.helwan.fci.storage.GitDestination;
import pl.helwan.fci.storage.LocalfileDestination;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PluginConfiguration {

    private static final String CONFIG_FILE_NAME = File.separator + "plugin-config.properties";

    private Workmode workmode;
    private DeploymentPlatformEnum deploymentPlatform;
    private String platformVersion;
    private String umlProfileName;

    private String gitUrl;
    private String gitPath;
    private String gitBranch;
    private String gitLogin;
    private String gitPassword;

    public PluginConfiguration(String environment) {
        String projectPath = ApplicationManager.instance().getProjectManager().getProject().getProjectFile().getAbsolutePath();
        String configFilePath = projectPath.substring(0, projectPath.lastIndexOf(File.separator)) + CONFIG_FILE_NAME;
        UIHelper.logMessage("configFilePath  " + configFilePath);
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(new File(configFilePath))) {
            properties.load(fis);
        } catch (IOException e) {
            UIHelper.logMessage(e.getMessage());
        }

        workmode = Workmode.valueOf(properties.getProperty(environment + ".workmode", Workmode.LOCAL.name()));
        deploymentPlatform = DeploymentPlatformEnum
                .valueOf(properties.getProperty(environment + ".deployment.platform", DeploymentPlatformEnum.QUORUM.name()));
        platformVersion = properties.getProperty(environment + ".deployment.platform.version");
        umlProfileName = properties.getProperty(environment + ".uml.profile.name");

        switch (workmode) {
            case GIT:
                gitUrl = properties.getProperty(environment + ".url");
                gitBranch = properties.getProperty(environment + ".branch");
                gitPath = properties.getProperty(environment + ".path");
                gitLogin = properties.getProperty(environment + ".login");
                gitPassword = properties.getProperty(environment + ".password");
                break;
        }

        UIHelper.logMessage("Configuration prepared for " + workmode);
    }

    public PlatformInfo getDeploymentPlatformInfo() {

        return PlatformInfo.builder()
                .deploymentPlatform(deploymentPlatform)
                .umlProfileName(umlProfileName)
                .platformVersion(platformVersion)
                .build();
    }

    public Destination getDestination() {
        switch (workmode) {
            case LOCAL:
                String destination =
                        UIHelper.selectFile(JFileChooser.DIRECTORIES_ONLY, "Select place where configuration will be generated.");
                UIHelper.logMessage("Configuration will be generated in location: " + destination);
                return new LocalfileDestination(destination);
            case GIT:
                return GitDestination.builder()
                        .gitUrl(gitUrl)
                        .gitBranch(gitBranch)
                        .gitPath(gitPath)
                        .gitLogin(gitLogin)
                        .gitPassword(gitPassword)
                        .build();
        }

        return null;
    }


}
