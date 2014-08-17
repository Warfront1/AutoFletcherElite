package scripts.actions;

import scripts.AntiBanCompliance;
import scripts.ClientAPIWrappers;
import scripts.actions.Utilities.interfaces;
import scripts.actions.Utilities.RSItems;
import scripts.data.collection.Statistics;


public class FletchingActions {

	public static void GenericBowCutting(String InGameLogName, int RSInterFaceMaster, int RSInterFaceChild){
		if(!Utilities.isCuttingIFaceOpen() && Utilities.isEnterXIFaceOpen()){
			Statistics.Status="Entering Amount";
			ClientAPIWrappers.sendKeyboardKeys(""+Utilities.getRandom(30,200));
			Utilities.waitFor(new Condition() {@Override
				public boolean active() {
				return ClientAPIWrappers.getPlayerAnimation()!=-1;
			}
			}, Utilities.getRandom(2000, 3500));
		}
		else if(!Utilities.isCuttingIFaceOpen()){
			Statistics.Status="Using Knife";
			RSItems[] KnifeDefined = RSItems.get("Knife");
			RSItems[] Item2Defined = RSItems.get(InGameLogName);
			if(KnifeDefined!=null && KnifeDefined.length>0){
				KnifeDefined[0].click("");
				AntiBanCompliance.waitItemDelay();
				if(Item2Defined!=null && Item2Defined.length>0){
					if(Item2Defined[0].click("")){
						AntiBanCompliance.waitItemDelay();
						Utilities.waitFor(new Condition() {@Override
							public boolean active() {
							return Utilities.isCuttingIFaceOpen();
						}
						}, Utilities.getRandom(1000, 1500));
					}
				}
			}
		}
		else{
			Statistics.Status="Clicking Make X";
			interfaces IFace = interfaces.get(RSInterFaceMaster,RSInterFaceChild);
			if(IFace!=null){
				if(IFace.click("Make x")){
					AntiBanCompliance.waitItemDelay();
					Utilities.waitFor(new Condition() {@Override
						public boolean active() {
						return Utilities.isEnterXIFaceOpen();
					}
					}, Utilities.getRandom(2000, 3500));
				}
			}
		}

	}
	public static void StringBows(final String ItemOneName){
		final String ItemTwoName = "Bow string";
		if(!( interfaces.get(309,6)!=null && !interfaces.get(309,6).isHidden())){
			Statistics.Status="Using Bow String";
			RSItems[] Item1Defined = RSItems.get(ItemOneName);
			RSItems[] Item2Defined = RSItems.get(ItemTwoName);
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
					return !(RSItems.get(ItemOneName)!=null && RSItems.get(ItemOneName).length>0 && RSItems.get(ItemTwoName)!=null && RSItems.get(ItemTwoName).length>0);
				}
				});
			}
		}
	}
	public static void useArrowHeadorBoltTip(String ItemOneName, final String ItemTwoName){
		if(!ClientAPIWrappers.isInterfaceValid(582)){
			Statistics.Status="Using "+ItemOneName;
			RSItems[] Item1Defined = RSItems.get(ItemOneName);
			RSItems[] Item2Defined = RSItems.get(ItemTwoName);
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
			final RSItems[] ItemTwo = RSItems.get(ItemTwoName);
			if(ArrowMenu!=null && !ArrowMenu.isHidden() && ItemTwo!=null && ItemTwo.length>0){
				ArrowMenu.click("Make 10 sets");
				Utilities.SleepWhileInventoryIsChanging(new Condition() {@Override
					public boolean active() {
					RSItems[] ItemTwoCheck = RSItems.get(ItemTwoName);
					return ItemTwoCheck!=null && ItemTwoCheck.length>0 && ItemTwo[0].getStackSize()-ItemTwoCheck[0].getStackSize()==150;
				}
				});
			}
		}
	}
	public static void useFeathers(String ItemOneName,final String ItemTwoName){
		Statistics.Status="Using "+ItemOneName;
		final RSItems[] Item1Defined = RSItems.get(ItemOneName);
		final RSItems[] Item2Defined = RSItems.get(ItemTwoName);
		if(Item1Defined!=null && Item1Defined.length>0){
			Item1Defined[0].click("");
			AntiBanCompliance.waitItemDelay();
			if(Item2Defined!=null && Item2Defined.length>0){
				Item2Defined[0].click("");
				AntiBanCompliance.waitItemDelay();
				Utilities.waitFor(new Condition() {@Override
					public boolean active() {
					RSItems[] ItemTwoCheck = RSItems.get(ItemTwoName);
					return ItemTwoCheck!=null && ItemTwoCheck.length>0 && Item2Defined[0].getStackSize()-ItemTwoCheck[0].getStackSize()==10;
				}
				}, Utilities.getRandom(1000, 1500));
			}
		}
	}
	public static void cutGems(final String ItemOneName, final String ItemTwoName){
		if(ClientAPIWrappers.isInterfaceValid(309)){
			Statistics.Status="Using "+ItemOneName;
			RSItems[] Item1Defined = RSItems.get(ItemOneName);
			RSItems[] Item2Defined = RSItems.get(ItemTwoName);
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
					return !(RSItems.get(ItemOneName)!=null && RSItems.get(ItemOneName).length>0 && RSItems.get(ItemTwoName)!=null && RSItems.get(ItemTwoName).length>0);
				}
				});
			}
		}
	}
}
