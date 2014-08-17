package scripts;


public class AntiBanCompliance {
	
	static boolean useABCL;
	
	 /**
	 * 
	 * 
	 */
	public static void waitNewOrSwitchDelay(final long last_busy_time, final boolean combat) {
		// All ABCUtil objects contain the exact same data, so it doesn't matter
		// whether we construct a new one, or use a constant one.
		if(useABCL) {
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
	}
	public static void waitItemDelay() {
		// All ABCUtil objects contain the exact same data, so it doesn't matter
		// whether we construct a new one, or use a constant one.
		if(useABCL) {
			org.tribot.api.util.ABCUtil abcl =  new org.tribot.api.util.ABCUtil();
			org.tribot.api.General.sleep(abcl.DELAY_TRACKER.ITEM_INTERACTION.next());
			abcl.DELAY_TRACKER.ITEM_INTERACTION.reset();
		}
	}
	public static void runAntiBanActions(){
		if(useABCL) {
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
	public static void enableAntiBan(){
		try{
			org.tribot.api.General.useAntiBanCompliance(true);
			useABCL=true;
		}
		catch(NoClassDefFoundError e){
			useABCL=false;
		}
	}
}
