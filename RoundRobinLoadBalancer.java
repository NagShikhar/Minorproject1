package nag;

import java.util.concurrent.locks.ReentrantLock;

import java.util.*;

public class RoundRobinLoadBalancer extends LoadBalancer {

	private int counter = 0;
	private final ReentrantLock mylock;
	int[] bursttime ;
	int requests  ;
//Threshhold quantum = 16; min =2 mid =8
	int Quantum = 2 ;
	int wait[];
	int totalTime[];
	int []oldBurstTime;
	int[] timeOnServer = new int[3]; 
	int[] requestsOnServer = new int[3];
	
	int total;
	
	String ip;


	public RoundRobinLoadBalancer(List<String> list, int[] BT, int NUM_OF_REQUESTS, int[] oldBT) {

		super(list);
		mylock = new ReentrantLock();
		bursttime = BT;
		oldBurstTime = oldBT;
		requests = NUM_OF_REQUESTS;
		wait = new int[requests];
		totalTime = new int[requests];
	}



	// SUPER KEYWORD IS USED AS REFRENCE VARIABLE WHICH IMMEDITALY REFERES OBJECT OF
	// PARENT CLASS

	// IMPLEMENTATION IS PROVIDED OF ABSTRACT METHOD
	public String getIp(int clientNumber) {
		//CALLING LOCK 
		mylock.lock();

		try {
			do {
				for(int i=0;i<requests;i++) {
					requestsOnServer[i % 3]++;
					
					if(bursttime[i]>Quantum)
					{

						for(int j=0;j<requests;j++)  {
							if(j!=i && bursttime[j]!=0) {
								wait[j]+=Quantum;
							}
						}
						
						bursttime[i]-=Quantum;
					}


					else {

						for(int j=0;j<requests;j++)  {
							if(j!=i && bursttime[j]!=0) {
								wait[j]+=bursttime[i];
							}
						}
						
						bursttime[i]=0;
						
						//SERVER ALLOCATION OF ROUND ROBIN
						ip = (clientNumber % 3 == 0) ? ipList.get(3) : ipList.get((clientNumber % 3));;
						
						
						counter += 1;
						
						if (counter == ipList.size()) {
							counter = 0;
						}
					}

					total = 0;

					for(int j = 0 ; j < requests ; j++) {
						total += bursttime[j];
					}
				}
			}while(total != 0);
			
			
			System.out.println("Request\t\t\tWaiting time\t\t\tTotal time");

			float total_wait = 0;
			float total_time = 0;

			for(int i = 0 ; i < requests ; i++) {
				total_wait += wait[i]; 
				
				totalTime[i] = oldBurstTime[i] + wait[i];
				total_time += totalTime[i];
				
				timeOnServer[i % 3] +=  totalTime[i];
				
				System.out.println("R" + (i + 1) + "\t\t\t" + wait[i] + "\t\t\t\t\t" + totalTime[i]);
			}

			System.out.println("Average waiting time is: " + (total_wait / requests));
			System.out.println("Average Total time is: " + (total_time / requests));
			
			for(int i = 0 ; i < 3 ; i++) {
		        System.out.println("Total Response  Time on Server : " + (i + 1) + " is " + timeOnServer[i]);
		        System.out.println("Average  Response Time on Server : " + (i + 1) + " is " + (timeOnServer[i] / requestsOnServer[i]));
			    timeOnServer[i]= 0;
			    requestsOnServer[i] = 0;
		    }
		}
		finally {
			mylock.unlock();
		}
		
		return ip;
	}

}

