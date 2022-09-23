//import org.junit.runner.RunWith;
//import org.junit.runners.Suite;
//
//@RunWith(Suite.class)
//@Suite.SuiteClasses({LibraryTest.class})
//
//public class LibraryTestSuite{
//
//}
//


import org.junit.BeforeClass;
import org.junit.Test;

public class LibraryTest {
    private static Library testLibrary;
    private static Book[] books = new Book[5];
    private static Patron[] patrons = new Patron[3];

    @BeforeClass
    public static void creatLibrary() throws Exception {
        System.out.println("Creat Library and others ..");
        testLibrary = new Library(6, 2, 4);

        books[0] = new Book("Animal Farm", "George Orwell", 1935, 5, 8, 4);
        books[1] = new Book("Ottoman Empire", "Halil Inalcik", 1985, 0, 3, 10);
        books[2] = new Book("Sherlock Holmes", "Arthur Dowell", 1890, 5, 9, 3);
        books[3] = new Book("Laurel & Hardy", "John McCabe", 1915, 8, 4, 1);
        books[4] = new Book("Great Expectations", "Charles Dickens", 1860, 2, 10, 3);

        patrons[0] = new Patron("Muaz", "Abdeen", 4, 7, 7, 90);
        patrons[1] = new Patron("Luay", "Muhtaseb", 2, 6, 9, 100);
        patrons[2] = new Patron("Hazem", "Omari", 8, 6, 3, 80);

        System.out.println(" DONE .. !!");
    }


    @Test
    public void testAddBookToLibrary() {
//        for (Book book : books) {
//            Library.addBookToLibrary(book);
//        }
    }

    @Test
    public void isBookIdValid() {
//        Library.isBookIdValid();
    }

    @Test
    public void getBookId() {
    }

    @Test
    public void isBookAvailable() {
    }

    @Test
    public void registerPatronToLibrary() {
//        for (Patron patron: patrons) {
//            Library.registerPatronToLibrary(patron);
//        }
    }

    @Test
    public void isPatronIdValid() {
    }

    @Test
    public void getPatronId() {
    }

    @Test
    public void borrowBook() {
    }

    @Test
    public void returnBook() {
    }

    @Test
    public void suggestBookToPatron() {
    }
}