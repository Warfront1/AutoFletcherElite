package scripts.userinterface;

import com.sun.javafx.application.PlatformImpl;
import java.awt.Dimension;
import com.allatori.annotations.DoNotRename;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import scripts.ClientAPIWrappers;
import scripts.data.FletchingRecipe;

import netscape.javascript.JSObject;

  
/** 
 * SwingFXWebView 
 */  
public class JavaFXUI extends JPanel {  
     
	public static FletchingRecipe Recipe = null;
    private Stage stage;  
    private WebView browser;  
    private JFXPanel jfxPanel;  
    final static JFrame frame = new JFrame(); 
  
    
    public JavaFXUI(){  
        initComponents();  
        setBackground(new java.awt.Color(39, 43, 48));
    }  
  
    public static void main(String args[]){  
    	// Null Check Recipe to see if it was already assigned a value (ie. Bot Clients with Re-Run features that retain static assignments)
    	if(Recipe==null){
	        // Run this later:
	        SwingUtilities.invokeLater(new Runnable() {  
	            @Override
	            public void run() {  
	                frame.setTitle("Auto Fletcher Elite");
	                frame.setMinimumSize(new Dimension(500, 700));  
	                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
	                frame.setVisible(true);
	                frame.getContentPane().add(new JavaFXUI());
	            }  
	        });
	        // Sleep Main Script Thread while user Select Item to Fletch
	        while(Recipe == null){
	        	ClientAPIWrappers.sleep(500);
	        }
    	}
    	else{
    		System.out.println("Re-Run Detected, Restart the Script if you would like to select another Item to Fletch");
    	}
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
                StackPane root =  new StackPane();
                Scene scene = new Scene(root, 470,680);  
                stage.setScene(scene);  
                Platform.setImplicitExit(false);
                 
                // Set up the embedded browser:
                browser = new WebView();
                final WebEngine webEngine = browser.getEngine();
                webEngine.setJavaScriptEnabled(true);
//                webEngine.load("http://elitescripts.tk/AutoFletcherElite/AutoFletcherEliteGUI.html");
                webEngine.load("http://warfront1.github.io/AutoFletcherElite/UserInterfaces/AutoFletcherEliteGUI.html");
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
                System.out.println("Main FX UI Successfully Created!");
            }  
        });  
    }
    public class Bridge {
        WebEngine engine;
        boolean runOnce = true;
        Bridge(WebEngine engine){
            this.engine=engine;
	          engine.executeScript("exit='"+exit()+"'; ");
        }
        @DoNotRename
        public String exit() {
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        	if(runOnce){
//        		Exception e = new Exception();
//        		e.fillInStackTrace();
        		runOnce=false;
        		System.out.println("FX UI Exit Hooked on: "+"java."+methodName+"()");
        		return "java."+methodName+"()";
        	}
//        	String EndProduct = ""+ engine.executeScript("exit1();");
        	String EndProduct = ""+ engine.executeScript("$('#td10').text();");
        	FletchingRecipe.loadRecipes();
        	Recipe = FletchingRecipe.Recipes.get(EndProduct);
        	System.out.println("Fletching Recipe Successfully Loaded from JavaFX UI: "+Recipe.getEndProduct().getInGameName());
        	frame.dispose();
        	Thread.currentThread().stop();
        	return " ";
        }
   }


}