package scripts;

import scripts.AutoFletcherEliteUtilities.RSItems;
import scripts.AutoFletcherEliteUtilities.interfaces;

public class AutoFletcherEliteActions {

	public static void GenericBowCutting(String InGameLogName, int RSInterFaceMaster, int RSInterFaceChild){
		if(!AutoFletcherEliteUtilities.isCuttingIFaceOpen() && AutoFletcherEliteUtilities.isEnterXIFaceOpen()){
			AutoFletcherEliteStatistics.Status="Entering Amount";
			ClientAPIWrappers.sendKeyboardKeys(""+AutoFletcherEliteUtilities.getRandom(30,200));
			AutoFletcherEliteUtilities.waitFor(new Condition() {@Override
				public boolean active() {
				return ClientAPIWrappers.getPlayerAnimation()!=-1;
			}
			}, AutoFletcherEliteUtilities.getRandom(2000, 3500));
		}
		else if(!AutoFletcherEliteUtilities.isCuttingIFaceOpen()){
			AutoFletcherEliteStatistics.Status="Using Knife";
			RSItems[] KnifeDefined = RSItems.get("Knife");
			RSItems[] Item2Defined = RSItems.get(InGameLogName);
			if(KnifeDefined!=null && KnifeDefined.length>0){
				KnifeDefined[0].click("");
				AntiBanCompliance.waitItemDelay();
				if(Item2Defined!=null && Item2Defined.length>0){
					if(Item2Defined[0].click("")){
						AntiBanCompliance.waitItemDelay();
						AutoFletcherEliteUtilities.waitFor(new Condition() {@Override
							public boolean active() {
							return AutoFletcherEliteUtilities.isCuttingIFaceOpen();
						}
						}, AutoFletcherEliteUtilities.getRandom(1000, 1500));
					}
				}
			}
		}
		else{
			AutoFletcherEliteStatistics.Status="Clicking Make X";
			interfaces IFace = interfaces.get(RSInterFaceMaster,RSInterFaceChild);
			if(IFace!=null){
				if(IFace.click("Make x")){
					AntiBanCompliance.waitItemDelay();
					AutoFletcherEliteUtilities.waitFor(new Condition() {@Override
						public boolean active() {
						return AutoFletcherEliteUtilities.isEnterXIFaceOpen();
					}
					}, AutoFletcherEliteUtilities.getRandom(2000, 3500));
				}
			}
		}

	}
	public static void StringBows(final String ItemOneName){
		final String ItemTwoName = "Bow string";
		if(!( interfaces.get(309,6)!=null && !interfaces.get(309,6).isHidden())){
			AutoFletcherEliteStatistics.Status="Using Bow String";
			RSItems[] Item1Defined = RSItems.get(ItemOneName);
			RSItems[] Item2Defined = RSItems.get(ItemTwoName);
			if(Item1Defined!=null && Item1Defined.length>0){
				Item1Defined[0].click("");
				AntiBanCompliance.waitItemDelay();
				if(Item2Defined!=null && Item2Defined.length>0){
					Item2Defined[0].click("");
					AntiBanCompliance.waitItemDelay();
					AutoFletcherEliteUtilities.waitFor(new Condition() {@Override
						public boolean active() {
						return ( interfaces.get(309,6)!=null && !interfaces.get(309,6).isHidden());
					}
					}, AutoFletcherEliteUtilities.getRandom(1000, 1500));
				}
			}
		}
		else{
			AutoFletcherEliteStatistics.Status="Stringing All";
			interfaces BowStringingMenu = interfaces.get(309, 6);
			if(BowStringingMenu!=null && !BowStringingMenu.isHidden()){
				BowStringingMenu.click("Make All");
				AutoFletcherEliteUtilities.SleepWhileFlashAnimating(new Condition() {@Override
					public boolean active() {
					return !(RSItems.get(ItemOneName)!=null && RSItems.get(ItemOneName).length>0 && RSItems.get(ItemTwoName)!=null && RSItems.get(ItemTwoName).length>0);
				}
				});
			}
		}
	}
	public static void useArrowHeadorBoltTip(String ItemOneName, final String ItemTwoName){
		if(!ClientAPIWrappers.isInterfaceValid(582)){
			AutoFletcherEliteStatistics.Status="Using "+ItemOneName;
			RSItems[] Item1Defined = RSItems.get(ItemOneName);
			RSItems[] Item2Defined = RSItems.get(ItemTwoName);
			if(Item1Defined!=null && Item1Defined.length>0){
				Item1Defined[0].click("use");
				AntiBanCompliance.waitItemDelay();
				if(Item2Defined!=null && Item2Defined.length>0){
					Item2Defined[0].click("");
					AntiBanCompliance.waitItemDelay();
					AutoFletcherEliteUtilities.waitFor(new Condition() {@Override
						public boolean active() {
						return ClientAPIWrappers.isInterfaceValid(582);
					}
					}, AutoFletcherEliteUtilities.getRandom(1000, 1500));
				}
			}
		}
		else{
			AutoFletcherEliteStatistics.Status="Clicking Make 10";
			interfaces ArrowMenu = interfaces.get(582, 5);
			final RSItems[] ItemTwo = RSItems.get(ItemTwoName);
			if(ArrowMenu!=null && !ArrowMenu.isHidden() && ItemTwo!=null && ItemTwo.length>0){
				ArrowMenu.click("Make 10 sets");
				AutoFletcherEliteUtilities.SleepWhileInventoryIsChanging(new Condition() {@Override
					public boolean active() {
					RSItems[] ItemTwoCheck = RSItems.get(ItemTwoName);
					return ItemTwoCheck!=null && ItemTwoCheck.length>0 && ItemTwo[0].getStackSize()-ItemTwoCheck[0].getStackSize()==150;
				}
				});
			}
		}
	}
	public static void useFeathers(String ItemOneName,final String ItemTwoName){
		AutoFletcherEliteStatistics.Status="Using "+ItemOneName;
		final RSItems[] Item1Defined = RSItems.get(ItemOneName);
		final RSItems[] Item2Defined = RSItems.get(ItemTwoName);
		if(Item1Defined!=null && Item1Defined.length>0){
			Item1Defined[0].click("");
			AntiBanCompliance.waitItemDelay();
			if(Item2Defined!=null && Item2Defined.length>0){
				Item2Defined[0].click("");
				AntiBanCompliance.waitItemDelay();
				AutoFletcherEliteUtilities.waitFor(new Condition() {@Override
					public boolean active() {
					RSItems[] ItemTwoCheck = RSItems.get(ItemTwoName);
					return ItemTwoCheck!=null && ItemTwoCheck.length>0 && Item2Defined[0].getStackSize()-ItemTwoCheck[0].getStackSize()==10;
				}
				}, AutoFletcherEliteUtilities.getRandom(1000, 1500));
			}
		}
	}
	public static void cutGems(final String ItemOneName, final String ItemTwoName){
		if(ClientAPIWrappers.isInterfaceValid(309)){
			AutoFletcherEliteStatistics.Status="Using "+ItemOneName;
			RSItems[] Item1Defined = RSItems.get(ItemOneName);
			RSItems[] Item2Defined = RSItems.get(ItemTwoName);
			if(Item1Defined!=null && Item1Defined.length>0){
				Item1Defined[0].click("");
				AntiBanCompliance.waitItemDelay();
				if(Item2Defined!=null && Item2Defined.length>0){
					Item2Defined[0].click("");
					AntiBanCompliance.waitItemDelay();
					AutoFletcherEliteUtilities.waitFor(new Condition() {@Override
						public boolean active() {
						return ClientAPIWrappers.isInterfaceValid(309);
					}
					}, AutoFletcherEliteUtilities.getRandom(1000, 1500));
				}
			}
		}
		else{
			AutoFletcherEliteStatistics.Status="Clicking Make All";
			interfaces BowStringingMenu = interfaces.get(309, 6);
			if(BowStringingMenu!=null && !BowStringingMenu.isHidden()){
				BowStringingMenu.click("Make All");
				AutoFletcherEliteUtilities.SleepWhileFlashAnimating(new Condition() {@Override
					public boolean active() {
					return !(RSItems.get(ItemOneName)!=null && RSItems.get(ItemOneName).length>0 && RSItems.get(ItemTwoName)!=null && RSItems.get(ItemTwoName).length>0);
				}
				});
			}
		}
	}
}
