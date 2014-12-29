package scripts.actions;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import scripts.ClientAPIWrappers;
import scripts.data.FletchingItem;
import scripts.data.FletchingRecipe;
import scripts.data.collection.Statistics;
import scripts.timing.Condition;
import scripts.timing.Timer;

public class Utilities {
	
	public static String getRuneScapeUserName(){
		interfaces UserNameIFace = interfaces.get(137, 1);
		if(UserNameIFace!=null){
			return UserNameIFace.getText().split(":")[0];
		}
		return "";
	}
	static boolean isEnterXIFaceOpen(){
		return !ClientAPIWrappers.isInterfaceHidden(548, 120);
	}
	static boolean isBankOpen(){
		return ClientAPIWrappers.isInterfaceValid(12);
	}
	static boolean isInventoryOpen(){
		return !ClientAPIWrappers.isInterfaceHidden(149, 0);
	}
	static boolean openInventory(){
		return !ClientAPIWrappers.clickInterface(548, 51, "Inventory");
	}
	
    public static boolean isReadyToFletch(FletchingRecipe Recipe, int ItemOneAmount, int ItemTwoAmount){
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
    	if(RSItems.get(Recipe.getItem1().getID())!=null && RSItems.get(Recipe.getItem1().getID()).length>0 && RSItems.get(Recipe.getItem2().getID())!=null && RSItems.get(Recipe.getItem2().getID()).length>0){
    		if(!isBankOpen()){
    			if(Utilities.isInventoryOpen()){
    				if(ClientAPIWrappers.getPlayerAnimation()!=-1){
    					Statistics.Status="Fletching";
    					ClientAPIWrappers.runAntiBanActions();
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
    	else if(!IsMyInventoryCorrect(Recipe.getItem1(), ItemOneAmount, Recipe.getItem2(), ItemTwoAmount)){
    		GenericBanking(Recipe.getItem1(), ItemOneAmount, Recipe.getItem2(), ItemTwoAmount);
    	}
    	return false;
    }
    public static boolean IsMyInventoryCorrect(final FletchingItem Item1, final int ItemAmount1, final FletchingItem Item2, final int ItemAmount2){
    	if(RSItems.get(Item1.getID())!=null && RSItems.get(Item1.getID()).length>0 && (RSItems.get(Item1.getID()).length==ItemAmount1||RSItems.get(Item1.getID())[0].getStackSize()==ItemAmount1 )){
        	if(RSItems.get(Item2.getID())!=null && RSItems.get(Item2.getID()).length>0 && (RSItems.get(Item2.getID()).length==ItemAmount2||RSItems.get(Item2.getID())[0].getStackSize()==ItemAmount2 )){
        		return true;
        	}
    	}
    	return false;
    }
    public static void GenericBanking(final FletchingItem Item1, final int ItemAmount1, final FletchingItem Item2, final int ItemAmount2){
		Statistics.Status="Banking";
    	if(isBankOpen()){
    		int CorrectItem = 0;
    		int SecondCorrectItem = 0;
    		RSItems[] Item1Defined = RSItems.get(Item1.getID());
    		final RSItems[] Item2Defined = RSItems.get(Item2.getID());
    		if(Item1Defined!=null && Item1Defined.length>0 && (Item1Defined.length==ItemAmount1||Item1Defined[0].getStackSize()==ItemAmount1)){
    			CorrectItem = Item1Defined[0].getID();
    		}
    		if(Item2Defined!=null && Item2Defined.length>0 && (Item2Defined.length==ItemAmount2||Item2Defined[0].getStackSize()==ItemAmount2)){
    			SecondCorrectItem = Item2Defined[0].getID();
    		}
    		ClientAPIWrappers.DepositAllItemsExcept(CorrectItem, SecondCorrectItem);
    		if(CorrectItem ==0){
    			ClientAPIWrappers.withdrawItems(ItemAmount1, Item1.getID());
    			Utilities.waitFor(new Condition() {@Override
				    public boolean active() {
				         return (RSItems.get(Item1.getID())!=null && RSItems.get(Item1.getID()).length>0 && (RSItems.get(Item1.getID()).length==ItemAmount1||RSItems.get(Item1.getID())[0].getStackSize()==ItemAmount1 ));
				    }
				    }, Utilities.getRandom(2000, 2500));
    		}
    		if(SecondCorrectItem==0){
    			ClientAPIWrappers.withdrawItems(ItemAmount2, Item2.getID());
    			Utilities.waitFor(new Condition() {@Override
				    public boolean active() {
				         return (RSItems.get(Item2.getID())!=null && RSItems.get(Item2.getID()).length>0 && (RSItems.get(Item2.getID()).length==ItemAmount2||RSItems.get(Item2.getID())[0].getStackSize()==ItemAmount2 ));
				    }
				    }, Utilities.getRandom(2000, 2500));
    		}
    	}
    	else{
    		ClientAPIWrappers.openBank();
    	}
    }
    public static void SleepWhileFlashAnimating(Condition condition){
    	long START_TIME = System.currentTimeMillis();
    	long ROLLING_TIME = System.currentTimeMillis();
    	while((ROLLING_TIME-START_TIME)<5000){
    		ClientAPIWrappers.runAntiBanActions();
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
    public static void SleepWhileInventoryIsChanging(Condition condition){
    	long START_TIME = System.currentTimeMillis();
    	long ROLLING_TIME = System.currentTimeMillis();
    	RSItems[] AllInventoryItems = RSItems.getAll();
    	while((ROLLING_TIME-START_TIME)<5000){
    		ClientAPIWrappers.runAntiBanActions();
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
    static boolean waitFor(Condition condition, int timeout) {
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
		public static interfaces getByText(String Text){
			String IFaceText;
			for(interfaces IFace: getAll()){
				IFaceText = IFace.getText();
				IFaceText = IFaceText.replaceAll("[\\s ]","");
				if(IFaceText.toLowerCase().contains(Text)){
					return IFace;
				}
			}
			return null;
		}
		public static interfaces[] getAll(){
			ArrayList<interfaces> InterfaceReturn = new ArrayList<interfaces>();
			for(int Master=0; Master<594; Master++){
				for(int Child=0; Child<1000; Child++){
					interfaces IFace = get(Master,Child);
					if(IFace!=null){
						InterfaceReturn.add(IFace);
					}
				}
			}
			return InterfaceReturn.toArray(new interfaces[InterfaceReturn.size()]);
		}
		public String getText(){
			return ClientAPIWrappers.getText(this.MasterInterfaceID, this.ChildInterfaceID);
		}
		public boolean click(String string) {
			return ClientAPIWrappers.clickInterface(this.MasterInterfaceID, this.ChildInterfaceID, string);
		}
		public boolean isHidden() {
			return ClientAPIWrappers.isInterfaceHidden(this.MasterInterfaceID, this.ChildInterfaceID);
		}
			
	}
	public static class RSItems {
		Rectangle ItemRectangle;
		int StackSize;
		int ID;
		public RSItems(Rectangle ItemRectangle, int StackSize, int ID){
			this.ItemRectangle=ItemRectangle;
			this.StackSize=StackSize;
			this.ID=ID;
		}
		public static RSItems[] get(int ID){

			RSItems[] AllItems = ClientAPIWrappers.getAllItems();
			if(AllItems!=null && AllItems.length>0){
				ArrayList<RSItems> ItemArrayReturn = new ArrayList<RSItems>();
				for(RSItems i: AllItems){
					if(i.getID()==ID){
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
			
	}
}
