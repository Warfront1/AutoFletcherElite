package scripts.userinterface;

import com.sun.javafx.application.PlatformImpl;


import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import scripts.data.collection.Statistics;

import netscape.javascript.JSObject;

/** 
 * SwingFXWebView 
 */  
public class Paint extends JPanel {  
     
	static Object Object= null;
	public static boolean ToolKitReady = false;
    Stage stage;  
    static WebEngine webenginetoModify;
    public static BufferedImage imageforPaint;
    static WebView browser;  
    private JFXPanel jfxPanel;   
    public static boolean minimizePaint = false;
//    private static WebEngine webEngine;  
  
    public Paint(){  
        initComponents();  
        setBackground(new java.awt.Color(39, 43, 48));
    }  
  
    public static void main(String args[]){  
        // Run this later:
        SwingUtilities.invokeLater(new Runnable() {  
            @Override
            public void run() {  
                final JFrame frame = new JFrame();  
                 frame.setTitle("Auto Fletcher Elite");
                frame.getContentPane().add(new Paint());  
                 
                frame.setMinimumSize(new Dimension(535, 185));  
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
                frame.setVisible(false); 
//                frame.setMinimumSize(new Dimension(535, 185)); 
//                frame.setVisible(true);  
                frame.setMinimumSize(new Dimension(535, 400));
            }  
        });     
    }  
     
    private void initComponents(){  
         
        jfxPanel = new JFXPanel();  
        createScene();  
         
//        setLayout(new BorderLayout());  
        add(jfxPanel);  
        ToolKitReady = true;
         
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
//                Scene scene = new Scene(root, 519,165);  
                Scene scene = new Scene(root, 519,400);
                stage.setScene(scene);  
                Platform.setImplicitExit(false);
                 
                // Set up the embedded browser:
                browser = new WebView();
                final WebEngine webEngine = browser.getEngine();
                webEngine.setJavaScriptEnabled(true);
                webEngine.load("http://elitescripts.tk/autofletcherelite/AutoFletcherElitePaint.html");
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
                webenginetoModify=webEngine;
            }

        });  
    }
    public class Bridge {
        WebEngine engine;
        boolean updateMethodName= true;
        Bridge(WebEngine engine){
            this.engine=engine;
//            engine.executeScript("$(document).ready(function() {"+
//            		"updateMethodName='"+update()+"'; " + 
//            		"});");
            engine.executeScript("updateMethodName='"+update()+"'; ");
        }
        public String update(){
        	if(updateMethodName){
            	Exception e = new Exception();
            	e.fillInStackTrace();
            	String methodName = e.getStackTrace()[0].getMethodName();
            	System.out.println("Paint JavaFX Update Method hooked on: "+"java."+methodName+"()");
            	updateMethodName = false;
            	return "java."+methodName+"()";
        	}
        	setStatus();
        	setItemName();
        	setRunTime();
        	setCurrentLevel();
        	setNextLevel();
        	if(Statistics.getTotalXpGained()>0 && Statistics.getRunTimeSeconds()>0){
	        	setXPPerHour();
	        	setTimeTolevel();
	        	setProgressLevel();
	        	setAmountofItemMade();
	        	setAmountofItemMadePerHour();
        	}
        	return " ";
        }
        public void setStatus(){
        	engine.executeScript("setStatus('"+Statistics.Status+"');");
        }
        public void setItemName(){
        	engine.executeScript("setItemName('"+Statistics.ItemName+"');");
        }
        public void setRunTime(){
        	engine.executeScript("setRunTime('"+Statistics.getFormatedRunTime()+"');");
        }
        public void setXPPerHour(){
        	engine.executeScript("setXPPerHour('"+Statistics.getXpPerHour()+"');");
        }
        public void setCurrentLevel(){
        	engine.executeScript("setCurrentLevel('"+Statistics.getCurrentLevel()+"');");
        }
        public void setNextLevel(){
        	engine.executeScript("setNextLevel('"+Statistics.getNextLevel()+"');");
        }
        public void setTimeTolevel(){
        	engine.executeScript("setTimeTolevel('"+Statistics.getHoursTillNextLevel()+" H');");
        }
        public void setProgressLevel(){
        	engine.executeScript("setProgressBar('"+Statistics.getPercentCompleted(Statistics.getCurrentXP())+"');");
        }
        public void setAmountofItemMade(){
        	engine.executeScript("setAmountofItemMade('"+Statistics.getAmountofItemMade()+"');");
        }
        public void setAmountofItemMadePerHour(){
        	engine.executeScript("setAmountofItemMadePerHour('"+Statistics.getAmountofItemMadePerHour()+"');");
        }
    }

    public static BufferedImage getScreenshot(WebView browser){
    	if(browser!=null){
    		WritableImage returnimage = browser.snapshot(new SnapshotParameters(), null);
    		return SwingFXUtils.fromFXImage(returnimage, null);
    	}
    	return null;
    }
    public static void updateAndSavePaint(){
		javafx.application.Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	Paint.imageforPaint = Paint.getScreenshot(Paint.browser);
            }
        });
    }
    
	public static void PaintToggleHandeling(final Point mouseposition){
		javafx.application.Platform.runLater(new Runnable() {
            @Override
            public void run() {
        		if(mouseposition!=null && JavaFXUI.Object!=null){
        			if(new Rectangle(494, 340, 20, 20).contains(mouseposition)){
        				Paint.browser.getEngine().executeScript( "setPaint(); ");
        				minimizePaint=!minimizePaint;
        				
        			}
        		}
            }
        });
	}


}