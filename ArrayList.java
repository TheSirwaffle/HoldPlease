import java.util.Iterator;

import edu.neumont.util.List;


public class ArrayList<T> implements List<T> {
	private T[] values = (T[]) new Object[16];
	private int firstOpen;
	
	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(T t) {
		values[firstOpen] = t;
		obtainNextOpen();
		return true;
	}

	@Override
	public T get(int index) {
		return values[index];
	}

	@Override
	public boolean remove(T t) {
		boolean removed = false;
		for(int i=0; i<values.length; i++)
		{
			if(removed && (i+1) < values.length)
			{
				values[i] = values[i+1];
			}
			else if(values[i] == t)
			{
				removed = true;
				if(i+1 < values.length)
				{
					values[i] = values[i+1];
				}
				else
				{
					values[i] = null;
				}
			}
		}
		obtainNextOpen();
		return false;
	}

	@Override
	public int size() {
		int size = 0;
		for(int i=0; i<values.length; i++)
		{
			size += (values[i] != null)?1:0;
		}
		return size;
	}
	
	private void obtainNextOpen()
	{
		boolean found = false;
		for(int i=0; i<values.length && !found;i++)
		{
			if(values[i] == null)
			{
				firstOpen = i;
				found = true;
			}
		}
		if(!found)
		{
			T[] newValues = (T[])new Object[values.length*2];
			for(int i=0; i<values.length; i++)
			{
				newValues[i] = values[i];
			}
			firstOpen = values.length;
			values = newValues;
		}
	}

}
