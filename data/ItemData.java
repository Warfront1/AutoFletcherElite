package scripts.data;


public class ItemData {

	public enum LogType{
		Logs("Logs","Logs.png"),
		OakLogs("Oak logs","Oak_logs.png"),
		WillowLogs("Willow logs","Willow_logs.png"),
		MapleLogs("Maple logs","Maple_logs.png"),
		YewLogs("Yew logs","Yew_logs.png"),
		MagicLogs("Magic logs","Magic_logs.png"),
		;
		public String InGameLogName;
		public String ImageFileName;
		LogType(String InGameLogName,String ImageFileName){
			this.setInGameLogName(InGameLogName);
			this.ImageFileName=ImageFileName;
		}
		public String getInGameLogName() {
			return InGameLogName;
		}
		public void setInGameLogName(String inGameLogName) {
			InGameLogName = inGameLogName;
		}
		public String getImageLocation(){
			return "Bows/Logs/"+this.ImageFileName;
		}
	}
	
	public enum FletchingTools{
		Knife("Knife","Knife.png"),
		Bowstring("Bow string","Bowstring.png"),
		Chisel("Chisel","Chisel.png"),
		Feather("Feather","Feather.png"),
		HeadlessArrow("Headless arrow","Headless_arrow.png");
		public String InGameName;
		public String ImageFileName;
		FletchingTools(String InGameName, String ImageFileName){
			this.InGameName=InGameName;
			this.ImageFileName=ImageFileName;
		}
		public String getImageLocation(){
			return "FletchingTools/"+this.ImageFileName;
		}
	}
	
	public static class Bows{

//			Logs;
			
			public enum Cutting implements ItemInterface{
				Shafts("Shafts",305,9,LogType.Logs,0.33,"shafts.png","Shaft"),
				Shortbow("Shortbow",305,13,LogType.Logs,5,"Shortbow_(u).png","Shortbow (u)"),
				Longbow("Longbow",305,17,LogType.Logs,10,"Longbow_(u).gif","Longbow (u)"),
				CrossBowStock("Crossbow stock",305,21,LogType.Logs,6,"NoImage.jpg","Wooden stock"),
				
				OakShortBow("Oak shortbow",304,8,LogType.OakLogs,16.5,"Oak_shortbow_(u).png","Oak shortbow (u)"),
				OakLongBow("Oak longbow",304,12,LogType.OakLogs,25,"oak_longbow_(u).gif","Oak longbow (u)"),
				OakCrossBowStock("Oak CrossBowStock",304,16,LogType.OakLogs,16,"NoImage.jpg","Oak stock"),
				
				WillowShortBow("Willow shortbow",304,8,LogType.WillowLogs,33.25,"Willow_shortbow_(u).png","Willow shortbow (u)"),
				WillowLongBow("Willow longbow",304,12,LogType.WillowLogs,41.5,"willow_longbow_(u).gif","Willow longbow (u)"),
				WillowCrossBowStock("Willow CrossBowStock",304,16,LogType.WillowLogs,22,"NoImage.jpg","Willow stock"),
				
				MapleShortBow("Maple shortbow",304,8,LogType.MapleLogs,50,"Maple_shortbow_(u).png","Maple shortbow (u)"),
				MapleLongBow("Maple longbow",304,12,LogType.MapleLogs,58.3,"maple_longbow_(u).gif","Maple longbow (u)"),
				MapleCrossBowStock("Maple CrossBowStock",304,16,LogType.MapleLogs,32,"NoImage.jpg","Maple stock"),
				
				YewShortBow("Yew shortbow",304,8,LogType.YewLogs,67.5,"Yew_shortbow_(u).png","Yew shortbow (u)"),
				YewLongBow("Yew longbow",304,12,LogType.YewLogs,75,"unstrung_yewlong.gif","Yew longbow (u)"),
				YewCrossBowStock("Yew CrossBowStock",304,16,LogType.YewLogs,50,"NoImage.jpg","Yew stock"),
				
				MagicShortBow("Magic shortbow",303,7,LogType.MagicLogs,83.3,"Magic_shortbow_(u).png","Magic shortbow (u)"),
				MagicLongBow("Magic longbow",303,11,LogType.MagicLogs,91.5,"maple_longbow_(u).gif","Magic longbow (u)");
				
