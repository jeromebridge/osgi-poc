package poc.osgi.qi4j.api.entity;

import java.util.Date;

import org.qi4j.api.property.Property;

public interface Accident {
   Property<String> description();

   Property<Date> occured();

   Property<Date> repaired();
}
