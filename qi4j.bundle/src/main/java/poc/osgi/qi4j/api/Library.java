package poc.osgi.qi4j.api;


public interface Library
{
    Book borrowBook( String author, String title );

    void returnBook( Book book );
}