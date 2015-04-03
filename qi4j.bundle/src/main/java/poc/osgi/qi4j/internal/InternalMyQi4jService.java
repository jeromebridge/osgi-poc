package poc.osgi.qi4j.internal;

import org.qi4j.api.property.Property;

import poc.osgi.qi4j.api.MyQi4jService;

public class InternalMyQi4jService implements MyQi4jService {

   @Override
   public void registerServices() throws Exception {
      System.out.println( "Internal Service Handling this." );

   }

   @Override
   public void unregisterServices() throws Exception {
      System.out.println( "Internal Service Handling this." );

   }

   @Override
   public Property<String> identity() {
      System.out.println( "Internal Service Handling this." );

      return null;
   }

   @Override
   public void hello() {
      System.out.println( "Internal Service Handling this." );

   }

}
