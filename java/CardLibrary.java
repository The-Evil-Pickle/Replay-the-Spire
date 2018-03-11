package com.megacrit.cardcrawl.helpers;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.characters.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.random.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import com.megacrit.cardcrawl.metrics.*;
import com.megacrit.cardcrawl.cards.status.*;
import org.apache.logging.log4j.*;

public class CardLibrary
{
    private static final Logger logger;
    public static int totalCardCount;
    public static HashMap<String, AbstractCard> cards;
    private static HashMap<String, AbstractCard> curses;
    public static int redCards;
    public static int greenCards;
    public static int blueCards;
    public static int colorlessCards;
    public static int curseCards;
    public static int seenRedCards;
    public static int seenGreenCards;
    public static int seenBlueCards;
    public static int seenColorlessCards;
    public static int seenCurseCards;
    
    public static void initialize() {
        final long startTime = System.currentTimeMillis();
        addRedCards();
        addGreenCards();
        addColorlessCards();
        addCurseCards();
        CardLibrary.logger.info("Card load time: " + (System.currentTimeMillis() - startTime) + "ms with " + CardLibrary.cards.size() + " cards");
        if (Settings.isDev) {
            CardLibrary.logger.info(CardLibrary.redCards + "/75 (Red)");
            CardLibrary.logger.info(CardLibrary.greenCards + "/75 (Green)");
            CardLibrary.logger.info(CardLibrary.blueCards + "/75 (Blue)");
            CardLibrary.logger.info(CardLibrary.colorlessCards + "/20 (Colorless)");
            CardLibrary.logger.info(CardLibrary.curseCards + "/10 (Curse)");
            CardLibrary.logger.info("3/3 (Status)");
        }
    }
    
    private static void addRedCards() {
        add(new Abandon());
        add(new Anger());
        add(new Armaments());
        add(new Barricade());
        add(new Bash());
        add(new BattleTrance());
        add(new Berserk());
        add(new BloodForBlood());
        add(new Bloodletting());
        add(new Bludgeon());
        add(new BodySlam());
        add(new Brutality());
        add(new BurningPact());
        add(new Carnage());
        add(new Clash());
        add(new Cleave());
        add(new Clothesline());
        add(new Combust());
        add(new Corruption());
        add(new DarkEmbrace());
        add(new Defend_Red());
        add(new DemonForm());
        add(new Disarm());
        add(new DoubleTap());
        add(new Dropkick());
        add(new DualWield());
        add(new Entrench());
        add(new Evolve());
        add(new Exhume());
        add(new Feed());
        add(new FeelNoPain());
        add(new FiendFire());
        add(new FireBreathing());
        add(new FlameBarrier());
        add(new Flex());
        add(new GhostlyArmor());
        add(new Havoc());
        add(new Headbutt());
        add(new HeavyBlade());
        add(new Hemokinesis());
        add(new Immolate());
        add(new Impervious());
        add(new InfernalBlade());
        add(new Inflame());
        add(new Intimidate());
        add(new IronWave());
        add(new Juggernaut());
        add(new LifeLink());
        add(new LimitBreak());
		add(new Massacre());
        add(new Metallicize());
        add(new Offering());
        add(new PerfectedStrike());
        add(new PommelStrike());
        add(new PowerThrough());
        add(new Pummel());
        add(new Rage());
        add(new Rampage());
        add(new Reaper());
        add(new RecklessCharge());
        add(new Rupture());
        add(new SearingBlow());
        add(new SecondWind());
        add(new SeeingRed());
        add(new Sentinel());
        add(new SeverSoul());
        add(new Shockwave());
        add(new ShrugItOff());
        add(new SpotWeakness());
        add(new Strike_Red());
        add(new SwordBoomerang());
        add(new ThunderClap());
        add(new TrueGrit());
        add(new TwinStrike());
        add(new Uppercut());
        add(new Warcry());
        add(new Whirlwind());
        add(new WildStrike());
    }
    
