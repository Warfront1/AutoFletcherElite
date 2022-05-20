package scripts;

import java.awt.Point;
import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSItem;

import scripts.actions.Utilities.RSItems;

public class ClientAPIWrappers {

	/** Returns the String Name of the Bot Client being used.*/
	/** Only used for statistical analysis, Simply Change the return to the Name of the Bot Client you use. Ie. "OSBot", etc. */
	public static String getBotClientName(){
		return "Tribot";
	}
	
	/** Returns the String Name of the script user's Forum/Bot-Client Username. This is NOT the Runescape in game name. */
	/** Only used for statistical analysis, return an empty String if no method is available */
	public static String getBotClientUserName(){
		return General.getTRiBotUsername();
	}
	
	/** Returns the current total experience obtained in the Fletching Skill as an int */
	public static int getFletchingXP(){
		return Skills.getXP(SKILLS.FLETCHING);
	}
	
	/** Stops the thread from spinning for a inputed amount of Milliseconds.  */
	public static void sleep(int Millisecond){
		General.sleep(Millisecond);
	}
	
	/** Withdraws an inputed String Parameter ItemName and a specific Amount denoted as the parameter AmountToWithdraw */
	/** Returns true if the withdraw successfully placed the items within the inventory.*/
	/** The return Boolean is only used in order to assist with dynamic sleeping, and is not 100% mandatory in the case of a primitive BotClient API.  */
	public static boolean withdrawItems(int AmountToWithdraw,int ID){
		return Banking.withdraw(AmountToWithdraw, ID);
	}
	
	/** Deposits everything in the inventory except inputed parameter(s) ID's provided */
	public static void DepositAllItemsExcept(int... Itemids){
		Banking.depositAllExcept(Itemids);
	}
	
	/** Walks to the closest Bank and open's a booth. Returns true if the banking interface successfully pops up, false otherwise. */
	public static boolean openBank(){
		return Banking.openBank();
	}
	
	/** Closes the Banking interface. Returns true if the Banking interface is successfully closed, false elsewise */
	public static boolean closeBank(){
		return Banking.close();
	}
	
	/** Returns an int value corresponding to the local players current animation */	
	public static int getPlayerAnimation(){
		return Player.getAnimation();
	}
	
	/** Clicks a particular point on the screen. Point Randomization IS handled in the back-end, no anti-ban/randomization is necessary within this method. */	
	public static void clickPoint(Point p){
		Mouse.click(p,1);
	}
	
	/** Returns true an RS Interface Master is valid given it's RS Interface Master Index. Else returns false */	
	public static boolean isInterfaceValid(int index){
		return Interfaces.isInterfaceValid(index);
	}
	
	/** Returns true an RS Interface Child is valid given it's RS Interface Master and Child Index */	
	public static boolean isInterfaceChildValid(int InterfaceMaster, int InterfaceChild){
		RSInterfaceChild IFace = org.tribot.api2007.Interfaces.get(InterfaceMaster,InterfaceChild);
		return (IFace !=null);
	}
	
	/** Clicks a String (denoted InteractionText) option on an interface, given an RSInterface Master and Child. Returns true if the click was successful*/	
	/** The return Boolean is only used in order to assist with dynamic sleeping, and is not 100% mandatory in the case of a primitive BotClient API.*/	
	public static boolean clickInterface(int InterfaceMaster, int InterfaceChild, String InteractionText){
		RSInterfaceChild IFace = org.tribot.api2007.Interfaces.get(InterfaceMaster,InterfaceChild);
		return (IFace !=null && IFace.click(InteractionText));
	}
	
	/** Returns if an RS Interface Child is Hidden given it's RS Interface Master and Child Index. Else returns false */	
	public static boolean isInterfaceHidden(int InterfaceMaster, int InterfaceChild){
		RSInterfaceChild IFace = org.tribot.api2007.Interfaces.get(InterfaceMaster,InterfaceChild);
		return IFace !=null && (IFace.isHidden() || !IFace.isBeingDrawn());
	}
	
	/** Returns the String Text of an interface. If the text is null or the interface is null return an empty String */
	public static String getText(int InterfaceMaster, int InterfaceChild){
		RSInterfaceChild IFace = org.tribot.api2007.Interfaces.get(InterfaceMaster,InterfaceChild);
		if(IFace !=null && !IFace.isHidden() && IFace.getText()!=null){
			return IFace.getText();
		}
		return "";
	}

