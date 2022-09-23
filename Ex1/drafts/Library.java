/**
 * This class represents a library, which hold a collection of books. Patrons can register at the library
 * to be able to check out books, if a copy of the requested book is available.
 */
class Library {

    /** The maximal number of books this library can hold. */
    final private int _maxBookCapacity;

    /** The maximal number of books this library allows a single patron to borrow at the same time. */
    final private int _maxBorrowedBooks;

    /** The maximal number of registered patrons this library can handle. */
    final private int _maxPatronCapacity;

    /** List of all registered books in the library */
    private Book[] _booksList;

    /** List of registered books IDs, book ID index in this array is the same as book's in the
     * _booksList array.
     */
    private int[] _booksIDs;

    /** Number of books have been added to the library */
    private int _booksAdded;

    /** List of registered books availability, if a book is available this array holds (true) at the same
     * index of the book in the _booksList array, otherwise it is (false).
     */
    private boolean[] _availableBooks;

    /** List of all registered patrons in the library */
    private Patron[] _patronsList;

    /** List of registered patron IDs, patron ID index in this array is the same as patron's in the
     * _patronsList array.
     */
    private int[] _patronsIDs;

    /** Number of patrons have been added to the library */
    private int _patronsAdded;

    /** List of books borrowed by each registered patron.
     */
    private int[] _booksPerPatron;



    /*----=  Constructors  =-----*/

    /**
     * Creates a new library with the given parameters.
     * @param maxBookCapacity The maximal number of books this library can hold.
     * @param maxBorrowedBooks The maximal number of books this library allows a single patron to borrow at
     *                        the same time.
     * @param maxPatronCapacity The maximal number of registered patrons this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity){
        _maxBookCapacity = maxBookCapacity;
        _maxBorrowedBooks = maxBorrowedBooks;
        _maxPatronCapacity = maxPatronCapacity;

        _booksList = new Book[_maxBookCapacity];
        _booksIDs = new int[_maxBookCapacity];
        _availableBooks = new boolean[_maxBookCapacity];
        _booksAdded = 0;

        _patronsList = new Patron[_maxPatronCapacity];
        _patronsIDs = new int[_maxPatronCapacity];
        _booksPerPatron = new int[_maxPatronCapacity];
        _patronsAdded = 0;
    }

    /*----=  Instance Methods  =-----*/

    int getBooksLen() {
        return _booksList.length;
    }

    int getPatronsLen() {
        return _patronsList.length;
    }

    void printBooks() {
        for (int i=0; i<_maxBookCapacity; ++i) {
            if (_booksList[i] != null)
                System.out.println(_booksList[i].stringRepresentation() +
                        " \t [ " + getBookId(_booksList[i]) + " =?= " + _booksIDs[i] +
                        " ] \t [ " + isBookAvailable(getBookId(_booksList[i])) + " =?= " + _availableBooks[i] +
                        " ] \t ( " + _booksList[i].getCurrentBorrowerId() +
                        " ) \t" + _booksAdded + "\t" + getBooksLen());
            else
                System.out.println(_booksList[i]);
        }
    }

    void printPatrons() {
        for (int i=0; i<_maxPatronCapacity; ++i) {
            if (_patronsList[i] != null)
                System.out.println(_patronsList[i].stringRepresentation() +
                        " \t [ " + getPatronId(_patronsList[i]) + " =?= " + _patronsIDs[i] +
                        " ] \t [ " + + _booksPerPatron[i] +
                        " ] \t" + _patronsAdded + "\t" + getPatronsLen() +
                        "\t suggested = ( " +
                        suggestBookToPatron(getPatronId(_patronsList[i])).stringRepresentation() + " ).");
            else
                System.out.println(_patronsList[i]);
        }
    }

    /**
     * Adds the given book to this library, if there is place available, and it isn't already in the library.
     * @param book The book to add to this library.
     * @return a non-negative id number for the book if there was a spot and the book was successfully
     * added, or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book){
        if (_booksAdded == _maxBookCapacity)
            return -1;
        int bookID = getBookId(book);
        if (bookID != -1)
            return bookID;
        _booksList[_booksAdded] = book;
        bookID = generateBookID(book);
        _booksIDs[_booksAdded] = bookID;
        _availableBooks[_booksAdded] = true;
        ++_booksAdded;
        return bookID;
    }

    /**
     * Generates an ID for the newly added book.
     * @param book The book to generate ID for.
     * @return The ID for the newly added book.
     */
    private int generateBookID(Book book) {
        int IDLength = 0;
        int tempNum = _maxBookCapacity;
        while (tempNum != 0){
            ++IDLength;
            tempNum /= 10;
        }
        int category;
        int max = Math.max(book.comicValue, Math.max(book.dramaticValue, book.educationalValue));
        if (max == book.comicValue) {
            category = 10;
        } else if (max == book.dramaticValue) {
            category = 20;
        } else {
            category = 30;
        }
        return category * (int) Math.pow(10, IDLength) + _booksAdded;
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     * @param bookId bookId - The id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId) {
        for (int ID : _booksIDs) {
            if (bookId == ID)
                return true;
        }
        return false;
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     * @param book The book for which to find the id number.
     * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book){
        int idx = 0;
        for (Book _book : _booksList) {
            if (_book == book) {
                return _booksIDs[idx];
            }
//            if (_book != null) {
//                if (_book.title.equalsIgnoreCase(book.title))
//                    return _booksIDs[idx];
//            }
            ++idx;
        }
        return -1;
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     * @param bookId The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId){
        if (isBookIdValid(bookId))
            return _availableBooks[getBookIndex(bookId)];
        return false;
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     * @param patron The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot and the patron was successfully
     * registered or if the patron was already registered. a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron){
        if (_patronsAdded == _maxPatronCapacity)
            return -1;
        int patronID = getPatronId(patron);
        if (patronID != -1)
            return patronID;
        _patronsList[_patronsAdded] = patron;
        patronID = generatePatronID();
        _patronsIDs[_patronsAdded] = patronID;
        ++_patronsAdded;
        return patronID;
    }

