package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		this.head = new LLNode<E>(null);
		this.tail = new LLNode<E>(null);
		this.size = 0;
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		if (element == null) {
			throw new NullPointerException("LLNode object cannot store null pointers");
		}
		
		LLNode<E> temp = new LLNode<E>(element);
		LLNode<E> current = head;
		for (int i=0; i< size; i++) {
			current = current.next;
		}
		current.next = temp;
		temp.next = tail;
		size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		
		LLNode<E> current = head.next;
		
		for (int i=0; i< index; i++) {
			
			if(current.next == null) {
				return null;
			}
				current = current.next;
		}
		return current.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		if (element == null) {
			throw new NullPointerException("LLNode object cannot store null pointers");
		}
		
		if (index <0 || index > size) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		
		LLNode<E> temp = new LLNode<E>(element);
		LLNode<E> current = head;
		for (int i=1; i<index && current.next != null; i++) {
			current = current.next;
		}
		temp.next = current.next.next;
		current.next = temp;
		size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		LLNode<E> current = head;
		for (int i=1; i<index; i++) {
			if (current.next == null) {
				throw new IndexOutOfBoundsException("Index out of bounds");
			}
			current = current.next;
		}
		E ans = current.next.data;
		current.next = current.next.next;
		current.next.next.prev = current;
		
		size--;
		return ans;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if (element == null) {
			throw new NullPointerException("Invalid input value");
		}
		LLNode<E> current = head;
		for (int i=1; i< index; i++) {
			if (current.next == null) {
				throw new IndexOutOfBoundsException("Index out of bounds");
			}
			current = current.next;
		}
		E old = current.data;
		current.data = element;
		
		return old;
	}   
}