    private static void addGreenCards() {
        add(new Accuracy());
        add(new Acrobatics());
        add(new Adrenaline());
        add(new AfterImage());
        add(new Alchemize());
        add(new AllOutAttack());
        add(new AThousandCuts());
        add(new AtomBomb());
        add(new Backflip());
        add(new Backstab());
        add(new Bane());
        add(new BladeDance());
        add(new Blur());
        add(new BouncingFlask());
        add(new BulletTime());
        add(new Burst());
        add(new CalculatedGamble());
        add(new Caltrops());
        add(new Catalyst());
        add(new Choke());
        add(new CloakAndDagger());
        add(new Concentrate());
        add(new CorpseExplosion());
        add(new CripplingPoison());
        add(new DaggerSpray());
        add(new DaggerThrow());
        add(new Dash());
        add(new DeadlyPoison());
        add(new Defend_Green());
        add(new Deflect());
        add(new DieDieDie());
        add(new Distraction());
        add(new DodgeAndRoll());
        add(new Doppelganger());
        add(new EndlessAgony());
        add(new Envenom());
        add(new EscapePlan());
        add(new Eviscerate());
        add(new Expertise());
        add(new Finisher());
        add(new Flechettes());
        add(new FlyingKnee());
        add(new Footwork());
        add(new GlassKnife());
        add(new GrandFinale());
        add(new HeelHook());
        add(new InfiniteBlades());
        add(new LegSweep());
        add(new Malaise());
        add(new MasterfulStab());
        add(new Neutralize());
        add(new Nightmare());
        add(new NoxiousFumes());
        add(new Outmaneuver());
        add(new PhantasmalKiller());
        add(new PiercingWail());
        add(new PoisonedStab());
        add(new PoisonDarts());
        add(new Predator());
        add(new Prepared());
        add(new QuickSlash());
        add(new Reflex());
        add(new RiddleWithHoles());
        add(new Setup());
        add(new Skewer());
        add(new Slice());
        add(new StormOfSteel());
        add(new Strike_Green());
        add(new SuckerPunch());
        add(new Survivor());
        add(new Tactician());
        add(new Terror());
        add(new ToolsOfTheTrade());
        add(new UnderhandedStrike());
        add(new Unload());
        add(new WellLaidPlans());
        add(new WraithForm());
    }
    
    private static void addColorlessCards() {
        add(new Apotheosis());
        add(new BandageUp());
        add(new Bite());
        add(new Blind());
        add(new DarkShackles());
        add(new DeepBreath());
        add(new DramaticEntrance());
        add(new Enlightenment());
        add(new Finesse());
        add(new FlashOfSteel());
        add(new GoodInstincts());
		add(new Improvise());
        add(new JackOfAllTrades());
        add(new JAX());
        add(new Madness());
        add(new MasterOfStrategy());
        add(new MindBlast());
        add(new Panacea());
        add(new Panache());
        add(new Purity());
        add(new SadisticNature());
        add(new SecretTechnique());
        add(new SecretWeapon());
        add(new Shiv());
        add(new SwiftStrike());
        add(new ThinkingAhead());
        add(new Transmutation());
        add(new Trip());
    }
    
    private static void addCurseCards() {
        add(new Clumsy());
        add(new Decay());
        add(new Doubt());
        add(new Hallucinations());
        add(new Injury());
        //add(new LoomingEvil());
        add(new Necronomicurse());
        add(new Normality());
        add(new Pain());
        add(new Parasite());
        add(new Regret());
        add(new Writhe());
    }
    
    private static void removeNonFinalizedCards() {
        final ArrayList<String> toRemove = new ArrayList<String>();
        for (final Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
            if (c.getValue().assetURL == null) {
                toRemove.add(c.getKey());
            }
        }
        for (final String s : toRemove) {
            CardLibrary.logger.info("Removing Card " + s + " for trailer build.");
            CardLibrary.cards.remove(s);
        }
        toRemove.clear();
        for (final Map.Entry<String, AbstractCard> c : CardLibrary.curses.entrySet()) {
            if (c.getValue().assetURL == null) {
                toRemove.add(c.getKey());
            }
        }
        for (final String s : toRemove) {
            CardLibrary.logger.info("Removing Curse " + s + " for trailer build.");
            CardLibrary.curses.remove(s);
        }
    }
    
