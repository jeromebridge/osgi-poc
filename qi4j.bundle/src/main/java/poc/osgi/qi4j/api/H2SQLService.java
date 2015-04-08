package poc.osgi.qi4j.api;

import org.qi4j.api.mixin.Mixins;
import org.qi4j.index.sql.SQLIndexingEngineService;
import org.qi4j.index.sql.internal.SQLEntityFinder;
import org.qi4j.index.sql.internal.SQLStateChangeListener;
import org.qi4j.index.sql.support.api.SQLAppStartup;
import org.qi4j.index.sql.support.api.SQLIndexing;
import org.qi4j.index.sql.support.api.SQLQuerying;
import org.qi4j.index.sql.support.skeletons.AbstractSQLIndexing;
import org.qi4j.index.sql.support.skeletons.AbstractSQLQuerying;

/**
 * This is actual service responsible of managing indexing and queries and creating database structure.
 * 
 * The reason why all these components are in one single service is that they all require some data about the database
 * structure. Rather than exposing all of that data publicly to be available via another service, it is stored in a
 * state-style private mixin. Thus all the database-related data is available only to this service, and no one else.
 */
@Mixins(
{
      SQLEntityFinder.class,
      SQLStateChangeListener.class,
      H2SQLStartup.class,
      AbstractSQLIndexing.class,
      AbstractSQLQuerying.class,
})
public interface H2SQLService
      extends SQLAppStartup, SQLIndexing, SQLQuerying, SQLIndexingEngineService {

}