				String guiOption;
				public int InterfaceMasterIndex;
				public int InterfaceMasterChild;
				public LogType log;
				public double xpPerBow;
				public  String ImageFileName;
				public String endProductInGameName;
				Cutting(String guiOption,int InterfaceMasterIndex, int InterfaceMasterChild, LogType log, double xpPerBow, String ImageFileName, String endProductInGameName){
					this.guiOption=guiOption;
					this.InterfaceMasterIndex=InterfaceMasterIndex;
					this.InterfaceMasterChild=InterfaceMasterChild;
					this.log=log;
					this.xpPerBow=xpPerBow;
					this.ImageFileName=ImageFileName;
					this.endProductInGameName=endProductInGameName;
				}
				public String getImageLocation(){
					return "Bows/Cutting/"+this.ImageFileName;
				}
				@Override
				public double getXPperItem() {
					return this.xpPerBow;
				}
				@Override
				public String getEndProduct() {
					return this.endProductInGameName;
				}
			}
			public enum Stringing implements ItemInterface{
				Shortbow("Shortbow",Cutting.Shortbow,5,"Shortbow.png"),
				Longbow("Longbow",Cutting.Longbow,10,"longbow.gif"),
				
				OakShortBow("Oak shortbow",Cutting.OakShortBow,16.5,"Oak_shortbow.png"),
				OakLongBow("Oak longbow",Cutting.OakLongBow,25,"Oak_longbow.gif"),
				
				WillowShortBow("Willow shortbow",Cutting.WillowShortBow,33.25,"Willow_shortbow.png"),
				WillowLongBow("Willow longbow",Cutting.WillowLongBow,41.5,"Willow_longbow.gif"),
				
				MapleShortBow("Maple shortbow",Cutting.MapleShortBow,50,"Maple_shortbow.png"),
				MapleLongBow("Maple longbow",Cutting.MapleLongBow,58.3,"Maple_longbow.gif"),
				
				YewShortBow("Yew shortbow",Cutting.YewShortBow,67.5,"Yew_shortbow.png"),
				YewLongBow("Yew longbow",Cutting.YewLongBow,75,"Yew_longbow.gif"),
				
				MagicShortBow("Magic shortbow",Cutting.MagicShortBow,83.3,"Magic_shortbow.png"),
				MagicLongBow("Magic longbow",Cutting.MagicLongBow,91.5,"Magic_longbow.gif");
				
				public String endProductInGameName;
				public Cutting CutBow;
				public double xpPerBow;
				public String ImageFileName;
				Stringing(String endProductInGameName,Cutting CutBow,double xpPerBow,String ImageFileName){
					this.endProductInGameName=endProductInGameName;
					this.CutBow=CutBow;
					this.xpPerBow=xpPerBow;
					this.ImageFileName=ImageFileName;
				}
				public String getImageLocation(){
					return "Bows/Stringing/"+this.ImageFileName;
				}
				@Override
				public double getXPperItem() {
					return this.xpPerBow;
				}
				@Override
				public String getEndProduct() {
					return this.endProductInGameName;
				}
			}
			
			

	};
	public static class Arrows{
//		Logs("Logs", FletchingOptions.Shafts, FletchingOptions.Shortbow, FletchingOptions.Longbow, FletchingOptions.CrossBowStock);
		 
		public enum AttachArrowTips implements ItemInterface{
			Bronze("Bronze","Bronze arrowtips",2.6,"Bronze_arrow.png","Bronze.png","Bronze arrow"),
			Iron("Iron","Iron arrowtips",3.8,"Iron_arrow.png","Iron.png","Iron arrow"),
			Steel("Steel","Steel arrowtips",6.3,"Steel_arrow.png","Steel.png","Steel arrow"),
			Mithril("Mithril","Mithril arrowtips",8.8,"Mithril_arrow.png","Mithril.png","Mithril arrow"),
			Adamant("Adamant","Adamant arrowtips",10,"Adamant_arrow.png","Adamant.png","Adamant arrow"),
			Rune("Rune","Rune arrowtips",13.8,"Rune_arrow.png","Adamant.png","Rune arrow"),
			Dragon("Dragon","Dragon arrowtips",15,"Dragon_arrow.png","Dragon.png","Dragon arrow");

