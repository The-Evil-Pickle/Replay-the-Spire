package replayTheSpire;

import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.hubris.HubrisMod;
import com.evacipated.cardcrawl.mod.hubris.events.thebeyond.TheBottler;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.blue.*;
import com.megacrit.cardcrawl.mod.replay.cards.colorless.*;
import com.megacrit.cardcrawl.mod.replay.cards.curses.*;
import com.megacrit.cardcrawl.mod.replay.cards.green.*;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.mod.replay.events.shrines.*;
import com.megacrit.cardcrawl.mod.replay.events.thebottom.*;
import com.megacrit.cardcrawl.mod.replay.events.thecity.GremboTheGreat;
import com.megacrit.cardcrawl.mod.replay.events.thecity.ReplayMapScoutEvent;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.CaptainAbe;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.FadingForestBoss;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.PondfishBoss;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.eastereggs.J_louse_1;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.eastereggs.J_louse_2;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.eastereggs.J_louse_3;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.eastereggs.R_Hoarder;
import com.megacrit.cardcrawl.mod.replay.potions.*;
import com.megacrit.cardcrawl.mod.replay.powers.RidThyselfOfStatusCardsPower;
import com.megacrit.cardcrawl.mod.replay.powers.TheWorksPower;
import com.megacrit.cardcrawl.mod.replay.relics.*;
import com.megacrit.cardcrawl.mod.replay.relics.RingOfChaos.ChaosUpgradeType;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
//import com.megacrit.cardcrawl.core.Settings.GameLanguage;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.unlock.*;

import basemod.*;
import basemod.helpers.*;
import basemod.interfaces.*;
import blackrusemod.BlackRuseMod;
import chronomuncher.ChronoMod;
import coloredmap.ColoredMap;
import fetch.FetchMod;
import fruitymod.FruityMod;
import fruitymod.seeker.patches.*;
import infinitespire.InfiniteSpire;
import madsciencemod.MadScienceMod;
//import madsciencemod.powers.*;
import mysticmod.MysticMod;
import replayTheSpire.panelUI.*;
import replayTheSpire.patches.SimplicityRunePatches;
import replayTheSpire.replayxover.beakedbs;
import replayTheSpire.replayxover.chronobs;
import replayTheSpire.replayxover.constructbs;
import replayTheSpire.replayxover.infinitebs;
import replayTheSpire.variables.Exhaustive;
import replayTheSpire.variables.MagicMinusOne;

import java.lang.reflect.*;
import java.io.*;

import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BloodyIdol;
import com.megacrit.cardcrawl.relics.Dodecahedron;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.relics.SneckoEye;
import com.megacrit.cardcrawl.relics.Sozu;
import com.megacrit.cardcrawl.rooms.ShopRoom;

import java.util.*;
import java.util.function.*;
//SetUnlocksSubscriber, 

