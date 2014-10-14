package scripts.actions;

import scripts.AntiBanCompliance;
import scripts.ClientAPIWrappers;
import scripts.actions.Utilities.interfaces;
import scripts.actions.Utilities.RSItems;
import scripts.data.FletchingRecipe;
import scripts.data.collection.Statistics;
import scripts.timing.Condition;

public class FletchingActions {

	public static void GenericBowCutting(final FletchingRecipe Recipe){
		final String IFaceText = (Recipe.getEndProduct().getInGameName().replace("(u)", "")).replaceAll("[\\s ]","").toLowerCase();
		interfaces FletchingIFace = interfaces.getByText(IFaceText);
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
					AntiBanCompliance.waitItemDelay();
					if(Item2Defined!=null && Item2Defined.length>0){
						if(Item2Defined[0].click("")){
							AntiBanCompliance.waitItemDelay();
							Utilities.waitFor(new Condition() {@Override
								public boolean active() {
								return interfaces.getByText(IFaceText)!=null;
							}
							}, Utilities.getRandom(1000, 1500));
						}
					}
				}
			}
		}
		else{
			Statistics.Status="Clicking Make X";
			if(FletchingIFace.click("Make x")){
				AntiBanCompliance.waitItemDelay();
				Utilities.waitFor(new Condition() {@Override
					public boolean active() {
					return Utilities.isEnterXIFaceOpen();
				}
				}, Utilities.getRandom(2000, 3500));
			}
		}
	}
	public static void StringBows(final FletchingRecipe Recipe){
		if(!( interfaces.get(309,6)!=null && !interfaces.get(309,6).isHidden())){
			Statistics.Status="Using Bow String";
			RSItems[] Item1Defined = RSItems.get(Recipe.getItem1().getID());
			RSItems[] Item2Defined = RSItems.get(Recipe.getItem2().getID());
			if(Item1Defined!=null && Item1Defined.length>0){
				Item1Defined[0].click("");
				AntiBanCompliance.waitItemDelay();
				if(Item2Defined!=null && Item2Defined.length>0){
					Item2Defined[0].click("");
					AntiBanCompliance.waitItemDelay();
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
			interfaces BowStringingMenu = interfaces.get(309, 6);
			if(BowStringingMenu!=null && !BowStringingMenu.isHidden()){
				BowStringingMenu.click("Make All");
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
				AntiBanCompliance.waitItemDelay();
				if(Item2Defined!=null && Item2Defined.length>0){
					Item2Defined[0].click("");
					AntiBanCompliance.waitItemDelay();
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
			AntiBanCompliance.waitItemDelay();
			if(Item2Defined!=null && Item2Defined.length>0){
				Item2Defined[0].click("");
				AntiBanCompliance.waitItemDelay();
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
		if(ClientAPIWrappers.isInterfaceValid(309)){
			Statistics.Status="Using "+Recipe.getItem1().getInGameName();
			RSItems[] Item1Defined = RSItems.get(Recipe.getItem1().getID());
			RSItems[] Item2Defined = RSItems.get(Recipe.getItem2().getID());
			if(Item1Defined!=null && Item1Defined.length>0){
				Item1Defined[0].click("");
				AntiBanCompliance.waitItemDelay();
				if(Item2Defined!=null && Item2Defined.length>0){
					Item2Defined[0].click("");
					AntiBanCompliance.waitItemDelay();
					Utilities.waitFor(new Condition() {@Override
						public boolean active() {
						return ClientAPIWrappers.isInterfaceValid(309);
					}
					}, Utilities.getRandom(1000, 1500));
				}
			}
		}
		else{
			Statistics.Status="Clicking Make All";
			interfaces BowStringingMenu = interfaces.get(309, 6);
			if(BowStringingMenu!=null && !BowStringingMenu.isHidden()){
				BowStringingMenu.click("Make All");
				Utilities.SleepWhileFlashAnimating(new Condition() {@Override
					public boolean active() {
					return !(RSItems.get(Recipe.getItem1().getID())!=null && RSItems.get(Recipe.getItem1().getID()).length>0 && RSItems.get(Recipe.getItem2().getID())!=null && RSItems.get(Recipe.getItem2().getID()).length>0);
				}
				});
			}
		}
	}
}
