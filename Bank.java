import java.util.Iterator;

import edu.neumont.util.Client;
import edu.neumont.util.QueueableService;


public class Bank implements QueueableService {
	Client[] tellers;
	LinkedList<Client> clients = new LinkedList<Client>();
	
	public Bank(int numTellers)
	{
		tellers = new Client[numTellers];
	}
	
	private int findNextOpenTeller(int[] waitTimes)
	{
		double shortestTime = 999999;
		int shortestIndex = 0;
		for(int i=0; i<tellers.length; i++)
		{
			if(waitTimes[i] < shortestTime)
			{
				shortestTime = waitTimes[i];
				shortestIndex = i;
			}
		}
		return shortestIndex;
	}
	
	public void printValues()
	{
		for(int i=0; i<tellers.length; i++)
		{
			System.out.println("Time to take: " + tellers[i].getExpectedServiceTime() + ", Wait time: " + getClientWaitTime(tellers[i]));
		}
		Iterator<Client> iterator = clients.iterator();
		while(iterator.hasNext())
		{
			Client c = iterator.next();
			System.out.println("Time to take: " + c.getExpectedServiceTime() + ", Wait time: " + getClientWaitTime(c));
		}
		System.out.println("Average wait time: " + getAverageClientWaitTime());
		System.out.println();
	}

	@Override
	public void advanceMinute() {
//		Iterator<Client> iterator = clients.iterator();
//		while(iterator.hasNext())
//		{
//			Client c = iterator.next();
//			c.waitTime -= 1;
//		}
		for(int i=0; i<tellers.length; i++)
		{
			//tellers[i].servedMinute();
			if(tellers[i].servedMinute() <= 0)
			{
				tellers[i] = clients.poll();
			}
		}
	}

	@Override
	public boolean addClient(Client client) {
		double[] waitTimes = new double[tellers.length];
		boolean added = false;
		for(int i=0; i<tellers.length&&!added;i++)
		{
			if(tellers[i] == null)
			{
				tellers[i] = client;
				added=true;
			}
			else
			{
				waitTimes[i] = tellers[i].getExpectedServiceTime();
			}
		}
		if(!added)
		{
			clients.offer(client);
		}
		return true;
	}

	@Override
	public double getClientWaitTime(Client client) {
		Iterator<Client> iterator = clients.iterator();
		int[] waitTimes = new int[tellers.length];
		for(int i=0; i<waitTimes.length; i++)
		{
			waitTimes[i] = tellers[i].getExpectedServiceTime();
		}
		boolean found = false; 
		int index;
		int finalTime = 0;
		while(iterator.hasNext() && !found)
		{
			index = findNextOpenTeller(waitTimes);
			Client c = iterator.next();
			if(c == client)
			{
				finalTime = waitTimes[index];
				found = true;
			}
			else
			{
				waitTimes[index] += c.getExpectedServiceTime();
			}
				
		}
		for(int i=0; i<tellers.length; i++)
		{
			finalTime = (tellers[i] == client)?0:finalTime;
		}
		return finalTime;
		
	}

	@Override
	public double getAverageClientWaitTime() {
		Iterator<Client> iterator = clients.iterator();
		int count = 0;
		double totalTime = 0;
		while(iterator.hasNext())
		{
			Client c = iterator.next();
			totalTime += getClientWaitTime(c);
			count++;
		}
		return totalTime/((double)count);
	}

}
