package scripts.actions;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import scripts.AntiBanCompliance;
import scripts.ClientAPIWrappers;
import scripts.data.ItemData.Bows;
import scripts.data.ItemData.Bows.Cutting;
import scripts.data.collection.Statistics;

public class Utilities {
	
	static boolean isCuttingIFaceOpen(){
		for(Cutting BowType : Bows.Cutting.values()){
			if(ClientAPIWrappers.isInterfaceValid(BowType.InterfaceMasterIndex)){
				return true;
			}
		}
		return false;
	}
	static boolean isEnterXIFaceOpen(){
		return !ClientAPIWrappers.isInterfaceHidden(548, 123);
	}
	static boolean isInventoryOpen(){
		return !ClientAPIWrappers.isInterfaceHidden(149, 0);
	}
	static boolean openInventory(){
		return !ClientAPIWrappers.clickInterface(548, 51, "Inventory");
	}
	
    public static boolean isReadyToFletch(String ItemOneName,int ItemOneAmount,String ItemTwoName,int ItemTwoAmount){
    	if(interfaces.get(165, 2)!=null || interfaces.get(519,2)!=null || interfaces.get(210,1)!=null){
			interfaces LevelUpScreen = interfaces.get(165,2);
			interfaces NewFeaturesDueToUpgradeScreen = interfaces.get(519,2);
			interfaces NewFeaturesDueToUpgradeScreen2 = interfaces.get(210,1);
			if(LevelUpScreen!=null){
				if(LevelUpScreen.click(""));
			}
			else if(NewFeaturesDueToUpgradeScreen!=null){
				NewFeaturesDueToUpgradeScreen.click("");
			}
			else if(NewFeaturesDueToUpgradeScreen2!=null){
				NewFeaturesDueToUpgradeScreen2.click("");
			}
			Utilities.waitFor(new Condition() {@Override
			    public boolean active() {
			         return !(interfaces.get(165, 2)!=null || interfaces.get(519,2)!=null || interfaces.get(210,1)!=null);
			    }
			    }, Utilities.getRandom(1500, 2500));
    	}
    	if(RSItems.get(ItemOneName)!=null && RSItems.get(ItemOneName).length>0 && RSItems.get(ItemTwoName)!=null && RSItems.get(ItemTwoName).length>0){
    		if(!ClientAPIWrappers.isBankScreenOpen()){
    			if(Utilities.isInventoryOpen()){
    				if(ClientAPIWrappers.getPlayerAnimation()!=-1){
    					Statistics.Status="Fletching";
    					AntiBanCompliance.runAntiBanActions();
    				}
    				else{
    					return true;
    				}
    			}
    			else{
    				Statistics.Status="Opening Inventory";
    				Utilities.openInventory();
    			}
    		}
    		else{
    			ClientAPIWrappers.closeBank();
    		}
    	}
    	else if(!IsMyInventoryCorrect(ItemOneName, ItemOneAmount, ItemTwoName, ItemTwoAmount)){
    		GenericBanking(ItemOneName, ItemOneAmount, ItemTwoName, ItemTwoAmount);
    	}
    	return false;
    }
    public static boolean IsMyInventoryCorrect(String ItemName1, int ItemAmount1, String ItemName2, int ItemAmount2){
    	if(RSItems.get(ItemName1)!=null && RSItems.get(ItemName1).length>0 && (RSItems.get(ItemName1).length==ItemAmount1||RSItems.get(ItemName1)[0].getStackSize()==ItemAmount1 )){
        	if(RSItems.get(ItemName2)!=null && RSItems.get(ItemName2).length>0 && (RSItems.get(ItemName2).length==ItemAmount2||RSItems.get(ItemName2)[0].getStackSize()==ItemAmount2 )){
        		return true;
        	}
    	}
    	return false;
    }
    public static void GenericBanking(final String ItemName1, final int ItemAmount1, final String ItemName2, final int ItemAmount2){
		Statistics.Status="Banking";
    	if(ClientAPIWrappers.isBankScreenOpen()){
    		int CorrectItem = 0;
    		int SecondCorrectItem = 0;
    		RSItems[] Item1Defined = RSItems.get(ItemName1);
    		final RSItems[] Item2Defined = RSItems.get(ItemName2);
    		if(Item1Defined!=null && Item1Defined.length>0 && (Item1Defined.length==ItemAmount1||Item1Defined[0].getStackSize()==ItemAmount1)){
    			CorrectItem = Item1Defined[0].getID();
    		}
    		if(Item2Defined!=null && Item2Defined.length>0 && (Item2Defined.length==ItemAmount2||Item2Defined[0].getStackSize()==ItemAmount2)){
    			SecondCorrectItem = Item2Defined[0].getID();
    		}
    		ClientAPIWrappers.DepositAllItemsExcept(CorrectItem, SecondCorrectItem);
    		if(CorrectItem ==0){
    			ClientAPIWrappers.withdrawItems(ItemAmount1, ItemName1);
    			Utilities.waitFor(new Condition() {@Override
				    public boolean active() {
				         return (RSItems.get(ItemName1)!=null && RSItems.get(ItemName1).length>0 && (RSItems.get(ItemName1).length==ItemAmount1||RSItems.get(ItemName1)[0].getStackSize()==ItemAmount1 ));
				    }
				    }, Utilities.getRandom(2000, 2500));
    		}
    		if(SecondCorrectItem==0){
    			ClientAPIWrappers.withdrawItems(ItemAmount2, ItemName2);
    			Utilities.waitFor(new Condition() {@Override
				    public boolean active() {
				         return (RSItems.get(ItemName2)!=null && RSItems.get(ItemName2).length>0 && (RSItems.get(ItemName2).length==ItemAmount2||RSItems.get(ItemName2)[0].getStackSize()==ItemAmount2 ));
				    }
				    }, Utilities.getRandom(2000, 2500));
    		}
    	}
    	else{
    		ClientAPIWrappers.openBank();
    	}
    }
    public static void SleepWhileFlashAnimating(scripts.actions.Condition condition){
    	long START_TIME = System.currentTimeMillis();
    	long ROLLING_TIME = System.currentTimeMillis();
    	while((ROLLING_TIME-START_TIME)<5000){
    		AntiBanCompliance.runAntiBanActions();
    		if(ClientAPIWrappers.getPlayerAnimation()!=-1){
    			START_TIME = System.currentTimeMillis();
    		}
    		if(condition.active()){
    			break;
    		}
    		ROLLING_TIME = System.currentTimeMillis();
    		ClientAPIWrappers.sleep(Utilities.getRandom(150,250));
    	}
    }
    public static void SleepWhileInventoryIsChanging(scripts.actions.Condition condition){
    	long START_TIME = System.currentTimeMillis();
    	long ROLLING_TIME = System.currentTimeMillis();
    	RSItems[] AllInventoryItems = RSItems.getAll();
    	while((ROLLING_TIME-START_TIME)<5000){
    		AntiBanCompliance.runAntiBanActions();
    		if(!InventoriesAreTheSame(AllInventoryItems, RSItems.getAll())){
    			AllInventoryItems = RSItems.getAll();
    			START_TIME = System.currentTimeMillis();
    		}
    		if(condition.active()){
    			break;
    		}
    		ROLLING_TIME = System.currentTimeMillis();
    		ClientAPIWrappers.sleep(Utilities.getRandom(150,250));
    	}
    }
    private static boolean InventoriesAreTheSame(RSItems[] Array1, RSItems[] Array2){
    	if(Array1!=null && Array1.length>0 && Array2!=null && Array2.length>0){
    		for(int x=0; x<Array1.length; x++){
    			if( !(Array1[x].getID()==Array2[x].getID() && Array1[x].getStackSize()==Array2[x].getStackSize())  ){
    				return false;
    			}
    		}
    	}
    	return true;
    }
    public static int getRandom(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
    static boolean waitFor(scripts.actions.Condition condition, int timeout) {
        Timer time = new Timer(timeout);
        while ((time.isRunning()) && (!condition.active())) {
        	ClientAPIWrappers.sleep(20);
        }
        condition.active();
        return condition.active();
    }
    public static Point getRandomPointWithinRectangle(Rectangle A){
    	return new Point(getRandom(A.x,A.x+A.width),getRandom(A.y,A.y+A.height));
    }
	public static class interfaces {
		int MasterInterfaceID;
		int ChildInterfaceID;
		
		interfaces(int MasterInterfaceID, int ChildInterfaceID){
			this.MasterInterfaceID=MasterInterfaceID;
			this.ChildInterfaceID= ChildInterfaceID;
		}
		public static interfaces get(int MasterInterfaceID, int ChildInterfaceID){
			if(ClientAPIWrappers.isInterfaceChildValid(MasterInterfaceID,ChildInterfaceID)){
				return new interfaces(MasterInterfaceID, ChildInterfaceID);
			}
			return null;
		}
		public boolean click(String string) {
			return ClientAPIWrappers.clickInterface(this.MasterInterfaceID, this.ChildInterfaceID, string);
		}
		public boolean isHidden() {
			return ClientAPIWrappers.isInterfaceHidden(this.MasterInterfaceID, this.ChildInterfaceID);
		}
			
	}
	public static class RSItems {
		String ItemName;
		Rectangle ItemRectangle;
		int StackSize;
		int ID;
		public RSItems(String ItemName, Rectangle ItemRectangle, int StackSize, int ID){
			this.ItemName=ItemName;
			this.ItemRectangle=ItemRectangle;
			this.StackSize=StackSize;
			this.ID=ID;
		}
		public static RSItems[] get(String ItemName){

			RSItems[] AllItems = ClientAPIWrappers.getAllItems();
			if(AllItems!=null && AllItems.length>0){
				ArrayList<RSItems> ItemArrayReturn = new ArrayList<RSItems>();
				for(RSItems i: AllItems){
					if(i.getItemName().equals(ItemName)){
						ItemArrayReturn.add(i);
					}
				}
				return ItemArrayReturn.toArray(new RSItems[ItemArrayReturn.size()]);
			}
			return null;
		}
		public static RSItems[] getAll(){
			return ClientAPIWrappers.getAllItems();
		}
		public boolean click(String string) {
			ClientAPIWrappers.clickPoint(getRandomPointWithinRectangle(this.ItemRectangle));
			return true;
		}
		public int getStackSize(){
			return this.StackSize;
		}
		public int getID(){
			return this.ID;
		}
		public String getItemName(){
			return this.ItemName;
		}
			
	}
}