			public String guiOption;
			public String InGameArrowTipName;
			public double xpPerArrow;
			public String ImageFileNameFinalArrowPicture;
			public String ImageFileNameArrowTipPicture;
			public String FinalProductInGameName;
			AttachArrowTips(String guiOption,String InGameArrowTipName,double xpPerArrow,String ImageFileNameFinalArrowPicture, String ImageFileNameArrowTipPicture, String FinalProductInGameName){
				this.guiOption=guiOption;
				this.InGameArrowTipName=InGameArrowTipName;
				this.xpPerArrow=xpPerArrow;
				this.ImageFileNameFinalArrowPicture=ImageFileNameFinalArrowPicture;
				this.ImageFileNameArrowTipPicture=ImageFileNameArrowTipPicture;
				this.FinalProductInGameName=FinalProductInGameName;
			}
			public String getArrowTipImageLocation(){
				return "Arrows/ArrowTips/"+this.ImageFileNameArrowTipPicture;
			}
			public String getCompletedArrowImageLocation(){
				return "Arrows/FullyCompletedArrows/"+this.ImageFileNameFinalArrowPicture;
			}
			@Override
			public double getXPperItem() {
				return this.xpPerArrow;
			}
			@Override
			public String getEndProduct() {
				return this.FinalProductInGameName;
			}
		}
		public enum HeadlessArrows implements ItemInterface{
			Shafts("Shafts",305,9,"Logs", 0.33);
			
			public String guiOption;
			public int InterfaceMasterIndex;
			public int InterfaceMasterChild;
			public String InGameLogName;
			public double xpPer;
			HeadlessArrows(String guiOption,int InterfaceMasterIndex, int InterfaceMasterChild, String InGameLogName, double xpPer){
				this.guiOption=guiOption;
				this.InterfaceMasterIndex=InterfaceMasterIndex;
				this.InterfaceMasterChild=InterfaceMasterChild;
				this.InGameLogName=InGameLogName;
				this.xpPer = xpPer;
			}
			@Override
			public double getXPperItem() {
				return this.xpPer;
			}
			@Override
			public String getEndProduct() {
				return "Headless Arrow";
			}
		}
	};
	
	public static class Darts{
//		Logs("Logs", FletchingOptions.Shafts, FletchingOptions.Shortbow, FletchingOptions.Longbow, FletchingOptions.CrossBowStock);
		
		public enum AttachFeathersToDartTips implements ItemInterface{
			Bronze("Bronze","Bronze dart tip",1.8,"Bronze.png","Bronze dart"),
			Iron("Iron","Iron dart tip",3.8,"Iron.png","Iron dart"),
			Steel("Steel","Steel dart tip",7.5,"Steel.png","Steel dart"),
			Mithril("Mithril","Mithril dart tip",11.2,"Mithril.png","Mithril dart"),
			Adamant("Adamant","Adamant dart tip",15,"Adamant.png","Adamant dart"),
			Rune("Rune","Rune dart tip",18.8,"Rune.png","Rune dart"),
			Dragon("Dragon","Dragon dart tip",25,"Dragon.png","Dragon dart");

			public String guiOption;
			public String InGameDartTipName;
			public double xpPerDart;
			public String ImageFileNamePicture;
			public String endProductInGameName;
			AttachFeathersToDartTips(String guiOption,String InGameDartTipName, double xpPerDart, String ImageFileNamePicture, String endProductInGameName){
				this.guiOption=guiOption;
				this.InGameDartTipName=InGameDartTipName;
				this.xpPerDart=xpPerDart;
				this.ImageFileNamePicture=ImageFileNamePicture;
				this.endProductInGameName=endProductInGameName;
			}
			public String getDartTipImageLocation(){
				return "Darts/DartTips/"+this.ImageFileNamePicture;
			}
			public String getCompletedDartImageLocation(){
				return "Darts/CompletedDart/"+this.ImageFileNamePicture;
			}
			@Override
			public double getXPperItem() {
				return this.xpPerDart;
			}
			@Override
			public String getEndProduct() {
				return this.endProductInGameName;
			}
		}
	};
	
	public static class Bolts{

//		Logs("Logs", FletchingOptions.Shafts, FletchingOptions.Shortbow, FletchingOptions.Longbow, FletchingOptions.CrossBowStock);
		
		public enum UncutGemCutting{
			Opal("Opal","Uncut opal","Opal.png","Opal"),
			Jade("Jade","Uncut jade","Jade.png","Jade"),
			RedTopaz("Red Topaz","Uncut red topaz","Red_topaz.png","Red topaz"),
			Sapphire("Sapphire","Uncut sapphire","Sapphire.png","Sapphire"),
			Emerald("Emerald","Uncut emerald","Emerald.png","Emerald"),
			Ruby("Ruby","Uncut ruby","Ruby.png","Ruby"),
			Diamond("Diamond","Uncut diamond","Diamond.png","Diamond"),
			Dragonstone("Dragonstone","Uncut dragonstone","Dragonstone.png","Dragonstone"),
			Onyx("Onyx","Uncut onyx","Onyx.png","Onyx");

