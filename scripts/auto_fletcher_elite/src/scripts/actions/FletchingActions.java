package scripts.actions;

import scripts.ClientAPIWrappers;
import scripts.actions.Utilities.interfaces;
import scripts.actions.Utilities.RSItems;
import scripts.data.FletchingRecipe;
import scripts.data.collection.Statistics;
import scripts.timing.Condition;

public class FletchingActions {

	public static void GenericBowCutting(final FletchingRecipe Recipe, String maxQuantityPerIFaceText){
		String IFaceText = Recipe.getInterfaceText();
		if (IFaceText == null){
			IFaceText = (Recipe.getEndProduct().getInGameName().replace("(u)", ""));
		}
		System.out.println(IFaceText);
		interfaces FletchingIFace = interfaces.getByComponentName(IFaceText);
		if(FletchingIFace==null){
			Statistics.Status="Using " + Recipe.getItem1().getInGameName();
			RSItems[] item1 = RSItems.get(Recipe.getItem1().getID());
			RSItems[] item2 = RSItems.get(Recipe.getItem2().getID());
			if(item1!=null && item1.length>0){
				item1[0].click("");
				ClientAPIWrappers.waitItemDelay();
				if(item2!=null && item2.length>0){
					if(item2[0].click("")){
						ClientAPIWrappers.waitItemDelay();
						if(!Recipe.getAction().equals("AttachingFeathers")) {
							final String finalIFaceText = IFaceText;
							Utilities.waitFor(() -> interfaces.getByText(finalIFaceText) != null,
									Utilities.getRandom(1000, 1500));
						}
					}
				}
			}
		}
		else{
			interfaces maxQuantityPerIFace = interfaces.getByAction(maxQuantityPerIFaceText); // The All IFace only has the Action "ALL" when not active
			if(maxQuantityPerIFace != null){
				Statistics.Status="Clicking Make " + maxQuantityPerIFaceText;
				maxQuantityPerIFace.click("");
				Utilities.waitFor(new Condition() {@Override
				public boolean active() {
					return interfaces.getByAction(maxQuantityPerIFaceText) == null;
				}
				}, Utilities.getRandom(2000, 3500));
			}
			else{
				Statistics.Status="Clicking Fletch " + maxQuantityPerIFaceText;
				FletchingIFace.click("");

				//TODO replace following conditional if/else with a blanket dynamic/flash animating sleep
				if(maxQuantityPerIFaceText.equalsIgnoreCase("All")) {
					Utilities.SleepWhileFlashAnimating(new Condition() {
						@Override
						public boolean active() {
							return !(RSItems.get(Recipe.getItem1().getID()) != null && RSItems.get(Recipe.getItem1().getID()).length > 0 && RSItems.get(Recipe.getItem2().getID()) != null && RSItems.get(Recipe.getItem2().getID()).length > 0);
						}
					});
				}
				else{
					final RSItems[] ItemTwo = RSItems.get(Recipe.getItem2().getID());
					Utilities.SleepWhileInventoryIsChanging(new Condition() {@Override
					public boolean active() {
						RSItems[] ItemTwoCheck = RSItems.get(Recipe.getItem2().getID());
						return ItemTwoCheck!=null && ItemTwoCheck.length>0 && ItemTwo[0].getStackSize()-ItemTwoCheck[0].getStackSize()==150;
					}
					});
				}
			}
		}
	}
}
