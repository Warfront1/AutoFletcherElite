package scripts.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import scripts.actions.FletchingActions;
import scripts.actions.Utilities;



public class FletchingRecipe {
	
	public static HashMap<String, FletchingRecipe> Recipes;
	
	FletchingItem Item1;
	FletchingItem Item2;
	FletchingItem EndProduct;
	double xp;
	String Action;
	FletchingRecipe(String Item1, String Item2, String EndProduct,double xp,String Action){
		this.Item1=FletchingItem.Items.get(Item1);
		this.Item2=FletchingItem.Items.get(Item2);
		this.EndProduct=FletchingItem.Items.get(EndProduct);
		this.xp=xp;
		this.Action=Action;
	}
	public String getAction(){
		return this.Action;
	}
	public double getXPPer(){
		return this.xp;
	}
	public FletchingItem getItem1(){
		return this.Item1;
	}
	public FletchingItem getItem2() {
		return this.Item2;
	}
	public FletchingItem getEndProduct() {
		return this.EndProduct;
	}
	public void run(){
		switch(Action){
			case "Cutting":
				if(Utilities.isReadyToFletch(this, 1, 27)){
					FletchingActions.GenericBowCutting(this);
				}
				break;
			case "CuttingGems":
				if(Utilities.isReadyToFletch(this,1,27)){
					FletchingActions.cutGems(this);
				}
				break;
			case "AttachingFeathers":
				if(Utilities.isReadyToFletch(this,1,1)){
					FletchingActions.useFeathers(this);
				}
				break;
			case "AttachingHead":
				if(Utilities.isReadyToFletch(this,1,1)){
					FletchingActions.useArrowHeadorBoltTip(this);
				}
				break;
			case "Stringing":
				if(Utilities.isReadyToFletch(this,14,14)){
					FletchingActions.StringBows(this);
				}
				break;
		}
	}
	public static void loadRecipes(){
		FletchingItem.loadItems();
		try {
			URL url = new URL("http://warfront1.github.io/AutoFletcherElite/UserInterfaces/recipes.xml");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader XmlReader = factory.createXMLStreamReader(in);


			HashMap<String, FletchingRecipe> FletchingRecipesMap = new HashMap<String, FletchingRecipe>();
			String LastLocalName="";
			String Item1="";
			String Item2="";
			String EndProduct="";
			double xp=0.0; 
			String Action="";
			while(XmlReader.hasNext()) {
				XmlReader.next();
				switch(XmlReader.getEventType()){
				case XMLEvent.CDATA:
				case XMLEvent.SPACE:
				case XMLEvent.CHARACTERS:
					if(XmlReader.getText().matches("[0-9A-Za-z() ]+")){
						if(LastLocalName.equals("Item1")){
							Item1 = XmlReader.getText().trim();
						}
						else if(LastLocalName.equals("Item2")){
							Item2 = XmlReader.getText().trim();
						}
						else if(LastLocalName.equals("EndProduct")){
							EndProduct = XmlReader.getText().trim();
						}
						else if(LastLocalName.equals("xp")){
							xp = Double.parseDouble(XmlReader.getText().trim());
						}
						else if(LastLocalName.equals("Action")){
							Action = XmlReader.getText().trim();
							FletchingRecipesMap.put(EndProduct,new FletchingRecipe(Item1,Item2,EndProduct, xp, Action));
						}
					}
					break;
				case XMLEvent.END_ELEMENT:
					break;
				case XMLEvent.START_ELEMENT:
					if((!XmlReader.getLocalName().equals("Recipe"))){
						LastLocalName=XmlReader.getLocalName().trim();
					}
					break;
				}
			}
			Recipes = FletchingRecipesMap;
		} catch (XMLStreamException | IOException e) {
			System.out.println("Error Downloading/Loading Fletching Recipes Data Files");
		}
	}
}
