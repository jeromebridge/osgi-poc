package poc.osgi.qi4j.api.entity;

import org.qi4j.api.common.UseDefaults;
import org.qi4j.api.property.Property;

public interface Manufacturer {
   Property<String> name();

   Property<String> country();

   @UseDefaults
   Property<Long> carsProduced();
}
