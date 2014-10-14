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

public class FletchingItem {
	static HashMap<String, FletchingItem> Items;
	
	String InGameName;
	int ID;
	String ImageURL;
	FletchingItem(String InGameName, int ID, String ImageURL){
		this.InGameName=InGameName;
		this.ID = ID;
		this.ImageURL = ImageURL;
	}
	public String getInGameName(){
		return this.InGameName;
	}
	public String getImageURL(){
		return this.ImageURL;
	}
	public int getID(){
		return this.ID;
	}
	public static void loadItems(){
		try {
			URL url = new URL("http://warfront1.github.io/AutoFletcherElite/UserInterfaces/items.xml");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		    XMLInputFactory factory = XMLInputFactory.newInstance();
		    XMLStreamReader XmlReader = factory.createXMLStreamReader(in);
		    
		    
		    HashMap<String, FletchingItem> FletchingItemsMap = new HashMap<String, FletchingItem>();
		    String LastLocalName="";
		    String InGameName="";
		    int ID=0;
		    String ImageURL;
		    while(XmlReader.hasNext()) {
		    	XmlReader.next();
		    		switch(XmlReader.getEventType()){
		                case XMLEvent.CDATA:
		                case XMLEvent.SPACE:
		                case XMLEvent.CHARACTERS:
		                	if(!XmlReader.isWhiteSpace()){
		                		if(LastLocalName.equals("InGameName")){
		                			InGameName = XmlReader.getText().trim();
		                		}
		                		else if(LastLocalName.equals("ID")){
		                			ID = Integer.parseInt(XmlReader.getText().trim());
		                		}
		                		else if(LastLocalName.equals("ImageURL")){
		                			ImageURL = XmlReader.getText().trim();
		                			FletchingItemsMap.put(InGameName,new FletchingItem(InGameName,ID,ImageURL));
		                		}
		                	}
		                    break;
		                case XMLEvent.END_ELEMENT:
		                    break;
		                case XMLEvent.START_ELEMENT:
	                			LastLocalName=XmlReader.getLocalName().trim();
		                	break;
		    		}
		    }
		    Items = FletchingItemsMap;
		} 
		catch (IOException | XMLStreamException e) {
			System.out.println("Error Downloading/Loading Fletching Item Data Files");
		}
	}
}
