// --== CS400 File Header Information ==--
// Name: Vadym Prokopov
// Email: prokopov@wisc.edu
// Team: EE
// TA: Keren Chen
// Lecturer: Florian Heimerl
// Notes to Grader: System tests were not implemented (as written in the proposal) due to them 
// being equivalent (and effective if not less) as testing individual methods of the BookStore class

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestSuite {

	protected Book _bookInstance = null;
	protected BookStore _bookStoreInstance = null;

	@BeforeEach
	public void createBookInstance() {
		_bookInstance = new Book(2001484100, "Turbulent Runner", "Action", 4);	
		try {
			_bookStoreInstance = new BookStore();
		} catch (Exception e) {
			assertEquals(true, false);
		}
	}

	@Test
	public void testBookMethods() {
		// This test tests all of the Book class getter and setter methods
		// Test getter methods
		assertEquals(2001484100, _bookInstance.getIsbn());
		assertEquals("Turbulent Runner", _bookInstance.getTitle());
		assertEquals("Action", _bookInstance.getGenre());
		assertEquals(4, _bookInstance.getQuantity());

		// Test setter methods
		_bookInstance.setQuantity(10);
		assertEquals(10, _bookInstance.getQuantity());

	}

	@Test
	public void testBookStoreInitial() {
		// Tests that the BookStore contains initial books in BookData.txt
		assertEquals(true, _bookStoreInstance.containsBook(501145254));
		assertEquals(true, _bookStoreInstance.containsBook(771681926));
		assertEquals(true, _bookStoreInstance.containsBook(442402973));
		assertEquals(true, _bookStoreInstance.containsBook(64442466));
	}

	@Test
	public void testBookStoreGet() {
		// Test BookStore's retrieval method
		Book book = _bookStoreInstance.getBook(501145254);
		assertEquals(501145254, book.getIsbn());
		assertEquals("My_Own_Words", book.getTitle());
		assertEquals("Bibliography", book.getGenre());
		assertEquals(2, book.getQuantity());
	}

	@Test
	public void testBookStoreExistingBookDonation() {
		// Test donation and retrieval of an existing Book in the BookStore
		BookStore _bookStoreInstance = new BookStore();
		Book tmp_book = new Book(501145254, "My_Own_Words", "Bibliography", 2);
		assertEquals(true, _bookStoreInstance.donate(501145254, tmp_book));
		Book book = _bookStoreInstance.getBook(501145254);
		assertEquals(4, book.getQuantity());
	}

	@Test
	public void testBookStoreNewBookDonation() {
		// Test donation and retrieval of a NEW Book in the BookStore
		Book tmp_book = new Book(2001484100, "Turbulent Runner", "Action", 4);
		assertEquals(true, _bookStoreInstance.donate(2001484100, tmp_book));
		Book book = _bookStoreInstance.getBook(2001484100);
		assertEquals(2001484100, book.getIsbn());
		assertEquals("Turbulent Runner", book.getTitle());
		assertEquals("Action", book.getGenre());
		assertEquals(4, book.getQuantity());
	}

	@Test
	public void testNonExistentBooks() {
		// Test retrieval of a non-existent Book in the BookStore
		for (int i = 0; i <= 1000; i++) {
			try {
				_bookStoreInstance.getBook(i);
				assertEquals(false, true);
			} catch (Exception e) {
				continue;
			}
		}
	}

	@Test
	public void testBookStoreSteal() {
		// Test stealing of all books in the BookStore (clear)
		_bookStoreInstance.steal();
		assertEquals(0, _bookStoreInstance.size());
	}

	@Test
	public void testBookStoreCount() {
		// Tests BookStore's count method
		assertEquals(11, _bookStoreInstance.size());
		Book tmp_book = new Book(2001484100, "Turbulent Runner", "Action", 23);
		_bookStoreInstance.donate(2001484100, tmp_book);
		assertEquals(34, _bookStoreInstance.size());
	}
}
