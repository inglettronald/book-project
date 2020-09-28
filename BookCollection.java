import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This represents a collection of books stored in an array
 * 
 * @author elaine
 *
 */
public class BookCollection {
  private Book[] initialBooksArray; // book array to store book
  private int size; // number of different books stored in collection


  /**
   * Gets the array of books
   * 
   * @return the initialBooksArray
 * @throws FileNotFoundException 
   */
  public Book[] getInitialBooks() {
	  try {
		addInitialBooksToTable();
	} catch (FileNotFoundException e) {
		System.out.println("Did not find initial books file.");
	}
	  return initialBooksArray;
  }


  /**
   * Creates instance of book collection
   * 
   * @param initialBooksArray
   */
  public BookCollection(int capacity) {
    super();
    this.initialBooksArray = new Book[capacity];
  }


  /**
   * Adds initial list of book data into book collection
   * 
   * @throws FileNotFoundException if initial list of books is not found
   * 
   */
  public void addInitialBooksToTable() throws FileNotFoundException {
    File file = new File("BookData.txt");
    Scanner inFile = new Scanner(file);

    int isbn = 0;
    String title = null; // spaces are indicated with _
    //int price = 0;
    String genre = null;
    int quantity = 0;

    while (inFile.hasNextLine()) {
      String line = inFile.nextLine();
      Scanner readLine = new Scanner(line);

      while (readLine.hasNext()) {
        isbn = readLine.nextInt(); // first 3 numbers (978) of actual ISBN # are cut out, thus isbn
                                   // variable stores last 10 digits of actual ISBN # (starts with 1
                                   // or 0)
        title = readLine.next();
        //price = readLine.nextInt();
        genre = readLine.next();
        quantity = readLine.nextInt();
      }
      // creates book instances and adds to book collection array
      readLine.close();
      Book book = new Book(isbn, title, genre, quantity);
      initialBooksArray[size] = book;
      size++;
      
    }
    inFile.close();
  }


  /**
   * Gets size of array
   * 
   * @return the size number of different books stored
   */
  public int getSize() {
    return size;
  }

}
