import java.util.ArrayList;
import java.util.Iterator;

import edu.neumont.util.Queue;


public class LinkedList<T> implements Queue<T> {
	private static class Node<T>
	{
		public T data;
		public Node next;
		
		public Node(T data)
		{
			this.data = data;
		}
	}
	
	private static class NodeIterator<T> implements Iterator<Object>
	{
		private Node<T> node;
		
		public NodeIterator(Node first)
		{
			node = first;
		}
		@Override
		public boolean hasNext() {
			return (node != null);
		}

		@Override
		public Object next() {
			Node result = node;
			node = node.next;
			return (Object)result.data;
		}

		@Override
		public void remove() {
			node = node.next;
		}
		
	}
	
	private Node head, tail;
	private NodeIterator<T> iterator;
	
	@Override
	public Iterator<T> iterator() {
		iterator = new NodeIterator<T>(head);
		return (Iterator<T>) iterator;
	}

	@Override
	public T poll() {
		T data = null;
		if(head != null)
		{
			Node<T> node = head;
			head = head.next;
			data = node.data;
		}
		return data;
	}

	@Override
	public boolean offer(T t) {
		if(head != null)
		{
			tail.next = new Node<T>(t);
			tail = tail.next;
		}
		else
		{
			head = new Node<T>(t);
			tail = head;
		}
		return true;
	}

	@Override
	public T peek() {
		Node<T> node = head;
		return node.data;
	}

}
