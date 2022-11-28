package nag;
import java.util.stream.IntStream;
import java.util.*;
public class Client {

    public static void main(String[] args) {
    	int selection;

    	System.out.println("__________________________________SYSTEM HARDWARE SPECIFICATION_____________________________________________________________________________________________________________________________________________");
    	System.out.println("");
    	System.out.println("");
    	
    	System.out.println("________________________________WE HAVE TOTAL THREE SERVERS OF SAME HARDWARE ABILITIES_____________________________________________________________________________________________________________________________________________  ");
    	System.out.println("");
    	System.out.println("");
    	System.out.println(" -SERVER 1 WITH IP 192.168.0.1");
    	System.out.println(" -SERVER 2 WITH IP 192.168.0.2");
    	System.out.println(" -SERVER 3 WITH IP 192.168.0.3");
    	
    	
    	
    	
    	try (Scanner sc = new Scanner(System.in)) {
			System.out.print("Enter No of Request - ");
			  int NUM_OF_REQUESTS = sc.nextInt() ;
			  
			  int [] BT = new int [NUM_OF_REQUESTS];
			  for (int i=0;i<NUM_OF_REQUESTS;i++) {
				  System.out.println("ENTER BURST TIME ");
				 
				  BT[i]=sc.nextInt();
			  }
			  
			  System.out.println("1.) ROUND ROBIN LOAD BALANCING ");
			  System.out.println("2.) LEAST CONNECTION LOAD BALANCING  ");
			  System.out.print("ENTER CHOICE :");
			  selection=sc.nextInt();
			  
			 
			  
		
			Client client = new Client();
			
			int [] oldBT = new int[NUM_OF_REQUESTS];
			
			for (int i=0;i<NUM_OF_REQUESTS;i++) {
				oldBT[i] = BT[i];
			}
			

			ArrayList <String> myipPool = new ArrayList <String>(Arrays.asList("REQUIRED DATA SEND BY SERVER TO CLIENT"));
			myipPool.add("SERVER 1 WITH IP  192.168.0.1");//SERVER 1  IP 192.168.0.1
			myipPool.add("SERVER 2 WITH IP  192.168.0.2 ");//SERVER 2 IP 192.168.0.2
			myipPool.add("SERVER 3 WITH IP 192.168.0.3 ");//SERVER 3  IP 192.168.0.3

			client.printNextTurn("LOAD BALANCER WHICH IS USING LOAD BALANCING ALGORITHM SELECTED BY CLIENT  TO REDIRECT CLIENT REQUEST TO SERVER  ");
			if ( selection ==1) {
			//  Round Robin" selected"
			LoadBalancer roundRobbin = new RoundRobinLoadBalancer(myipPool,BT,NUM_OF_REQUESTS, oldBT);
			client.processConcurrentClientRequest(roundRobbin,NUM_OF_REQUESTS );
			}
			if(selection==2){
				LoadBalancer leastconnection = new LeastConnection(myipPool,BT,NUM_OF_REQUESTS);
				client.processConcurrentClientRequest(leastconnection,NUM_OF_REQUESTS );
				
			}
			
			
			
			
		} 

    }

    private void processConcurrentClientRequest(LoadBalancer loadBalancer, int numOfCalls) {
// A SEQUENCE OF PRIMITIVE INT VALUED ELEMENTS .
        IntStream
                .range(1, numOfCalls + 2)
                //.sequential() // IF WE WANT CLIENT TO SEND REQUEST SQUENTIALLY (0,1 ,2 so on )
//                .parallel() // any client as they want 
                .sequential()
                .forEach(i ->
                        System.out.println(
                                " SERVER WITH IP: " + loadBalancer.getIp(i)
                                //"SENDING DATA........"+loadBalancer.getreturndata()
                                + " ------- >>>> IS ALLOCATED TO CLIENT NO: " + i
                                + " ------- [Thread: " + Thread.currentThread().getName() + "]"));//);
                
    }

    private void printNextTurn(String name) {
        System.out.println("*****************************************************************************************************************************************************************************");
        System.out.println("CLIENT STARTS TO SEND REQUEST TO  " + name + " ");
        System.out.println("*****************************************************************************************************************************************************************************");
    }
    
    public static  int  norequest (int NUM_OF_REQUESTS) {
    	
    	return NUM_OF_REQUESTS ;
			 
    	
    	}
    

}






