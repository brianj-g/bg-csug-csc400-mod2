
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
* The Bag class is the implementation of a Bag ADT using a linked list, and uses a generic type variable for flexibility.
*  I have also included an iterator for ease of traversing the items from external methods.
*/
public class Bag<T> implements Iterable<T> {
	
	private Node first;  // First item in the linked list
	private int num;  // Counter variable
	
	// Method: Add a node to the linked list
	public void add(T item) {
		Node node = new Node();
		// Populate the new node with added item, and point to the original 'first'
		node.item = item;
		node.next = first;
		
		// Set the new node as first and increment the counter
		first = node;
		num++;
	}
	
	// Method: Remove a node from the linked list
	public void remove(T item) {
		// Ensure bag is not empty
		if (first == null) return;
		
		// Check to see if item is the first node and remove it from the list if so
		if (first.item.equals(item)) {
			first = first.next;
			num--;
			return;
		}
		
		// Instantiate nodes to prepare for removing
		Node current = first.next;
		Node previous = first;
		
		// Traverse through the linked list until either found or no more items
		while (current != null) {
			// Remove the item and end loop if found
			if (current.item.equals(item)) {
				previous.next = current.next;
				num--;
				return;
			}
			// Continue through the list if not yet found
			previous = current;
			current = current.next;
		}
	}
	
	// Method: Check for existence of a value
	public boolean contains(T item) {
		Node current = first;
		
		while(current != null) {
			if (current.item.equals(item)) {
				return true;
			}
			// advance to next node if not found
			current = current.next;
		}
		
		// return false if item is not found
		return false;
	}
	
	// Method: Count occurences of a value
	public int count(T item) {
		int c = 0; // initialize counter
		
		// Traverse the linked list and increment the counter when occurrences are found
		Node current = first;
		while (current != null) {
			// Increment the counter any time the item is found
			if(current.item.equals(item)) {
				c++;
			}
			// Advance to the next node
			current = current.next;
		}
		
		// Return the total count of the requested item
		return c;
	}
	
	/* Additional methods to extend the Bag class (Module 2)
	 * int size(): This method should return the total number of elements in the bag, including duplicates.
	 * void merge(Bag<T> otherBag): This method should merge the elements of `otherBag` into the current bag.
	 * Bag<T> distinct(): This method should return a new bag that contains only the distinct elements from the current bag.
	 */
	
	// Return the total number of elements including duplicates (i.e., the number of nodes counted by the 'num' variable)
	public int size() {
	    // The 'num' variable keeps a count of nodes
	    return num;
	}
	
	// Merge two Bag objects by placing the elements of 'otherBag' into the list after the elements of the current bag
	public void merge(Bag<T> otherBag) {
	    // Create an iterator to traverse otherBag
	    Iterator<T> mergeIterator = otherBag.iterator();
	    // Iterate using the otherBag's hasNext() and next() methods and use the add() method of the current bag to add each element
	    while(mergeIterator.hasNext()) {
	        // Add the item to the original bag
	        add(mergeIterator.next());
	    }
	}
	
	// Return a new bag that contains only unique elements
	public Bag<T> distinct() {
	    // Instantiate a new bag for the unique elements
	    Bag<T> newBag = new Bag<T>();
	    // Create an item of T to store transient Bag items
	    T item;
	    
	    Iterator<T> currentIterator = this.iterator();
	    
	    // Iterate current bag and place an item in the new bag if it doesn't already contain that item
	    while(currentIterator.hasNext()) {
	        // Place next value into a temporary item
	        item = currentIterator.next();
	        
	        // Use the current bag's 'contains()' method to check existence and add the item to the new bag
	        if(!newBag.contains(item)) {
	            newBag.add(item);
	        }
	    }
	    
	    // return the new Bag
	    return newBag;
	}
	// End of additional methods

	// Nested class for linked list nodes
	private class Node {
		T item;
		Node next;
	}
	
	// Nested class to implement the iterator.  This makes it simpler to cycle through instances of the Bag in external methods (like main)..
	private class BagIterator implements Iterator<T>{
		Bag<T>.Node current = first; // Nested class allows access to Bag's members
		
		// If 'current' is null, then the iteration is complete
		public boolean hasNext() {
			return (current != null);
		}
		
		// return the current item and move to the next node
		public T next() {
			if (!hasNext()) throw new NoSuchElementException("No next element");
			
			T element = current.item;
			current = current.next;
			
			return element;
		}
	}
	
	// Function to return an instance of the iterator
	public Iterator<T> iterator(){
		return new BagIterator();
	}
}

// Including a separate BagMain class to perform test functions and main method
class BagMain {
	
	// Iterate through the bag and print each item
	static <T> void printBag(Bag<T> bag) {
		for (T item : bag) {
			System.out.println(item);
		}
	}
	
	// Use the Bag's "contains()" method to check that a specific value exists at least once
	static <T> void containsTest(T myItem, Bag<T> myBag) {
		if (myBag.contains(myItem)) {
			System.out.println("Bag contains '" + myItem + "'");
		} else {
			System.out.println("Bag does NOT contain '" + myItem + "'");
		}
	}
	
	// Use the Bag's "count()" method to count occurrences of a specific value
	static <T> void countTest(T myItem, Bag<T> myBag) {
		System.out.printf("Bag contains %d occurences of '%s'\n", myBag.count(myItem), myItem);
	}
	
	// Main function
	public static void main(String args[]) {
		// Create two instance of Bag
		Bag<String> firstBag = new Bag<String>();
		Bag<String> secondBag = new Bag<String>();
		
	      // For ease of testing, two arrays are used to populate words into the Bag
        String[] fruitList = {"orange", "apple", "apple", "banana", "pear", "banana", "banana", "kiwi", "orange"};
        String[] vegetableList = {"lettuce", "celery", "lettuce", "spinach", "carrot", "celery", "spinach", "lettuce", "carrot", "pepper"};
        
        // Iterate through the arrays and use the "add()" function to add items to each respective Bag
        for (String string : fruitList) {
            firstBag.add(string);
        }
        
        for (String string : vegetableList) {
            secondBag.add(string);
        }
        
        // Print out the size of each bag
        System.out.println();
        System.out.println("Printing bag sizes...");
        System.out.println("firstBag size: " + firstBag.size());
        System.out.println("secondBag size: " + secondBag.size());
		System.out.println();
        
		// Merge the two bags and print the contents
		System.out.println("Merging bags 1 and 2...");
		System.out.println();
        firstBag.merge(secondBag);
        System.out.println("Size of merged bag: " + firstBag.size());
        System.out.println("Printing contents of firstBag containing merged items...");
        for (String item : firstBag) {
            System.out.println(item);
        }
        System.out.println();
        
        // Create new bag with unique items and print the items
        System.out.println("Creating a new bag with only distinct items from bags 1 and 2...");
        System.out.println();
        Bag<String> newBag = firstBag.distinct();
        System.out.println("Size of new bag: " + newBag.size());
        System.out.println("Printing the contents of newBag containing distinct items");
        for (String item : newBag) {
            System.out.println(item);
        }
	}
}
