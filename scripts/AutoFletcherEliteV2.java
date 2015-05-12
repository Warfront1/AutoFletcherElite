package scripts;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import scripts.data.collection.DynamicSignature;
import scripts.userinterface.JavaFXUI;
import scripts.userinterface.SwingUI;
import scripts.userinterface.Paint;

public class AutoFletcherEliteV2{

//	Runs Auto Fletcher Elite utilizing java 8 javaFX libraries
	public static void runFrontEndViaJavaFX(){
		JavaFXUI.main(null);
		Paint.main(null);
		DynamicSignature.StartDynamicSignatureThread();
		while(true){
			JavaFXUI.Recipe.run();
		}
	}
//	Runs Auto Fletcher Elite utilizing old school swing library
	public static void runFrontEndViaSwing(){
		SwingUI.main(null);
//		Paint.main(null);
		while(SwingUI.Recipe==null){
			ClientAPIWrappers.sleep(500);
		}
		while(true){
			SwingUI.Recipe.run();
		}
	}
		
	
	
// GENERAL PAINT METHOD FOR GENERIC BOT PAINT
// Just Pass threw the graphics variable to this function
	public static void onPaint(final Graphics g) {
		if(Paint.ToolKitReady){
//			Composite original = ((Graphics2D) g).getComposite();
//			Set to semi translucent
			Composite translucent = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.98f);
			((Graphics2D)g).setComposite(translucent);
			
			Paint.updateAndSavePaint();
			BufferedImage Paintimage = Paint.imageforPaint;
			if(Paintimage!=null){
				if(Paint.minimizePaint){
					g.drawImage(Paintimage.getSubimage(Paintimage.getMinX(), Paintimage.getMinY(),Paintimage.getWidth(), 40),0, 339, null);
				}
				else{
					g.drawImage(Paintimage.getSubimage(Paintimage.getMinX(), Paintimage.getMinY(),Paintimage.getWidth(), 140),0, 339, null);
				}
			}
			
		}
	}
	
	
}
