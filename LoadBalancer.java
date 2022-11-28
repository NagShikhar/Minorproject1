package nag;

import java.util.*;

public abstract class LoadBalancer {
	
	public final List <String> ipList;
	
	
	// ABSTRACT METHOD 
	abstract String getIp(int clientNumber);
	
	public LoadBalancer(List <String> ipList) {
		this.ipList=Collections.unmodifiableList(ipList);		
	}
}
    






















































/*import java.util.*;

public abstract class LoadBalancer {
	
	
	final List <String> ipList;
	
	
	
	    // ABSTRACT METHOD 
		abstract String getIp();
	
	public LoadBalancer(List <String> ipList) {
		this.ipList=Collections.unmodifiableList(ipList);
		
		
		
	}
	
}
    
	
	
	

*/



//String [] dataoutput(String ip,String DATABYSERVERTOCLIENT) {
//String [] datafinaloutput= new String[2];
//datafinaloutput[0]= ipList.get(counter);
//datafinaloutput[1]="REQUIRED DATA SEND BY SERVER WHICH IS REQUESTED BY CLIENT";

//do{
//for(int i=0;i<requests;i++){
   // if(bursttime>Quant){
        //for(int j=0;j<requests;j++){
            //if(j!=i &&bursttime[j]!=0){
                //wait[j]=Quant+1;
            //}
            
       // }
    //}
