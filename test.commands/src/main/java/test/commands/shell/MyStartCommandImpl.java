package test.commands.shell;

import java.io.PrintStream;
import java.util.StringTokenizer;

import org.apache.felix.shell.Command;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

public class MyStartCommandImpl implements Command
{
   private BundleContext m_context = null;

   public MyStartCommandImpl( BundleContext context )
   {
      m_context = context;
   }

   public String getName()
   {
      return "mystart";
   }

   public String getUsage()
   {
      return "mystart <id> [<id> ...]";
   }

   public String getShortDescription()
   {
      return "start bundle(s).";
   }

   public void execute( String s, PrintStream out, PrintStream err )
   {
      StringTokenizer st = new StringTokenizer( s, " " );

      // Ignore the command name.
      st.nextToken();

      // There should be at least one bundle id.
      if( st.countTokens() >= 1 )
      {
         while( st.hasMoreTokens() )
         {
            String id = st.nextToken().trim();

            try {
               long l = Long.valueOf( id ).longValue();
               Bundle bundle = m_context.getBundle( l );
               if( bundle != null )
               {
                  bundle.start();
               }
               else
               {
                  err.println( "Bundle ID " + id + " is invalid." );
               }
            }
            catch( NumberFormatException ex ) {
               err.println( "Unable to parse id '" + id + "'." );
            }
            catch( BundleException ex ) {
               if( ex.getNestedException() != null )
                  err.println( ex.getNestedException().toString() );
               else
                  err.println( ex.toString() );
            }
            catch( Exception ex ) {
               err.println( ex.toString() );
            }
         }
      }
      else
      {
         err.println( "Incorrect number of arguments" );
      }
   }
}
