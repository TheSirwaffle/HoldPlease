import java.util.Iterator;

import edu.neumont.util.Client;
import edu.neumont.util.QueueableService;


public class GroceryStore implements QueueableService {
	ArrayList<LinkedList<Client>> lines;
	int numLines;
	
	public GroceryStore(int numLines)
	{
		lines = new ArrayList<LinkedList<Client>>();
		this.numLines = numLines;
		
		for(int i=0; i<numLines;i++)
		{
			LinkedList<Client> start = new LinkedList<Client>();
			lines.add(start);
		}
	}
	
	private int getShortestLine()
	{
		int shortest = 50000;
		int shortestIndex = 0;
		for(int i=0; i<numLines; i++)
		{
			int numClients = 0;
			Iterator<Client> iterator = lines.get(i).iterator();
			while(iterator.hasNext())
			{
				iterator.next();
				numClients++;
			}
			if(numClients < shortest)
			{
				shortestIndex = i;
				shortest = numClients;
			}
		}
		return shortestIndex;
	}
	
	public void printValues()
	{
		for(int i=0; i<numLines; i++)
		{
			Iterator<Client> iterator = lines.get(i).iterator();
			System.out.print("Line #" + i);
			int count = 1;
			while(iterator.hasNext())
			{
				Client c = iterator.next();
				System.out.print(" Client " +count+".) Service Time: " + c.getExpectedServiceTime() + " Wait Time: " + getClientWaitTime(c) + ", ");
				count++;
			}
			System.out.println();
		}
		System.out.println("Average line wait time: " +getAverageClientWaitTime());
		System.out.println();
	}

	@Override
	public void advanceMinute() {
		for(int i=0; i<numLines; i++)
		{
			
			Iterator<Client> iterator = lines.get(i).iterator();
			if(iterator.hasNext())
			{
					Client c =iterator.next();
					//c.timeToTake -= 1;
					if(c.servedMinute() <= 0)
					{
						lines.get(i).poll();
					}
			}
		}
	}

	@Override
	public boolean addClient(Client client) {
		int line = getShortestLine();
//		double waitTime = 0;
//		Iterator<Client> iterator = lines.get(line).iterator();
//		while(iterator.hasNext())
//		{
//			waitTime += iterator.next().getExpectedServiceTime();
//		}
//		client.waitTime = waitTime;
		lines.get(line).offer(client);
		return true;
	}

	@Override
	public double getClientWaitTime(Client client) {
		boolean found = false;
		double waitTime = 0;
		for(int i=0; i<numLines && !found; i++)
		{
			waitTime = 0;
			Iterator<Client> iterator = lines.get(i).iterator();
			while(iterator.hasNext() && !found)
			{
				Client c = iterator.next();
				if(c == client)
				{
					found = true;
				}
				else
				{
					waitTime += c.getExpectedServiceTime();
				}
			}
		}
		return waitTime;
	}

	@Override
	public double getAverageClientWaitTime() {
		double[] lineAverages = new double[numLines];
		for(int i=0; i<numLines; i++)
		{
			Iterator<Client> iterator = lines.get(i).iterator();
			int numClients = 0;
			while(iterator.hasNext())
			{
				lineAverages[i] += getClientWaitTime(iterator.next());
				numClients++;
			}
			lineAverages[i] /= (float)numClients;
		}
		double finalAverage = 0;
		for(int i=0; i<lineAverages.length;i++)
		{
			finalAverage += lineAverages[i];
		}
		finalAverage /= lineAverages.length;
		return finalAverage;
	}

}
