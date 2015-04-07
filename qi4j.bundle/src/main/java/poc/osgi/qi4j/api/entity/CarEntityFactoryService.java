package poc.osgi.qi4j.api.entity;

import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.service.ServiceComposite;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

@Mixins({ CarEntityFactoryService.CarEntityFactoryMixin.class })
public interface CarEntityFactoryService extends CarEntityFactory, ServiceComposite {

   public static class CarEntityFactoryMixin implements CarEntityFactory {
      @Structure
      UnitOfWorkFactory uowf;

      @Override
      public Car create( Manufacturer manufacturer, String model ) {
         UnitOfWork uow = uowf.currentUnitOfWork();
         EntityBuilder<Car> builder = uow.newEntityBuilder( Car.class );

         Car prototype = builder.instance();
         prototype.manufacturer().set( manufacturer );
         prototype.model().set( model );

         return builder.newInstance();
      }
   }
}
