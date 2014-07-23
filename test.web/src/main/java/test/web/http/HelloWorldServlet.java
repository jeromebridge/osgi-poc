package test.web.http;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class HelloWorldServlet extends HttpServlet {
   protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
      response.setContentType( "text/html" );
      PrintWriter out = response.getWriter();

      out.println( "<html>" );
      out.println( "<head>" );
      out.println( "<title>Hola</title>" );
      out.println( "</head>" );
      out.println( "<body bgcolor=\"white\">" );
      out.println( "</body>" );
      out.println( "</html>" );
   }
}