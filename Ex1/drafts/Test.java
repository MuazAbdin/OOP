public class Test {
    public static void main(String[] args) {
        Book[] books = new Book[5];
        books[0] = new Book("Animal Farm", "George Orwell", 1935, 5, 8, 4);
        books[1] = new Book("Ottoman Empire", "Halil Inalcik", 1985, 0, 3, 10);
        books[2] = new Book("Sherlock Holmes", "Arthur Dowell", 1890, 5, 9, 3);
        books[3] = new Book("Laurel & Hardy", "John McCabe", 1915, 8, 4, 1);
        books[4] = new Book("Great Expectations", "Charles Dickens", 1860, 2, 10, 3);
//
//        books[1].setBorrowerId(20_200_010);
//        books[1].returnBook();
//
//        for (Book book : books) {
//            System.out.println(book.stringRepresentation() + "\t" + book.getCurrentBorrowerId());
//        }
//        System.out.println("----------------------------------------------");
//
        Patron[] patrons = new Patron[3];
        patrons[0] = new Patron("Muaz", "Abdeen", 4, 7, 7, 90);
        patrons[1] = new Patron("Luay", "Muhtaseb", 2, 6, 9, 100);
        patrons[2] = new Patron("Hazem", "Omari", 8, 6, 3, 80);
//
//        for (Patron patron : patrons) {
//            System.out.println(patron.stringRepresentation() + " \t" + patron.getBookScore(books[0]) +
//                    "\t" + patron.willEnjoyBook(books[0]) + "\t" + patron.getBookScore(books[1]) +
//                    "\t" + patron.willEnjoyBook(books[1]) + "\t" + patron.getBookScore(books[2]) +
//                    "\t" + patron.willEnjoyBook(books[2]));
//        }


        Library HUJILibrary = new Library(6, 2, 4);
        for (Book book : books) {
            HUJILibrary.addBookToLibrary(book);
        }
        for (Patron patron: patrons) {
            HUJILibrary.registerPatronToLibrary(patron);
        }

//        HUJILibrary.printBooks();
//        System.out.println();
//        HUJILibrary.printPatrons();
//        System.out.println();

        HUJILibrary.borrowBook(103, 20202);
        HUJILibrary.borrowBook(103, 20202);
        HUJILibrary.borrowBook(203, 20202);
        HUJILibrary.borrowBook(204, 20202);
        HUJILibrary.borrowBook(301, 20202);
        HUJILibrary.borrowBook(301, 20201);
        HUJILibrary.borrowBook(200, 20200);
        HUJILibrary.borrowBook(103, 20201);
        HUJILibrary.borrowBook(202, 20200);
        System.out.println();

        HUJILibrary.returnBook(103);
//        HUJILibrary.returnBook(103);
//        HUJILibrary.returnBook(405);
        HUJILibrary.returnBook(200);
        HUJILibrary.returnBook(204);
        HUJILibrary.returnBook(202);
        HUJILibrary.returnBook(301);
//        HUJILibrary.returnBook(301);
        System.out.println();

//        HUJILibrary.printBooks();
//        System.out.println();
//        HUJILibrary.printPatrons();


//        System.out.println(HUJILibrary.isBookIdValid(301));
//        System.out.println(HUJILibrary.isBookIdValid(300));
//        System.out.println(HUJILibrary.isPatronIdValid(20201));
//        System.out.println(HUJILibrary.isPatronIdValid(20191));


    }
}
