package bbmri.testing;

import java.util.List;

/**
 * Interface pro praci s knihami. Obsahuje zakladni CRUD operace (Create-Retrieve-Update-Delete).
 *
 * @author Martin Kuba makub@ics.muni.cz
 * @version $Id:$
 */

public interface BooksManager {

    void addBook(Book book);

    List<Book> getAllBooks();

    Book getBookById(int id);

    /**
     * Nalezne knihy jejich jmeno autora zacina zadanym retezcem. Ukazka castecne shody.
     *
     * @param authorNameBeginsWith retezec predstavujii zacatek autorova jmena
     * @return seznam knih
     */
    List<Book> findBooksWithAuthor(String authorNameBeginsWith);

    void updateBook(Book book);

    void removeBook(int bookId);

    int getNumberOfBooks();
}