    public static void unlockAndSeeAllCards() {
        for (final String s : UnlockTracker.lockedCards) {
            UnlockTracker.hardUnlockOverride(s);
        }
        for (final Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
            if (c.getValue().rarity != AbstractCard.CardRarity.BASIC && !UnlockTracker.isCardSeen(c.getKey())) {
                UnlockTracker.markCardAsSeen(c.getKey());
            }
        }
        for (final Map.Entry<String, AbstractCard> c : CardLibrary.curses.entrySet()) {
            if (!UnlockTracker.isCardSeen(c.getKey())) {
                UnlockTracker.markCardAsSeen(c.getKey());
            }
        }
    }
    
    public static void add(final AbstractCard card) {
        switch (card.color) {
            case RED: {
                ++CardLibrary.redCards;
                if (UnlockTracker.isCardSeen(card.cardID)) {
                    ++CardLibrary.seenRedCards;
                    break;
                }
                break;
            }
            case GREEN: {
                ++CardLibrary.greenCards;
                if (UnlockTracker.isCardSeen(card.cardID)) {
                    ++CardLibrary.seenGreenCards;
                    break;
                }
                break;
            }
            case BLUE: {
                ++CardLibrary.blueCards;
                if (UnlockTracker.isCardSeen(card.cardID)) {
                    ++CardLibrary.seenBlueCards;
                    break;
                }
                break;
            }
            case COLORLESS: {
                ++CardLibrary.colorlessCards;
                if (UnlockTracker.isCardSeen(card.cardID)) {
                    ++CardLibrary.seenColorlessCards;
                    break;
                }
                break;
            }
            case CURSE: {
                ++CardLibrary.curseCards;
                if (UnlockTracker.isCardSeen(card.cardID)) {
                    ++CardLibrary.seenCurseCards;
                }
                CardLibrary.curses.put(card.cardID, card);
                break;
            }
        }
        if (!UnlockTracker.isCardSeen(card.cardID)) {
            card.isSeen = false;
        }
        CardLibrary.cards.put(card.cardID, card);
        card.initializeDescription();
        if (card.color != AbstractCard.CardColor.BLUE) {
            ++CardLibrary.totalCardCount;
        }
    }
    
    public static AbstractCard getCopy(final String key, final int upgradeTime) {
        final AbstractCard source = getCard(key);
        AbstractCard retVal = null;
        if (source == null) {
            retVal = getCard("Madness").makeCopy();
        }
        else {
            retVal = getCard(key).makeCopy();
        }
        for (int i = 0; i < upgradeTime; ++i) {
            retVal.upgrade();
        }
        return retVal;
    }
    
    public static AbstractCard getCopy(final String key) {
        return getCard(key).makeCopy();
    }
    
    public static AbstractCard getCard(final AbstractPlayer.PlayerClass plyrClass, final String key) {
        return CardLibrary.cards.get(key);
    }
    
    public static AbstractCard getCard(final String key) {
        return CardLibrary.cards.get(key);
    }
    
    public static AbstractCard getCurse(final int pool) {
        if (pool < 1 || pool > 2) {
            CardLibrary.logger.info("INCORRECT POOL FOR GET CURSE");
        }
        final ArrayList<String> tmp = new ArrayList<String>();
        for (final Map.Entry<String, AbstractCard> c : CardLibrary.curses.entrySet()) {
            if (c.getValue().pool == pool) {
                tmp.add(c.getKey());
            }
        }
        return CardLibrary.cards.get(tmp.get(MathUtils.random(0, tmp.size() - 1)));
    }
    
    public static AbstractCard getColorSpecificCard(final AbstractCard.CardColor color, final Random rand) {
        final ArrayList<String> tmp = new ArrayList<String>();
        switch (color) {
            case RED: {
                for (final Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
                    if (c.getValue().color == AbstractCard.CardColor.RED) {
                        tmp.add(c.getKey());
                    }
                }
                break;
            }
            case GREEN: {
                for (final Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
                    if (c.getValue().color == AbstractCard.CardColor.GREEN) {
                        tmp.add(c.getKey());
                    }
                }
                break;
            }
        }
        return CardLibrary.cards.get(tmp.get(rand.random(0, tmp.size() - 1)));
    }
    
