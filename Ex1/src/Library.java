/**
 * This class represents a library, which hold a collection of books. Patrons can register at the library
 * to be able to check out books, if a copy of the requested book is available.
 */
public class Library {

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

    /**
     * Adds the given book to this library, if there is place available, and it isn't already in the library.
     * @param book The book to add to this library.
     * @return a non-negative id number for the book if there was a spot and the book was successfully
     * added, or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book){
        int bookID = getBookId(book);
        if (bookID != -1)
            return bookID;
        if (_booksAdded == _maxBookCapacity)
            return -1;
        _booksList[_booksAdded] = book;
        bookID = _booksAdded;
        _booksIDs[_booksAdded] = bookID;
        _availableBooks[_booksAdded] = true;
        ++_booksAdded;
        return bookID;
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     * @param bookId bookId - The id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId) {
        for (int i=0; i < _booksAdded; ++i) {
            if (bookId == _booksIDs[i])
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
        for (int i=0; i<_booksAdded; ++i) {
            if (book == _booksList[i])
                return i;
//                return _booksIDs[i];
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
            return _availableBooks[bookId];
        return false;
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     * @param patron The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot and the patron was successfully
     * registered or if the patron was already registered. a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron){
        int patronID = getPatronId(patron);
        if (patronID != -1)
            return patronID;
        if (_patronsAdded == _maxPatronCapacity)
            return -1;
        _patronsList[_patronsAdded] = patron;
        patronID = _patronsAdded;
        _patronsIDs[_patronsAdded] = patronID;
        ++_patronsAdded;
        return patronID;
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     * @param patronId The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId){
        for (int i=0; i < _patronsAdded; ++i) {
            if (patronId == _patronsIDs[i])
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
        for (int i=0; i < _patronsAdded; ++i) {
            if (patron == _patronsList[i])
                return i;
//                return _patronsIDs[i];
        }
        return -1;
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
        if (!isBookAvailable(bookId) || !isPatronIdValid(patronId))
            return false;

        if (_booksPerPatron[patronId] == _maxBorrowedBooks)
            return false;

        if (!_patronsList[patronId].willEnjoyBook(_booksList[bookId]))
            return false;

        _availableBooks[bookId] = false;
        _booksPerPatron[patronId]++;
        _booksList[bookId].setBorrowerId(patronId);
        return true;
    }

    /**
     * Return the given book.
     * @param bookId The id number of the book to return.
     */
    void returnBook(int bookId){
        if (isBookIdValid(bookId) && !isBookAvailable(bookId)) {
            int borrowerID = _booksList[bookId].getCurrentBorrowerId();
            if (borrowerID != -1) {
                _availableBooks[bookId] = true;
                _booksPerPatron[borrowerID]--;
                _booksList[bookId].returnBook();
            }
        }
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
            Patron patron = _patronsList[patronId];
            Book bestBook = null;
            for (int i=0; i < _booksAdded; ++i) {
                if (isBookAvailable(getBookId(_booksList[i])) && patron.willEnjoyBook(_booksList[i])) {
                    int bestScore = _patronsList[patronId].getBookScore(bestBook);
                    int currentScore = _patronsList[patronId].getBookScore(_booksList[i]);
                    if (currentScore > bestScore)
                        bestBook = _booksList[i];
                }
            }
            return bestBook;
        }
        return null;
    }

}
