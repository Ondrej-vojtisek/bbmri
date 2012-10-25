package bbmri.testing;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext springCtx = new ClassPathXmlApplicationContext("spring-context.xml");
        BooksManager booksSpring = (BooksManager) springCtx.getBean("booksManager");
        testBooksManager(booksSpring);
    }

    static void testBooksManager(BooksManager bm) {

        System.out.println("------" + bm.getClass().getSimpleName() + "------");
        Book b1 = new Book("Kucharka", "Magdalena Rettigova");
        bm.addBook(b1);
        System.out.println("allBooks = " + bm.getAllBooks());
        System.out.println();

        Book b2 = bm.getBookById(b1.getId());
        b2.setName("Cervena Karkulka");
        bm.updateBook(b2);

        System.out.println("knihy autoru zacinajich na Ma:" + bm.findBooksWithAuthor("Ma"));
        System.out.println();

        System.out.println("allBooks = " + bm.getAllBooks());
        System.out.println();

        bm.removeBook(b2.getId());

    }
}

