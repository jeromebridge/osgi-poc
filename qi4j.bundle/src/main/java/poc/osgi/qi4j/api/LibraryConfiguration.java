package poc.osgi.qi4j.api;

import org.qi4j.api.configuration.ConfigurationComposite;
import org.qi4j.api.property.Property;

public interface LibraryConfiguration extends ConfigurationComposite {
   Property<String> titles();

   Property<String> authors();

   Property<Integer> copies();
}
