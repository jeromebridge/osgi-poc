package poc.osgi.qi4j.api;

import org.qi4j.api.service.ServiceComposite;

// @Mixins(LibraryServiceMixin.class)
public interface LibraryService
      extends Library, ServiceComposite
{

}