			public String guiOption;
			public String InGameGemName;
			public String ImageFileNameUnCutGem;
			public String FinalProductCutGemInGameName;
			UncutGemCutting(String guiOption,String InGameGemName,String ImageFileNameUnCutGem,String FinalProductCutGemInGameName){
				this.guiOption=guiOption;
				this.InGameGemName=InGameGemName;
				this.ImageFileNameUnCutGem=ImageFileNameUnCutGem;
				this.FinalProductCutGemInGameName=FinalProductCutGemInGameName;
			}
			public String getUnCutImageLocation(){
				return "Bolts/UnCutGems/"+this.ImageFileNameUnCutGem;
			}
			public String getCutImageLocation(){
				return "Bolts/CutGems/"+this.ImageFileNameUnCutGem;
			}
		}
		
		public enum CutGemstoTips{
			Opal(UncutGemCutting.Opal,1,12,"Opal.png","Opal bolt tips"),
			Jade(UncutGemCutting.Jade,2,12,"Jade.png","Jade bolt tips"),
			RedTopaz(UncutGemCutting.RedTopaz,4,12,"Red_topaz.png","Topaz bolt tips"),
			Sapphire(UncutGemCutting.Sapphire,4,12,"Sapphire.png","Sapphire bolt tips"),
			Emerald(UncutGemCutting.Emerald,5.5,12,"Emerald.png","Emerald bolt tips"),
			Ruby(UncutGemCutting.Ruby,6,12,"Ruby.png","Ruby bolt tips"),
			Diamond(UncutGemCutting.Diamond,7,12,"Diamond.png","Diamond bolt tips"),
			Dragonstone(UncutGemCutting.Dragonstone,8,12,"Dragon.png","Dragon bolt tips"),
			Onyx(UncutGemCutting.Onyx,9,24,"Onyx.png","Onyx bolt tips");

			public UncutGemCutting UnCutGem;
			public double xpPerConsumedGem;
			public int rewardedTipPerGem;
			public String ImageFileNameGemTips;
			public String FinalInGameProduct;
			CutGemstoTips(UncutGemCutting UnCutGem, double xpPerConsumedGem,int rewardedTipPerGem,String ImageFileNameGemTips,String FinalInGameProduct){
				this.UnCutGem=UnCutGem;
				this.xpPerConsumedGem=xpPerConsumedGem;
				this.rewardedTipPerGem=rewardedTipPerGem;
				this.ImageFileNameGemTips=ImageFileNameGemTips;
				this.FinalInGameProduct=FinalInGameProduct;
			}
			public String getGemBoltTipsImageLocation(){
				return "Bolts/GemTips/"+this.ImageFileNameGemTips;
			}
		}
		public enum AttachFeathersToBolts implements ItemInterface{
			Bronze("Bronze","Bronze bolts (unf)",.5,"Bronze.png","Bronze bolts"),
			Blurite("Blurite","Blurite bolts (unf)",1,"Blurite.png","Blurite bolts"),
			Iron("Iron","Iron bolts (unf)",1.5,"Iron.png","Iron bolts"),
			Silver("Silver","Silver bolts (unf)",2.5,"Silver.png","Silver bolts"),
			Steel("Steel","Steel bolts (unf)",3.5,"Steel.png","Steel bolts"),
			Mithril("Mithril","Mithril bolts (unf)",5,"Mithril.png","Mithril bolts"),
			Broad("Broad","Unfinished broad bolts",3,"Broad.png","Broad bolts"),
			Adamant("Adamant","Adamant bolts(unf)",7,"Adamant.png","Adamant bolts"),
			Rune("Rune","Runite bolts (unf)",10,"Runite.png","Runite bolts");

