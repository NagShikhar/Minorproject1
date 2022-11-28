package nag;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class LeastConnection extends LoadBalancer {
	private final ReentrantLock mylock;
	
	int requests;
	int[] bursttime;
	int[] requestsOnServer = new int[3];  
	int[] loadOnServers = new int[3];  

	public LeastConnection(List<String> ipList , int BT[], int NUM_OF_REQUESTS) {
		super(ipList);
		mylock = new ReentrantLock();
		requests = NUM_OF_REQUESTS;
		bursttime = BT;
	}

	@Override
	String getIp(int clientNumber) {
		mylock.lock();
		
		try {
			int totalLoad = 0;
			
		    for(int i = 0 ; i < requests ; i++) {
		        loadOnServers[i % 3] += bursttime[i];
		        requestsOnServer[i % 3]++;
		        
		        totalLoad += bursttime[i];
		        
		        System.out.println(ipList.get(0));
			    System.out.println(ipList.get((i % 3) + 1));
		    }
		    
		    for(int i = 0 ; i < 3 ; i++) {
	
		        int currLoad = (loadOnServers[i] / requestsOnServer[i]);
		        
		        System.out.println("Average Load on Server : " + (i + 1) + " is " + currLoad);
		       
		        
		    }
		    
		    System.out.println("\n Total Average Load on a cluster (group of servers) is : " + (totalLoad / requests));
		}
		
		finally {
			mylock.unlock();
		}
		
	    return (clientNumber % 3 == 0) ? ipList.get(3) : ipList.get((clientNumber % 3));
	}

}

