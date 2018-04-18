package scripts.actions;

import scripts.ClientAPIWrappers;
import scripts.actions.Utilities.interfaces;
import scripts.actions.Utilities.RSItems;
import scripts.data.FletchingRecipe;
import scripts.data.collection.Statistics;
import scripts.timing.Condition;

public class FletchingActions {

	public static void GenericBowCutting(final FletchingRecipe Recipe){
		String IFaceText = Recipe.getInterfaceText();
		if (IFaceText == null){
			IFaceText = (Recipe.getEndProduct().getInGameName().replace("(u)", ""));
		}
		System.out.println(IFaceText);
		interfaces FletchingIFace = interfaces.getByComponentName(IFaceText);
		if(FletchingIFace==null){
			if(Utilities.isEnterXIFaceOpen()){
				Statistics.Status="Entering Amount";
				ClientAPIWrappers.sendKeyboardKeys(""+Utilities.getRandom(30,200));
				Utilities.waitFor(new Condition() {@Override
					public boolean active() {
					return ClientAPIWrappers.getPlayerAnimation()!=-1;
				}
				}, Utilities.getRandom(2000, 3500));
			}
			else{
				Statistics.Status="Using Knife";
				RSItems[] KnifeDefined = RSItems.get(Recipe.getItem1().getID());
				RSItems[] Item2Defined = RSItems.get(Recipe.getItem2().getID());
				if(KnifeDefined!=null && KnifeDefined.length>0){
					KnifeDefined[0].click("");
					ClientAPIWrappers.waitItemDelay();
					if(Item2Defined!=null && Item2Defined.length>0){
						if(Item2Defined[0].click("")){
							ClientAPIWrappers.waitItemDelay();
							final String finalIFaceText = IFaceText;
							Utilities.waitFor(new Condition() {@Override
								public boolean active() {
								return interfaces.getByText(finalIFaceText)!=null;
							}
							}, Utilities.getRandom(1000, 1500));
						}
					}
				}
			}
		}
		else{
			interfaces makeAllIFace = interfaces.getByAction("All"); // The All IFace only has the Action "ALL" when not active
			if(makeAllIFace != null){
				Statistics.Status="Clicking Make All";
				makeAllIFace.click("");
				Utilities.waitFor(new Condition() {@Override
				public boolean active() {
					return interfaces.getByAction("All") == null;
				}
				}, Utilities.getRandom(2000, 3500));
			}
			else{
				Statistics.Status="Clicking Make All";
				FletchingIFace.click("");
				Utilities.waitFor(new Condition() {@Override
				public boolean active() {
					return ClientAPIWrappers.getPlayerAnimation()!=-1;
				}
				}, Utilities.getRandom(2000, 3500));
			}
		}
	}
	public static void StringBows(final FletchingRecipe Recipe){
		interfaces BowStringingMenu = interfaces.getByComponentName(Recipe.getEndProduct().getInGameName());
		if( BowStringingMenu==null || BowStringingMenu.isHidden()){
			Statistics.Status="Using Bow String";
			RSItems[] Item1Defined = RSItems.get(Recipe.getItem1().getID());
			RSItems[] Item2Defined = RSItems.get(Recipe.getItem2().getID());
			if(Item1Defined!=null && Item1Defined.length>0){
				Item1Defined[0].click("");
				ClientAPIWrappers.waitItemDelay();
				if(Item2Defined!=null && Item2Defined.length>0){
					Item2Defined[0].click("");
					ClientAPIWrappers.waitItemDelay();
					Utilities.waitFor(new Condition() {@Override
						public boolean active() {
						return ( interfaces.get(309,6)!=null && !interfaces.get(309,6).isHidden());
					}
					}, Utilities.getRandom(1000, 1500));
				}
			}
		}
		else{
			Statistics.Status="Stringing All";
			if(!BowStringingMenu.isHidden()){
				BowStringingMenu.click("");
				Utilities.SleepWhileFlashAnimating(new Condition() {@Override
					public boolean active() {
					return !(RSItems.get(Recipe.getItem1().getID())!=null && RSItems.get(Recipe.getItem1().getID()).length>0 && RSItems.get(Recipe.getItem2().getID())!=null && RSItems.get(Recipe.getItem2().getID()).length>0);
				}
				});
			}
		}
	}
	public static void useArrowHeadorBoltTip(final FletchingRecipe Recipe){
		if(!ClientAPIWrappers.isInterfaceValid(582)){
			Statistics.Status="Using "+Recipe.getItem1().getInGameName();
			RSItems[] Item1Defined = RSItems.get(Recipe.getItem1().getID());
			RSItems[] Item2Defined = RSItems.get(Recipe.getItem2().getID());
			if(Item1Defined!=null && Item1Defined.length>0){
				Item1Defined[0].click("use");
				ClientAPIWrappers.waitItemDelay();
				if(Item2Defined!=null && Item2Defined.length>0){
					Item2Defined[0].click("");
					ClientAPIWrappers.waitItemDelay();
					Utilities.waitFor(new Condition() {@Override
						public boolean active() {
						return ClientAPIWrappers.isInterfaceValid(582);
					}
					}, Utilities.getRandom(1000, 1500));
				}
			}
		}
		else{
			Statistics.Status="Clicking Make 10";
			interfaces ArrowMenu = interfaces.get(582, 5);
			final RSItems[] ItemTwo = RSItems.get(Recipe.getItem2().getID());
			if(ArrowMenu!=null && !ArrowMenu.isHidden() && ItemTwo!=null && ItemTwo.length>0){
				ArrowMenu.click("Make 10 sets");
				Utilities.SleepWhileInventoryIsChanging(new Condition() {@Override
					public boolean active() {
					RSItems[] ItemTwoCheck = RSItems.get(Recipe.getItem2().getID());
					return ItemTwoCheck!=null && ItemTwoCheck.length>0 && ItemTwo[0].getStackSize()-ItemTwoCheck[0].getStackSize()==150;
				}
				});
			}
		}
	}
	public static void useFeathers(final FletchingRecipe Recipe){
		Statistics.Status="Using "+Recipe.getItem1().getInGameName();
		final RSItems[] Item1Defined = RSItems.get(Recipe.getItem1().getID());
		final RSItems[] Item2Defined = RSItems.get(Recipe.getItem2().getID());
		if(Item1Defined!=null && Item1Defined.length>0){
			Item1Defined[0].click("");
			ClientAPIWrappers.waitItemDelay();
			if(Item2Defined!=null && Item2Defined.length>0){
				Item2Defined[0].click("");
				ClientAPIWrappers.waitItemDelay();
				Utilities.waitFor(new Condition() {@Override
					public boolean active() {
					RSItems[] ItemTwoCheck = RSItems.get(Recipe.getItem2().getID());
					return ItemTwoCheck!=null && ItemTwoCheck.length>0 && Item2Defined[0].getStackSize()-ItemTwoCheck[0].getStackSize()==10;
				}
				}, Utilities.getRandom(1000, 1500));
			}
		}
	}
	public static void cutGems(final FletchingRecipe Recipe){
		interfaces FletchingIFace = interfaces.getByComponentName(Recipe.getItem2().getInGameName());
		if(FletchingIFace == null){
			Statistics.Status="Using "+Recipe.getItem1().getInGameName();
			RSItems[] Item1Defined = RSItems.get(Recipe.getItem1().getID());
			RSItems[] Item2Defined = RSItems.get(Recipe.getItem2().getID());
			if(Item1Defined!=null && Item1Defined.length>0){
				Item1Defined[0].click("");
				ClientAPIWrappers.waitItemDelay();
				if(Item2Defined!=null && Item2Defined.length>0){
					Item2Defined[0].click("");
					ClientAPIWrappers.waitItemDelay();
					Utilities.waitFor(new Condition() {@Override
						public boolean active() {
						return interfaces.getByComponentName(Recipe.getItem2().getInGameName()) != null;
					}
					}, Utilities.getRandom(1000, 1500));
				}
			}
		}
		else{
			interfaces makeAllIFace = interfaces.getByAction("All"); // The All IFace only has the Action "ALL" when not active
			if(makeAllIFace != null){
				Statistics.Status="Clicking Make All";
				makeAllIFace.click("");
				Utilities.waitFor(new Condition() {@Override
				public boolean active() {
					return interfaces.getByAction("All") == null;
				}
				}, Utilities.getRandom(2000, 3500));
			}
			else{
				Statistics.Status="Clicking Make All";
				FletchingIFace.click("");
				Utilities.SleepWhileFlashAnimating(new Condition() {
					@Override
					public boolean active() {
						return !(RSItems.get(Recipe.getItem1().getID()) != null &&
								RSItems.get(Recipe.getItem1().getID()).length > 0 &&
								RSItems.get(Recipe.getItem2().getID()) != null &&
								RSItems.get(Recipe.getItem2().getID()).length > 0);
					}
				});
			}

		}
	}
}
