package poc.osgi.qi4j.api.entity;

public interface CarEntityFactory {
   Car create( Manufacturer manufacturer, String model );
}
