package scripts.data.collection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import scripts.ClientAPIWrappers;
import scripts.userinterface.JavaFXUI;


public class DynamicSignature implements Runnable {
	
	static int sessionID = 0;
	private volatile Thread MainThread;
	
//	Update Every 15 minutes
	static int updateTime = 900000;
	static final int NextUpdateTime = 900000;

//	Update Every 30 Seconds (For Testing Dynamic Signatures)
//	static int updateTime = 30000;
//	static final int NextUpdateTime = 30000;
	
	
	DynamicSignature(Thread MainThread){
		this.MainThread = MainThread;
	}
    
	public void run(){
    	while(MainThread.isAlive()){
    		if((System.currentTimeMillis()-Statistics.startTime)>updateTime){
				DataBasePush();
				updateTime = updateTime + NextUpdateTime;
			}
    		try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
	}
	public static void StartDynamicSignatureThread(){
	    	new Thread(new DynamicSignature(Thread.currentThread())).start();
	}
	public void DataBasePush(){
		if(sessionID==0){
			String FirstSubmit = "http://elitescripts.tk/autofletcherelitedynamicsignatures/datapush.php?"+
					"BotClient="+ClientAPIWrappers.getBotClientName() +
					"&BotClient_UserName="+ClientAPIWrappers.getBotClientUserName()+
					"&Runescape_UserName="+ClientAPIWrappers.getRunescapeUserName()+
					"&Run_Time_Seconds="+Statistics.getRunTimeSeconds()+
					"&Xp_Gained="+Statistics.getTotalXpGained()+
					"&Amount_of_Item_Made="+Statistics.getAmountofItemMade()+
					"&Item_Made="+JavaFXUI.Object.toString();
			sessionID = readURL(FirstSubmit);
			System.out.println("Dynamic Signature Session ID: "+sessionID);
		}
		else{
			String SubmitUpdate = "http://elitescripts.tk/autofletcherelitedynamicsignatures/DataUpdate.php?"+
					"BotClient="+ClientAPIWrappers.getBotClientName() +
					"&BotClient_UserName="+ClientAPIWrappers.getBotClientUserName()+
					"&Runescape_UserName="+ClientAPIWrappers.getRunescapeUserName()+
					"&Run_Time_Seconds="+Statistics.getRunTimeSeconds()+
					"&Xp_Gained="+Statistics.getTotalXpGained()+
					"&Amount_of_Item_Made="+Statistics.getAmountofItemMade()+
					"&Item_Made="+JavaFXUI.Object.toString()+
					"&ID="+sessionID;
			readURL(SubmitUpdate);
			System.out.println("Dynamic Signature Session ID: "+sessionID + " - Updated Successfully");
		}
	}
	public int readURL(String url){
		try {
			URL submit = new URL(url.replaceAll("[\\s ]","%20"));
			URLConnection con = submit.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setConnectTimeout(2000);
			con.setReadTimeout(2000);
			final BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
		    String line; 
	        while ((line = rd.readLine()) != null) { 
	        	if(line.replaceAll(" ", "")!=""){
	        		return Integer.parseInt(line);
	        	}
	        }
		    rd.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
