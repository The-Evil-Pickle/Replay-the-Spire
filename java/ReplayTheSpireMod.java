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
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.*;
import basemod.helpers.*;
import basemod.interfaces.*;

import com.megacrit.cardcrawl.relics.*;
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
	
	public ReplayTheSpireMod() {
		logger.info("subscribing to postInitialize event");
        BaseMod.subscribeToPostInitialize(this);
		
		logger.info("subscribing to editRelics event");
        BaseMod.subscribeToEditRelics(this);
        
        logger.info("subscribing to editCards event");
        BaseMod.subscribeToEditCards(this);
		
		logger.info("subscribing to editStrings event");
        BaseMod.subscribeToEditStrings(this);
        
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
		BaseMod.addRelic(new IronHammer(), RelicType.SHARED);
		BaseMod.addRelic(new KingOfHearts(), RelicType.RED);
		BaseMod.addRelic(new Mirror(), RelicType.SHARED);
		BaseMod.addRelic(new OnionRing(), RelicType.SHARED);
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
		logger.info("begin editting cards");
		
		logger.info("adding cards for Ironclad...");
		AddAndUnlockCard(new Abandon());
		AddAndUnlockCard(new LifeLink());
		AddAndUnlockCard(new Massacre());
		//AddAndUnlockCard(new PerfectedStrike());
		logger.info("adding cards for Silent...");
		AddAndUnlockCard(new AtomBomb());
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
		
		logger.info("done editting strings");
	}
	
	
	
	
}