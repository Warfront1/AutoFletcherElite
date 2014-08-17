package scripts;

import com.sun.javafx.application.PlatformImpl;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import netscape.javascript.JSObject;

import scripts.AutoFletcherEliteData.Arrows;
import scripts.AutoFletcherEliteData.Bolts;
import scripts.AutoFletcherEliteData.Bows;
import scripts.AutoFletcherEliteData.Darts;
  
/** 
 * SwingFXWebView 
 */  
public class AutoFletcherEliteGUIV3 extends JPanel {  
     
	static Object Object= null;
    private Stage stage;  
    private WebView browser;  
    private JFXPanel jfxPanel;  
    private JButton swingButton;  
    final static JFrame frame = new JFrame(); 
//    private static WebEngine webEngine;  
  
    public AutoFletcherEliteGUIV3(){  
        initComponents();  
        setBackground(new java.awt.Color(39, 43, 48));
    }  
  
    public static void main(String args[]){  
        // Run this later:
        SwingUtilities.invokeLater(new Runnable() {  
            @Override
            public void run() {  
                 frame.setTitle("Auto Fletcher Elite");
                frame.getContentPane().add(new AutoFletcherEliteGUIV3());  
                 
                frame.setMinimumSize(new Dimension(500, 696));  
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
                frame.setVisible(true);  
            }  
        });     
    }  
     
    private void initComponents(){  
         
        jfxPanel = new JFXPanel();  
        createScene();  
         
//        setLayout(new BorderLayout());  
        add(jfxPanel);  
         
         
//        add(swingButton, BorderLayout.SOUTH);  
    }     
     