			public String guiOption;
			public String InGameBoltName;
			public double xpPerBolt;
			public String ImageFileNameBolts;
			public String FinalProductInGameName;
			AttachFeathersToBolts(String guiOption,String InGameBoltName, double xpPerBolt, String ImageFileNameBolts, String FinalProductInGameName){
				this.guiOption=guiOption;
				this.InGameBoltName=InGameBoltName;
				this.xpPerBolt=xpPerBolt;
				this.ImageFileNameBolts=ImageFileNameBolts;
				this.FinalProductInGameName=FinalProductInGameName;
			}
			public String getGemBoltFinishedImageLocation(){
				return "Bolts/FinishedBolts/"+this.ImageFileNameBolts;
			}
			public String getGemBoltUnFinishedImageLocation(){
				return "Bolts/UnfinishedBolts/"+this.ImageFileNameBolts;
			}
			@Override
			public double getXPperItem() {
				return this.xpPerBolt;
			}
			@Override
			public String getEndProduct() {
				return this.FinalProductInGameName;
			}
		}
		public enum AttachBoltTips implements ItemInterface{
			BronzeOpalTips("Bronze",AttachFeathersToBolts.Bronze,CutGemstoTips.Opal.FinalInGameProduct,1.6,CutGemstoTips.Opal.ImageFileNameGemTips,"Opal.png","Opal bolts"),
			BronzeBarbTips("Bronze",AttachFeathersToBolts.Bronze,"Barb bolt tips",2,"Barb.png","Barbed.png","Barbed bolts"),
			
			BluriteJadeTips("Blurite",AttachFeathersToBolts.Blurite,CutGemstoTips.Jade.FinalInGameProduct,2.4,CutGemstoTips.Jade.ImageFileNameGemTips,"Jade.png","Jade bolts"),
			
			
			IronPearlTips("Iron",AttachFeathersToBolts.Iron,"Pearl bolt tips",3.2,"Pearl.png","Pearl.png","Pearl bolts"),
			
			MithrilSapphireTips("Mithril",AttachFeathersToBolts.Mithril,CutGemstoTips.Sapphire.FinalInGameProduct,4.7,CutGemstoTips.Sapphire.ImageFileNameGemTips,"Sapphire.png","Sapphire bolts"),
			MithrilEmeraldTips("Mithril",AttachFeathersToBolts.Mithril,CutGemstoTips.Emerald.FinalInGameProduct,5.5,CutGemstoTips.Emerald.ImageFileNameGemTips,"Emerald.png","Emerald bolts"),
			MithrilGrappleHook("Mithril",AttachFeathersToBolts.Mithril,"Mithril grapple tips",11,"Mith_grapple.png","Mith_grapple.png","Mith grapple"),
			
			AdamantRubyTips("Adamant",AttachFeathersToBolts.Adamant,CutGemstoTips.Ruby.FinalInGameProduct,6.3,CutGemstoTips.Ruby.ImageFileNameGemTips,"Ruby.png","Ruby bolts"),
			AdamantDiamondTips("Adamant",AttachFeathersToBolts.Adamant,CutGemstoTips.Diamond.FinalInGameProduct,7,CutGemstoTips.Diamond.ImageFileNameGemTips,"Diamond.png","Diamond bolts"),
			
			RuneDragonTips("Rune",AttachFeathersToBolts.Rune,CutGemstoTips.Dragonstone.FinalInGameProduct,8.2,CutGemstoTips.Dragonstone.ImageFileNameGemTips,"Dragon.png","Dragon bolts"),
			RuneOnyxTips("Rune",AttachFeathersToBolts.Rune,CutGemstoTips.Onyx.FinalInGameProduct,9.4,CutGemstoTips.Onyx.ImageFileNameGemTips,"Onyx.png","Onyx bolts")
			;

			public String guiOption;
			public AttachFeathersToBolts Bolt;
			public String InGameTipName;
			public double xpPerBoltTip;
			public String ImageFileNameTipImage;
			public String ImageFileEndProduct;
			public String FinalProductInGameName;
			AttachBoltTips(String guiOption,AttachFeathersToBolts Bolt, String InGameTipName, double xpPerBoltTip,String ImageFileNameTipImage, String ImageFileEndProduct, String FinalProductInGameName){
				this.guiOption=guiOption;
				this.Bolt=Bolt;
				this.InGameTipName=InGameTipName;
				this.xpPerBoltTip=xpPerBoltTip;
				this.ImageFileNameTipImage=ImageFileNameTipImage;
				this.ImageFileEndProduct=ImageFileEndProduct;
				this.FinalProductInGameName=FinalProductInGameName;
			}
			public String getTipeImageFileLocation(){
				return "Bolts/GemTips/"+this.ImageFileNameTipImage;
			}
			public String getFinalBoltImageFileLocation(){
				return "Bolts/FinishedBoltsWithTips/"+this.ImageFileEndProduct;
			}
			@Override
			public double getXPperItem() {
				return this.xpPerBoltTip;
			}
			@Override
			public String getEndProduct() {
				return this.FinalProductInGameName;
			}
		}
	}


	
}

