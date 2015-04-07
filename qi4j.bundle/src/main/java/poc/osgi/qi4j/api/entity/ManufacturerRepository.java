package poc.osgi.qi4j.api.entity;

public interface ManufacturerRepository {
   Manufacturer findByIdentity( String identity );

   Manufacturer findByName( String name );
}
