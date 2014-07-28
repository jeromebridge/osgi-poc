import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.Validate;
import java.util.Iterator;


String targetFile = project.properties['target.file'];
String sourceFile = project.properties['source.file'];

Validate.notNull( targetFile, "Must specify the property: target.file" );
Validate.notNull( sourceFile, "Must specify the property: source.file" );

PropertiesConfiguration source = new PropertiesConfiguration(sourceFile);
PropertiesConfiguration target = new PropertiesConfiguration(targetFile);

Iterator keyIterator = source.getKeys();
while( keyIterator.hasNext() ) {
	String key = keyIterator.next();
	Object value = source.getProperty( key );
	target.setProperty( key, value ); 
}
target.save();