	/** Returns the String Component Name of an interface. If the text is null or the interface is null return an empty String */
	public static String getComponentName(int InterfaceMaster, int InterfaceChild){
		RSInterfaceChild IFace = org.tribot.api2007.Interfaces.get(InterfaceMaster,InterfaceChild);
		if(IFace !=null && !IFace.isHidden() && IFace.getComponentName()!=null){
			return IFace.getComponentName();
		}
		return "";
	}

	/** Returns the Actions String Array of an interface. If the iFace is null or no actions it will return a empty string array */
	public static String[] getActions(int InterfaceMaster, int InterfaceChild){
		ArrayList<String> returnArrayList = new ArrayList<String>();
		RSInterfaceChild IFace = org.tribot.api2007.Interfaces.get(InterfaceMaster,InterfaceChild);
		if(IFace !=null){
			String[] actions = IFace.getActions();
			if(actions!=null){
				for (String action : actions){
					returnArrayList.add(action);
				}
			}
		}
		return returnArrayList.toArray(new String[returnArrayList.size()]);
	}
	
	
	/** Utilizing all inventory items, Generate an array of RSItems (a script based Items Object) */	
	/** Basic RSItem Construction: new RSItems(String ItemName, Rectangle ItemRectangle, int StackSize, int ID) */	
	public static RSItems[] getAllItems(){
		RSItem[] itemsDefined = org.tribot.api2007.Inventory.getAll();
		if(itemsDefined!=null && itemsDefined.length>0){
			ArrayList<RSItems> ItemArrayReturn = new ArrayList<RSItems>();
			for(RSItem i: itemsDefined){
				ItemArrayReturn.add(new RSItems(i.getArea(),i.getStack(),i.getID()));
			}
			return ItemArrayReturn.toArray(new RSItems[ItemArrayReturn.size()]);
		}
		return null;
	}
	
	
	/**  Auto Fletcher Elite Anti-Ban */	
	/**  The Following methods are void anti-ban methods. Either replace your own client Anti-ban (if available),                                */	
	/**  create your own anti-ban systems within the methods, place semi-static random sleeps, or just completely remove for maximum script speed*/	
	
	
	/** Additional Time to sleep between interacting with an object again (ie. clicking the bank booth) */	
	public static void waitNewOrSwitchDelay(final long last_busy_time, final boolean combat) {
		org.tribot.api.util.ABCUtil abcl =  new org.tribot.api.util.ABCUtil();
		if (org.tribot.api.Timing.timeFromMark(last_busy_time) >= org.tribot.api.General.random(8000, 12000)) {
			if (combat) {
				org.tribot.api.General.sleep(abcl.DELAY_TRACKER.NEW_OBJECT_COMBAT.next());
				abcl.DELAY_TRACKER.NEW_OBJECT_COMBAT.reset();
			} 
			else {
				org.tribot.api.General.sleep(abcl.DELAY_TRACKER.NEW_OBJECT.next());
				abcl.DELAY_TRACKER.NEW_OBJECT.reset();
			}
		} 
		else {
			if (combat) {
				org.tribot.api.General.sleep(abcl.DELAY_TRACKER.SWITCH_OBJECT_COMBAT.next());

				abcl.DELAY_TRACKER.SWITCH_OBJECT_COMBAT.reset();
			} 
			else {
				org.tribot.api.General.sleep(abcl.DELAY_TRACKER.SWITCH_OBJECT.next());

				abcl.DELAY_TRACKER.SWITCH_OBJECT.reset();
			}
		}
	}
	
	/** Additional Time to sleep between interacting with an inventory items again (ie. The time between clicking the knife then the log) */	
	public static void waitItemDelay() {
		// All ABCUtil objects contain the exact same data, so it doesn't matter
		// whether we construct a new one, or use a constant one.
		org.tribot.api.util.ABCUtil abcl =  new org.tribot.api.util.ABCUtil();
		org.tribot.api.General.sleep(abcl.DELAY_TRACKER.ITEM_INTERACTION.next());
		abcl.DELAY_TRACKER.ITEM_INTERACTION.reset();
	}
	
	/** Generic Anti-ban Methods */	
	public static void runAntiBanActions(){
		if(!General.useAntiBanCompliance()){
			System.out.println("Turning on ABC, TRiBot's Premium Anti-Ban System");
			org.tribot.api.General.useAntiBanCompliance(true);
		}
		org.tribot.api.util.ABCUtil abcl =  new org.tribot.api.util.ABCUtil();
		abcl.performXPCheck(org.tribot.api2007.Skills.SKILLS.FLETCHING);
		abcl.performExamineObject();
		abcl.performLeaveGame();
		abcl.performPickupMouse();
		abcl.performRandomRightClick();
		abcl.performRandomMouseMovement();
		abcl.performRotateCamera();
		abcl.performFriendsCheck();
	}
}
