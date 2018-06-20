package ReplayTheSpireMod;

import java.nio.charset.StandardCharsets;

import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.blue.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
//import com.megacrit.cardcrawl.core.Settings.GameLanguage;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.vfx.*;

import basemod.*;
import basemod.helpers.*;
import basemod.interfaces.*;
import java.lang.reflect.*;
import java.io.*;

import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.*;
import java.util.*;
import java.util.function.*;
//SetUnlocksSubscriber, 

@SpireInitializer
public class ReplayTheSpireMod implements PostInitializeSubscriber,
EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, PostDrawSubscriber, PotionGetSubscriber
{
	public static void InitCardTitle(AbstractCard c) {
		FontHelper.cardTitleFont_L.getData().setScale(1.0f);
        final GlyphLayout gl = new GlyphLayout(FontHelper.cardTitleFont_L, c.name, new Color(), 0.0f, 1, false);
        if (c.cost > 0 || c.cost == -1) {
            if (gl.width > AbstractCard.IMG_WIDTH * 0.6f) {
                //c.useSmallTitleFont = true;
				ReflectionHacks.setPrivate(c, AbstractCard.class, "useSmallTitleFont", true);
            }
        }
        else if (gl.width > AbstractCard.IMG_WIDTH * 0.7f) {
            //c.useSmallTitleFont = true;
			ReflectionHacks.setPrivate(c, AbstractCard.class, "useSmallTitleFont", true);
        }
	}
	
	public static final Logger logger = LogManager.getLogger(ReplayTheSpireMod.class.getName());
	public static TextureAtlas powerAtlas;
	
	private static final String MODNAME = "ReplayTheSpireMod";
    private static final String AUTHOR = "The_Evil_Pickle, AstroPenguin642, Stewartisme, Slimer509";
    private static final String DESCRIPTION = "Content expansion mod";
	
	public static final String BADGE_IMG = "img/ModBadge.png";
	
	protected static int commonPotionChance = 9;
	protected static int uncommonPotionChance = 7;
	protected static int rarePotionChance = 4;
	protected static int ultraPotionChance = 1;
	protected static int shopPotionChance = 12;
	
	public static final ArrayList<ReplayUnlockAchieve> unlockAchievements = new ArrayList<ReplayUnlockAchieve>();
	
	public static final AbstractCard.CardColor IronCoreColor = AbstractCard.CardColor.RED;
	
	//public static EnumMap<ReplayTheSpireMod.PotionRarity, int> potionCosts = new EnumMap<ReplayTheSpireMod.PotionRarity, int>(ReplayTheSpireMod.PotionRarity.class);
	
	public static EnumMap<ReplayTheSpireMod.PotionRarity, ArrayList<String>> potionsByRarity = new EnumMap<ReplayTheSpireMod.PotionRarity, ArrayList<String>>(ReplayTheSpireMod.PotionRarity.class);
	
	public static boolean renderFishFG = false;
	public static boolean renderForestBG = false;
	public static TextureAtlas fishAtlas;
	public static TextureAtlas.AtlasRegion fishFG;
	public static Texture forestBG;
	
	
	public static boolean BypassStupidBasemodRelicRenaming_hasRelic(String targetID) {
		if (AbstractDungeon.player == null) {
			return false;
		}
		for (final AbstractRelic r : AbstractDungeon.player.relics) {
			String unren = r.relicId.substring(r.relicId.lastIndexOf(":") + 1);
            if (r.relicId.equals(targetID) || unren.equals(targetID)) {
                return true;
            }
        }
        return false;
	}
	public static boolean BypassStupidBasemodRelicRenaming_loseRelic(String targetID) {
		if (!ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(targetID)) {
            return false;
        }
        AbstractRelic toRemove = null;
        for (final AbstractRelic r : AbstractDungeon.player.relics) {
            String unren = r.relicId.substring(r.relicId.lastIndexOf(":") + 1);
            if (r.relicId.equals(targetID) || unren.equals(targetID)) {
                r.onUnequip();
                toRemove = r;
            }
        }
        if (toRemove == null) {
            logger.info("WHY WAS RELIC: " + AbstractDungeon.player.name + " NOT FOUND???");
            return false;
        }
        AbstractDungeon.player.relics.remove(toRemove);
        AbstractDungeon.player.reorganizeRelics();
        return true;
	}
	public static AbstractRelic BypassStupidBasemodRelicRenaming_getRelic(String targetID) {
		if (AbstractDungeon.player == null) {
			return null;
		}
		for (final AbstractRelic r : AbstractDungeon.player.relics) {
            String unren = r.relicId.substring(r.relicId.lastIndexOf(":") + 1);
            if (r.relicId.equals(targetID) || unren.equals(targetID)) {
                return r;
            }
        }
        return null;
	}
	public static boolean BypassStupidBasemodRelicRenaming_flashRelic(String targetID) {
		AbstractRelic r = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic(targetID);
		if (r == null) {
			return false;
		}
		r.flash();
        return true;
	}
	
	
	public static enum PotionRarity
	{
		COMMON,  UNCOMMON,  RARE,  ULTRA, SPECIAL,  SHOP;
		
		private PotionRarity() {}
	}
	
	public static PotionRarity GetPotionRarity(AbstractPotion potion) {
		for (ReplayTheSpireMod.PotionRarity rarity : ReplayTheSpireMod.PotionRarity.values()) 
		{
			for (String sid : potionsByRarity.get(rarity)) {
				if (sid.equals(potion.ID)) {
					return rarity;
				}
			}
		}
		return PotionRarity.SPECIAL;
	}
	public static PotionRarity GetPotionRarity(String s) {
		for (ReplayTheSpireMod.PotionRarity rarity : ReplayTheSpireMod.PotionRarity.values()) 
		{
			for (String sid : potionsByRarity.get(rarity)) {
				if (sid.equals(s)) {
					return rarity;
				}
			}
		}
		return PotionRarity.SPECIAL;
	}
	
	public static int GetPotionCost(AbstractPotion potion) 
	{
		ReplayTheSpireMod.PotionRarity rarity = ReplayTheSpireMod.GetPotionRarity(potion);
		switch(rarity) {
			case COMMON:
				return 50;
			case UNCOMMON:
				return 60;
			case RARE:
				return 75;
			case ULTRA:
				return 100;
			case SPECIAL:
				return 70;
			case SHOP:
				return 40;
			default:
				return 50;
		}
		/*
		for (ReplayTheSpireMod.PotionRarity rarity : ReplayTheSpireMod.PotionRarity.values()) 
		{
			for (String sid : potionsByRarity.get(rarity)) {
				if (sid.equals(potion.ID)) {
					switch(rarity) {
						case COMMON:
							return 50;
						case UNCOMMON:
							return 60;
						case RARE:
							return 75;
						case ULTRA:
							return 100;
						case SPECIAL:
							return 70;
						case SHOP:
							return 40;
						default:
							return 50;
					}
				}
			}
		}
		*/
	}
	
	public static void RigPotionsList() {
		int tmpShopChance = 0;
		int tmpUltraChance = ultraPotionChance;
		int tmpCommonChance = commonPotionChance;
		int tmpUncommonChance = uncommonPotionChance;
		int tmpRareChance = rarePotionChance;
		if (AbstractDungeon.player != null) {
			if (AbstractDungeon.player.hasRelic("White Beast Statue")) {
				tmpCommonChance += 5;
				tmpUncommonChance += 2;
				tmpRareChance -= 1;
				if (tmpRareChance < 0)
					tmpRareChance = 0;
			}
			if (AbstractDungeon.player.hasRelic("Chameleon Ring")) {
				tmpCommonChance /= 2;
				tmpRareChance += 3;
				tmpUltraChance += 4;
			}
		}
		tmpUltraChance *= potionsByRarity.get(ReplayTheSpireMod.PotionRarity.ULTRA).size();
		tmpCommonChance *= potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).size();
		tmpUncommonChance *= potionsByRarity.get(ReplayTheSpireMod.PotionRarity.UNCOMMON).size();
		tmpRareChance *= potionsByRarity.get(ReplayTheSpireMod.PotionRarity.RARE).size();
		if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP || (AbstractDungeon.nextRoom != null && AbstractDungeon.nextRoom.room != null && AbstractDungeon.nextRoom.room instanceof ShopRoom)) {
			tmpShopChance = shopPotionChance * potionsByRarity.get(ReplayTheSpireMod.PotionRarity.SHOP).size();
			tmpUltraChance += 4 * potionsByRarity.get(ReplayTheSpireMod.PotionRarity.ULTRA).size();
		}
		
		
		PotionHelper.potions = new ArrayList<String>();
		for (ReplayTheSpireMod.PotionRarity rarity : ReplayTheSpireMod.PotionRarity.values()) 
		{
			for (String sid : potionsByRarity.get(rarity)) {
				if (!PotionHelper.potions.contains(sid)) {
					int byrar = 0;
					switch(rarity) {
						case COMMON:
							byrar = tmpCommonChance;
							break;
						case UNCOMMON:
							byrar = tmpUncommonChance;
							break;
						case RARE:
							byrar = tmpRareChance;
							break;
						case ULTRA:
							byrar = tmpUltraChance;
							break;
						case SPECIAL:
							byrar = 0;
							break;
						case SHOP:
							byrar = tmpShopChance;
							break;
						default:
							byrar = tmpCommonChance;
					}
					for (int i = 0; i < byrar; i++) {
						PotionHelper.potions.add(sid);
					}
				}
			}
		}
	}
	
	public static ReplayTheSpireMod.PotionRarity returnRandomPotionTier(Random rng)
	{
		int tmpShopChance = 0;
		int tmpUltraChance = ultraPotionChance;
		int tmpCommonChance = commonPotionChance;
		int tmpUncommonChance = uncommonPotionChance;
		int tmpRareChance = rarePotionChance;
		if (AbstractDungeon.player != null) {
			if (AbstractDungeon.player.hasRelic("White Beast Statue")) {
				tmpCommonChance += 5;
				tmpUncommonChance += 2;
				tmpRareChance -= 1;
				if (tmpRareChance < 0)
					tmpRareChance = 0;
			}
			if (AbstractDungeon.player.hasRelic("Chameleon Ring")) {
				tmpCommonChance /= 2;
				tmpRareChance += 3;
				tmpUltraChance += 4;
			}
		}
		tmpUltraChance *= potionsByRarity.get(ReplayTheSpireMod.PotionRarity.ULTRA).size();
		tmpCommonChance *= potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).size();
		tmpUncommonChance *= potionsByRarity.get(ReplayTheSpireMod.PotionRarity.UNCOMMON).size();
		tmpRareChance *= potionsByRarity.get(ReplayTheSpireMod.PotionRarity.RARE).size();
		if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP) {
			tmpShopChance = shopPotionChance * potionsByRarity.get(ReplayTheSpireMod.PotionRarity.SHOP).size();
			tmpUltraChance += 4 * potionsByRarity.get(ReplayTheSpireMod.PotionRarity.ULTRA).size();
		}
		int roll = rng.random(0, tmpCommonChance + tmpUncommonChance + tmpRareChance + tmpUltraChance + tmpShopChance - 1);
		if (roll < tmpCommonChance) {
		  return ReplayTheSpireMod.PotionRarity.COMMON;
		}
		if (roll < tmpCommonChance + tmpUncommonChance) {
		  return ReplayTheSpireMod.PotionRarity.UNCOMMON;
		}
		if (roll < tmpCommonChance + tmpUncommonChance + tmpRareChance) {
		  return ReplayTheSpireMod.PotionRarity.RARE;
		}
		if (roll < tmpCommonChance + tmpUncommonChance + tmpRareChance + tmpShopChance) {
		  return ReplayTheSpireMod.PotionRarity.SHOP;
		}
		return ReplayTheSpireMod.PotionRarity.ULTRA;
	}
	
	public static AbstractPotion getRandomPotion()
	{
		return getRandomPotion(AbstractDungeon.potionRng);
	}
	
	public static AbstractPotion getRandomPotion(Random rng)
	{
		if (rng == null) {
			rng = AbstractDungeon.potionRng;
		}
		if (rng == null) {
			rng = new Random();
		}
		//String randomKey = (String)potions.get(rng.random.nextInt(potions.size()));
		return getRandomPotion(rng, returnRandomPotionTier(rng));
	}
	
	public static AbstractPotion getRandomPotion(Random rng, ReplayTheSpireMod.PotionRarity rarity)
	{
		String randomKey = (String)potionsByRarity.get(rarity).get(rng.random.nextInt(potionsByRarity.get(rarity).size()));
		return PotionHelper.getPotion(randomKey);
	}
	
	public static enum ChaosMagicSetting
	{
		ALWAYS,  COST_ONLY,  NEVER,  STRICT;
		
		private ChaosMagicSetting() {}
	}
	
	public static ChaosMagicSetting RingOfChaos_CompatibilityMode = ChaosMagicSetting.COST_ONLY;
	
	public ReplayTheSpireMod() {
		
		logger.info("subscribing to postInitialize event");
        BaseMod.subscribeToPostInitialize(this);
		
		logger.info("subscribing to editRelics event");
        BaseMod.subscribeToEditRelics(this);
        
        logger.info("subscribing to editCards event");
        BaseMod.subscribeToEditCards(this);
		
		logger.info("subscribing to editStrings event");
        BaseMod.subscribeToEditStrings(this);
		
		logger.info("subscribing to postDraw event");
        BaseMod.subscribeToPostDraw(this);
		
		logger.info("subscribing to getPotions event");
        BaseMod.subscribeToPotionGet(this);
        
		initializePotions();
		
		
        // logger.info("subscribing to setUnlocks event");
        // BaseMod.subscribeToSetUnlocks(this);
        
        //BaseMod.subscribeToOnPowersModified(this);
        //BaseMod.subscribeToPostExhaust(this);
        //BaseMod.subscribeToPostBattle(this);
        //BaseMod.subscribeToPostDraw(this);
		
		
		
	}
	
	public static void initialize() {
    	logger.info("========================= ReplayTheSpireMod INIT =========================");
		
		ReplayTheSpireMod.initAchievementUnlocks();
		
		@SuppressWarnings("unused")
		ReplayTheSpireMod replayMod = new ReplayTheSpireMod();
		
		
		
		logger.info("================================================================");
    }
	
	public static void addPotionToSet(final Class potionClass, final Color liquidColor, final Color hybridColor, final Color spotsColor, final String potionID, final ReplayTheSpireMod.PotionRarity potionRarity) {
		BaseMod.addPotion(potionClass, liquidColor, hybridColor, spotsColor, potionID);
		ReplayTheSpireMod.potionsByRarity.get(potionRarity).add(potionID);
		
	}
	
	public static void initializePotions() {
		logger.info("begin editting potions");
		/*
		ReplayTheSpireMod.potionCosts = new EnumMap<ReplayTheSpireMod.PotionRarity, int>(ReplayTheSpireMod.PotionRarity.class);
		ReplayTheSpireMod.potionCosts.put(ReplayTheSpireMod.PotionRarity.COMMON, 50);
		ReplayTheSpireMod.potionCosts.put(ReplayTheSpireMod.PotionRarity.UNCOMMON, 60);
		ReplayTheSpireMod.potionCosts.put(ReplayTheSpireMod.PotionRarity.RARE, 75);
		ReplayTheSpireMod.potionCosts.put(ReplayTheSpireMod.PotionRarity.ULTRA, 90);
		ReplayTheSpireMod.potionCosts.put(ReplayTheSpireMod.PotionRarity.SPECIAL, 75);
		ReplayTheSpireMod.potionCosts.put(ReplayTheSpireMod.PotionRarity.SHOP, 40);
		*/
		ReplayTheSpireMod.potionsByRarity = new EnumMap<ReplayTheSpireMod.PotionRarity, ArrayList<String>>(ReplayTheSpireMod.PotionRarity.class);
		ReplayTheSpireMod.potionsByRarity.put(ReplayTheSpireMod.PotionRarity.COMMON, new ArrayList<String>());
		ReplayTheSpireMod.potionsByRarity.put(ReplayTheSpireMod.PotionRarity.UNCOMMON, new ArrayList<String>());
		ReplayTheSpireMod.potionsByRarity.put(ReplayTheSpireMod.PotionRarity.RARE, new ArrayList<String>());
		ReplayTheSpireMod.potionsByRarity.put(ReplayTheSpireMod.PotionRarity.ULTRA, new ArrayList<String>());
		ReplayTheSpireMod.potionsByRarity.put(ReplayTheSpireMod.PotionRarity.SPECIAL, new ArrayList<String>());
		ReplayTheSpireMod.potionsByRarity.put(ReplayTheSpireMod.PotionRarity.SHOP, new ArrayList<String>());
		
		ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Ancient Potion");
		ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Block Potion");
		ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Dexterity Potion");
		ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Energy Potion");
		ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Explosive Potion");
		ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Fire Potion");
		ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Strength Potion");
		ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Regen Potion");
		ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Swift Potion");
		ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Poison Potion");
		ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Weak Potion");
		
		
		/*
		  public int getPrice()
		  {
			switch(this.rarity){
				case UNCOMMON:
				  return 60;
				case RARE:
				  return 75;
				case ULTRA:
				  return 90;
				case SHOP:
				  return 40;
				default:
				  return 50;
			}
		  }
		  */
		  /*
		ReplayTheSpireMod.addPotionToSet(
			HealthPotion.class,
			Color.CHARTREUSE.cpy(),
			null,
			null,
			"Health Potion",
			ReplayTheSpireMod.PotionRarity.SHOP
		);*/
		ReplayTheSpireMod.addPotionToSet(
			ElixirPotion.class,
			Color.GOLD.cpy(),
			null,
			Color.DARK_GRAY.cpy(),
			"Elixir",
			ReplayTheSpireMod.PotionRarity.SHOP
		);
		/*
		ReplayTheSpireMod.addPotionToSet(
			SpiritPotion.class,
			Color.GOLD.cpy(),
			Color.CHARTREUSE.cpy(),
			null,
			"Spirit Potion",
			ReplayTheSpireMod.PotionRarity.ULTRA
		);
		*/
		ReplayTheSpireMod.addPotionToSet(
			CursedPotion.class,
			Color.DARK_GRAY.cpy(),
			null,
			Color.RED.cpy(),
			"Cursed Concoction",
			ReplayTheSpireMod.PotionRarity.ULTRA
		);
		ReplayTheSpireMod.addPotionToSet(
			DoomPotion.class,
			Color.valueOf("0d429dff"),
			Color.DARK_GRAY.cpy(),
			null,
			"Doom Potion",
			ReplayTheSpireMod.PotionRarity.ULTRA
		);
		ReplayTheSpireMod.addPotionToSet(
			AdrenalinePotion.class,
			Color.ORANGE.cpy(),
			null,
			null,
			"Adrenaline Potion",
			ReplayTheSpireMod.PotionRarity.COMMON
		);
		/*
		ReplayTheSpireMod.addPotionToSet(
			IronSkinPotion.class,
			Color.SKY.cpy(),
			null,
			null,
			"Ironskin Potion",
			ReplayTheSpireMod.PotionRarity.UNCOMMON
		);
		ReplayTheSpireMod.addPotionToSet(
			ThornsPotion.class,
			Color.GOLD.cpy(),
			null,
			Color.LIGHT_GRAY.cpy(),
			"Thorns Potion",
			ReplayTheSpireMod.PotionRarity.UNCOMMON
		);
		*/
		ReplayTheSpireMod.addPotionToSet(
			VenomPotion.class,
			Color.OLIVE.cpy(),
			null,
			Color.CHARTREUSE.cpy(),
			"Venom Potion",
			ReplayTheSpireMod.PotionRarity.UNCOMMON
		);
		ReplayTheSpireMod.addPotionToSet(
			DeathPotion.class,
			Color.DARK_GRAY.cpy(),
			Color.FIREBRICK.cpy(),
			Color.CORAL.cpy(),
			"Death Potion",
			ReplayTheSpireMod.PotionRarity.RARE
		);
		ReplayTheSpireMod.addPotionToSet(
			ToxicPotion.class,
			Color.OLIVE.cpy(),
			null,
			Color.CHARTREUSE.cpy(),
			"Toxic Potion",
			ReplayTheSpireMod.PotionRarity.RARE
		);
		ReplayTheSpireMod.addPotionToSet(
			MilkshakePotion.class,
			Color.LIGHT_GRAY.cpy(),
			Color.WHITE.cpy(),
			null,
			"Milkshake",
			ReplayTheSpireMod.PotionRarity.RARE
		);
		/*
		ReplayTheSpireMod.addPotionToSet(
			InspirationPotion.class,
			Color.LIGHT_GRAY.cpy(),
			null,
			Color.SKY.cpy(),
			"Inspiration Potion",
			ReplayTheSpireMod.PotionRarity.RARE
		);
		*/
		logger.info("end editting potions");
	}
	
	
	public int currentSettingsTab;
	public int currentSettingsSubTab;
	public ModPanel settingsPanel;
	
	public ModToggleButton chaos_button_1;
	public ModToggleButton chaos_button_2;
	public ModToggleButton chaos_button_3;
	public ModToggleButton chaos_button_4;
	
	private ModToggleButton makeLabeledButton(final String labelText, final float xPos, final float yPos, final Color color, final BitmapFont font, final boolean enabled, final ModPanel p, final Consumer<ModLabel> labelUpdate, final Consumer<ModToggleButton> c) {
		p.addLabel(labelText, xPos + 40.0f, yPos + 8.0f, (me) -> {});
		return new ModToggleButton(xPos, yPos, enabled, false, p, c);
	}
	
	@Override
    public void receivePostInitialize() {
		
		ReplayTheSpireMod.powerAtlas = new com.badlogic.gdx.graphics.g2d.TextureAtlas(Gdx.files.internal("powers/replayPowers.atlas"));
		
		ReplayTheSpireMod.renderFishFG = false;
		ReplayTheSpireMod.fishAtlas = new TextureAtlas(Gdx.files.internal("images/replayScenes/fishfight.atlas"));
		ReplayTheSpireMod.fishFG = ReplayTheSpireMod.fishAtlas.findRegion("mod/fg");
		ReplayTheSpireMod.forestBG = ImageMaster.loadImage("images/monsters/fadingForest/fadingForest_bg.png");
        // Mod badge
        Texture badgeTexture = new Texture(BADGE_IMG);
		this.currentSettingsSubTab = 0;
        this.settingsPanel = new ModPanel();
		loadSettingsData();
		settingsPanel.addLabel("Avoid Ring Of Chaos magic number changes?", 350.0f, 750.0f, (me) -> {});
		settingsPanel.addLabel("(Avoiding these changes makes RoC less buggy, but also have less interesting effect variety)", 350.0f, 700.0f, (me) -> {});
		chaos_button_1 = makeLabeledButton("Don't avoid changes including magic number (more variety, more bugs)", 350.0f, 650.0f, Color.WHITE, FontHelper.buttonLabelFont, ReplayTheSpireMod.RingOfChaos_CompatibilityMode == ChaosMagicSetting.ALWAYS, this.settingsPanel, (me) -> {}, me -> {
            if (!(this.chaos_button_1.enabled || this.chaos_button_2.enabled || this.chaos_button_3.enabled || this.chaos_button_4.enabled)) {
				me.enabled = true;
			} else {
				ReplayTheSpireMod.RingOfChaos_CompatibilityMode = ChaosMagicSetting.ALWAYS;
				this.chaos_button_1.enabled = true;
				this.chaos_button_2.enabled = false;
				this.chaos_button_3.enabled = false;
				this.chaos_button_4.enabled = false;
				saveSettingsData();
			}
		});
		settingsPanel.addUIElement(chaos_button_1);
		chaos_button_2 = makeLabeledButton("Avoid changes including only magic number (compromise option)", 350.0f, 600.0f, Color.WHITE, FontHelper.buttonLabelFont, ReplayTheSpireMod.RingOfChaos_CompatibilityMode == ChaosMagicSetting.COST_ONLY, this.settingsPanel, (me) -> {}, me -> {
            if (!(this.chaos_button_1.enabled || this.chaos_button_2.enabled || this.chaos_button_3.enabled || this.chaos_button_4.enabled)) {
				me.enabled = true;
			} else {
				ReplayTheSpireMod.RingOfChaos_CompatibilityMode = ChaosMagicSetting.COST_ONLY;
				this.chaos_button_1.enabled = false;
				this.chaos_button_2.enabled = true;
				this.chaos_button_3.enabled = false;
				this.chaos_button_4.enabled = false;
				saveSettingsData();
			}
		});
		settingsPanel.addUIElement(chaos_button_2);
		chaos_button_3 = makeLabeledButton("Avoid all magic number changes (less variety, full stability)", 350.0f, 550.0f, Color.WHITE, FontHelper.buttonLabelFont, ReplayTheSpireMod.RingOfChaos_CompatibilityMode == ChaosMagicSetting.NEVER, this.settingsPanel, (me) -> {}, me -> {
            if (!(this.chaos_button_1.enabled || this.chaos_button_2.enabled || this.chaos_button_3.enabled || this.chaos_button_4.enabled)) {
				me.enabled = true;
			} else {
				ReplayTheSpireMod.RingOfChaos_CompatibilityMode = ChaosMagicSetting.NEVER;
				this.chaos_button_1.enabled = false;
				this.chaos_button_2.enabled = false;
				this.chaos_button_3.enabled = true;
				this.chaos_button_4.enabled = false;
				saveSettingsData();
			}
		});
		settingsPanel.addUIElement(chaos_button_3);
		chaos_button_4 = makeLabeledButton("Never change any values on cards with magic number (for the extremely paranoid)", 350.0f, 500.0f, Color.WHITE, FontHelper.buttonLabelFont, ReplayTheSpireMod.RingOfChaos_CompatibilityMode == ChaosMagicSetting.STRICT, this.settingsPanel, (me) -> {}, me -> {
            if (!(this.chaos_button_1.enabled || this.chaos_button_2.enabled || this.chaos_button_3.enabled || this.chaos_button_4.enabled)) {
				me.enabled = true;
			} else {
				ReplayTheSpireMod.RingOfChaos_CompatibilityMode = ChaosMagicSetting.STRICT;
				this.chaos_button_1.enabled = false;
				this.chaos_button_2.enabled = false;
				this.chaos_button_3.enabled = false;
				this.chaos_button_4.enabled = true;
				saveSettingsData();
			}
		});
		settingsPanel.addUIElement(chaos_button_4);
		/*
        settingsPanel.addLabel("Potion Rarities", 1000.0f, 700.0f, (me) -> {});
		
		ModSlider potionSliderC = new ModSlider("Common", 1000.0f, 650.0f, 10.0f, "", settingsPanel, (me) -> {
			//logger.info((int)Math.round(me.value * me.multiplier));
			ReplayTheSpireMod.commonPotionChance = (int)Math.round(me.value * me.multiplier);
		});
		potionSliderC.setValue((float)ReplayTheSpireMod.commonPotionChance / potionSliderC.multiplier);
		settingsPanel.addUIElement(potionSliderC);
		
		ModSlider potionSliderUC = new ModSlider("Uncommon", 1000.0f, 600.0f, 10.0f, "", settingsPanel, (me) -> {
			ReplayTheSpireMod.uncommonPotionChance = (int)Math.round(me.value * me.multiplier);
		});
		potionSliderUC.setValue((float)ReplayTheSpireMod.uncommonPotionChance / potionSliderUC.multiplier);
		settingsPanel.addUIElement(potionSliderUC);
		
		ModSlider potionSliderR = new ModSlider("Rare", 1000.0f, 550.0f, 10.0f, "", settingsPanel, (me) -> {
			ReplayTheSpireMod.rarePotionChance = (int)Math.round(me.value * me.multiplier);
		});
		potionSliderR.setValue((float)ReplayTheSpireMod.rarePotionChance / potionSliderR.multiplier);
		settingsPanel.addUIElement(potionSliderR);
		
		ModSlider potionSliderUR = new ModSlider("Ultra Rare", 1000.0f, 500.0f, 10.0f, "", settingsPanel, (me) -> {
			ReplayTheSpireMod.ultraPotionChance = (int)Math.round(me.value * me.multiplier);
		});
		potionSliderUR.setValue((float)ReplayTheSpireMod.ultraPotionChance / potionSliderUR.multiplier);
		settingsPanel.addUIElement(potionSliderUR);
		logger.info("end editting json");
		*/
		
		logger.info("badge");
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        
		
		logger.info("keywords");
        final String[] necroNames = { "necrotic", "necrotic poison", "Necrotic" };
        BaseMod.addKeyword(necroNames, "A powerful poison that deals 2 damage each turn, but doesn't last as long.");
		final String[] refundNames = { "refund", "refunds", "Refund", "Refunds"};
		BaseMod.addKeyword(refundNames, "Returns energy spent on playing the card, up to the Refund value.");
		final String[] crystalNames = { "crystal"};
		BaseMod.addKeyword(crystalNames, "Orb: Gives adjacent orbs #b+2 #yFocus. When #yEvoked, if you have fewer than #b3 orb slots, gain an orb slot. NL #yPassive effect is not affected by other #yCrystal orbs.");
		final String[] hfNames = { "hellfire"};
		BaseMod.addKeyword(hfNames, "Orb: At the start of your turn, gain #b+2 #yStrength until the end of your turn. NL When #yEvoked, applies 1 #yVulnerable to a random enemy.");
		final String[] glNames = { "glass"};
		BaseMod.addKeyword(glNames, "Orb: No #yPassive effect. When #yEvoked while you have more than #b3 orb slots, consumes your leftmost orb slot and #yEvokes the occupying orb.");
		final String[] rfNames = { "reflection", "Reflection", "reflection."};
		BaseMod.addKeyword(rfNames, "Goes down by 1 each round, is removed on 0. While active, completely blocking Attack damage reflects it back at the attacker.");
		
		
		logger.info("end post init");
        Settings.isDailyRun = false;
        Settings.isTrial = false;
        Settings.isDemo = false;
		
		ReplayTheSpireMod.receiveEditUnlocks();
		
    }
	
	
	@Override
	public void receiveEditRelics() {
		logger.info("begin editting relics");
		ReplayTheSpireMod.receiveEditUnlocks();
        
        // Add relics
		//BaseMod.addRelic(new AncientBracer(), RelicType.GREEN);
		RelicLibrary.addBlue(new AncientBracer());
		RelicLibrary.addBlue(new SolarPanel());
		RelicLibrary.addBlue(new Carrot());
		RelicLibrary.addBlue(new Geode());
		RelicLibrary.addBlue(new RaidersMask());
		BaseMod.addRelic(new Arrowhead(), RelicType.SHARED);
		BaseMod.addRelic(new AbesTreasure(), RelicType.SHARED);
		BaseMod.addRelic(new Bandana(), RelicType.SHARED);
		BaseMod.addRelic(new Baseball(), RelicType.SHARED);
		BaseMod.addRelic(new ByrdSkull(), RelicType.GREEN);
		BaseMod.addRelic(new ChameleonRing(), RelicType.SHARED);
		BaseMod.addRelic(new ChemicalX(), RelicType.SHARED);
		BaseMod.addRelic(new ChewingGum(), RelicType.SHARED);
		BaseMod.addRelic(new CounterBalance(), RelicType.SHARED);
		BaseMod.addRelic(new Durian(), RelicType.SHARED);
		BaseMod.addRelic(new DivineProtection(), RelicType.SHARED);
		BaseMod.addRelic(new ElectricBlood(), RelicType.RED);
		BaseMod.addRelic(new Funnel(), RelicType.SHARED);
		BaseMod.addRelic(new Garlic(), RelicType.SHARED);
		BaseMod.addRelic(new GoldenEgg(), RelicType.SHARED);
		BaseMod.addRelic(new GremlinFood(), RelicType.SHARED);
		BaseMod.addRelic(new GrinningJar(), RelicType.SHARED);
		BaseMod.addRelic(new GuideBook(), RelicType.SHARED);
		BaseMod.addRelic(new HoneyJar(), RelicType.SHARED);
		BaseMod.addRelic(new IronHammer(), RelicType.SHARED);
		BaseMod.addRelic(new IronCore(), RelicType.SHARED);
		BaseMod.addRelic(new KingOfHearts(), RelicType.RED);
		BaseMod.addRelic(new Kintsugi(), RelicType.SHARED);
		BaseMod.addRelic(new Mirror(), RelicType.SHARED);
		BaseMod.addRelic(new OnionRing(), RelicType.SHARED);
		BaseMod.addRelic(new OozeArmor(), RelicType.RED);
		BaseMod.addRelic(new PainkillerHerb(), RelicType.SHARED);
		BaseMod.addRelic(new PondfishScales(), RelicType.SHARED);
		BaseMod.addRelic(new PetGhost(), RelicType.SHARED);
		BaseMod.addRelic(new QuantumEgg(), RelicType.SHARED);
		BaseMod.addRelic(new RingOfChaos(), RelicType.SHARED);
		BaseMod.addRelic(new RingOfFury(), RelicType.SHARED);
		BaseMod.addRelic(new RingOfPeace(), RelicType.SHARED);
		BaseMod.addRelic(new RingOfFangs(), RelicType.SHARED);
		BaseMod.addRelic(new RingOfPanic(), RelicType.SHARED);
		BaseMod.addRelic(new RingOfSearing(), RelicType.SHARED);
		BaseMod.addRelic(new RingOfShattering(), RelicType.SHARED);
		BaseMod.addRelic(new RingOfHypnosis(), RelicType.SHARED);
		BaseMod.addRelic(new SecondSwordRelic(), RelicType.RED);
		//BaseMod.addRelic(new SimpleRune(), RelicType.SHARED);
		BaseMod.addRelic(new SizzlingBlood(), RelicType.SHARED);
		BaseMod.addRelic(new SnackPack(), RelicType.SHARED);
		BaseMod.addRelic(new SnakeBasket(), RelicType.GREEN);
		BaseMod.addRelic(new SneckoScales(), RelicType.GREEN);
		BaseMod.addRelic(new SneckoHeart(), RelicType.SHARED);
		BaseMod.addRelic(new TagBag(), RelicType.SHARED);
		BaseMod.addRelic(new VampiricSpirits(), RelicType.GREEN);
        
		
        logger.info("done editting relics");
	}
	
	void AddAndUnlockCard(AbstractCard c)
	{
		BaseMod.addCard(c);
		UnlockTracker.unlockCard(c.cardID);
	}
	
	@Override
	public void receiveEditCards() {
		logger.info("[RtS] begin editting cards");
		logger.info("adding cards for Ironclad...");
		AddAndUnlockCard(new Abandon());
		AddAndUnlockCard(new Hemogenesis());
		AddAndUnlockCard(new LifeLink());
		AddAndUnlockCard(new Massacre());
		AddAndUnlockCard(new RunThrough());
		AddAndUnlockCard(new DefyDeath());
		AddAndUnlockCard(new DemonicInfusion());
		logger.info("adding cards for Silent...");
		AddAndUnlockCard(new AtomBomb());
		AddAndUnlockCard(new DrainingMist());
		AddAndUnlockCard(new FluidMovement());
		AddAndUnlockCard(new PoisonDarts());
		AddAndUnlockCard(new ToxinWave());
		AddAndUnlockCard(new HiddenBlade());
		AddAndUnlockCard(new SneakUp());
		logger.info("adding cards for Defect...");
		AddAndUnlockCard(new PanicButton());
		AddAndUnlockCard(new MirrorShield());
		AddAndUnlockCard(new BasicCrystalCard());
		AddAndUnlockCard(new TimeBomb());
		AddAndUnlockCard(new ReflectiveLens());
		//AddAndUnlockCard(new Crystallizer());
		AddAndUnlockCard(new ReplayRNGCard());
		AddAndUnlockCard(new FIFOQueue()); 
		logger.info("adding colorless cards...");
		AddAndUnlockCard(new Improvise());
		AddAndUnlockCard(new PoisonedStrike());
		AddAndUnlockCard(new Specialist());
		AddAndUnlockCard(new AwakenedRitual());
		AddAndUnlockCard(new SurveyOptions());
		logger.info("adding curses...");
		AddAndUnlockCard(new Hallucinations());
		//AddAndUnlockCard(new Languid());
		AddAndUnlockCard(new Sickly());
		AddAndUnlockCard(new Delirium());
		AddAndUnlockCard(new Voices());
		AddAndUnlockCard(new LoomingEvil());
		AddAndUnlockCard(new Amnesia());
		//AddAndUnlockCard(new FaultyEquipment());
		AddAndUnlockCard(new SpreadingInfection());
		AddAndUnlockCard(new AbeCurse());
		AddAndUnlockCard(new Overencumbered());
		logger.info("adding unobtainable cards...");
		AddAndUnlockCard(new PotOfGreed());
		AddAndUnlockCard(new GhostDefend());
		AddAndUnlockCard(new GhostSwipe());
		AddAndUnlockCard(new GhostFetch());
		AddAndUnlockCard(new RitualComponent());
		AddAndUnlockCard(new DarkEchoRitualCard());
		AddAndUnlockCard(new IC_ScorchingBeam());
		AddAndUnlockCard(new IC_FireWall());
		//AddAndUnlockCard(new IC_BasicHellfireCard());
		AddAndUnlockCard(new WeaponsOverheat());
		logger.info("done editting cards");
	}
	
	
	private static void doStringOverrides(final Type stringType, final String jsonString) {
		
		HashMap<Type, String> typeMaps = (HashMap<Type, String>)ReflectionHacks.getPrivateStatic(BaseMod.class, "typeMaps");
		HashMap<Type, Type> typeTokens = (HashMap<Type, Type>)ReflectionHacks.getPrivateStatic(BaseMod.class, "typeTokens");
		/*new HashMap<Type, String>();
        typeMaps.put(CardStrings.class, "cards");
        typeMaps.put(CharacterStrings.class, "characters");
        typeMaps.put(CreditStrings.class, "credits");
        typeMaps.put(EventStrings.class, "events");
        typeMaps.put(KeywordStrings.class, "keywords");
        typeMaps.put(MonsterStrings.class, "monsters");
        typeMaps.put(PotionStrings.class, "potions");
        typeMaps.put(PowerStrings.class, "powers");
        typeMaps.put(RelicStrings.class, "relics");
        typeMaps.put(ScoreBonusStrings.class, "scoreBonuses");
        typeMaps.put(TutorialStrings.class, "tutorials");
        typeMaps.put(UIStrings.class, "ui");
		*/
		
        final String typeMap = typeMaps.get(stringType);
        final Type typeToken = typeTokens.get(stringType);
        //final String modName = findCallingModName();
        final Map localizationStrings = (Map)ReflectionHacks.getPrivateStatic(LocalizedStrings.class, typeMap);
        final Map map = new HashMap((Map)BaseMod.gson.fromJson(jsonString, typeToken));
        localizationStrings.putAll(map);
        ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, typeMap, localizationStrings);
	}
	
	private void editStringsByLang(String jsonPath) {
		
        // RelicStrings
        String relicStrings = Gdx.files.internal(jsonPath + "ReplayRelicStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
		relicStrings = Gdx.files.internal(jsonPath + "ReplayOverrideRelicStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        ReplayTheSpireMod.doStringOverrides(RelicStrings.class, relicStrings);
        // CardStrings
        String cardStrings = Gdx.files.internal(jsonPath + "ReplayCardStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
		cardStrings = Gdx.files.internal(jsonPath + "ReplayOverrideCardStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        ReplayTheSpireMod.doStringOverrides(CardStrings.class, cardStrings);
        // PowerStrings
        String powerStrings = Gdx.files.internal(jsonPath + "ReplayPowerStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        // EventStrings
        String eventStrings = Gdx.files.internal(jsonPath + "ReplayEventStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
        String eventStringsF = Gdx.files.internal(jsonPath + "FadingForestEventStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(EventStrings.class, eventStringsF);
        // PotionStrings
        String potionStrings = Gdx.files.internal(jsonPath + "ReplayPotionStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
        // UIStrings
        String uiStrings = Gdx.files.internal(jsonPath + "ReplayUIStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
        String mStrings = Gdx.files.internal(jsonPath + "ReplayMonsterStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(MonsterStrings.class, mStrings);
        // OrbStrings
		/*
        String orbStrings = Gdx.files.internal(jsonPath + "ReplayOrbStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(OrbStrings.class, orbStrings);
		*/
	}
	
	@Override
	public void receiveEditStrings() {
		logger.info("begin editting strings");
		String jsonPath = "localization/";
		editStringsByLang(jsonPath);
		if (Settings.language.toString().equals("SPA")) {
			logger.info("Spanish detected!");
			jsonPath = "localization/spa/";
			editStringsByLang(jsonPath);
		}
		
		logger.info("done editting strings");
	}
	
	public void receivePostDraw(AbstractCard c) {
		if (AbstractDungeon.player.hasPower("TPH_Confusion") && c.cost > -1 && c.color != AbstractCard.CardColor.CURSE && c.type != AbstractCard.CardType.STATUS) {
			if (BypassStupidBasemodRelicRenaming_hasRelic("Snecko Heart")) {
				SneckoHeart snek = (SneckoHeart)BypassStupidBasemodRelicRenaming_getRelic("Snecko Heart");
				if (snek.checkCard(c)) {
					snek.flash();
					c.setCostForTurn(-99);
				} else {
					c.setCostForTurn(AbstractDungeon.cardRandomRng.random(Math.min(3, c.cost + 1)));
				}
			} else {
				c.setCostForTurn(AbstractDungeon.cardRandomRng.random(3));
			}
		}
	}
	
	public void receivePotionGet(final AbstractPotion p0) {
		if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Chameleon Ring")) {
			switch(p0.ID) {
				case "AttackPotion":
					p0.description = "Add a random Upgraded Attack card to your hand, it costs #b0 this turn.";
					return;
				case "SkillPotion":
					p0.description = "Add a random Upgraded Skill card to your hand, it costs #b0 this turn.";
					return;
				case "PowerPotion":
					p0.description = "Add a random Upgraded Power card to your hand, it costs #b0 this turn.";
					return;
				default:
					return;
			}
		}
	}
	
	
	public static void saveRunData() {
		try {
			SpireConfig config = new SpireConfig("ReplayTheSpireMod", "replayRunSaveData");
			if (AbstractDungeon.player.hasRelic("Baseball")) {
				
			} else {
				
			}
			if (AbstractDungeon.player.hasRelic("Chaos Ring")) {
				
			} else {
				
			}
			
			config.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void saveSettingsData() {
		try {
			SpireConfig config = new SpireConfig("ReplayTheSpireMod", "replaySettingsData");
			switch(ReplayTheSpireMod.RingOfChaos_CompatibilityMode) {
				case ALWAYS: 
					config.setInt("chaos_mode", 0);
					break;
				case COST_ONLY: 
					config.setInt("chaos_mode", 1);
					break;
				case NEVER: 
					config.setInt("chaos_mode", 2);
					break;
				case STRICT: 
					config.setInt("chaos_mode", 3);
					break;
			}
			config.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    public static void loadSettingsData() {
    	logger.info("ReplayTheSpireMod | Loading Data...");
    	try {
			Properties defaultProperties = new Properties();
			defaultProperties.setProperty("chaos_mode", "1");
			SpireConfig config = new SpireConfig("ReplayTheSpireMod", "replaySettingsData");
			config.load();
			
			try {
				switch(config.getInt("chaos_mode")) {
					case 0:
						ReplayTheSpireMod.RingOfChaos_CompatibilityMode = ChaosMagicSetting.ALWAYS;
						break;
					case 1:
						ReplayTheSpireMod.RingOfChaos_CompatibilityMode = ChaosMagicSetting.COST_ONLY;
						break;
					case 2:
						ReplayTheSpireMod.RingOfChaos_CompatibilityMode = ChaosMagicSetting.NEVER;
						break;
					case 3:
						ReplayTheSpireMod.RingOfChaos_CompatibilityMode = ChaosMagicSetting.STRICT;
						break;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			
			
		} catch (IOException e) {
			logger.error("Failed to load ReplayTheSpireMod settings data!");
			e.printStackTrace();
		}
    	
    }
	
	public static boolean rua_DefeatedAbe = false;
	public static boolean rua_DefeatedForest = false;
	
	public static void completeAchievement(String key) {
		ReplayTheSpireMod.loadUnlocksData();
		for (ReplayUnlockAchieve ach : ReplayTheSpireMod.unlockAchievements) {
			if (ach.achievementId.equals(key)) {
				ach.isUnlocked = true;
			}
		}
		ReplayTheSpireMod.saveUnlocksData();
	}
	
	public static void saveUnlocksData() {
		try {
			SpireConfig config = new SpireConfig("ReplayTheSpireMod", "replayUnlocksData");
			for (ReplayUnlockAchieve ach : ReplayTheSpireMod.unlockAchievements) {
				config.setBool(ach.achievementId, ach.isUnlocked);
			}
			//config.setBool("abe_win", rua_DefeatedAbe);
			//config.setBool("forest_win", rua_DefeatedForest);
			config.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static void loadUnlocksData() {
    	logger.info("ReplayTheSpireMod | Loading Unlock Data...");
    	try {
			SpireConfig config = new SpireConfig("ReplayTheSpireMod", "replayUnlocksData");
			config.load();
			for (ReplayUnlockAchieve ach : ReplayTheSpireMod.unlockAchievements) {
				ach.isUnlocked = config.getBool(ach.achievementId);
			}
			//rua_DefeatedAbe = config.getBool("abe_win");
			//rua_DefeatedForest = config.getBool("forest_win");
			
		} catch (IOException e) {
			logger.error("Failed to load ReplayTheSpireMod unlocks data!");
			e.printStackTrace();
		}
    }
	
	public static void receiveEditUnlocks() {
		loadUnlocksData();
		for (ReplayUnlockAchieve ach : ReplayTheSpireMod.unlockAchievements) {
			String unlockReq = UnlockTracker.unlockReqs.get(ach.relicId);
			if (unlockReq == null) {
				UnlockTracker.unlockReqs.put(ach.relicId, ach.desc + " to unlock.");
			}
			if(ach.isUnlocked) {
				while (UnlockTracker.isRelicLocked(ach.relicId)) {
					UnlockTracker.lockedRelics.remove(ach.relicId);
				}
			} else {
				if (!UnlockTracker.isRelicLocked(ach.relicId)) {
					UnlockTracker.lockedRelics.add(ach.relicId);
				}
			}
		}
	}
	
	public static void initAchievementUnlocks() {
		ReplayTheSpireMod.unlockAchievements.add(new ReplayUnlockAchieve("abe_win", "Big Fish in a Small Pond", "Defeat Captain Abe", "Pondfish Scales"));
		ReplayTheSpireMod.unlockAchievements.add(new ReplayUnlockAchieve("abe_perfect", "The Worst Pirate I've Ever Heard Of", "Defeat Captain Abe without his Deadweight power ever triggering", "Abe's Treasure"));
		//ReplayTheSpireMod.unlockAchievements.add(new ReplayUnlockAchieve("abe_special", "From Beyond Hell", "Complete a run with Abe's Revenge in your deck", "noneyetmyguy"));
		//ReplayTheSpireMod.unlockAchievements.add(new ReplayUnlockAchieve("forest_win", "The Trees Have Ears", "Defeat The Fading Forest", "Transient Totem"));
		//ReplayTheSpireMod.unlockAchievements.add(new ReplayUnlockAchieve("forest_perfect", "Onceler", "Defeat The Fading Forest without losing HP", "noneyetmyguy"));
		//ReplayTheSpireMod.unlockAchievements.add(new ReplayUnlockAchieve("forest_special", "Environmentalist", "Defeat The Fading Forest killing anything", "noneyetmyguy"));
		ReplayTheSpireMod.unlockAchievements.add(new ReplayUnlockAchieve("complete_eye", "Baffling", "Complete a run with Snecko Eye", "Snecko Heart"));
	}
	
    /*
    public static void clearRunData() {
    	logger.info("ReplayTheSpireMod | Clearing Saved Data...");
    	saveData();
    }
    */
	
}