    /** 
     * createScene 
     * 
     * Note: Key is that Scene needs to be created and run on "FX user thread" 
     *       NOT on the AWT-EventQueue Thread 
     * 
     */  
    private void createScene() {  
        PlatformImpl.startup(new Runnable() {  
            @Override
            public void run() {  
                 
                stage = new Stage();  
                 
                stage.setTitle("Hello Java FX");  
                stage.setResizable(true);  
   
                StackPane root =  new StackPane();
                Scene scene = new Scene(root, 470,680);  
                stage.setScene(scene);  
                Platform.setImplicitExit(false);
                 
                // Set up the embedded browser:
                browser = new WebView();
                final WebEngine webEngine = browser.getEngine();
                webEngine.setJavaScriptEnabled(true);
                webEngine.load("http://elitescripts.tk/autofletcherelite/AutoFletcherEliteGUI.html");
                webEngine.getLoadWorker().stateProperty().addListener(
                        new ChangeListener<Worker.State>() {
                            @Override
                            public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                                if (newState == Worker.State.SUCCEEDED) {                        
                                    JSObject jso = (JSObject) webEngine.executeScript("window");
                                    jso.setMember("java", new Bridge(webEngine));
                                }

                            }
                        });
                ObservableList<Node> children = root.getChildren();
                children.add(browser);                     
                
                jfxPanel.setScene(scene);  
            }  
        });  
    }
    public class Bridge {
        WebEngine engine;
        boolean runOnce = true;
        Bridge(WebEngine engine){
            this.engine=engine;
//            engine.executeScript("$(document).ready(function() {"+
//            		"startmethod='"+start()+"'; " + 
//            		"setThirdBox='"+setThirdBox()+"'; " + 
//            		"setThirdBoxTable='"+setThirdBoxTable()+"'; " + 
//            		"exit='"+exit()+"'; " + 
//            		"});");
	          engine.executeScript("startmethod='"+start()+"'; " + 
	    		"setThirdBox='"+setThirdBox()+"'; " + 
	    		"setThirdBoxTable='"+setThirdBoxTable()+"'; " + 
	    		"exit='"+exit()+"'; ");
        }
//        public String testBridge(){
//        	System.out.println(("WebEngine Bridge Connectivity Established"));
//        	Exception e = new Exception();
//        	e.fillInStackTrace();
//        	String methodName = e.getStackTrace()[0].getMethodName();
//        	System.out.println(("java."+methodName+"();"));
////        	return "'java."+methodName+"();'";
//        	return "java."+methodName+"()";
//        }
        public String start(){
        	if(runOnce){
        		Exception e = new Exception();
            	e.fillInStackTrace();
            	String methodName = e.getStackTrace()[0].getMethodName();
            	return "java."+methodName+"()";
        	}
//        	System.out.println(("First Box Action Listener Fired"));
        	setSecond((String) engine.executeScript("getFirstBoxString()"),engine);	
        	return " ";
        }
        public String setThirdBox(){
        	if(runOnce){
        		Exception e = new Exception();
            	e.fillInStackTrace();
            	String methodName = e.getStackTrace()[0].getMethodName();
            	return "java."+methodName+"()";
        	}
        	setThird(engine);
        	return " ";
        }
       public String setThirdBoxTable(){
       	if(runOnce){
    		Exception e = new Exception();
        	e.fillInStackTrace();
        	String methodName = e.getStackTrace()[0].getMethodName();
        	return "java."+methodName+"()";
    	}
    	   pullFinalObjectFromGUI(engine);
    	   return " ";
       }
       public String exit() {
          	if(runOnce){
       		Exception e = new Exception();
           	e.fillInStackTrace();
           	String methodName = e.getStackTrace()[0].getMethodName();
           	runOnce=false;
           	return "java."+methodName+"()";
       	}
       	 Object=pullFinalObjectFromGUI(engine);
       	 System.out.println("GUI Final Object Selected: "+Object);
       	 frame.dispose();
       	 return " ";
          }
   }
    public String JavaArrayToJavaScript(Object[] Array){
    	String returnstring = "var x = [";
    	for(Object i: Array){
    		returnstring= returnstring+ "'"+i.toString()+ "',";
    	}
    	return returnstring+"];";
    }
    public void setThird(WebEngine engine){
    	switch((String) engine.executeScript("getFirstBoxString()")){
			case "Bows":
				switch((String) engine.executeScript("getSecondBoxString()")){
					case "Cutting":
						engine.executeScript("setFirstItemImage('"+AutoFletcherEliteData.FletchingTools.Knife.getImageLocation()+"','"+AutoFletcherEliteData.FletchingTools.Knife.InGameName+"');");
						engine.executeScript(JavaArrayToJavaScript(Bows.Cutting.values()));
						engine.executeScript("setThirdBoxString(x);");
						engine.executeScript("showThirdBox();");
					break;
					case "Stringing":
						engine.executeScript("setFirstItemImage('"+AutoFletcherEliteData.FletchingTools.Bowstring.getImageLocation()+"','"+AutoFletcherEliteData.FletchingTools.Bowstring.InGameName+"');");
						engine.executeScript(JavaArrayToJavaScript(Bows.Stringing.values()));
						engine.executeScript("setThirdBoxString(x);");
						engine.executeScript("showThirdBox();");
					break;
				}
			break;
			case "Arrows":
				switch((String) engine.executeScript("getSecondBoxString()")){
					case "Arrow Tips":
						engine.executeScript("setFirstItemImage('"+AutoFletcherEliteData.FletchingTools.HeadlessArrow.getImageLocation()+"','"+AutoFletcherEliteData.FletchingTools.HeadlessArrow.InGameName+"');");
						engine.executeScript(JavaArrayToJavaScript(Arrows.AttachArrowTips.values()));
						engine.executeScript("setThirdBoxString(x);");
						engine.executeScript("showThirdBox();");
					break;
					case "Headless Arrows":
						engine.executeScript("setFirstItemImage('"+AutoFletcherEliteData.FletchingTools.Feather.getImageLocation()+"','"+AutoFletcherEliteData.FletchingTools.Feather.InGameName+"');");
						engine.executeScript("setSecondItemImage('"+AutoFletcherEliteData.Bows.Cutting.Shafts.getImageLocation()+"','"+AutoFletcherEliteData.Bows.Cutting.Shafts.endProductInGameName+"');");
						engine.executeScript("setThirdItemImage('"+AutoFletcherEliteData.FletchingTools.HeadlessArrow.getImageLocation()+"','"+AutoFletcherEliteData.FletchingTools.HeadlessArrow.InGameName+"');");
						engine.executeScript("hideThirdBox();");
						Object = Arrows.HeadlessArrows.Shafts;
					break;
					case "Shafts":
						engine.executeScript("setFirstItemImage('"+AutoFletcherEliteData.FletchingTools.Knife.getImageLocation()+"','"+AutoFletcherEliteData.FletchingTools.Knife.InGameName+"');");
						engine.executeScript("setSecondItemImage('"+AutoFletcherEliteData.LogType.Logs.getImageLocation()+"','"+AutoFletcherEliteData.LogType.Logs.InGameLogName+"');");
						engine.executeScript("setThirdItemImage('"+AutoFletcherEliteData.Bows.Cutting.Shafts.getImageLocation()+"','"+AutoFletcherEliteData.Bows.Cutting.Shafts.endProductInGameName+"');");
						engine.executeScript("hideThirdBox();");
						Object = Bows.Cutting.Shafts;
					break;
				}
			break;
			case "Darts":
				switch((String) engine.executeScript("getSecondBoxString()")){
					case "Attach Feathers":
						engine.executeScript("setFirstItemImage('"+AutoFletcherEliteData.FletchingTools.Feather.getImageLocation()+"','"+AutoFletcherEliteData.FletchingTools.Feather.InGameName+"');");
						engine.executeScript(JavaArrayToJavaScript(Darts.AttachFeathersToDartTips.values()));
						engine.executeScript("setThirdBoxString(x);");
						engine.executeScript("showThirdBox();");
					break;
				}
			break;
			case "Bolts":
				switch((String) engine.executeScript("getSecondBoxString()")){
					case "Attach Bolt Tips":
						engine.executeScript(JavaArrayToJavaScript(Bolts.AttachBoltTips.values()));
						engine.executeScript("setThirdBoxString(x);");
						engine.executeScript("showThirdBox();");
					break;
					case "Attach Feathers":
						engine.executeScript("setFirstItemImage('"+AutoFletcherEliteData.FletchingTools.Feather.getImageLocation()+"','"+AutoFletcherEliteData.FletchingTools.Feather.InGameName+"');");
						engine.executeScript(JavaArrayToJavaScript(Bolts.AttachFeathersToBolts.values()));
						engine.executeScript("setThirdBoxString(x);");
						engine.executeScript("showThirdBox();");
					break;
					case  "Cut: Gems -> Tips":
						engine.executeScript("setFirstItemImage('"+AutoFletcherEliteData.FletchingTools.Chisel.getImageLocation()+"','"+AutoFletcherEliteData.FletchingTools.Chisel.InGameName+"');");
						engine.executeScript(JavaArrayToJavaScript(Bolts.CutGemstoTips.values()));
						engine.executeScript("setThirdBoxString(x);");
						engine.executeScript("showThirdBox();");
					break;
					case "Cut: Uncut Gems -> Gems":
						engine.executeScript("setFirstItemImage('"+AutoFletcherEliteData.FletchingTools.Chisel.getImageLocation()+"','"+AutoFletcherEliteData.FletchingTools.Chisel.InGameName+"');");
						engine.executeScript(JavaArrayToJavaScript(Bolts.UncutGemCutting.values()));
						engine.executeScript("setThirdBoxString(x);");
						engine.executeScript("showThirdBox();");
//						engine.executeScript("$('#e3').on('change', function() {java.setThirdBox();});");
					break;
				}
			break;
    	}
    }
    public Object pullFinalObjectFromGUI(WebEngine engine){
    	switch((String) engine.executeScript("getFirstBoxString();")){
			case "Bows":
				switch((String) engine.executeScript("getSecondBoxString()")){
					case "Cutting":
						AutoFletcherEliteData.Bows.Cutting objectToReturn= valueOfIgnoreCase(Bows.Cutting.class,(String) engine.executeScript("getThirdBoxString()"));
						engine.executeScript("setSecondItemImage('"+objectToReturn.log.getImageLocation()+"','"+objectToReturn.log.InGameLogName+"');");
						engine.executeScript("setThirdItemImage('"+objectToReturn.getImageLocation()+"','"+objectToReturn.endProductInGameName+"');");
						return objectToReturn;
					case "Stringing":
						AutoFletcherEliteData.Bows.Stringing objectToReturn2= valueOfIgnoreCase(Bows.Stringing.class,(String) engine.executeScript("getThirdBoxString()"));
						engine.executeScript("setSecondItemImage('"+objectToReturn2.CutBow.getImageLocation()+"','"+objectToReturn2.CutBow.endProductInGameName+"');");
						engine.executeScript("setThirdItemImage('"+objectToReturn2.getImageLocation()+"','"+objectToReturn2.endProductInGameName+"');");
						return objectToReturn2;
				}
			break;
			case "Arrows":
				switch((String) engine.executeScript("getSecondBoxString()")){
					case "Arrow Tips":
						Arrows.AttachArrowTips objectToReturn=valueOfIgnoreCase(Arrows.AttachArrowTips.class,(String) engine.executeScript("getThirdBoxString()"));
						engine.executeScript("setSecondItemImage('"+objectToReturn.getArrowTipImageLocation()+"','"+objectToReturn.InGameArrowTipName+"');");
						engine.executeScript("setThirdItemImage('"+objectToReturn.getCompletedArrowImageLocation()+"','"+objectToReturn.FinalProductInGameName+"');");
						return objectToReturn;
					case "Headless Arrows":
						return Arrows.HeadlessArrows.Shafts;
					case "Shafts":
						return Bows.Cutting.Shafts;
				}
			break;
			case "Darts":
				switch((String) engine.executeScript("getSecondBoxString()")){
					case "Attach Feathers":
						Darts.AttachFeathersToDartTips objectToReturn= valueOfIgnoreCase(Darts.AttachFeathersToDartTips.class,(String) engine.executeScript("getThirdBoxString()"));
						engine.executeScript("setSecondItemImage('"+objectToReturn.getDartTipImageLocation()+"','"+objectToReturn.InGameDartTipName+"');");
						engine.executeScript("setThirdItemImage('"+objectToReturn.getCompletedDartImageLocation()+"','"+objectToReturn.endProductInGameName+"');");
						return objectToReturn;
				}
			break;
			case "Bolts":
				switch((String) engine.executeScript("getSecondBoxString()")){
					case "Attach Bolt Tips":
						Bolts.AttachBoltTips objectToReturn= valueOfIgnoreCase(Bolts.AttachBoltTips.class,(String) engine.executeScript("getThirdBoxString()"));
						engine.executeScript("setFirstItemImage('"+objectToReturn.Bolt.getGemBoltFinishedImageLocation()+"','"+objectToReturn.Bolt.FinalProductInGameName+"');");
						engine.executeScript("setSecondItemImage('"+objectToReturn.getTipeImageFileLocation()+"','"+objectToReturn.InGameTipName+"');");
						engine.executeScript("setThirdItemImage('"+objectToReturn.getFinalBoltImageFileLocation()+"','"+objectToReturn.FinalProductInGameName+"');");
						return objectToReturn;
					case "Attach Feathers":
						Bolts.AttachFeathersToBolts objectToReturn2= valueOfIgnoreCase(Bolts.AttachFeathersToBolts.class,(String) engine.executeScript("getThirdBoxString()"));
						engine.executeScript("setSecondItemImage('"+objectToReturn2.getGemBoltUnFinishedImageLocation()+"','"+objectToReturn2.InGameBoltName+"');");
						engine.executeScript("setThirdItemImage('"+objectToReturn2.getGemBoltFinishedImageLocation()+"','"+objectToReturn2.FinalProductInGameName+"');");
						return objectToReturn2;
					case  "Cut: Gems -> Tips":
						Bolts.CutGemstoTips objectToReturn3= valueOfIgnoreCase(Bolts.CutGemstoTips.class,(String) engine.executeScript("getThirdBoxString()"));
						engine.executeScript("setSecondItemImage('"+objectToReturn3.UnCutGem.getCutImageLocation()+"','"+objectToReturn3.UnCutGem.FinalProductCutGemInGameName+"');");
						engine.executeScript("setThirdItemImage('"+objectToReturn3.getGemBoltTipsImageLocation()+"','"+objectToReturn3.FinalInGameProduct+"');");
						return objectToReturn3;
					case "Cut: Uncut Gems -> Gems":
						Bolts.UncutGemCutting objectToReturn4 =  valueOfIgnoreCase(Bolts.UncutGemCutting.class,(String) engine.executeScript("getThirdBoxString()"));
						engine.executeScript("setSecondItemImage('"+objectToReturn4.getUnCutImageLocation()+"','"+objectToReturn4.InGameGemName+"');");
						engine.executeScript("setThirdItemImage('"+objectToReturn4.getCutImageLocation()+"','"+objectToReturn4.FinalProductCutGemInGameName+"');");
						return objectToReturn4;
				}
			break;
    	}
    	return null;
    }
    
    public void setSecond(String FirstSelection, WebEngine engine){
//    	System.out.println("First: "+(String) engine.executeScript("getFirstBoxString();"));
    	engine.executeScript("hideThirdBox()");
    	switch(FirstSelection){
			case "Bows":
				engine.executeScript("var x = ['Cutting','Stringing' ];");
				engine.executeScript("setSecondBoxString(x);");
				engine.executeScript("showSecondBox();");
				engine.executeScript("$('#e2').on('change', function() {java.setThirdBox();});");
			break;
			case "Arrows":
				engine.executeScript("var x = ['Arrow Tips', 'Headless Arrows', 'Shafts'];");
				engine.executeScript("setSecondBoxString(x);");
				engine.executeScript("showSecondBox();");
				engine.executeScript("$('#e2').on('change', function() {java.setThirdBox();});");
			break;
			case "Darts":
				engine.executeScript("var x = ['Attach Feathers'];");
				engine.executeScript("setSecondBoxString(x);");
				engine.executeScript("showSecondBox();");
				engine.executeScript("$('#e2').on('change', function() {java.setThirdBox();});");
			break;
			case "Bolts":
				engine.executeScript("var x = ['Attach Bolt Tips' , 'Attach Feathers' , 'Cut: Gems -> Tips', 'Cut: Uncut Gems -> Gems'];");
				engine.executeScript("setSecondBoxString(x);");
				engine.executeScript("showSecondBox();");
				engine.executeScript("$('#e2').on('change', function() {java.setThirdBox();});");
			break;
    	}
    }
    public static <T extends Enum<T>> T valueOfIgnoreCase(Class<T> enumeration, String name) {
        for(T enumValue : enumeration.getEnumConstants()) {
            if (enumValue.name().equalsIgnoreCase(name)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("There is no value with name '" + name + " in Enum " + enumeration.getClass().getName());        
    }
}