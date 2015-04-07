package poc.osgi.qi4j.api.entity;

import org.qi4j.api.association.Association;
import org.qi4j.api.association.ManyAssociation;
import org.qi4j.api.property.Immutable;
import org.qi4j.api.property.Property;

public interface Car {
   @Immutable
   Association<Manufacturer> manufacturer();

   @Immutable
   Property<String> model();

   ManyAssociation<Accident> accidents();
}
