package poc.osgi.qi4j.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.qi4j.api.injection.scope.Uses;
import org.qi4j.api.service.ServiceDescriptor;
import org.qi4j.index.sql.support.skeletons.AbstractSQLStartup;
import org.sql.generation.api.grammar.common.datatypes.SQLDataType;
import org.sql.generation.api.vendor.PostgreSQLVendor;

public class H2SQLStartup extends AbstractSQLStartup {

   private PostgreSQLVendor _vendor;

   public H2SQLStartup( @Uses ServiceDescriptor descriptor )
   {
      super( descriptor );

      this._vendor = descriptor.metaInfo( PostgreSQLVendor.class );
   }

   @Override
   protected void testRequiredCapabilities( Connection connection ) throws SQLException {

   }

   @Override
   protected void modifyPrimitiveTypes( Map<Class<?>, SQLDataType> primitiveTypes, Map<Class<?>, Integer> jdbcTypes ) {

   }

   @Override
   protected SQLDataType getCollectionPathDataType() {
      return this._vendor.getDataTypeFactory().userDefined( "ltree" );
   }

}
