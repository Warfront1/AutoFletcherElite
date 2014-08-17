package scripts;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


import scripts.AutoFletcherEliteData.Arrows.AttachArrowTips;
import scripts.AutoFletcherEliteData.Arrows.HeadlessArrows;
import scripts.AutoFletcherEliteData.Bolts.AttachBoltTips;
import scripts.AutoFletcherEliteData.Bolts.AttachFeathersToBolts;
import scripts.AutoFletcherEliteData.Bolts.CutGemstoTips;
import scripts.AutoFletcherEliteData.Bolts.UncutGemCutting;
import scripts.AutoFletcherEliteData.Bows.Cutting;
import scripts.AutoFletcherEliteData.Bows.Stringing;
import scripts.AutoFletcherEliteData.Darts.AttachFeathersToDartTips;

public class AutoFletcherEliteV2{

	
//	=====================================================================
//	====Auto Fletcher Elite Main Call Methods============================
//	=====================================================================	

	
	public static void fletchBows(AutoFletcherEliteData.Bows.Cutting BowToCut){
		if(AutoFletcherEliteUtilities.isReadyToFletch(AutoFletcherEliteData.FletchingTools.Knife.InGameName,1,BowToCut.log.getInGameLogName(),27)){
			AutoFletcherEliteActions.GenericBowCutting(BowToCut.log.getInGameLogName(), BowToCut.InterfaceMasterIndex, BowToCut.InterfaceMasterChild);
		}
	}
	public static void fletchBows(AutoFletcherEliteData.Bows.Stringing BowToString){
		if(AutoFletcherEliteUtilities.isReadyToFletch(AutoFletcherEliteData.FletchingTools.Bowstring.InGameName,14,BowToString.CutBow.endProductInGameName,14)){
			AutoFletcherEliteActions.StringBows(BowToString.CutBow.endProductInGameName);
		}
	}
	
	public static void fletchArrows(AutoFletcherEliteData.Arrows.AttachArrowTips ArrowTips){
		if(AutoFletcherEliteUtilities.isReadyToFletch("Headless arrow",1,ArrowTips.InGameArrowTipName,1)){
			AutoFletcherEliteActions.useArrowHeadorBoltTip("Headless arrow", ArrowTips.InGameArrowTipName);
		}
	}
	
	public static void fletchHeadlessArrows(){
		if(AutoFletcherEliteUtilities.isReadyToFletch("Arrow shaft",1,"Feather",1)){
			AutoFletcherEliteActions.useArrowHeadorBoltTip("Arrow shaft", "Feather");
		}
	}
	
	public static void fletchDarts(AutoFletcherEliteData.Darts.AttachFeathersToDartTips Dart){
		if(AutoFletcherEliteUtilities.isReadyToFletch(Dart.InGameDartTipName,1,"Feather",1)){
			AutoFletcherEliteActions.useFeathers(Dart.InGameDartTipName, "Feather");
		}
	}
	
	public static void fletchBolts(AutoFletcherEliteData.Bolts.AttachFeathersToBolts Bolts){
		if(AutoFletcherEliteUtilities.isReadyToFletch(Bolts.InGameBoltName,1,"Feather",1)){
			AutoFletcherEliteActions.useFeathers(Bolts.InGameBoltName, "Feather");
		}
	}
	
	public static void fletchBolts(AutoFletcherEliteData.Bolts.AttachBoltTips Bolts){
		if(AutoFletcherEliteUtilities.isReadyToFletch(Bolts.Bolt.FinalProductInGameName,1,Bolts.InGameTipName,1)){
			AutoFletcherEliteActions.useArrowHeadorBoltTip(Bolts.Bolt.FinalProductInGameName, Bolts.InGameTipName);
		}
	}
	
	public static void cutGems(AutoFletcherEliteData.Bolts.UncutGemCutting UnCutGem){
		if(AutoFletcherEliteUtilities.isReadyToFletch(UnCutGem.InGameGemName,27,"Chisel",1)){
			AutoFletcherEliteActions.cutGems("Chisel", UnCutGem.InGameGemName);
		}
	}
	
	public static void cutGems(AutoFletcherEliteData.Bolts.CutGemstoTips CutGem){
		if(AutoFletcherEliteUtilities.isReadyToFletch(CutGem.UnCutGem.FinalProductCutGemInGameName,27,"Chisel",1)){
			AutoFletcherEliteActions.cutGems("Chisel", CutGem.UnCutGem.FinalProductCutGemInGameName);
		}
	}
	
	
	
	
	
//	Runs Auto Fletcher Elite utilizing java 8 javaFX libraries
	public static void runFrontEndViaJavaFX(){
		if(AutoFletcherEliteGUIV3.Object==null){
			AutoFletcherEliteGUIV3.main(null);
			AutoFletcherElitePaintHelper.main(null);
			DynamicSignature.StartDynamicSignatureThread();
			while(AutoFletcherEliteGUIV3.Object==null){
				ClientAPIWrappers.sleep(500);
			}
		}
		while(true){
			AntiBanCompliance.enableAntiBan();
			AutoFletcherEliteGenericRun(AutoFletcherEliteGUIV3.Object);
		}
	}
//	Runs Auto Fletcher Elite utilizing old school swing library
	public void runFrontEndViaSwing(){
		if(!AutoFletcherEliteGUISwing.Start){
			AutoFletcherEliteGUISwing.main(null);
			while(!AutoFletcherEliteGUISwing.Start){
				ClientAPIWrappers.sleep(500);
			}
		}
		while(true){
			AutoFletcherEliteGenericRun(AutoFletcherEliteGUISwing.Object);
		}
	}
		
	
	
//	Extracts AutoFletcherElite data from webUI
	public static void AutoFletcherEliteGenericRun(Object obj){
		if(obj instanceof ItemInterface){
			AutoFletcherEliteStatistics.ItemName=((ItemInterface) obj).getEndProduct();
			AutoFletcherEliteStatistics.xpPerItem=((ItemInterface) obj).getXPperItem();
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
// Just pass the graphics variable to this function
	public static void onPaint(final Graphics g) {
		if(AutoFletcherEliteGUIV3.Object!=null && AutoFletcherElitePaintHelper.ToolKitReady){
//			Composite original = ((Graphics2D) g).getComposite();
//			Set to semi translucent
			Composite translucent = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.98f);
			((Graphics2D)g).setComposite(translucent);
			
			AutoFletcherElitePaintHelper.updateAndSavePaint();
			BufferedImage Paint = AutoFletcherElitePaintHelper.imageforPaint;
			if(Paint!=null){
				if(AutoFletcherElitePaintHelper.minimizePaint){
					g.drawImage(Paint.getSubimage(Paint.getMinX(), Paint.getMinY(),Paint.getWidth(), 40),0, 339, null);
				}
				else{
					g.drawImage(Paint.getSubimage(Paint.getMinX(), Paint.getMinY(),Paint.getWidth(), 140),0, 339, null);
				}
			}
			
		}
	}
	
	
}
