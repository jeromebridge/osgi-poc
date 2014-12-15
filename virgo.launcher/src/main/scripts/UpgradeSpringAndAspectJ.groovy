import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.Validate;
import org.apache.commons.io.FileUtils;
import java.util.Iterator;
import java.io.File;
import java.util.regex.Pattern;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


String virgoHomeFolderPath = project.properties['virgo.home'];
String springUpgradeFolderPath = project.properties['spring.upgrade.directory'];
String aspectjUpgradeFolderPath = project.properties['aspectj.upgrade.directory'];
String springUpgradeConfFolderPath = project.properties['spring.upgrade.conf.directory'];
String aspectjVersion = project.properties['upgrade.aspectj.version'];

Validate.notNull( virgoHomeFolderPath, "Must specify the property: virgo.home" );
Validate.notNull( springUpgradeFolderPath, "Must specify the property: spring.upgrade.directory" );
Validate.notNull( aspectjUpgradeFolderPath, "Must specify the property: aspectj.upgrade.directory" );
Validate.notNull( springUpgradeConfFolderPath, "Must specify the property: spring.upgrade.conf.directory" );

File virgoHomeFolder = new File( virgoHomeFolderPath );
File springUpgradeFolder = new File( springUpgradeFolderPath );
File aspectjUpgradeFolder = new File( aspectjUpgradeFolderPath );
File springUpgradeConfFolder = new File( springUpgradeConfFolderPath );

Validate.isTrue( virgoHomeFolder.exists(), String.format( "Virgo Home Folder: %s does not exist", virgoHomeFolderPath ) );
Validate.isTrue( springUpgradeFolder.exists(), String.format( "Spring Upgrade Folder: %s does not exist", springUpgradeFolderPath ) );
Validate.isTrue( aspectjUpgradeFolder.exists(), String.format( "AspectJ Upgrade Folder: %s does not exist", aspectjUpgradeFolderPath ) );
Validate.isTrue( springUpgradeConfFolder.exists(), String.format( "Spring Upgrade Conf Folder: %s does not exist", springUpgradeConfFolderPath ) );

String repositoryExtFolderPath = virgoHomeFolder.getAbsolutePath() + File.separator + "repository" + File.separator + "ext";
File repositoryExtFolder = new File( repositoryExtFolderPath );
Validate.isTrue( repositoryExtFolder.exists(), String.format( "Repository Ext Folder: %s does not exist", repositoryExtFolderPath ) );

String pluginsFolderPath = virgoHomeFolder.getAbsolutePath() + File.separator + "plugins";
File pluginsFolder = new File( pluginsFolderPath );
Validate.isTrue( pluginsFolder.exists(), String.format( "Plugins Folder: %s does not exist", pluginsFolderPath ) );

// Remove Existing Spring
String oldSpringExpression = "org\\.springframework.*3.1.0.RELEASE.*";
File[] children = repositoryExtFolder.listFiles();
if (children != null) {
    for( File child : children ) {
        if( Pattern.matches( oldSpringExpression, child.getName() ) ) {
            child.delete();
        }
    }
}

// Add New Spring
File[] upgradeSpringFiles = springUpgradeFolder.listFiles();
if( upgradeSpringFiles != null ) {
    for( File upgradeSpringFile : upgradeSpringFiles ) {
        FileUtils.copyFileToDirectory( upgradeSpringFile, repositoryExtFolder );
    }
}
File[] upgradeSpringConfFiles = springUpgradeConfFolder.listFiles();
if( upgradeSpringConfFiles != null ) {
    for( File upgradeSpringConfFile : upgradeSpringConfFiles ) {
        FileUtils.copyFileToDirectory( upgradeSpringConfFile, repositoryExtFolder );
    }
}

// Remove Existing AspectJ
String oldAspectJExpression = ".*aspectj.weaver.*";
File[] children2 = repositoryExtFolder.listFiles();
if( children2 != null ) {
    for( File child : children2 ) {
        if( Pattern.matches( oldAspectJExpression, child.getName() ) ) {
            child.delete();
        }
    }
}
File[] children3 = pluginsFolder.listFiles();
if( children3 != null ) {
    for( File child : children3 ) {
        if( Pattern.matches( oldAspectJExpression, child.getName() ) ) {
            child.delete();
        }
    }
}

// Add New AspectJ
File[] upgradeAspectjFiles = aspectjUpgradeFolder.listFiles();
if( upgradeAspectjFiles != null ) {
    for( File upgradeAspectJ : upgradeAspectjFiles ) {
        FileUtils.copyFileToDirectory( upgradeAspectJ, repositoryExtFolder );
        FileUtils.copyFileToDirectory( upgradeAspectJ, pluginsFolder );
    }
}
Path path = Paths.get( virgoHomeFolder.getAbsolutePath() + File.separator + "configuration" + File.separator + "org.eclipse.virgo.kernel.userregion.properties" );
Charset charset = StandardCharsets.UTF_8;
String content = new String(Files.readAllBytes(path), charset);
String oldLine = "org.aspectj.*;version=\"[1.6.5.RELEASE,2.0.0)\"";
String newLine = "org.aspectj.*;version=\"[" + aspectjVersion + ".RELEASE,2.0.0)\"";
content = content.replace( oldLine, newLine );
Files.write(path, content.getBytes(charset));

Path path2 = Paths.get( virgoHomeFolder.getAbsolutePath() + File.separator + "configuration" + File.separator + "org.eclipse.equinox.simpleconfigurator" + File.separator + "bundles.info" );
String content2 = new String(Files.readAllBytes(path2), charset);
String oldLine2 = "com.springsource.org.aspectj.weaver,1.6.12.RELEASE,plugins/com.springsource.org.aspectj.weaver_1.6.12.RELEASE.jar,4,false";
String newLine2 = "com.springsource.org.aspectj.weaver," + aspectjVersion + ".RELEASE,plugins/com.springsource.org.aspectj.weaver-" + aspectjVersion + ".RELEASE.jar,4,false";
content2 = content2.replace( oldLine2, newLine2 );
Files.write(path2, content2.getBytes(charset));