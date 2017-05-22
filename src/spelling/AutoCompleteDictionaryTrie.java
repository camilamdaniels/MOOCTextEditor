package spelling;

import java.util.List;
import java.util.Set;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
    	size = 0;
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
		if (isWord(word.toLowerCase())) {
			return false;
		}
	    //TODO: Implement this method.
		char[] cArr = word.toLowerCase().toCharArray();
		TrieNode tn = root;
		for (int i=0; i<cArr.length; i++) {
			char currChar = cArr[i];
			if (tn.getChild(currChar) == null) {
				tn.insert(currChar);
			}
			tn = tn.getChild(currChar);
		}
		tn.setEndsWord(true);
		size ++;
	    return true;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		char[] cArr = s.toLowerCase().toCharArray();
		TrieNode tn = root;
		for (int i=0; i<cArr.length; i++) {
			TrieNode curr = tn.getChild(cArr[i]);
			if (curr == null) {
				return false;
			}
			tn = tn.getChild(cArr[i]);
		}
		return tn.endsWord();
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 List<String> answer = new ArrayList<String>();
    	 MyLinkedList<TrieNode> queue = new MyLinkedList<TrieNode>();
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 TrieNode tn = root;
    	 char[] target = prefix.toLowerCase().toCharArray();
    	 for (int i=0; i<target.length; i++) {
    		 TrieNode curr = tn.getChild(target[i]);
    		 if (curr == null) {
    			 return answer;
    		 } else {
    			 tn = tn.getChild(target[i]); 
    		 }
    	 }
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 if (target.length == 0) {
    		 queue.add(root);
    	 } 
    	 
    	 queue.add(tn);
    	 
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	while (!queue.isEmpty() && answer.size() < numCompletions) {
        	 //       remove the first Node from the queue
    		TrieNode curr = queue.get(0);
        	 //       If it is a word, add it to the completions list
    		 if (curr.endsWord() && !answer.contains(curr.getText())) {
    			 //System.out.println(curr.getText());
    			 answer.add(curr.getText());
    		 }
    		 
    		 Set<Character> chars = curr.getValidNextCharacters();
    		 for (Character c: chars) {
    			 TrieNode t = curr.getChild(c);
    			 //    	       Add all of its child nodes to the back of the queue
    			 queue.add(t);
    		 }
    		 
    		 if (queue.size() <= 1) {
    			 break;
    		 }
    		 queue.remove(0);
    	 } 
         return answer;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	
 	public class MyLinkedList<E> extends AbstractList<E> {
 		public LNode<E> head;
 		public LNode<E> tail;
 		public int lSize;
 		
 		public MyLinkedList() {
 			this.head = new LNode<E>(null);
 			this.tail = new LNode<E>(null);
 			
 			head.next = tail;
 			//head.prev = null;
 			//head.val = null;
 			
 			//tail.next = null;
 			tail.prev = head;
 			//tail.val = null;
 		}

 		public boolean add(E element ) 
 		{
 			if (element == null) {
 				throw new NullPointerException("LLNode object cannot store null pointers");
 			}
 			
 			LNode<E> temp = new LNode<E>(element);
 			LNode<E> current = head;
 			for (int i=0; i< lSize; i++) {
 				current = current.next;
 			}
 			current.next = temp;
 			temp.next = tail;
 			lSize++;
 			return true;
 		}
 		
 		public E remove(int index) 
 		{
 			// TODO: Implement this method
 			LNode<E> current = head;
 			for (int i=1; i<index; i++) {
 				if (current.next == null) {
 					throw new IndexOutOfBoundsException("Index out of bounds");
 				}
 				current = current.next;
 			}
 			E ans = current.next.val;
 			current.next = current.next.next;
 			current.next.next.prev = current;
 			
 			lSize--;
 			return ans;
 		}
 		
 		@Override
		public E get(int index) {
			// TODO Auto-generated method stub
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException("Index out of bounds");
			}
			
			LNode<E> current = head.next;
			
			for (int i=0; i< index; i++) {
				
				if(current.next == null) {
					return null;
				}
					current = current.next;
			}
			return current.val;
		}

		@Override
		public int size() {
			// TODO Auto-generated method stub
			return lSize;
		}
 		
 	}
}
