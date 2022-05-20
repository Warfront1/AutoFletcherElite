package scripts.userinterface;

import com.sun.javafx.application.PlatformImpl;


import java.awt.*;
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

    private static WebView browser;
    private static BufferedImage image;
    //    public static boolean minimizePaint = false;

    public static void main(String args[]){
        // Run this later:
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Paint().createFrame();
            }
        });
    }

    private void createFrame(){
        JFrame frame = new JFrame();
        frame.setTitle("Auto Fletcher Elite");
        frame.setBackground(new java.awt.Color(39, 43, 48));
        frame.setMinimumSize(new Dimension(550, 200));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.setState(JFrame.ICONIFIED); // Minimized Jframe
        frame.setType(javax.swing.JFrame.Type.UTILITY); //Hide window frame

        JFXPanel jfxPanel = new JFXPanel();
        createScene(jfxPanel);
        frame.add(jfxPanel);

        frame.setVisible(true);
        frame.setState(JFrame.ICONIFIED);
    }


    /** 
     * createScene 
     * 
     * Note: Key is that Scene needs to be created and run on "FX user thread" 
     *       NOT on the AWT-EventQueue Thread 
     * 
     */  
    private void createScene(JFXPanel jfxPanel) {
        PlatformImpl.startup(new Runnable() {  
            @Override
            public void run() {
                Stage stage = new Stage();
                StackPane root =  new StackPane();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                Platform.setImplicitExit(false);
                 
                // Set up the embedded browser:
                browser = new WebView();
                final WebEngine webEngine = browser.getEngine();
                webEngine.setJavaScriptEnabled(true);
                webEngine.load("http://warfront1.github.io/AutoFletcherElite/UserInterfaces/AutoFletcherElitePaint.html");
                webEngine.getLoadWorker().stateProperty().addListener(
                        new ChangeListener<Worker.State>() {
                            Bridge javaBridge = new Bridge(webEngine);
                            @Override
                            public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                                if (newState == Worker.State.SUCCEEDED) {
                                    JSObject jso = (JSObject) webEngine.executeScript("window");
                                    jso.setMember("java", javaBridge);
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
        boolean updateMethodName= true;
        Bridge(WebEngine engine){
            this.engine=engine;
//            engine.executeScript("$(document).ready(function() {"+
//            		"updateMethodName='"+update()+"'; " + 
//            		"});");
            engine.executeScript("updateMethodName='"+update()+"'; ");
        }

        public String update(){
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        	if(updateMethodName){
            	System.out.println("Paint JavaFX Update Method hooked on : "+"java."+methodName+"()");
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
        	engine.executeScript("setItemName('"+Statistics.getEndProductName()+"');");
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

    public static BufferedImage getScreenshot(){
        javafx.application.Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(browser!=null){
                    WritableImage returnimage = browser.snapshot(new SnapshotParameters(), null);
                    image = SwingFXUtils.fromFXImage(returnimage, null);
                }
            }
        });
        return image;

    }
    
//	public static void PaintToggleHandeling(final Point mouseposition){
//		javafx.application.Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//        		if(mouseposition!=null){
//        			if(new Rectangle(494, 340, 20, 20).contains(mouseposition)){
//        				Paint.browser.getEngine().executeScript( "setPaint(); ");
//        				minimizePaint=!minimizePaint;
//
//        			}
//        		}
//            }
//        });
//	}


}