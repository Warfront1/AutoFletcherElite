package scripts;

import java.awt.Point;
import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;

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
	
	/** Returns the in game Runescape Username as a String */
	/** Only used for statistical analysis, return an empty String if no method is available */
	public static String getRunescapeUserName(){
		return Player.getRSPlayer().getName();
	}
	
	/** Returns the current total experience obtained in the Fletching Skill as an int */
	public static int getFletchingXP(){
		return Skills.getXP(SKILLS.FLETCHING);
	}
	
	/** Types a String provided as a parameter, and then hits the enter key.  */
	public static void sendKeyboardKeys(String StringToType){
		Keyboard.typeSend(StringToType);
	}
	
	/** Stops the thread from spinning for a inputed amount of Milliseconds.  */
	public static void sleep(int Millisecond){
		General.sleep(Millisecond);
	}
	
	/** Returns true if the Runescape Banking interface is Open. Returns false otherwise. */
	public static boolean isBankScreenOpen(){
		return Banking.isBankScreenOpen();
	}
	
	/** Withdraws an inputed String Parameter ItemName and a specific Amount denoted as the parameter AmountToWithdraw */
	/** Returns true if the withdraw successfully placed the items within the inventory.*/
	/** The return Boolean is only used in order to assist with dynamic sleeping, and is not 100% mandatory in the case of a primitive BotClient API.  */
	public static boolean withdrawItems(int AmountToWithdraw,String ItemName){
		return Banking.withdraw(AmountToWithdraw, ItemName);
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
		return (IFace !=null && IFace.isHidden());
	}
	
	/** Returns a String pertaining to the Name of an item, given it's ID as an int input parameter. */	
	/** When using RSItemDefintions (cache extraction, etc.), in the case of a null return an empty string. */	
	public static String getItemNameFromID(int ID){
		RSItem[] Item = Inventory.find(ID);
		if(Item!=null && Item.length>0){
			RSItemDefinition ItemDefinition = Item[0].getDefinition();
			if(ItemDefinition!=null){
				String NameDefinition = ItemDefinition.getName();
				if(NameDefinition !=null){
					return NameDefinition;
				}
			}
		}
		return "";
	}
	
	/** Utilizing all inventory items, Generate an array of RSItems (a script based Items Object) */	
	/** Basic RSItem Construction: new RSItems(String ItemName, Rectangle ItemRectangle, int StackSize, int ID) */	
	public static RSItems[] getAllItems(){
		RSItem[] itemsDefined = org.tribot.api2007.Inventory.getAll();
		if(itemsDefined!=null && itemsDefined.length>0){
			ArrayList<RSItems> ItemArrayReturn = new ArrayList<RSItems>();
			for(RSItem i: itemsDefined){
				ItemArrayReturn.add(new RSItems(getItemNameFromID(i.getID()), i.getArea(),i.getStack(),i.getID()));
			}
			return ItemArrayReturn.toArray(new RSItems[ItemArrayReturn.size()]);
		}
		return null;
	}
}
