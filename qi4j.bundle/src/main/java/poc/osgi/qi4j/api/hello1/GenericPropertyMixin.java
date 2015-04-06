package poc.osgi.qi4j.api.hello1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.qi4j.api.common.AppliesTo;
import org.qi4j.api.common.AppliesToFilter;
import org.qi4j.api.injection.scope.State;
import org.qi4j.api.property.Property;
import org.qi4j.api.property.StateHolder;

@AppliesTo({ GenericPropertyMixin.PropertyFilter.class })
public class GenericPropertyMixin implements InvocationHandler {
   @State
   private StateHolder state;

   @Override
   public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable {
      System.out.println( "GENERIC CALLED: " + method );
      return state.propertyFor( method );
   }

   public static class PropertyFilter implements AppliesToFilter {
      @Override
      public boolean appliesTo( Method method, Class<?> mixin, Class<?> compositeType, Class<?> modifierClass ) {
         return Property.class.isAssignableFrom( method.getReturnType() );
      }
   }
}
