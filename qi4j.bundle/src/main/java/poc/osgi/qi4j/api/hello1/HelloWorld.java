package poc.osgi.qi4j.api.hello1;

/**
 * This interface aggregates the behavior and state
 * of the HelloWorld sub-interfaces. To a client
 * this is the same as before though, since it only
 * has to deal with this interface instead of the
 * two sub-interfaces.
 */
public interface HelloWorld extends HelloWorldBehaviour, HelloWorldState {}
