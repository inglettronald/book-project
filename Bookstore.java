// --== CS400 File Header Information ==--
// Name: Ronnie Inglett & Keegan Ripley
// Email: ringlett@wisc.edu
// Team: EE
// TA: Keren Chen
// Lecturer: Florian Heimerl
// Notes to Grader: This class is meant to do the work of setting up the hash table for the incoming books. This has some preliminary stuff, so it will
// need to be constructed by the user.

import java.util.NoSuchElementException;
public class Bookstore extends BookCollection
{
	private HashTableMap<Integer, Book> table;
	private int numBooks;
	
	/**
	 * Constructor that sets up a HashTable for the book store. Begins with default size 20.
	 */
	public Bookstore()
	{
		super(20);
		Book[] books = getInitialBooks();
		table = new HashTableMap<Integer, Book>(20);
		for (Book b : books)
		{
			numBooks = numBooks + b.getQuantity();
			// If the object already exists in the hash table, we want to update the quantity of that item in the hash table. This ends up being somewhat
			// of a lengthy statement.
			if (table.containsKey(b.getIsbn()))
			{
				table.get(b.getIsbn()).setQuantity(table.get(b.getIsbn()).getQuantity() + b.getQuantity());
			}
			else
				table.put(b.getIsbn(), b);
			
		}
	}

	/**
	 * Allows user to determine if a book exists by looking it up by it's ISBN
	 * 
	 * @param key
	 * @return boolean
	 */
	public boolean containsKey(int key)
	{
		// Check to see if the hash table we created earlier has the key using the HashTableMap containsKey() method.
		if (table.containsKey(key))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Adds a book to the hash table, using the HashTableMap put method.
	 * 
	 * @param key
	 * @param book
	 * @return boolean : confirmation that the book was inserted
	 */
	public boolean donate(int key, Book book)
	{
		numBooks = numBooks + book.getQuantity();

		// If the object already exists in the hash table, we want to update the quantity of that item in the hash table. This ends up being somewhat
		// of a lengthy statement.
		if (table.containsKey(key))
		{
			table.get(key).setQuantity(table.get(key).getQuantity() + book.getQuantity());
		}
		else
			table.put(key, book);
		
		return (containsKey(key));
	}
	
	/**
	 * Simple method that removes a book from our hash table. Returns the given book's title as confirmation.
	 * 
	 * @param key
	 * @return String title
	 */
	public String remove(int key)
	{
		Book removed = table.get(key);
		table.remove(key);
		--numBooks;
		return removed.getTitle();
	}
	
	/**
	 * Clears the hash table if the book store is robbed.
	 */
	public void steal()
	{
		table.clear();
	}
	
	/**
	 * Returns the current amount of books.
	 */
	public int size()
	{
		return numBooks;
	}
	
	/**
	 * Gets a given book, given a key.
	 * @param key
	 * @return Book : object and all of it's associated things
	 * @throws NoSuchElementException
	 */
	public Book getBook(int key) throws NoSuchElementException
	{
		// table.get already throws an exception, so no need to check.
		return table.get(key);
	}
}
