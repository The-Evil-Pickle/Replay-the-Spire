package ReplayTheSpireMod;

import java.nio.charset.StandardCharsets;

import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.green.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.*;
import basemod.helpers.*;
import basemod.interfaces.*;

import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.EnumMap;
//SetUnlocksSubscriber, 

@SpireInitializer
public class ReplayTheSpireMod implements PostInitializeSubscriber,
EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber
{
	public static final Logger logger = LogManager.getLogger(ReplayTheSpireMod.class.getName());
	
	private static final String MODNAME = "ReplayTheSpireMod";
    private static final String AUTHOR = "AstroPenguin642, The_Evil_Pickle, Stewart";
    private static final String DESCRIPTION = "Content expansion mod";
	
	public static final String BADGE_IMG = "img/ModBadge.png";
	
	protected static int commonPotionChance = 9;
	protected static int uncommonPotionChance = 7;
	protected static int rarePotionChance = 4;
	protected static int ultraPotionChance = 1;
	protected static int shopPotionChance = 10;
	
	public static EnumMap<ReplayTheSpireMod.PotionRarity, ArrayList<String>> potionsByRarity = new EnumMap<ReplayTheSpireMod.PotionRarity, ArrayList<String>>(ReplayTheSpireMod.PotionRarity.class);
	
	public static enum PotionRarity
	{
		COMMON,  UNCOMMON,  RARE,  ULTRA, SPECIAL,  SHOP;
		
		private PotionRarity() {}
	}
	
	
	public static ReplayTheSpireMod.PotionRarity returnRandomPotionTier(Random rng)
	{
		int tmpShopChance = 0;
		int tmpUltraChance = ultraPotionChance;
		if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP) {
			tmpShopChance = shopPotionChance;
			tmpUltraChance *= 4;
		}
		int roll = rng.random(0, commonPotionChance + uncommonPotionChance + rarePotionChance + tmpUltraChance + tmpShopChance - 1);
		if (roll < commonPotionChance) {
		  return ReplayTheSpireMod.PotionRarity.COMMON;
		}
		if (roll < commonPotionChance + uncommonPotionChance) {
		  return ReplayTheSpireMod.PotionRarity.UNCOMMON;
		}
		if (roll < commonPotionChance + uncommonPotionChance + rarePotionChance) {
		  return ReplayTheSpireMod.PotionRarity.RARE;
		}
		if (roll < commonPotionChance + uncommonPotionChance + rarePotionChance + tmpShopChance) {
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
		//String randomKey = (String)potions.get(rng.random.nextInt(potions.size()));
		return getRandomPotion(rng, returnRandomPotionTier(rng));
	}
	
	public static AbstractPotion getRandomPotion(Random rng, ReplayTheSpireMod.PotionRarity rarity)
	{
		String randomKey = (String)potionsByRarity.get(rarity).get(AbstractDungeon.potionRng.random.nextInt(potionsByRarity.get(rarity).size()));
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
		
		commonPotionChance = 9;
		uncommonPotionChance = 7;
		rarePotionChance = 4;
		ultraPotionChance = 1;
		shopPotionChance = 10;
		
		
		
		logger.info("================================================================");
    }
	
	public static void addPotionToSet(final Class potionClass, final Color liquidColor, final Color hybridColor, final Color spotsColor, final String potionID, final ReplayTheSpireMod.PotionRarity potionRarity) {
		BaseMod.addPotion(potionClass, liquidColor, hybridColor, spotsColor, potionID);
		ReplayTheSpireMod.potionsByRarity.get(potionRarity).add(potionID);
		
	}
	
	public static void initializePotions() {
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
		
		ReplayTheSpireMod.addPotionToSet(
			HealthPotion.class,
			Color.CHARTREUSE.cpy(),
			null,
			null,
			"Health Potion",
			ReplayTheSpireMod.PotionRarity.SHOP
		);
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
		
	}
	
	@Override
    public void receivePostInitialize() {
        // Mod badge
        Texture badgeTexture = new Texture(BADGE_IMG);
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addLabel("Settings menu W.I.P.", 400.0f, 700.0f, (me) -> {});
		
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        
		
        final String[] necroNames = { "necrotic", "necrotic poison" };
        BaseMod.addKeyword(necroNames, "A powerful poison that deals 2 damage each turn.");
		
        Settings.isDailyRun = false;
        Settings.isTrial = false;
        Settings.isDemo = false;
    }
	
	@Override
	public void receiveEditRelics() {
		logger.info("begin editting relics");
        
        // Add relics
		BaseMod.addRelic(new AncientBracer(), RelicType.SHARED);
		BaseMod.addRelic(new Arrowhead(), RelicType.SHARED);
		BaseMod.addRelic(new Bandana(), RelicType.SHARED);
		BaseMod.addRelic(new Baseball(), RelicType.SHARED);
		BaseMod.addRelic(new DivineProtection(), RelicType.SHARED);
		BaseMod.addRelic(new ElectricBlood(), RelicType.RED);
		BaseMod.addRelic(new Funnel(), RelicType.SHARED);
		BaseMod.addRelic(new Garlic(), RelicType.SHARED);
		BaseMod.addRelic(new GremlinFood(), RelicType.SHARED);
		BaseMod.addRelic(new GuideBook(), RelicType.SHARED);
		BaseMod.addRelic(new IronHammer(), RelicType.SHARED);
		BaseMod.addRelic(new KingOfHearts(), RelicType.RED);
		BaseMod.addRelic(new Mirror(), RelicType.SHARED);
		BaseMod.addRelic(new OnionRing(), RelicType.SHARED);
		BaseMod.addRelic(new OozeArmor(), RelicType.SHARED);
		BaseMod.addRelic(new PetGhost(), RelicType.SHARED);
		BaseMod.addRelic(new RingOfChaos(), RelicType.SHARED);
		BaseMod.addRelic(new RingOfFury(), RelicType.SHARED);
		BaseMod.addRelic(new SimpleRune(), RelicType.SHARED);
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
		//AddAndUnlockCard(new PerfectedStrike());
		logger.info("adding cards for Silent...");
		AddAndUnlockCard(new AtomBomb());
		AddAndUnlockCard(new ToxinWave());
		AddAndUnlockCard(new FluidMovement());
		AddAndUnlockCard(new PoisonDarts());
		AddAndUnlockCard(new ToxinWave());
		AddAndUnlockCard(new HiddenBlade());
		logger.info("adding colorless cards...");
		AddAndUnlockCard(new GhostDefend());
		AddAndUnlockCard(new GhostSwipe());
		AddAndUnlockCard(new Improvise());
		AddAndUnlockCard(new PoisonedStrike());
		logger.info("adding curses...");
		AddAndUnlockCard(new Hallucinations());
		AddAndUnlockCard(new Languid());
		AddAndUnlockCard(new Sickly());
		AddAndUnlockCard(new Delirium());
		AddAndUnlockCard(new Voices());
		AddAndUnlockCard(new LoomingEvil());
		
		
		
		logger.info("done editting cards");
	}
	
	
	@Override
	public void receiveEditStrings() {
		logger.info("begin editting strings");
		
        // RelicStrings
        String relicStrings = Gdx.files.internal("localization/ReplayRelicStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        // CardStrings
        String cardStrings = Gdx.files.internal("localization/ReplayCardStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        // PowerStrings
        String powerStrings = Gdx.files.internal("localization/ReplayPowerStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        // EventStrings
        String eventStrings = Gdx.files.internal("localization/ReplayEventStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
        // PotionStrings
        String potionStrings = Gdx.files.internal("localization/ReplayPotionStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
		
		logger.info("done editting strings");
	}
	
	
	
	
}