    public static AbstractCard getCurse(final AbstractCard prohibitedCard, final Random rng) {
        final ArrayList<String> tmp = new ArrayList<String>();
        for (final Map.Entry<String, AbstractCard> c : CardLibrary.curses.entrySet()) {
            if (!Objects.equals(c.getValue().cardID, prohibitedCard.cardID) && !Objects.equals(c.getValue().cardID, "Necronomicurse")) {
                tmp.add(c.getKey());
            }
        }
        return CardLibrary.cards.get(tmp.get(rng.random(0, tmp.size() - 1)));
    }
    
    public static AbstractCard getCurse(final AbstractCard prohibitedCard) {
        return getCurse(prohibitedCard, new Random());
    }
    
    public static void uploadCardData() {
        final ArrayList<String> data = new ArrayList<String>();
        for (final Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
            data.add(c.getValue().debugDetailedCardDataString());
            final AbstractCard c2 = c.getValue().makeCopy();
            if (c2.canUpgrade()) {
                c2.upgrade();
                data.add(c2.debugDetailedCardDataString());
            }
        }
        final LeaderboardPoster poster = new LeaderboardPoster();
        poster.setValues(LeaderboardPoster.LeaderboardDataType.CARD_DATA, "", AbstractCard.debugDetailedCardDataHeader(), data, false);
        final Thread t = new Thread(poster);
        t.run();
    }
    
    public static ArrayList<AbstractCard> getAllCards() {
        final ArrayList<AbstractCard> retVal = new ArrayList<AbstractCard>();
        for (final Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
            retVal.add(c.getValue());
        }
        return retVal;
    }
    
    public static ArrayList<AbstractCard> getCardList(final LibraryType type) {
        final ArrayList<AbstractCard> retVal = new ArrayList<AbstractCard>();
        switch (type) {
            case COLORLESS: {
                for (final Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
                    if (c.getValue().color == AbstractCard.CardColor.COLORLESS) {
                        retVal.add(c.getValue());
                    }
                }
                break;
            }
            case CURSE: {
                for (final Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
                    if (c.getValue().color == AbstractCard.CardColor.CURSE) {
                        retVal.add(c.getValue());
                    }
                }
                break;
            }
            case RED: {
                for (final Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
                    if (c.getValue().color == AbstractCard.CardColor.RED) {
                        retVal.add(c.getValue());
                    }
                }
                break;
            }
            case GREEN: {
                for (final Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
                    if (c.getValue().color == AbstractCard.CardColor.GREEN) {
                        retVal.add(c.getValue());
                    }
                }
                break;
            }
            case BLUE: {
                for (final Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
                    if (c.getValue().color == AbstractCard.CardColor.BLUE) {
                        retVal.add(c.getValue());
                    }
                }
                break;
            }
            case STATUS: {
                retVal.add(new Wound());
                retVal.add(new Burn());
                retVal.add(new Dazed());
                break;
            }
        }
        return retVal;
    }
    
    static {
        logger = LogManager.getLogger(CardLibrary.class.getName());
        CardLibrary.totalCardCount = 0;
        CardLibrary.cards = new HashMap<String, AbstractCard>();
        CardLibrary.curses = new HashMap<String, AbstractCard>();
        CardLibrary.redCards = 0;
        CardLibrary.greenCards = 0;
        CardLibrary.blueCards = 0;
        CardLibrary.colorlessCards = 0;
        CardLibrary.curseCards = 0;
        CardLibrary.seenRedCards = 0;
        CardLibrary.seenGreenCards = 0;
        CardLibrary.seenBlueCards = 0;
        CardLibrary.seenColorlessCards = 0;
        CardLibrary.seenCurseCards = 0;
    }
    
    public enum LibraryType
    {
        RED, 
        GREEN, 
        BLUE, 
        CURSE, 
        STATUS, 
        COLORLESS;
    }
}
