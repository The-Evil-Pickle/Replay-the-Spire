package ReplayTheSpireMod;

import java.nio.charset.StandardCharsets;

import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.green.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
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

import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.*;
import java.util.*;
//SetUnlocksSubscriber, 

@SpireInitializer
public class ReplayTheSpireMod implements PostInitializeSubscriber,
EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, PostDrawSubscriber
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
	
	private static final String MODNAME = "ReplayTheSpireMod";
    private static final String AUTHOR = "AstroPenguin642, The_Evil_Pickle, Stewart";
    private static final String DESCRIPTION = "Content expansion mod";
	
	public static final String BADGE_IMG = "img/ModBadge.png";
	
	protected static int commonPotionChance = 9;
	protected static int uncommonPotionChance = 7;
	protected static int rarePotionChance = 4;
	protected static int ultraPotionChance = 1;
	protected static int shopPotionChance = 12;
	
	//public static EnumMap<ReplayTheSpireMod.PotionRarity, int> potionCosts = new EnumMap<ReplayTheSpireMod.PotionRarity, int>(ReplayTheSpireMod.PotionRarity.class);
	
	public static EnumMap<ReplayTheSpireMod.PotionRarity, ArrayList<String>> potionsByRarity = new EnumMap<ReplayTheSpireMod.PotionRarity, ArrayList<String>>(ReplayTheSpireMod.PotionRarity.class);
	
	public static enum PotionRarity
	{
		COMMON,  UNCOMMON,  RARE,  ULTRA, SPECIAL,  SHOP;
		
		private PotionRarity() {}
	}
	
	public static int GetPotionCost(AbstractPotion potion) 
	{
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
		return 50;
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
		potions.add("Health Potion");
		potions.add("Elixir");
		potions.add("Adrenaline Potion");
		potions.add("Death Potion");
		potions.add("Ironskin Potion");
		potions.add("Thorns Potion");
		potions.add("Toxic Potion");
		potions.add("Venom Potion");
		potions.add("Doom Potion");
		potions.add("Spirit Potion");
		*/
		
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
		);
		*/
		ReplayTheSpireMod.addPotionToSet(
			ElixirPotion.class,
			Color.GOLD.cpy(),
			null,
			Color.DARK_GRAY.cpy(),
			"Elixir",
			ReplayTheSpireMod.PotionRarity.SHOP
		);
		ReplayTheSpireMod.addPotionToSet(
			SpiritPotion.class,
			Color.GOLD.cpy(),
			Color.CHARTREUSE.cpy(),
			null,
			"Spirit Potion",
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
			ReplayTheSpireMod.PotionRarity.UNCOMMON
		);
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
		ReplayTheSpireMod.addPotionToSet(
			InspirationPotion.class,
			Color.LIGHT_GRAY.cpy(),
			null,
			Color.SKY.cpy(),
			"Inspiration Potion",
			ReplayTheSpireMod.PotionRarity.RARE
		);
		
		logger.info("end editting potions");
	}
	
	@Override
    public void receivePostInitialize() {
		logger.info("begin editting json");
		commonPotionChance = 9;
		uncommonPotionChance = 7;
		rarePotionChance = 4;
		ultraPotionChance = 1;
		shopPotionChance = 12;
		
        // Mod badge
        Texture badgeTexture = new Texture(BADGE_IMG);
        ModPanel settingsPanel = new ModPanel();
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
		
		
		logger.info("badge");
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        
		
		logger.info("keywords");
        final String[] necroNames = { "necrotic", "necrotic poison" };
        BaseMod.addKeyword(necroNames, "A powerful poison that deals 2 damage each turn, but doesn't last as long.");
		final String[] refundNames = { "refund", "refunds"};
		BaseMod.addKeyword(refundNames, "Returns all energy spent on playing the card.");
		
		
		logger.info("end post init");
        Settings.isDailyRun = false;
        Settings.isTrial = false;
        Settings.isDemo = false;
    }
	
	@Override
	public void receiveEditRelics() {
		logger.info("begin editting relics");
        
        // Add relics
		BaseMod.addRelic(new AncientBracer(), RelicType.GREEN);
		BaseMod.addRelic(new Arrowhead(), RelicType.SHARED);
		BaseMod.addRelic(new Bandana(), RelicType.SHARED);
		BaseMod.addRelic(new Baseball(), RelicType.SHARED);
		BaseMod.addRelic(new ChameleonRing(), RelicType.SHARED);
		BaseMod.addRelic(new ChemicalX(), RelicType.SHARED);
		BaseMod.addRelic(new DivineProtection(), RelicType.SHARED);
		BaseMod.addRelic(new ElectricBlood(), RelicType.RED);
		BaseMod.addRelic(new Funnel(), RelicType.SHARED);
		BaseMod.addRelic(new Garlic(), RelicType.SHARED);
		BaseMod.addRelic(new GremlinFood(), RelicType.SHARED);
		BaseMod.addRelic(new GrinningJar(), RelicType.SHARED);
		BaseMod.addRelic(new GuideBook(), RelicType.SHARED);
		BaseMod.addRelic(new HoneyJar(), RelicType.SHARED);
		BaseMod.addRelic(new IronHammer(), RelicType.SHARED);
		BaseMod.addRelic(new KingOfHearts(), RelicType.RED);
		//BaseMod.addRelic(new Mirror(), RelicType.SHARED);
		BaseMod.addRelic(new OnionRing(), RelicType.SHARED);
		BaseMod.addRelic(new OozeArmor(), RelicType.SHARED);
		BaseMod.addRelic(new PainkillerHerb(), RelicType.SHARED);
		BaseMod.addRelic(new PetGhost(), RelicType.SHARED);
		BaseMod.addRelic(new RingOfChaos(), RelicType.SHARED);
		BaseMod.addRelic(new RingOfFury(), RelicType.SHARED);
		BaseMod.addRelic(new SimpleRune(), RelicType.SHARED);
		BaseMod.addRelic(new SizzlingBlood(), RelicType.SHARED);
		BaseMod.addRelic(new SnackPack(), RelicType.SHARED);
		BaseMod.addRelic(new SneckoScales(), RelicType.GREEN);
        
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
		logger.info("adding cards for Silent...");
		AddAndUnlockCard(new AtomBomb());
		AddAndUnlockCard(new ToxinWave());
		AddAndUnlockCard(new FluidMovement());
		AddAndUnlockCard(new PoisonDarts());
		AddAndUnlockCard(new ToxinWave());
		AddAndUnlockCard(new HiddenBlade());
		AddAndUnlockCard(new SneakUp());
		logger.info("adding colorless cards...");
		AddAndUnlockCard(new Improvise());
		AddAndUnlockCard(new PoisonedStrike());
		AddAndUnlockCard(new Specialist());
		logger.info("adding curses...");
		AddAndUnlockCard(new Hallucinations());
		//AddAndUnlockCard(new Languid());
		AddAndUnlockCard(new Sickly());
		AddAndUnlockCard(new Delirium());
		AddAndUnlockCard(new Voices());
		AddAndUnlockCard(new LoomingEvil());
		logger.info("adding unobtainable cards...");
		AddAndUnlockCard(new PotOfGreed());
		AddAndUnlockCard(new GhostDefend());
		AddAndUnlockCard(new GhostSwipe());
		AddAndUnlockCard(new GhostFetch());
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
	
	@Override
	public void receiveEditStrings() {
		logger.info("begin editting strings");
		
		String jsonPath = "localization/";
		if (Settings.language.toString().equals("SPA")) {
			logger.info("Spanish detected!");
			jsonPath = "localization/spa/";
		}
		
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
        // PotionStrings
        String potionStrings = Gdx.files.internal(jsonPath + "ReplayPotionStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
        // UIStrings
        String uiStrings = Gdx.files.internal(jsonPath + "ReplayUIStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
		
		logger.info("done editting strings");
	}
	
	public void receivePostDraw(AbstractCard c) {
		if (AbstractDungeon.player.hasPower("TPH_Confusion") && c.cost > -1 && c.color != AbstractCard.CardColor.CURSE && c.type != AbstractCard.CardType.STATUS) {
			c.setCostForTurn(AbstractDungeon.cardRandomRng.random(3));
		}
	}
	
	
}