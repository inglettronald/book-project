
// --== CS400 File Header Information ==--
// Name: Ronnie Inglett
// Email: ringlett@wisc.edu
// Team: EE
// TA: Keren Chen
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>
import java.util.NoSuchElementException;
import java.util.LinkedList;

public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType>
{
	private LinkedList<Entry>[] field;
	private int itemCount;

	/**
	 * Constructor with given capacity.
	 * 
	 * @param capacity
	 */
	public HashTableMap(int capacity)
	{
		this.field = new LinkedList[capacity];
		this.itemCount = 0;
		// Instantiates the field for put method to function.
		for (int i = 0; i < field.length; ++i)
		{
			field[i] = new LinkedList<Entry>();
		}
	}

	/**
	 * Constructor with no given capacity. In this case, the default capacity will
	 * be set to 10.
	 */
	public HashTableMap()
	{
		this.field = new LinkedList[10];
		this.itemCount = 0;
		// Instantiates the field for put method to function.
		for (int i = 0; i < field.length; ++i)
		{
			field[i] = new LinkedList<Entry>();
		}
	}

	/**
	 * Inserts and item with given type and value.
	 * 
	 * @return boolean : returns false if item already exists in the field.
	 */
	@Override
	public boolean put(KeyType key, ValueType value)
	{
		// Checks to see if item exists by finding the index and iterating through that
		// list.
		int index = Math.abs(key.hashCode()) % field.length;

		// Create the entry based on the given parameters.
		Entry e = new Entry(key, value);

		// If such an element is found, return false and do not insert.
		if (this.containsKey(key))
			return false;

		// Since we know we will insert now, immediately increment size and check if
		// rehashing will be necessary.
		++itemCount;
		
		// Then we can safely insert.
		field[index].add(e);
		
		// If the array is full, we need to rehash.
		if (isFull())
			rehash();
		return true;
	}

	/**
	 * Searches for the Value for a given KeyType.
	 * 
	 * @return ValueType val
	 */
	@Override
	public ValueType get(KeyType key) throws NoSuchElementException
	{
		// Find the given index for the key
		int index = Math.abs(key.hashCode()) % field.length;

		// If it contains an entry with that key, return the value.
		for (Entry e : field[index])
		{
			if (e.key.equals(key))
				return e.val;
		}

		// If it completes the for loop without returning, we know the element doesn't
		// exist.
		throw new NoSuchElementException();
	}

	/**
	 * Returns the amount of items currently in the hash table.
	 * 
	 * @return itemCount
	 */
	@Override
	public int size()
	{
		return itemCount;
	}

	/**
	 * Checks to see if the hash table has an element with that given key.
	 * 
	 * @param key
	 * @return boolean
	 */
	@Override
	public boolean containsKey(KeyType key)
	{
		// This just tries to use the get method, and if there's an exception, we know
		// the key doesn't exist.
		try
		{
			get(key);
		} catch (NoSuchElementException e)
		{
			return false;
		}
		return true;
	}

	/**
	 * Removes an item from the hash table. Returns null if value does not exist.
	 * Upon successful removal, a reference to the value that was removed will be
	 * returned.
	 * 
	 * @param Keytype key
	 * @return ValueType val
	 */
	@Override
	public ValueType remove(KeyType key)
	{
		// Finds the index for that key.
		int index = Math.abs(key.hashCode()) % field.length;
		
		// Basically does the same thing as the get method, and searches for an object
		// with that key.
		// If that object is found, the index is being tracked and it will be removed.
		int i = 0;
		boolean isFound = false;
		ValueType val = null;

		for (Entry e : field[index])
		{
			if (e.key.equals(key))
			{
				val = e.val;
				isFound = true;
			}
			if (!isFound)
				++i;
		}

		// Checks if the value exists and returns an appropriate result.
		if (isFound)
		{
			field[index].remove(i);
			return val;
		}
		return null;
	}

	/**
	 * Clears all of the array. The array will remain at it's current size.
	 */
	@Override
	public void clear()
	{
		// Loops through the indices of the given array, using the built in
		// java.util.LinkedList clear()
		// method.
		for (int i = 0; i < field.length; ++i)
		{
			field[i].clear();
		}
		
		// Resets item count
		itemCount = 0;

	}

	/**
	 * Checks to see if the total number of elements is greater than or equal to 80%
	 * of the total table length.
	 * 
	 * @return boolean
	 */
	public boolean isFull()
	{
		if (this.itemCount >= .8 * field.length)
			return true;
		return false;
	}

	/**
	 * Creates a new, larger array and rehashes the values from the old field.
	 * Finally, it sets the old array to equal the new array.
	 */
	public void rehash()
	{
		// Create the new array. This is sized the way it is to favor prime numbers and
		// a more even distribution
		// among the table.
		LinkedList<Entry>[] newField = new LinkedList[field.length * 2];

		// Instantiates the new field for put method to function.
		for (int i = 0; i < newField.length; ++i)
		{
			newField[i] = new LinkedList<Entry>();
		}

		// Loop through both the array and the lists
		for (int i = 0; i < field.length; ++i)
		{
			for (Entry e : field[i])
			{
				// Calculate the new hash key and add it to the correct index
				int newPos = Math.abs(e.key.hashCode()) % newField.length;
				newField[newPos].add(e);
			}
		}

		// Replace the old array
		field = newField;
	}
	
	/**
	 * Private inner class for packaging keys/values.
	 * 
	 * @author Ronnie Inglett
	 */
	private class Entry
	{
		KeyType key;
		ValueType val;

		Entry(KeyType key, ValueType val)
		{
			this.key = key;
			this.val = val;
		}
	}
}