import java.util.Iterator;

import edu.neumont.util.Queue;


public class PriorityQueue<T extends Comparable> implements Queue<T> {

	private static class Node<T extends Comparable>
	{
		public T data;
		public Node next;
		public Node(T type)
		{
			data = type;
		}
	}
	
	private static class NodeIterator<T extends Comparable> implements Iterator<Object>
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
			return (Object)result;
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
		return (Iterator<T>) (iterator = new NodeIterator(head));
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
			boolean test = (head == tail);
			tail = tail.next;
			if(test)
			{
				head.next = tail;
			}
			iterator = new NodeIterator<T>(head);
			while(iterator.hasNext())
			{
				Node<T> nodeData = (Node<T>)iterator.next();
				T data = (T)nodeData.data;
				if(data.compareTo(tail.data) > 0)
				{
					T temp = nodeData.data;
					nodeData.data = (T) tail.data;
					tail.data = temp;
				}
			}
		}
		else
		{
			head = new Node<T>(t);
			tail = head;
			iterator = new NodeIterator<T>(head);
		}
		return false;
	}
	
	public void printValues()
	{
		iterator = new NodeIterator<T>(head);
		while(iterator.hasNext())
		{
			Node<T> node = (Node<T>)iterator.next();
			System.out.println(node.data);
		}
	}

	@Override
	public T peek() {
		return (T) head.data;
	}

}
