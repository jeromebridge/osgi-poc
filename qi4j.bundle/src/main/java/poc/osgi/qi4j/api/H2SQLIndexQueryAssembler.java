package poc.osgi.qi4j.api;

import java.io.IOException;

import org.qi4j.index.sql.assembly.AbstractSQLIndexQueryAssembler;
import org.sql.generation.api.vendor.H2Vendor;
import org.sql.generation.api.vendor.SQLVendor;
import org.sql.generation.api.vendor.SQLVendorProvider;

public class H2SQLIndexQueryAssembler
      extends AbstractSQLIndexQueryAssembler<H2SQLIndexQueryAssembler>
{

   @Override
   protected SQLVendor getSQLVendor()
         throws IOException
   {
      return SQLVendorProvider.createVendor( H2Vendor.class );
   }

   @Override
   protected Class<?> getIndexQueryServiceType()
   {
      return H2SQLService.class;
   }

}