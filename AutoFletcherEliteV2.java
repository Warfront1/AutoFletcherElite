package scripts;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


import scripts.actions.FletchingActions;
import scripts.actions.Utilities;
import scripts.data.ItemData;
import scripts.data.ItemInterface;
import scripts.data.ItemData.Arrows.AttachArrowTips;
import scripts.data.ItemData.Arrows.HeadlessArrows;
import scripts.data.ItemData.Bolts.AttachBoltTips;
import scripts.data.ItemData.Bolts.AttachFeathersToBolts;
import scripts.data.ItemData.Bolts.CutGemstoTips;
import scripts.data.ItemData.Bolts.UncutGemCutting;
import scripts.data.ItemData.Bows.Cutting;
import scripts.data.ItemData.Bows.Stringing;
import scripts.data.ItemData.Darts.AttachFeathersToDartTips;
import scripts.data.collection.Statistics;
import scripts.data.collection.DynamicSignature;
import scripts.userinterface.JavaFXUI;
import scripts.userinterface.SwingUI;
import scripts.userinterface.Paint;

public class AutoFletcherEliteV2{

	
//	=====================================================================
//	====Auto Fletcher Elite Main Call Methods============================
//	=====================================================================	

	
	public static void fletchBows(ItemData.Bows.Cutting BowToCut){
		if(Utilities.isReadyToFletch(ItemData.FletchingTools.Knife.InGameName,1,BowToCut.log.getInGameLogName(),27)){
			FletchingActions.GenericBowCutting(BowToCut.log.getInGameLogName(), BowToCut.InterfaceMasterIndex, BowToCut.InterfaceMasterChild);
		}
	}
	public static void fletchBows(ItemData.Bows.Stringing BowToString){
		if(Utilities.isReadyToFletch(ItemData.FletchingTools.Bowstring.InGameName,14,BowToString.CutBow.endProductInGameName,14)){
			FletchingActions.StringBows(BowToString.CutBow.endProductInGameName);
		}
	}
	
	public static void fletchArrows(ItemData.Arrows.AttachArrowTips ArrowTips){
		if(Utilities.isReadyToFletch("Headless arrow",1,ArrowTips.InGameArrowTipName,1)){
			FletchingActions.useArrowHeadorBoltTip("Headless arrow", ArrowTips.InGameArrowTipName);
		}
	}
	
	public static void fletchHeadlessArrows(){
		if(Utilities.isReadyToFletch("Arrow shaft",1,"Feather",1)){
			FletchingActions.useArrowHeadorBoltTip("Arrow shaft", "Feather");
		}
	}
	
	public static void fletchDarts(ItemData.Darts.AttachFeathersToDartTips Dart){
		if(Utilities.isReadyToFletch(Dart.InGameDartTipName,1,"Feather",1)){
			FletchingActions.useFeathers(Dart.InGameDartTipName, "Feather");
		}
	}
	
	public static void fletchBolts(ItemData.Bolts.AttachFeathersToBolts Bolts){
		if(Utilities.isReadyToFletch(Bolts.InGameBoltName,1,"Feather",1)){
			FletchingActions.useFeathers(Bolts.InGameBoltName, "Feather");
		}
	}
	
	public static void fletchBolts(ItemData.Bolts.AttachBoltTips Bolts){
		if(Utilities.isReadyToFletch(Bolts.Bolt.FinalProductInGameName,1,Bolts.InGameTipName,1)){
			FletchingActions.useArrowHeadorBoltTip(Bolts.Bolt.FinalProductInGameName, Bolts.InGameTipName);
		}
	}
	
	public static void cutGems(ItemData.Bolts.UncutGemCutting UnCutGem){
		if(Utilities.isReadyToFletch(UnCutGem.InGameGemName,27,"Chisel",1)){
			FletchingActions.cutGems("Chisel", UnCutGem.InGameGemName);
		}
	}
	
	public static void cutGems(ItemData.Bolts.CutGemstoTips CutGem){
		if(Utilities.isReadyToFletch(CutGem.UnCutGem.FinalProductCutGemInGameName,27,"Chisel",1)){
			FletchingActions.cutGems("Chisel", CutGem.UnCutGem.FinalProductCutGemInGameName);
		}
	}
	
	
	
	
	
//	Runs Auto Fletcher Elite utilizing java 8 javaFX libraries
	public static void runFrontEndViaJavaFX(){
		if(JavaFXUI.Object==null){
			JavaFXUI.main(null);
			Paint.main(null);
			DynamicSignature.StartDynamicSignatureThread();
			while(JavaFXUI.Object==null){
				ClientAPIWrappers.sleep(500);
			}
		}
		while(true){
			AntiBanCompliance.enableAntiBan();
			AutoFletcherEliteGenericRun(JavaFXUI.Object);
		}
	}
//	Runs Auto Fletcher Elite utilizing old school swing library
	public void runFrontEndViaSwing(){
		if(!SwingUI.Start){
			SwingUI.main(null);
			while(!SwingUI.Start){
				ClientAPIWrappers.sleep(500);
			}
		}
		while(true){
			AutoFletcherEliteGenericRun(SwingUI.Object);
		}
	}
		
	
	
//	Extracts AutoFletcherElite data from webUI
	public static void AutoFletcherEliteGenericRun(Object obj){
		if(obj instanceof ItemInterface){
			Statistics.ItemName=((ItemInterface) obj).getEndProduct();
			Statistics.xpPerItem=((ItemInterface) obj).getXPperItem();
		}
		if(obj.getClass().equals(Cutting.class)){
			AutoFletcherEliteV2.fletchBows((Cutting) obj);
		}
		else if(obj.getClass().equals(Stringing.class)){
			AutoFletcherEliteV2.fletchBows((Stringing) obj);
		}
		else if(obj.getClass().equals(AttachArrowTips.class)){
			AutoFletcherEliteV2.fletchArrows((AttachArrowTips) obj);
		}
		else if(obj.getClass().equals(HeadlessArrows.class)){
			AutoFletcherEliteV2.fletchHeadlessArrows();
		}
		else if(obj.getClass().equals(AttachFeathersToDartTips.class)){
			AutoFletcherEliteV2.fletchDarts((AttachFeathersToDartTips) obj);
		}
		else if(obj.getClass().equals(AttachBoltTips.class)){
			AutoFletcherEliteV2.fletchBolts((AttachBoltTips) obj);
		}
		else if(obj.getClass().equals(AttachFeathersToBolts.class)){
			AutoFletcherEliteV2.fletchBolts((AttachFeathersToBolts) obj);
		}
		else if(obj.getClass().equals(UncutGemCutting.class)){
			AutoFletcherEliteV2.cutGems((UncutGemCutting) obj);
		}
		else if(obj.getClass().equals(CutGemstoTips.class)){
			AutoFletcherEliteV2.cutGems((CutGemstoTips) obj);
		}
		else{
			System.out.println("Incorrect GUI Input");
		}
	}
// GENERAL PAINT METHOD FOR GENERIC BOT PAINT
// Just Pass threw the graphics variable to this function
	public static void onPaint(final Graphics g) {
		if(JavaFXUI.Object!=null && Paint.ToolKitReady){
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