@SpireInitializer
public class ReplayTheSpireMod implements PostInitializeSubscriber,
EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, PostDrawSubscriber, PotionGetSubscriber, StartGameSubscriber, EditKeywordsSubscriber
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
    private static final String AUTHOR = "The_Evil_Pickle, AstroPenguin642, Bakuhaku, Slimer509, Stewartisme";
    private static final String DESCRIPTION = "Content expansion mod";
	
	public static final String BADGE_IMG = "img/replay/ModBadge.png";
	
	protected static int commonPotionChance = 9;
	protected static int uncommonPotionChance = 7;
	protected static int rarePotionChance = 4;
	protected static int ultraPotionChance = 1;
	protected static int shopPotionChance = 12;
	
	public static final ArrayList<ReplayUnlockAchieve> unlockAchievements = new ArrayList<ReplayUnlockAchieve>();
	public static final HashMap<String, HashMap<String, String>> relicInteractionStrings = new HashMap<String, HashMap<String, String>>();
	
	public static final AbstractCard.CardColor IronCoreColor = AbstractCard.CardColor.RED;
	
	//public static EnumMap<ReplayTheSpireMod.PotionRarity, int> potionCosts = new EnumMap<ReplayTheSpireMod.PotionRarity, int>(ReplayTheSpireMod.PotionRarity.class);
	
	public static EnumMap<ReplayTheSpireMod.PotionRarity, ArrayList<String>> potionsByRarity = new EnumMap<ReplayTheSpireMod.PotionRarity, ArrayList<String>>(ReplayTheSpireMod.PotionRarity.class);
	
	public static boolean renderFishFG = false;
	public static boolean renderForestBG = false;
	public static TextureAtlas fishAtlas;
	public static TextureAtlas.AtlasRegion fishFG;
	public static Texture forestBG;
	public static Texture shieldingIcon;
	public static Texture bonfireIcon;
	public static Texture bonfireBG;
	public static Texture portalIcon;
	public static Texture portalBG;
	public static Texture multitaskButton;
	public static Texture mineButton;
	public static Texture polymerizeButton;
	public static Texture exploreButton;
	public static Texture rebottleButton;
	public static int playerShielding = 0;
	public static ArrayList<Integer> monsterShielding = new ArrayList<Integer>();
	public static boolean noSkipRewardsRoom;
	public static boolean useBakuSkeleton = true;
	
	public static int shieldingAmount(AbstractCreature creature) {
		if (creature == null) {
			return 0;
		}
		if (creature instanceof AbstractPlayer) {
			return ReplayTheSpireMod.playerShielding;
		}
		for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size(); i++) {
			if (AbstractDungeon.getMonsters().monsters.get(i) != null && AbstractDungeon.getMonsters().monsters.get(i) == creature) {
				while (ReplayTheSpireMod.monsterShielding.size() <= i) {
					ReplayTheSpireMod.monsterShielding.add(0);
				}
				return ReplayTheSpireMod.monsterShielding.get(i);
			}
		}
		return 0;
	}
	public static void addShielding(AbstractCreature creature, int amt) {
		if (creature == null) {
			return;
		}
		if (creature instanceof AbstractPlayer) {
			ReplayTheSpireMod.playerShielding += amt;
			return;
		}
		for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size(); i++) {
			if (AbstractDungeon.getMonsters().monsters.get(i) != null && AbstractDungeon.getMonsters().monsters.get(i) == creature) {
				while (ReplayTheSpireMod.monsterShielding.size() <= i) {
					ReplayTheSpireMod.monsterShielding.add(0);
				}
				ReplayTheSpireMod.monsterShielding.set(i, ReplayTheSpireMod.monsterShielding.get(i) + amt);
			}
		}
		
	}
	public static void clearShielding(AbstractCreature creature) {
		if (creature == null) {
			return;
		}
		if (creature instanceof AbstractPlayer) {
			ReplayTheSpireMod.playerShielding = 0;
			return;
		}
		for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size(); i++) {
			if (AbstractDungeon.getMonsters().monsters.get(i) != null && AbstractDungeon.getMonsters().monsters.get(i) == creature) {
				while (ReplayTheSpireMod.monsterShielding.size() <= i) {
					ReplayTheSpireMod.monsterShielding.add(0);
					return;
				}
				ReplayTheSpireMod.monsterShielding.set(i, 0);
				return;
			}
		}
		
	}
	public static void clearShielding() {
		ReplayTheSpireMod.playerShielding = 0;
		ReplayTheSpireMod.monsterShielding = new ArrayList<Integer>();
	}
	
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
		BaseMod.subscribe(this);
	}

	//just stole this code from blank lol
    public static boolean checkForMod(final String classPath) {
        try {
            Class.forName(classPath);
            ReplayTheSpireMod.logger.info("Found mod: " + classPath);
            return true;
        }
        catch (ClassNotFoundException | NoClassDefFoundError ex) {
        	ReplayTheSpireMod.logger.info("Could not find mod: " + classPath);
            return false;
        }
    }
	public static void initialize() {
    	logger.info("========================= ReplayTheSpireMod INIT =========================");
		
		ReplayTheSpireMod.initAchievementUnlocks();
		
		@SuppressWarnings("unused")
		ReplayTheSpireMod replayMod = new ReplayTheSpireMod();
		
		
		ReplayTheSpireMod.forestBG = ImageMaster.loadImage("images/monsters/fadingForest/fadingForest_bg.png");
		ReplayTheSpireMod.shieldingIcon = ImageMaster.loadImage("images/ui/replay/shielding.png");
		ReplayTheSpireMod.bonfireIcon = ImageMaster.loadImage("images/ui/map/replay_bonfire.png");
		ReplayTheSpireMod.bonfireBG = ImageMaster.loadImage("images/ui/map/replay_bonfireOutline.png");
		ReplayTheSpireMod.portalIcon = ImageMaster.loadImage("images/ui/map/replay_portal.png");
		ReplayTheSpireMod.portalBG = ImageMaster.loadImage("images/ui/map/replay_portalOutline.png");
		ReplayTheSpireMod.mineButton = ImageMaster.loadImage("images/ui/campfire/replay/mine.png");
		ReplayTheSpireMod.polymerizeButton = ImageMaster.loadImage("images/ui/campfire/replay/polymerize.png");
		ReplayTheSpireMod.multitaskButton = ImageMaster.loadImage("images/ui/campfire/replay/multitask.png");
		ReplayTheSpireMod.exploreButton = ImageMaster.loadImage("images/ui/campfire/replay/explore.png");
		
		//Loader.isModLoaded("beakedthecultist-sts")
		foundmod_science = checkForMod("madsciencemod.MadScienceMod");
	    foundmod_seeker = checkForMod("fruitymod.FruityMod");
	    foundmod_servant = checkForMod("blackrusemod.BlackRuseMod");
	    foundmod_fetch = checkForMod("fetch.FetchMod");
	    foundmod_infinite = checkForMod("infinitespire.InfiniteSpire");
	    foundmod_colormap = checkForMod("coloredmap.ColoredMap");
	    foundmod_hubris = checkForMod("com.evacipated.cardcrawl.mod.hubris.HubrisMod");
	    foundmod_stslib = checkForMod("com.evacipated.cardcrawl.mod.stslib.StSLib");
	    foundmod_mystic = checkForMod("mysticmod.MysticMod");
	    foundmod_beaked = checkForMod("beaked.Beaked");
	    foundmod_deciple = checkForMod("chronomuncher.ChronoMod");
	    foundmod_construct = Loader.isModLoaded("constructmod");
		
		logger.info("================================================================");
    }
	
	public static void initializeRelicInteractions() {
		ReplayTheSpireMod.relicInteractionStrings.put(Bandana.ID, new HashMap<String, String>());
		relicInteractionStrings.get(Bandana.ID).put(BloodyIdol.ID, "While active, #bBloody #bIdol gives #yShielding instead of HP.");
		ReplayTheSpireMod.relicInteractionStrings.put(BloodyIdol.ID, new HashMap<String, String>());
		relicInteractionStrings.get(BloodyIdol.ID).put(Bandana.ID, "While #bBandana is active, gives #yShielding instead of HP.");
		ReplayTheSpireMod.relicInteractionStrings.put(ChameleonRing.ID, new HashMap<String, String>());
		relicInteractionStrings.get(ChameleonRing.ID).put(Sozu.ID, "#yBrewing only gives #b1 potion, but bypasses the effect of #bSozu.");
		ReplayTheSpireMod.relicInteractionStrings.put(Sozu.ID, new HashMap<String, String>());
		relicInteractionStrings.get(Sozu.ID).put(ChameleonRing.ID, "Will not block potions gained from #yBrewing with #bChameleon #Ring, but reduces the number of potions gained to #b1.");
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
		ReplayTheSpireMod.addPotionToSet(
			VenomPotion.class,
			Color.OLIVE.cpy(),
			null,
			Color.CHARTREUSE.cpy(),
			"Venom Potion",
			ReplayTheSpireMod.PotionRarity.UNCOMMON
		);
		ReplayTheSpireMod.addPotionToSet(
				ReflectiveCoating.class,
				Color.LIGHT_GRAY.cpy(),
				Color.WHITE.cpy(),
				null,
				ReflectiveCoating.POTION_ID,
				ReplayTheSpireMod.PotionRarity.UNCOMMON
			);
		ReplayTheSpireMod.addPotionToSet(
				LanguidPotion.class,
				Color.DARK_GRAY.cpy(),
				null,
				null,
				LanguidPotion.POTION_ID,
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
		if (foundmod_stslib) {
			ReplayTheSpireMod.addPotionToSet(
					LifebloodPotion.class,
					Color.BLUE.cpy(),
					null,
					null,
					LifebloodPotion.POTION_ID,
					ReplayTheSpireMod.PotionRarity.UNCOMMON
				);
			ReplayTheSpireMod.addPotionToSet(
				FlashbangPotion.class,
				Color.YELLOW.cpy(),
				null,
				null,
				"Flashbang",
				ReplayTheSpireMod.PotionRarity.RARE
			);
		} else {
			ReplayTheSpireMod.addPotionToSet(
					ShieldPotion.class,
					Color.ROYAL.cpy(),
					null,
					null,
					ShieldPotion.POTION_ID,
					ReplayTheSpireMod.PotionRarity.UNCOMMON
				);
		}
		logger.info("end editting potions");
	}
	
	
	public int currentSettingsTab;
	public int currentSettingsSubTab;
	public static ModPanel settingsPanel;
	
	public ModToggleButton chaos_button_1;
	public ModToggleButton chaos_button_2;
	public ModToggleButton chaos_button_3;
	public ModToggleButton chaos_button_4;
	public static final ReplayIntSliderSetting SETTING_TAG_NORMAL_CHANCE = new ReplayIntSliderSetting("Tag_Chance_Normal", "Normal Sale Tag Chance", 3, 1, 5);
	public static final ReplayIntSliderSetting SETTING_TAG_SPECIAL_CHANCE = new ReplayIntSliderSetting("Tag_Chance_Special", "Special Edition Tag Chance", 1, 0, 5);
	public static final ReplayIntSliderSetting SETTING_TAG_DOUBLE_CHANCE = new ReplayIntSliderSetting("Tag_Chance_Double", "2 For 1 Tag Chance", 1, 0, 5);
	public static final ReplayIntSliderSetting SETTING_ROOMS_BONFIRE = new ReplayIntSliderSetting("Bonfire_Chance", "Bonfire Chance", 100, 0, 100, "%");
	public static final ReplayIntSliderSetting SETTING_ROOMS_PORTAL = new ReplayIntSliderSetting("Portal_Chance", "Portal Chance", 66, 0, 100, "%");
	public static final ReplayBooleanSetting SETTING_REBOTTLE_V_ENABLE = new ReplayBooleanSetting("Rebottle_V_Enable", "Enabled for Vanilla's Bottles", true);
	public static final ReplayBooleanSetting SETTING_REBOTTLE_V_FREE = new ReplayBooleanSetting("Rebottle_V_Free", "Free Action for Vanilla's Bottles", false);
	public static final ReplayBooleanSetting SETTING_REBOTTLE_R_ENABLE = new ReplayBooleanSetting("Rebottle_R_Enable", "Enabled for Replay's Bottles", true);
	public static final ReplayBooleanSetting SETTING_REBOTTLE_R_FREE = new ReplayBooleanSetting("Rebottle_R_Free", "Free Action for Replay's Bottles", true);
	public static final ReplayBooleanSetting SETTING_REBOTTLE_M_ENABLE = new ReplayBooleanSetting("Rebottle_M_Enable", "Enabled for Other Mods' Bottles", true);
	public static final ReplayBooleanSetting SETTING_REBOTTLE_M_FREE = new ReplayBooleanSetting("Rebottle_M_Free", "Free Action for Other Mods' Bottles", false);
	
	public static HashMap<String, ReplayRelicSetting> ConfigSettings = new HashMap<String, ReplayRelicSetting>();
	public static HashMap<ReplayAbstractRelic, ArrayList<ReplayRelicSetting>> RelicSettings = new HashMap<ReplayAbstractRelic, ArrayList<ReplayRelicSetting>>();
	static final float setting_start_x = 350.0f;
	static final float setting_start_y = 550.0f;
	public static void BuildSettings(ReplayAbstractRelic relic) {
		ReplayTheSpireMod.RelicSettings.put(relic, relic.BuildRelicSettings());
		for (ReplayRelicSetting setting : ReplayTheSpireMod.RelicSettings.get(relic)) {
			ReplayTheSpireMod.ConfigSettings.put(setting.settingsId, setting);
		}
	}
	public static RelicSettingsButton BuildSettingsButton(ReplayAbstractRelic relic) {
		ArrayList<IUIElement> settingElements = new ArrayList<IUIElement>();
		ArrayList<ReplayRelicSetting> settings = new ArrayList<ReplayRelicSetting>();
		float x = setting_start_x;
		float y = setting_start_y;
		for (String s : relic.GetSettingStrings()) {
			settingElements.add(new ModLabel(s, x + ((y == setting_start_y) ? 150.0f : 0.0f), y, settingsPanel, (me) -> {}));
			y -= 40.0f;
		}
		for (ReplayRelicSetting setting : ReplayTheSpireMod.RelicSettings.get(relic)) {
			settings.add(setting);
			settingElements.addAll(setting.GenerateElements(x, y));
			y -= setting.elementHeight;
		}
		return new RelicSettingsButton(relic, settingElements, settings);
	}
	
	private ModToggleButton makeLabeledButton(final ArrayList<IUIElement> settingElements, final String labelText, final float xPos, final float yPos, final Color color, final BitmapFont font, final boolean enabled, final ModPanel p, final Consumer<ModLabel> labelUpdate, final Consumer<ModToggleButton> c) {
		settingElements.add(new ModLabel(labelText, xPos + 40.0f, yPos + 8.0f, color, font, p, labelUpdate));
		ModToggleButton b = new ModToggleButton(xPos, yPos, enabled, false, p, c);
		settingElements.add(b);
		return b;
	}

	static SpireConfig config;
	@Override
    public void receivePostInitialize() {
		ReplayTheSpireMod.powerAtlas = new com.badlogic.gdx.graphics.g2d.TextureAtlas(Gdx.files.internal("powers/replayPowers.atlas"));

		ReplayTheSpireMod.forestBG = ImageMaster.loadImage("images/monsters/fadingForest/fadingForest_bg.png");
		ReplayTheSpireMod.shieldingIcon = ImageMaster.loadImage("images/ui/replay/shielding.png");
		ReplayTheSpireMod.bonfireIcon = ImageMaster.loadImage("images/ui/map/replay_bonfire.png");
		ReplayTheSpireMod.bonfireBG = ImageMaster.loadImage("images/ui/map/replay_bonfireOutline.png");
		ReplayTheSpireMod.portalIcon = ImageMaster.loadImage("images/ui/map/replay_portal.png");
		ReplayTheSpireMod.portalBG = ImageMaster.loadImage("images/ui/map/replay_portalOutline.png");
		ReplayTheSpireMod.mineButton = ImageMaster.loadImage("images/ui/campfire/replay/mine.png");
		ReplayTheSpireMod.polymerizeButton = ImageMaster.loadImage("images/ui/campfire/replay/polymerize.png");
		ReplayTheSpireMod.multitaskButton = ImageMaster.loadImage("images/ui/campfire/replay/multitask.png");
		ReplayTheSpireMod.exploreButton = ImageMaster.loadImage("images/ui/campfire/replay/explore.png");
		ReplayTheSpireMod.rebottleButton = ImageMaster.loadImage("images/ui/campfire/replay/rebottle.png");
		ReplayTheSpireMod.renderFishFG = false;
		ReplayTheSpireMod.fishAtlas = new TextureAtlas(Gdx.files.internal("images/replayScenes/fishfight.atlas"));
		ReplayTheSpireMod.fishFG = ReplayTheSpireMod.fishAtlas.findRegion("mod/fg");
        // Mod badge
		try {
			config = new SpireConfig("ReplayTheSpireMod", "replaySettingsData");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Texture badgeTexture = new Texture(BADGE_IMG);
		this.currentSettingsSubTab = 0;
        settingsPanel = new ModPanel();
		final List<RelicSettingsButton> settingsButtons = new ArrayList<RelicSettingsButton>();
		ArrayList<IUIElement> settingElements = new ArrayList<IUIElement>();
		
		
		settingElements.add(new ModLabel("Custom Room Settings", setting_start_x + 150.0f, setting_start_y, settingsPanel, (me) -> {}));
		settingElements.addAll(SETTING_ROOMS_BONFIRE.GenerateElements(setting_start_x, setting_start_y + 50.0f));
		settingElements.addAll(SETTING_ROOMS_PORTAL.GenerateElements(setting_start_x, setting_start_y + 100.0f));
		settingsButtons.add(new RelicSettingsButton(ImageMaster.loadImage("images/ui/map/replay_bonfire.png"), ImageMaster.loadImage("images/ui/map/replay_bonfire.png"), RelicSettingsButton.DEFAULT_X, RelicSettingsButton.DEFAULT_Y, RelicSettingsButton.DEFAULT_W, RelicSettingsButton.DEFAULT_H, settingElements));
		ReplayTheSpireMod.ConfigSettings.put(SETTING_ROOMS_BONFIRE.settingsId, SETTING_ROOMS_BONFIRE);
		ReplayTheSpireMod.ConfigSettings.put(SETTING_ROOMS_PORTAL.settingsId, SETTING_ROOMS_PORTAL);
		//settingElements = new ArrayList<IUIElement>();
		
		BuildSettings(new RingOfChaos());
		BuildSettings(new Ninjato());
		BuildSettings(new TagBag());
		BuildSettings(new HoneyJar());
		BuildSettings(new BargainBundle());
		//BuildSettings(new EnergyBall());
		
		loadSettingsData();
		/*
		settingsButtons.add(new RelicSettingsButton(new RingOfChaos(), settingElements));
		settingElements = new ArrayList<IUIElement>();
		*/
		for (int s = 0; s <= 5; s++) {
			for (ReplayAbstractRelic relic : RelicSettings.keySet()) {
				if (relic.SettingsPriorety == s) {
					settingsButtons.add(BuildSettingsButton(relic));
				}
			}
		}
		
		
		final Pagination pager = new Pagination(new ImageButton("img/replay/tinyRightArrow.png", 615, 550, 100, 100, b -> {}), new ImageButton("img/replay/tinyLeftArrow.png", 350, 550, 100, 100, b -> {}), 2, 3, 50, 50, settingsButtons);
        settingsPanel.addUIElement((IUIElement)pager);
		
		
		logger.info("badge");
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        
		logger.info("Events");
		BaseMod.addEvent(MirrorMist.ID, MirrorMist.class, "Exordium");
		BaseMod.addEvent(Stuck.ID, Stuck.class, "Exordium");
		BaseMod.addEvent(ReplayMapScoutEvent.ID, ReplayMapScoutEvent.class, "TheCity");
		BaseMod.addEvent(TrappedChest.ID, TrappedChest.class);
		BaseMod.addEvent(ChaosEvent.ID, ChaosEvent.class);
		
		
		if (foundmod_infinite) {
			infinitebs.NeowEventNonsense();
			logger.info("Replay | Registering Quests");
			infinitebs.registerQuests();
		}
		if (checkForMod("com.evacipated.cardcrawl.mod.hubris.events.thebeyond.TheBottler")) {
            TheBottler.addBottleRelic(BottledFlurry.ID);
            TheBottler.addBottleRelic(BottledSteam.ID);
        }
		InitializeMonsters();
		initializePotions();
		logger.info("end post init");
        Settings.isDailyRun = false;
        Settings.isTrial = false;
        Settings.isDemo = false;
		
		ReplayTheSpireMod.receiveEditUnlocks();
		
    }
	

    public void receiveEditKeywords() {
        final Gson gson = new Gson();
        final String json = Gdx.files.internal("localization/ReplayKeywords.json").readString(String.valueOf(StandardCharsets.UTF_8));
        final Keyword[] keywords = (Keyword[])gson.fromJson(json, (Class)Keyword[].class);
        if (keywords != null) {
            for (final Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }
	
	@Override
	public void receiveEditRelics() {
		logger.info("begin editting relics");
		ReplayTheSpireMod.receiveEditUnlocks();

		if (ReplayTheSpireMod.foundmod_stslib) {
			//if StSLib is installed, use Temporary HP instead of Shielding for certain relics.
			BaseMod.addRelic(new Durian_stslib(), RelicType.SHARED);
			//Hubris adds a relic that does the same thing as Divine Protection, so don't bother adding Divine Protection if Hubris is installed.
			if (!ReplayTheSpireMod.foundmod_hubris) {
				BaseMod.addRelic(new DivineProtection_stslib(), RelicType.SHARED);
			}
		} else {
			BaseMod.addRelic(new Durian(), RelicType.SHARED);
			BaseMod.addRelic(new DivineProtection(), RelicType.SHARED);
		}
        // Add relics
		RelicLibrary.addBlue(new AncientBracer());
		RelicLibrary.addBlue(new SolarPanel());
		RelicLibrary.addBlue(new Carrot());
		RelicLibrary.addBlue(new Geode());
		RelicLibrary.addBlue(new RaidersMask());
		BaseMod.addRelic(new Arrowhead(), RelicType.SHARED);
		BaseMod.addRelic(new AbesTreasure(), RelicType.SHARED);
		BaseMod.addRelic(new Bandana(), RelicType.SHARED);
		BaseMod.addRelic(new BargainBundle(), RelicType.SHARED);
		BaseMod.addRelic(new Baseball(), RelicType.SHARED);
		BaseMod.addRelic(new BottledFlurry(), RelicType.SHARED);
		BaseMod.addRelic(new BottledSteam(), RelicType.SHARED);
		BaseMod.addRelic(new ByrdSkull(), RelicType.GREEN);
		BaseMod.addRelic(new ChameleonRing(), RelicType.SHARED);
		//BaseMod.addRelic(new ChemicalX(), RelicType.SHARED);
		BaseMod.addRelic(new ChewingGum(), RelicType.SHARED);
		BaseMod.addRelic(new CounterBalance(), RelicType.SHARED);
		BaseMod.addRelic(new CursedCoin(), RelicType.SHARED);
		BaseMod.addRelic(new DimensionalGlitch(), RelicType.SHARED);
		BaseMod.addRelic(new ElectricBlood(), RelicType.RED);
		//BaseMod.addRelic(new EnergyBall(), RelicType.SHARED);
		BaseMod.addRelic(new Funnel(), RelicType.SHARED);
		BaseMod.addRelic(new Garlic(), RelicType.SHARED);
		BaseMod.addRelic(new GoldenEgg(), RelicType.SHARED);
		BaseMod.addRelic(new GrabBag(), RelicType.SHARED);
		BaseMod.addRelic(new GremlinFood(), RelicType.SHARED);
		BaseMod.addRelic(new GrinningJar(), RelicType.SHARED);
		BaseMod.addRelic(new GuideBook(), RelicType.SHARED);
		BaseMod.addRelic(new HoneyJar(), RelicType.SHARED);
		BaseMod.addRelic(new IronHammer(), RelicType.SHARED);
		BaseMod.addRelic(new IronCore(), RelicType.SHARED);
		BaseMod.addRelic(new KingOfHearts(), RelicType.RED);
		BaseMod.addRelic(new Kintsugi(), RelicType.SHARED);
		BaseMod.addRelic(new LightBulb(), RelicType.SHARED);
		BaseMod.addRelic(new Mirror(), RelicType.SHARED);
		BaseMod.addRelic(new Multitool(), RelicType.SHARED);
		BaseMod.addRelic(new Ninjato(), RelicType.SHARED);
		BaseMod.addRelic(new OnionRing(), RelicType.SHARED);
		BaseMod.addRelic(new OnyxGauntlets(), RelicType.SHARED);
		BaseMod.addRelic(new OozeArmor(), RelicType.RED);
		BaseMod.addRelic(new PainkillerHerb(), RelicType.SHARED);
		BaseMod.addRelic(new PondfishScales(), RelicType.SHARED);
		BaseMod.addRelic(new PetGhost(), RelicType.SHARED);
		BaseMod.addRelic(new QuantumEgg(), RelicType.SHARED);
		BaseMod.addRelic(new RingOfChaos(), RelicType.SHARED);
		BaseMod.addRelic(new SecondSwordRelic(), RelicType.RED);
		BaseMod.addRelic(new Shallot(), RelicType.SHARED);
		BaseMod.addRelic(new SimpleRune(), RelicType.SHARED);
		BaseMod.addRelic(new SizzlingBlood(), RelicType.SHARED);
		BaseMod.addRelic(new SnackPack(), RelicType.SHARED);
		BaseMod.addRelic(new SnakeBasket(), RelicType.GREEN);
		BaseMod.addRelic(new SneckoScales(), RelicType.GREEN);
		BaseMod.addRelic(new SneckoHeart(), RelicType.SHARED);
		BaseMod.addRelic(new ReplaySpearhead(), RelicType.RED);
		BaseMod.addRelic(new TagBag(), RelicType.SHARED);
		BaseMod.addRelic(new TigerMarble(), RelicType.SHARED);
		BaseMod.addRelic(new VampiricSpirits(), RelicType.GREEN);
        
		ChaosEvent.addRing(new RingOfFury());
		ChaosEvent.addRing(new RingOfPeace());
		ChaosEvent.addRing(new ChaosEvent.RingListEntry(new RingOfFangs(), AbstractPlayer.PlayerClass.THE_SILENT, true));
		ChaosEvent.addRing(new ChaosEvent.RingListEntry(new RingOfPanic(), new String[]{SneckoEye.ID, SneckoHeart.ID}));
		ChaosEvent.addRing(new ChaosEvent.RingListEntry(new RingOfSearing(), AbstractPlayer.PlayerClass.IRONCLAD, true, new String[]{Dodecahedron.ID}));
		ChaosEvent.addRing(new ChaosEvent.RingListEntry(new RingOfShattering(), AbstractPlayer.PlayerClass.DEFECT, false));
		ChaosEvent.addRing(new RingOfHypnosis());
		ChaosEvent.addRing(new ChaosEvent.RingListEntry(new RingOfGreed(), new String[]{Ectoplasm.ID}));
		if (foundmod_stslib) {
			ChaosEvent.addRing(new RingOfMisfortune());
		}
		
		initializeCrossoverRelics();
		if (foundmod_infinite) {
			BaseMod.addRelic(new SealedPack(), RelicType.SHARED);
		}
		
        logger.info("done editting relics");
	}
	
	void AddAndUnlockCard(AbstractCard c)
	{
		BaseMod.addCard(c);
		UnlockTracker.unlockCard(c.cardID);
	}
	
	@Override
	public void receiveEditCards() {
		BaseMod.addDynamicVariable(new MagicMinusOne());
		BaseMod.addDynamicVariable(new Exhaustive());
        try {
        	initializeInfiniteMod(LoadType.CARD);
		} catch (ClassNotFoundException | NoClassDefFoundError e) {
			logger.info("Replay | Infinite not detected");
		}
		logger.info("[RtS] begin editting cards");
		logger.info("adding cards for Ironclad...");
		AddAndUnlockCard(new Abandon());
		AddAndUnlockCard(new Hemogenesis());
		AddAndUnlockCard(new LifeLink());
		AddAndUnlockCard(new LimbFromLimb());
		AddAndUnlockCard(new Massacre());
		AddAndUnlockCard(new RunThrough());
		AddAndUnlockCard(new DefyDeath());
		AddAndUnlockCard(new DemonicInfusion());
		AddAndUnlockCard(new Eclipse());
		AddAndUnlockCard(new StrikeFromHell());
		AddAndUnlockCard(new LeadingStrike());
		AddAndUnlockCard(new ReplayReversal());
		AddAndUnlockCard(new ReplayStacked());
		AddAndUnlockCard(new MuscleTraining());
		logger.info("adding cards for Silent...");
		AddAndUnlockCard(new AtomBomb());
		AddAndUnlockCard(new DrainingMist());
		AddAndUnlockCard(new FluidMovement());
		AddAndUnlockCard(new PoisonDarts());
		AddAndUnlockCard(new Necrosis());
		AddAndUnlockCard(new ToxinWave());
		AddAndUnlockCard(new HiddenBlade());
		AddAndUnlockCard(new SneakUp());
		AddAndUnlockCard(new ScrapShanks());
		AddAndUnlockCard(new TheWorks());
		AddAndUnlockCard(new FromAllSides());
		AddAndUnlockCard(new ExploitWeakness());
		AddAndUnlockCard(new ShivToss());
		AddAndUnlockCard(new SpeedTraining());
		logger.info("adding cards for Defect...");
		AddAndUnlockCard(new com.megacrit.cardcrawl.mod.replay.cards.blue.PanicButton());
		AddAndUnlockCard(new MirrorShield());
		AddAndUnlockCard(new BasicCrystalCard());
		AddAndUnlockCard(new TimeBomb());
		AddAndUnlockCard(new ReplayRepulse());
		AddAndUnlockCard(new ReplayGoodbyeWorld());
		AddAndUnlockCard(new ReplayGash());
		AddAndUnlockCard(new ReplayOmegaCannon());
		AddAndUnlockCard(new ReplayRNGCard());
		AddAndUnlockCard(new FIFOQueue());
		AddAndUnlockCard(new ReplaySort());
		AddAndUnlockCard(new SystemScan());
		AddAndUnlockCard(new SolidLightProjector());
		AddAndUnlockCard(new CalculationTraining());
		//AddAndUnlockCard(new ReflectiveLens());
		//AddAndUnlockCard(new Crystallizer());
		logger.info("adding colorless cards...");
		//AddAndUnlockCard(new Improvise());
		AddAndUnlockCard(new PoisonedStrike());
		AddAndUnlockCard(new PrivateReserves());
		AddAndUnlockCard(new Specialist());
		AddAndUnlockCard(new AwakenedRitual());
		AddAndUnlockCard(new SurveyOptions());
		AddAndUnlockCard(new ReplayUltimateDefense());
		AddAndUnlockCard(new MidasTouch());
		AddAndUnlockCard(new Trickstab());
		AddAndUnlockCard(new ReplayBrewmasterCard());
		/*if (Loader.isModLoaded("Friendly_Minions_0987678")) {
			AddAndUnlockCard(new GrembosGang());
		}*/
		logger.info("adding curses...");
		AddAndUnlockCard(new CommonCold());
		AddAndUnlockCard(new Hallucinations());
		AddAndUnlockCard(new Languid());
		AddAndUnlockCard(new Sickly());
		AddAndUnlockCard(new Delirium());
		AddAndUnlockCard(new Voices());
		AddAndUnlockCard(new LoomingEvil());
		AddAndUnlockCard(new Amnesia());
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
		if (foundmod_stslib) {
			logger.info("adding stslib cards...");
			AddAndUnlockCard(new FaultyEquipment());
			AddAndUnlockCard(new Sssssssssstrike());
			AddAndUnlockCard(new Necrogeddon());
		}
		if (foundmod_deciple) {
			chronobs.addCards();
		}
		if (foundmod_beaked) {
			logger.info("adding beaked cards...");
			beakedbs.addBeakedCards();
		}
		if (foundmod_construct) {
			constructbs.addCards();
		}
		logger.info("done editting cards");
	}
	
	private void InitializeMonsters() {
        BaseMod.addMonster("Pondfish", () -> new MonsterGroup(new AbstractMonster[] {new CaptainAbe(170.0f, -80.0f), new PondfishBoss(0.0f, -650.0f)}));
        BaseMod.addBoss("TheCity", "Pondfish", "images/ui/map/boss/pondfish.png", "images/ui/map/bossOutline/pondfish.png");
        BaseMod.addMonster("R_Hoarder", () -> new MonsterGroup(new AbstractMonster[] {new R_Hoarder(0, 10)}));
        BaseMod.addMonster("Erikyupuro", () -> new MonsterGroup(new AbstractMonster[] { new J_louse_1(-350.0f, 25.0f), new J_louse_2(-125.0f, 10.0f), new J_louse_3(80.0f, 30.0f) }));
        BaseMod.addMonster("Fading Forest", () -> new MonsterGroup(new AbstractMonster[] {new FadingForestBoss()}));
        
	}

    private enum LoadType {
    	RELIC,
    	CARD,
    	KEYWORD,
    }

    public static boolean foundmod_science = false;
    public static boolean foundmod_seeker = false;
    public static boolean foundmod_servant = false;
    public static boolean foundmod_fetch = false;
    public static boolean foundmod_infinite = false;
    public static boolean foundmod_colormap = false;
    public static boolean foundmod_hubris = false;
    public static boolean foundmod_stslib = false;
    public static boolean foundmod_mystic = false;
    public static boolean foundmod_beaked = false;
    public static boolean foundmod_deciple = false;
    public static boolean foundmod_construct = false;
    

    private static void initializeCrossoverRelics() {
    	try {
    		initializeScienceMod(LoadType.RELIC);
		} catch (ClassNotFoundException | NoClassDefFoundError e) {
			logger.info("Replay | Mad Science Mod not detected");
		}
    	try {
			initializeFruityMod(LoadType.RELIC);
		} catch (ClassNotFoundException | NoClassDefFoundError e) {
			logger.info("Replay | FruityMod not detected");
		}
    	try {
    		initializeServantMod(LoadType.RELIC);
		} catch (ClassNotFoundException | NoClassDefFoundError e) {
			logger.info("Replay | Servant mod not detected");
		}
    	try {
    		initializeMysticMod(LoadType.RELIC);
		} catch (ClassNotFoundException | NoClassDefFoundError e) {
			logger.info("Replay | Servant mod not detected");
		}
    	try {
    		initializeDecipleMod(LoadType.RELIC);
		} catch (ClassNotFoundException | NoClassDefFoundError e) {
			logger.info("Replay | Servant mod not detected");
		}
    }

	private static void initializeScienceMod(LoadType type) throws ClassNotFoundException, NoClassDefFoundError {
		//Class<MadScienceMod> madScienceMod = MadScienceMod.class;
		logger.info("ReplayTheSpireMod | Detected Mad Science Mod!");
		foundmod_science = true;
		
		if(type == LoadType.RELIC) {
			logger.info("ReplayTheSpireMod | Initializing Relics for MadScience...");
			BaseMod.addRelicToCustomPool((AbstractRelic)new ChemicalBlood(), madsciencemod.patches.CardColorEnum.MAD_SCIENCE);
		}
		if(type == LoadType.CARD) {
			logger.info("ReplayTheSpireMod | Initializing Cards for MadScience...");
		}
	}

	private static void initializeInfiniteMod() throws ClassNotFoundException, NoClassDefFoundError {
		Class<InfiniteSpire> infiniteMod = InfiniteSpire.class;
		logger.info("ReplayTheSpireMod | Detected Infinite Spire!");
		foundmod_infinite = true;
	}

	private static void initializeFetchMod() throws ClassNotFoundException, NoClassDefFoundError {
		Class<FetchMod> fetchMod = FetchMod.class;
		logger.info("ReplayTheSpireMod | Detected Fetch Mod!");
		foundmod_fetch = true;
	}
	private static void initializeColorMapMod() throws ClassNotFoundException, NoClassDefFoundError {
		Class<ColoredMap> cmMod = ColoredMap.class;
		logger.info("ReplayTheSpireMod | Detected Colored Map Mod!");
		foundmod_colormap = true;
	}
	private static void initializeServantMod() throws ClassNotFoundException, NoClassDefFoundError {
		Class<BlackRuseMod> servMod = BlackRuseMod.class;
		logger.info("ReplayTheSpireMod | Detected Servant Mod!");
		foundmod_servant = true;
	}
	private static void initializeHubrisMod() throws ClassNotFoundException, NoClassDefFoundError {
		Class<HubrisMod> servMod = HubrisMod.class;
		logger.info("ReplayTheSpireMod | Detected Hubris Mod!");
		foundmod_hubris = true;
	}
	private static void initializeStsLibMod() throws ClassNotFoundException, NoClassDefFoundError {
		if (foundmod_stslib) {
			return;
		}
		Class<StSLib> servMod = StSLib.class;
		logger.info("ReplayTheSpireMod | Detected StSLib Mod!");
		foundmod_stslib = true;
	}
	private static void initializeStsLibMod(LoadType type) throws ClassNotFoundException, NoClassDefFoundError {
		Class<StSLib> servMod = StSLib.class;
		if (!foundmod_stslib) {
			logger.info("ReplayTheSpireMod | Detected StSLib Mod!");
			foundmod_stslib = true;
		}
		
	}
	private static void initializeInfiniteMod(LoadType type) throws ClassNotFoundException, NoClassDefFoundError {
		Class<InfiniteSpire> infiniteMod = InfiniteSpire.class;
		logger.info("ReplayTheSpireMod | Detected Infinite Spire!");
		foundmod_infinite = true;
		if(type == LoadType.RELIC) {
			logger.info("ReplayTheSpireMod | Initializing Relics for Infinite...");
		}
		if(type == LoadType.CARD) {
			logger.info("ReplayTheSpireMod | Initializing Cards for Infinite...");
			BaseMod.addColor(infinitespire.patches.CardColorEnumPatch.CardColorPatch.INFINITE_BLACK, InfiniteSpire.CARD_COLOR, InfiniteSpire.CARD_COLOR, InfiniteSpire.CARD_COLOR, InfiniteSpire.CARD_COLOR, InfiniteSpire.CARD_COLOR, Color.BLACK.cpy(), InfiniteSpire.CARD_COLOR, "img/infinitespire/cards/ui/512/boss-attack.png", "img/infinitespire/cards/ui/512/boss-skill.png", "img/infinitespire/cards/ui/512/boss-power.png", "img/infinitespire/cards/ui/512/boss-orb.png", "img/infinitespire/cards/ui/1024/boss-attack.png", "img/infinitespire/cards/ui/1024/boss-skill.png", "img/infinitespire/cards/ui/1024/boss-power.png", "img/infinitespire/cards/ui/1024/boss-orb.png");
			infinitebs.BlackCards();
		}
	}
	private static void initializeMysticMod(LoadType type) throws ClassNotFoundException, NoClassDefFoundError {
		Class<MysticMod> servMod = MysticMod.class;
		logger.info("ReplayTheSpireMod | Detected Mystic Mod!");
		foundmod_mystic = true;
		if(type == LoadType.RELIC) {
			logger.info("ReplayTheSpireMod | Initializing Relics for Mystic...");
			BaseMod.addRelicToCustomPool(new m_BookOfShivs(), mysticmod.patches.AbstractCardEnum.MYSTIC_PURPLE);
		}
		if(type == LoadType.CARD) {
			logger.info("ReplayTheSpireMod | Initializing Cards for Mystic...");
		}
	}
	private static void initializeServantMod(LoadType type) throws ClassNotFoundException, NoClassDefFoundError {
		Class<BlackRuseMod> servMod = BlackRuseMod.class;
		logger.info("ReplayTheSpireMod | Detected Servant Mod!");
		foundmod_servant = true;

		if(type == LoadType.RELIC) {
			logger.info("ReplayTheSpireMod | Initializing Relics for Servant...");
			BaseMod.addRelicToCustomPool(new m_SnakeCloak(), blackrusemod.patches.AbstractCardEnum.SILVER);
		}
		if(type == LoadType.CARD) {
			logger.info("ReplayTheSpireMod | Initializing Cards for Servant...");
		}
	}
	private static void initializeDecipleMod(LoadType type) throws ClassNotFoundException, NoClassDefFoundError {
		Class<ChronoMod> servMod = ChronoMod.class;
		logger.info("ReplayTheSpireMod | Detected Deciple Mod!");
		foundmod_deciple = true;

		if(type == LoadType.RELIC) {
			logger.info("ReplayTheSpireMod | Initializing Relics for Deciple...");
			BaseMod.addRelicToCustomPool(new m_MercuryCore(), chronomuncher.patches.Enum.CHRONO_GOLD);
		}
		if(type == LoadType.CARD) {
			logger.info("ReplayTheSpireMod | Initializing Cards for Deciple...");
		}
	}

	private static void initializeFruityMod(LoadType type)throws ClassNotFoundException, NoClassDefFoundError {
		Class<FruityMod> fruityMod = FruityMod.class;
		logger.info("ReplayTheSpireMod | Detected FruityMod!");
		foundmod_seeker = true;
		if(type == LoadType.RELIC) {
			logger.info("ReplayTheSpireMod | Initializing Relics for FruityMod...");
			BaseMod.addRelicToCustomPool(new m_ArcaneBlood(), AbstractCardEnum.SEEKER_PURPLE);
		}
		if(type == LoadType.CARD) {
			logger.info("ReplayTheSpireMod | Initializing Cards for FruityMod...");
		}
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
        try {
        	initializeFetchMod();
		} catch (ClassNotFoundException | NoClassDefFoundError e) {
			logger.info("Replay | Fetch not detected");
		}
        try {
        	initializeStsLibMod();
		} catch (ClassNotFoundException | NoClassDefFoundError e) {
			logger.info("Replay | Fetch not detected");
		}
        try {
        	initializeColorMapMod();
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
			logger.info("Replay | Colored Map not detected");
		}
        if (foundmod_fetch || foundmod_stslib) {
        	String cardStrings = Gdx.files.internal(jsonPath + "ReplayFetchOverrideStrings.json").readString(
            		String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        }
		if (Settings.language.toString().equals("SPA")) {
			logger.info("Spanish detected!");
			jsonPath = "localization/spa/";
			editStringsByLang(jsonPath);
		}
		
		logger.info("done editting strings");
	}
	
	public void receivePostDraw(AbstractCard c) {
		if (AbstractDungeon.player.hasPower("ReplayChaosPower") && AbstractDungeon.player.getPower("ReplayChaosPower").amount > 0) {
			if (RingOfChaos.ChaosScrambleCard(c)) {
				AbstractDungeon.player.getPower("ReplayChaosPower").amount--;
				AbstractDungeon.player.getPower("ReplayChaosPower").updateDescription();
			}
		}
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
		if (AbstractDungeon.player.hasPower(TheWorksPower.POWER_ID)) {
			AbstractDungeon.player.getPower(TheWorksPower.POWER_ID).onSpecificTrigger();
		}
		if (AbstractDungeon.player.hasPower(RidThyselfOfStatusCardsPower.POWER_ID) && c.type == AbstractCard.CardType.STATUS) {
			AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
			AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
			AbstractDungeon.player.getPower(RidThyselfOfStatusCardsPower.POWER_ID).onSpecificTrigger();
		}
	}
	
	public void receivePotionGet(final AbstractPotion p0) {
		if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Chameleon Ring")) {
			switch(p0.ID) {
				case "AttackPotion":
					p0.description = "Add a random Upgraded Attack card to your hand, it costs #b0 this turn.";
					p0.tips = new ArrayList<PowerTip>();
					p0.tips.add(new PowerTip(p0.name, p0.description));
					return;
				case "SkillPotion":
					p0.description = "Add a random Upgraded Skill card to your hand, it costs #b0 this turn.";
					p0.tips = new ArrayList<PowerTip>();
					p0.tips.add(new PowerTip(p0.name, p0.description));
					return;
				case "PowerPotion":
					p0.description = "Add a random Upgraded Power card to your hand, it costs #b0 this turn.";
					p0.tips = new ArrayList<PowerTip>();
					p0.tips.add(new PowerTip(p0.name, p0.description));
					return;
				default:
					return;
			}
		}
	}
	
	public static final String DEFAULTSETTINGSUFFIX = "_IS_SET_TO_DEFAULT";
	public static void saveSettingsData() {
		try {
			SpireConfig config = new SpireConfig("ReplayTheSpireMod", "replaySettingsData");
			for (String key : ConfigSettings.keySet()) {
				ReplayRelicSetting setting = ConfigSettings.get(key);
				setting.SaveToData(config);
			}
			config.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    public static void loadSettingsData() {
    	logger.info("ReplayTheSpireMod | Loading Data...");
    	try {
			config.load();
			for (String key : ConfigSettings.keySet()) {
				ReplayRelicSetting setting = ConfigSettings.get(key);
				if (!config.has(setting.settingsId)) {
					config.setString(setting.settingsId, setting.defaultProperty);
					config.setBool(setting.settingsId + DEFAULTSETTINGSUFFIX, true);
				}
				else if (!config.has(setting.settingsId + DEFAULTSETTINGSUFFIX)) {
					config.setBool(setting.settingsId + DEFAULTSETTINGSUFFIX, false);
				}
				setting.LoadFromData(config);
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
	
	public static void loadRunData() {
        ReplayTheSpireMod.logger.info("Loading Save Data");
        try {
            final SpireConfig config = new SpireConfig("ReplayTheSpireMod", "SaveData");
            BottledFlurry.load(config);
            BottledSteam.load(config);
            Baseball.load(config);
            ReplayMapScoutEvent.load(config);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void saveRunData() {
    	ReplayTheSpireMod.logger.info("Saving Data");
        try {
            final SpireConfig config = new SpireConfig("ReplayTheSpireMod", "SaveData");
            BottledFlurry.save(config);
            BottledSteam.save(config);
            Baseball.save(config);
            ReplayMapScoutEvent.save(config);
            config.save();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void clearRunData() {
    	ReplayTheSpireMod.logger.info("Clearing Saved Data");
        try {
            final SpireConfig config = new SpireConfig("ReplayTheSpireMod", "SaveData");
            config.clear();
            config.save();
            BottledFlurry.clear();
            BottledSteam.clear();
            Baseball.clear();
            ReplayMapScoutEvent.clear();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void receiveStartGame() {
		ReplayMapScoutEvent.bannedBoss = "none";
        loadRunData();
    }
}