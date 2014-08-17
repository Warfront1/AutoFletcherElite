package scripts;

import java.text.DecimalFormat;


public class AutoFletcherEliteStatistics {
	static String Status = " ";
	static String ItemName = " ";
	static double xpPerItem = 1.0;
	static final long startTime =System.currentTimeMillis();
	static int startingFletchingXP=ClientAPIWrappers.getFletchingXP();

	public void setItemName(String ItemName1){
		ItemName=ItemName1;
	}
	public void setxpPerItem(double xpPerItem1){
		xpPerItem=xpPerItem1;
	}

	public final static int[] xpTable = new int[]{
			0,83,174,276,388,512,650,801,969,1154,1358,1584,1833,2107,2411,2746,3115,3523,3973,4470,5018,5624,6291,7028,7842,8740,9730,10824,12031,13363,14833,16456,18247,20224,22406,24815,27473,30408,33648,37224,41171,45529,50339,55649,61512,67983,75127,83014,91721,101333,111945,123660,136594,150872,166636,184040,203254,224466,247886,273742,302288,333804,368599,407015,449428,496254,547953,605032,668051,737627,814445,899257,992895,1096278,1210421,1336443,1475581,1629200,1798808,1986068,2192818,2421087,2673114,2951373,3258594,3597792,3972294,4385776,4842295,5346332,5902831,6517253,7195629,7944614,8771558,9684577,10692629,11805606,13034431
	};

//	getLevelFromXP takes one input paramater of XP (the total xp gained in a particular skill),
//  The method returns the corresponding level.
	public static int getLevelFromXP(int xp){
		int counter = 0;
		for(int i: xpTable){
			if(i>xp){
				return counter;
			}
			counter++;
		}
		return -1;
	}
	
//	getBaseXPForLevel returns the starting of xp for that particular level
//	For example 2 returns 83,  3 returns 174
	public static int getBaseXpForLevel(int level){
		return xpTable[level-1];
	}
//	getBaseXPForLevel returns the maximum of xp for that particular level before reaching the next level
//	For example 1 returns 82,  2 returns 174
	public static int getMaxXpForLevel(int level){
		return xpTable[level];
	}
	
//	Returns your current Fletching level
	public static int getCurrentLevel(){
		return getLevelFromXP(ClientAPIWrappers.getFletchingXP());
	}
//	Get Current XP
	public static int getCurrentXP(){
		return ClientAPIWrappers.getFletchingXP();
	}
//	Returns your Next Fletching level
	public static int getNextLevel(){
		return getCurrentLevel()+1;
	}
	
//	Returns your Next Fletching level
	public static int getXpRemainingToNextLevel(){
		return getMaxXpForLevel(getCurrentLevel())-getCurrentXP();
	}
	
	
//	getPercentCompleted() returns the percentage of your current level that has been completed.
//	This statistic is able to determine how much you have acheived within your current level.
//	For example you have completed 58.61% of level 40, it would return 58.61
	public static double getPercentCompleted(int xp){
		int currentlevel = getLevelFromXP(xp);
		int totalXPinLevel=getMaxXpForLevel(currentlevel)-getBaseXpForLevel(currentlevel);
		int XPinLevelCompleted = xp -getBaseXpForLevel(currentlevel);
		DecimalFormat twoDecimals = new DecimalFormat("#.##");
        return Double.valueOf(twoDecimals.format((double)(XPinLevelCompleted*100)/totalXPinLevel));
	}
	
//	Returns the amount of XP gained per hour
	public static double getXpPerHour(){
		DecimalFormat twoDecimals = new DecimalFormat("#.##");
		return Double.valueOf(twoDecimals.format((double)(getTotalXpGained()*3600/getRunTimeSeconds())));
	}
	
//	Returns the xp gained since the starting of the script
	public static int getTotalXpGained(){
		int returnvalue = (ClientAPIWrappers.getFletchingXP()-startingFletchingXP);
//		Check to see if the value for xp gained is too high to be possible
//		Resets the orginal fletching xp, in order to compensate. 
		if(startingFletchingXP<1){
			startingFletchingXP=ClientAPIWrappers.getFletchingXP();
		}
		return returnvalue;
	}
	
//	Returns the amount of item created
	public static int getAmountofItemMade(){
		return (int) (getTotalXpGained()/xpPerItem);
	}
	
//	Returns the amount of item created per hour
	public static Double getAmountofItemMadePerHour(){
		DecimalFormat twoDecimals = new DecimalFormat("#.##");
		try{
			return Double.valueOf(twoDecimals.format((double)((getAmountofItemMade()*3600/getRunTimeSeconds()))));
		}
		catch(NumberFormatException e) {
			return 0.0;
		}
	}
	
//	Returns the amount of item created per hour
	public static Double getHoursTillNextLevel(){
		DecimalFormat twoDecimals = new DecimalFormat("#.##");
		try{
			return Double.valueOf(twoDecimals.format((double)(getXpRemainingToNextLevel()/getXpPerHour())));
		}
		catch(NumberFormatException e) {
			return 0.0;
		}
	}
	
	public static long getRunTimeSeconds(){
		return (System.currentTimeMillis()-startTime)/1000;
	}
	
//	Formats the run time into a neat String that is suitable for display/output in either a paint/GUI/console.
	public static String getFormatedRunTime(){
		long runTime = getRunTimeSeconds();
		long hours = runTime/3600;
		long minutes = (runTime%3600)/60;
		long seconds = ((runTime%3600)%60);
		return ""+hours+":"+minutes+":"+seconds;
	}
	

}
