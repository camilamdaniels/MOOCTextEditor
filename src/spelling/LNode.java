package spelling;

public class LNode<E> {
	public LNode<E> prev;
	public LNode<E> next;
	public E val;
	
	public LNode(E tn) {
		this.val = tn;
	}
}