    private int generatePatronID() {
        int IDLong = 0;
        int tempNum = _maxPatronCapacity;
        while (tempNum != 0){
            ++IDLong;
            tempNum /= 10;
        }
        return 2020 * (int) Math.pow(10, IDLong) + _patronsAdded;
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     * @param patronId The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId){
        for (int ID : _patronsIDs) {
            if (patronId == ID)
                return true;
        }
        return false;
    }

    /**
     * Returns the non-negative id number of the given patron if he is registered to this library,
     * -1 otherwise.
     * @param patron The patron for which to find the id number.
     * @return a non-negative id number of the given patron if he is registered to this library,
     * -1 otherwise.
     */
    int getPatronId(Patron patron){
        int idx = 0;
        for (Patron _patron : _patronsList) {
            if (_patron == patron) {
                return _patronsIDs[idx];
            }
//            if (_patron != null) {
//                if (_patron.firstName.equalsIgnoreCase(patron.firstName) &&
//                        _patron.lastName.equalsIgnoreCase(patron.lastName))
//                    return _patronsIDs[idx];
//            }
            ++idx;
        }
        return -1;
    }

    private int getBookIndex(int bookId) {
        int bookIndex = 0;
        for (int ID : _booksIDs) {
            if (bookId == ID)
                break;
            bookIndex++;
        }
        return bookIndex;
    }

    private int getPatronIndex(int patronId) {
        int patronIndex = 0;
        for (int ID : _patronsIDs) {
            if (patronId == ID)
                break;
            patronIndex++;
        }
        return patronIndex;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id, if this
     * book is available, the given patron isn't already borrowing the maximal number of books allowed, and
     * if the patron will enjoy this book.
     * @param bookId The id number of the book to borrow.
     * @param patronId The id number of the patron that will borrow the book.
     * @return true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId){
        if (!isBookAvailable(bookId) || !isPatronIdValid(patronId)) {
            System.out.println("NOT VALID");
            return false;
        }
        int bookIndex = getBookIndex(bookId);
        int patronIndex = getPatronIndex(patronId);

        if (_booksPerPatron[patronIndex] == _maxBorrowedBooks) {
            System.out.println("MAX BORROWS");
            return false;
        }

        if (!_patronsList[patronIndex].willEnjoyBook(_booksList[bookIndex])) {
            System.out.println("NOT ENJOYABLE");
            return false;
        }

        _availableBooks[bookIndex] = false;
        _booksPerPatron[patronIndex]++;
        _booksList[bookIndex].setBorrowerId(patronId);
        System.out.println("WELL DONE");
        return true;
    }

    /**
     * Return the given book.
     * @param bookId The id number of the book to return.
     */
    void returnBook(int bookId){
        if (isBookIdValid(bookId) && !isBookAvailable(bookId)) {
            int bookIndex = getBookIndex(bookId);
            int borrowerID = _booksList[bookIndex].getCurrentBorrowerId();
            if (borrowerID != -1) {
                int patronIndex = getPatronIndex(borrowerID);
                _availableBooks[bookIndex] = true;
                _booksPerPatron[patronIndex]--;
                _booksList[bookIndex].returnBook();
            }
//            return;
        }
//        System.out.println("PROBLEM ... !");
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available books he
     * will enjoy, if any such exist.
     * @param patronId The id number of the patron to suggest the book to.
     * @return The available book the patron with the given ID will enjoy the most. Null if no book is
     * available.
     */
    Book suggestBookToPatron(int patronId){
        if (isPatronIdValid(patronId)) {
            int patronIndex = getPatronIndex(patronId);
            Book bestBook = _booksList[0];
            int idx = 0;
            for (Book _book : _booksList) {
                if (_book != null) {
                    int bestScore = _patronsList[patronIndex].getBookScore(bestBook);
                    int currentScore = _patronsList[patronIndex].getBookScore(_book);
                    if (currentScore > bestScore)
                        bestBook = _booksList[idx];
                }

                idx++;
            }
            return bestBook;
        }
        return null;
    }

}
