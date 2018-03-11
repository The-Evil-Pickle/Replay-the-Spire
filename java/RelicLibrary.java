package com.megacrit.cardcrawl.helpers;

import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.metrics.LeaderboardPoster;
import com.megacrit.cardcrawl.metrics.LeaderboardPoster.LeaderboardDataType;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.relics.AncientBracer;
import com.megacrit.cardcrawl.relics.Anchor;
import com.megacrit.cardcrawl.relics.Arrowhead;
import com.megacrit.cardcrawl.relics.AncientTeaSet;
import com.megacrit.cardcrawl.relics.ArtOfWar;
import com.megacrit.cardcrawl.relics.Astrolabe;
import com.megacrit.cardcrawl.relics.BagOfMarbles;
import com.megacrit.cardcrawl.relics.BagOfPreparation;
import com.megacrit.cardcrawl.relics.Bandana;
import com.megacrit.cardcrawl.relics.Baseball;
import com.megacrit.cardcrawl.relics.BirdFacedUrn;
import com.megacrit.cardcrawl.relics.BlackBlood;
import com.megacrit.cardcrawl.relics.BlackStar;
import com.megacrit.cardcrawl.relics.BloodVial;
import com.megacrit.cardcrawl.relics.BloodyIdol;
import com.megacrit.cardcrawl.relics.BlueCandle;
import com.megacrit.cardcrawl.relics.BottledFlame;
import com.megacrit.cardcrawl.relics.BottledLightning;
import com.megacrit.cardcrawl.relics.BottledTornado;
import com.megacrit.cardcrawl.relics.BronzeScales;
import com.megacrit.cardcrawl.relics.BurningBlood;
import com.megacrit.cardcrawl.relics.Calipers;
import com.megacrit.cardcrawl.relics.CallingBell;
import com.megacrit.cardcrawl.relics.Cauldron;
import com.megacrit.cardcrawl.relics.CentennialPuzzle;
import com.megacrit.cardcrawl.relics.ChampionsBelt;
import com.megacrit.cardcrawl.relics.CharonsAshes;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.Courier;
import com.megacrit.cardcrawl.relics.CursedKey;
import com.megacrit.cardcrawl.relics.DarkstonePeriapt;
import com.megacrit.cardcrawl.relics.DeadBranch;
import com.megacrit.cardcrawl.relics.DivineProtection;
import com.megacrit.cardcrawl.relics.Dodecahedron;
import com.megacrit.cardcrawl.relics.DreamCatcher;
import com.megacrit.cardcrawl.relics.DuVuDoll;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.relics.ElectricBlood;
import com.megacrit.cardcrawl.relics.Enchiridion;
import com.megacrit.cardcrawl.relics.EternalFeather;
import com.megacrit.cardcrawl.relics.FrozenEgg2;
import com.megacrit.cardcrawl.relics.FrozenEye;
import com.megacrit.cardcrawl.relics.Funnel;
import com.megacrit.cardcrawl.relics.GamblingChip;
import com.megacrit.cardcrawl.relics.Garlic;
import com.megacrit.cardcrawl.relics.Ginger;
import com.megacrit.cardcrawl.relics.Girya;
import com.megacrit.cardcrawl.relics.GoldenIdol;
import com.megacrit.cardcrawl.relics.GremlinHorn;
import com.megacrit.cardcrawl.relics.HappyFlower;
import com.megacrit.cardcrawl.relics.IceCream;
import com.megacrit.cardcrawl.relics.IronHammer;
import com.megacrit.cardcrawl.relics.JuzuBracelet;
import com.megacrit.cardcrawl.relics.Kunai;
import com.megacrit.cardcrawl.relics.Lantern;
import com.megacrit.cardcrawl.relics.LetterOpener;
import com.megacrit.cardcrawl.relics.LizardTail;
import com.megacrit.cardcrawl.relics.MagicFlower;
import com.megacrit.cardcrawl.relics.Mango;
import com.megacrit.cardcrawl.relics.MarkOfPain;
import com.megacrit.cardcrawl.relics.Matryoshka;
import com.megacrit.cardcrawl.relics.MeatOnTheBone;
import com.megacrit.cardcrawl.relics.MedicalKit;
import com.megacrit.cardcrawl.relics.MembershipCard;
import com.megacrit.cardcrawl.relics.MercuryHourglass;
import com.megacrit.cardcrawl.relics.Mirror;
import com.megacrit.cardcrawl.relics.MoltenEgg2;
import com.megacrit.cardcrawl.relics.MummifiedHand;
import com.megacrit.cardcrawl.relics.Necronomicon;
import com.megacrit.cardcrawl.relics.NeowsLament;
import com.megacrit.cardcrawl.relics.NilrysCodex;
import com.megacrit.cardcrawl.relics.NinjaScroll;
import com.megacrit.cardcrawl.relics.NlothsGift;
import com.megacrit.cardcrawl.relics.OddMushroom;
import com.megacrit.cardcrawl.relics.OddlySmoothStone;
import com.megacrit.cardcrawl.relics.OldCoin;
import com.megacrit.cardcrawl.relics.Omamori;
import com.megacrit.cardcrawl.relics.OnionRing;
import com.megacrit.cardcrawl.relics.Orichalcum;
import com.megacrit.cardcrawl.relics.OrnamentalFan;
import com.megacrit.cardcrawl.relics.Orrery;
import com.megacrit.cardcrawl.relics.PandorasBox;
import com.megacrit.cardcrawl.relics.Pantograph;
import com.megacrit.cardcrawl.relics.PaperCrane;
import com.megacrit.cardcrawl.relics.PaperFrog;
import com.megacrit.cardcrawl.relics.PeacePipe;
import com.megacrit.cardcrawl.relics.Pear;
import com.megacrit.cardcrawl.relics.PenNib;
import com.megacrit.cardcrawl.relics.PhilosopherStone;
import com.megacrit.cardcrawl.relics.PrayerWheel;
import com.megacrit.cardcrawl.relics.QuestionCard;
import com.megacrit.cardcrawl.relics.RedMask;
import com.megacrit.cardcrawl.relics.RedSkull;
import com.megacrit.cardcrawl.relics.RegalPillow;
import com.megacrit.cardcrawl.relics.RunicCube;
import com.megacrit.cardcrawl.relics.RunicDome;
import com.megacrit.cardcrawl.relics.RunicPyramid;
import com.megacrit.cardcrawl.relics.SelfFormingClay;
import com.megacrit.cardcrawl.relics.Shovel;
import com.megacrit.cardcrawl.relics.Shuriken;
import com.megacrit.cardcrawl.relics.SimpleRune;
import com.megacrit.cardcrawl.relics.SingingBowl;
import com.megacrit.cardcrawl.relics.SmilingMask;
import com.megacrit.cardcrawl.relics.SnackPack;
import com.megacrit.cardcrawl.relics.SnakeRing;
import com.megacrit.cardcrawl.relics.SneckoEye;
import com.megacrit.cardcrawl.relics.SneckoScales;
import com.megacrit.cardcrawl.relics.SneckoSkull;
import com.megacrit.cardcrawl.relics.Sozu;
import com.megacrit.cardcrawl.relics.SpiritPoop;
import com.megacrit.cardcrawl.relics.StrangeSpoon;
import com.megacrit.cardcrawl.relics.Strawberry;
import com.megacrit.cardcrawl.relics.Sundial;
import com.megacrit.cardcrawl.relics.TheSpecimen;
import com.megacrit.cardcrawl.relics.ThreadAndNeedle;
import com.megacrit.cardcrawl.relics.Tingsha;
import com.megacrit.cardcrawl.relics.TinyChest;
import com.megacrit.cardcrawl.relics.TinyHouse;
import com.megacrit.cardcrawl.relics.Toolbox;
import com.megacrit.cardcrawl.relics.Torii;
import com.megacrit.cardcrawl.relics.ToughBandages;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import com.megacrit.cardcrawl.relics.ToyOrnithopter;
import com.megacrit.cardcrawl.relics.UnceasingTop;
import com.megacrit.cardcrawl.relics.Vajra;
import com.megacrit.cardcrawl.relics.VelvetChoker;
import com.megacrit.cardcrawl.relics.Waffle;
import com.megacrit.cardcrawl.relics.WarPaint;
import com.megacrit.cardcrawl.relics.Whetstone;
import com.megacrit.cardcrawl.relics.WhiteBeast;
import com.megacrit.cardcrawl.relics.deprecated.DerpRock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RelicLibrary
{
  private static final Logger logger = LogManager.getLogger(RelicLibrary.class.getName());
  public static int totalRelicCount = 0;
  public static int seenRelics = 0;
  private static HashMap<String, AbstractRelic> sharedRelics = new HashMap();
  private static HashMap<String, AbstractRelic> redRelics = new HashMap();
  private static HashMap<String, AbstractRelic> greenRelics = new HashMap();
  private static HashMap<String, AbstractRelic> blueRelics = new HashMap();
  public static ArrayList<AbstractRelic> starterList = new ArrayList();
  public static ArrayList<AbstractRelic> commonList = new ArrayList();
  public static ArrayList<AbstractRelic> uncommonList = new ArrayList();
  public static ArrayList<AbstractRelic> rareList = new ArrayList();
  public static ArrayList<AbstractRelic> bossList = new ArrayList();
  public static ArrayList<AbstractRelic> specialList = new ArrayList();
  public static ArrayList<AbstractRelic> shopList = new ArrayList();
  public static ArrayList<AbstractRelic> redList = new ArrayList();
  public static ArrayList<AbstractRelic> greenList = new ArrayList();
  public static ArrayList<AbstractRelic> blueList = new ArrayList();
  
  public static void initialize()
  {
    long startTime = System.currentTimeMillis();
    
	add(new AncientBracer());
    add(new Anchor());
    add(new Arrowhead());
    add(new ArtOfWar());
    add(new Astrolabe());
    add(new BagOfMarbles());
    add(new BagOfPreparation());
    add(new Bandana());
    add(new Baseball());
    add(new BirdFacedUrn());
    add(new BlackStar());
    add(new BloodVial());
    add(new BloodyIdol());
    add(new BlueCandle());
    add(new BottledFlame());
    add(new BottledLightning());
    add(new BottledTornado());
    add(new BronzeScales());
    add(new Calipers());
    add(new CallingBell());
    add(new Cauldron());
    add(new CentennialPuzzle());
    add(new Courier());
    add(new CursedKey(null));
    add(new DarkstonePeriapt());
    add(new DeadBranch());
    add(new DivineProtection());
    add(new Dodecahedron(null));
    add(new DreamCatcher());
    add(new DuVuDoll());
    add(new Ectoplasm());
    add(new Enchiridion());
    add(new EternalFeather());
    add(new FrozenEgg2());
    add(new FrozenEye());
    add(new Funnel());
    add(new GamblingChip());
    add(new Garlic());
    add(new Ginger());
    add(new Girya());
    add(new GoldenIdol());
    add(new GremlinHorn(null));
    add(new HappyFlower());
    add(new IceCream());
    add(new IronHammer());
    add(new JuzuBracelet());
    add(new Kunai());
    add(new Lantern());
    add(new LetterOpener());
    add(new LizardTail());
    add(new Mango());
    add(new Matryoshka());
    add(new MeatOnTheBone());
    add(new MedicalKit());
    add(new MembershipCard());
    add(new MercuryHourglass());
    add(new Mirror());
    add(new MoltenEgg2());
    add(new MummifiedHand());
    add(new NeowsLament());
    add(new Necronomicon());
    add(new NilrysCodex());
    add(new NlothsGift());
    add(new OddlySmoothStone());
    add(new OddMushroom());
    add(new OldCoin());
    add(new Omamori());
    add(new OnionRing());
    add(new Orichalcum());
    add(new OrnamentalFan());
    add(new Orrery());
    add(new PandorasBox());
    add(new Pantograph());
    add(new PeacePipe());
    add(new Pear());
    add(new PenNib());
    add(new PhilosopherStone());
    add(new PrayerWheel());
    add(new QuestionCard());
    add(new RedMask());
    add(new RegalPillow());
    add(new RunicDome(null));
    add(new RunicPyramid());
    add(new Shovel());
    add(new Shuriken());
    add(new SimpleRune());
    add(new SingingBowl());
    add(new SmilingMask());
    add(new SnackPack());
    add(new SneckoEye());
    add(new Sozu());
    add(new SpiritPoop());
    add(new StrangeSpoon());
    add(new Strawberry());
    add(new Sundial());
    add(new AncientTeaSet());
    add(new ThreadAndNeedle());
    add(new TinyChest());
    add(new TinyHouse());
    add(new Toolbox());
    add(new Torii());
    add(new ToxicEgg2());
    add(new ToyOrnithopter());
    add(new UnceasingTop());
    add(new Vajra());
    add(new VelvetChoker());
    add(new Waffle());
    add(new WarPaint());
    add(new Whetstone());
    add(new WhiteBeast());
    
    addGreen(new NinjaScroll());
    addGreen(new PaperCrane());
    addGreen(new SnakeRing());
    addGreen(new SneckoSkull());
    addGreen(new SneckoScales());
    addGreen(new TheSpecimen());
    addGreen(new Tingsha());
    addGreen(new ToughBandages());
    
    addRed(new BlackBlood());
    addRed(new BurningBlood());
    addRed(new ChampionsBelt());
    addRed(new CharonsAshes());
	addRed(new ElectricBlood());
    addRed(new MagicFlower());
    addRed(new MarkOfPain());
    addRed(new PaperFrog());
    addRed(new RedSkull());
    addRed(new RunicCube());
    addRed(new SelfFormingClay());
    if (Settings.isDev) {
      addBlue(new DerpRock());
    }
    if (Settings.isBeta) {}
    logger.info("Relic load time: " + (System.currentTimeMillis() - startTime) + "ms");
    sortLists();
    printRelicCount();
  }
  
  private static void sortLists()
  {
    Collections.sort(starterList);
    Collections.sort(commonList);
    Collections.sort(uncommonList);
    Collections.sort(rareList);
    Collections.sort(bossList);
    Collections.sort(specialList);
    Collections.sort(shopList);
    logger.info(starterList);
    logger.info(commonList);
    logger.info(uncommonList);
    logger.info(rareList);
    logger.info(bossList);
  }
  
  private static void printRelicCount()
  {
    int common = 0;int uncommon = 0;int rare = 0;int boss = 0;int shop = 0;int other = 0;
    for (Entry<String, AbstractRelic> r : sharedRelics.entrySet()) {
      switch (((AbstractRelic)r.getValue()).tier)
      {
      case COMMON: 
        common++;
        break;
      case UNCOMMON: 
        uncommon++;
        break;
      case RARE: 
        rare++;
        break;
      case BOSS: 
        boss++;
        break;
      case SHOP: 
        shop++;
        break;
      default: 
        other++;
      }
    }
    logger.info(common + "/25 (Common)");
    logger.info(uncommon + "/25 (Uncommon)");
    logger.info(rare + "/20 (Rare)");
    logger.info(boss + "/20 (Boss)");
    logger.info(shop + "/5 (Shop)");
    logger.info(other + "/? (Other)");
    logger.info(redRelics.size() + "/8 (Red)");
    logger.info(greenRelics.size() + "/8 (Green)");
    logger.info(blueRelics.size() + "/8 (Blue)");
  }
  
  public static void add(AbstractRelic relic)
  {
    if (UnlockTracker.isRelicSeen(relic.relicId)) {
      seenRelics += 1;
    }
    relic.isSeen = UnlockTracker.isRelicSeen(relic.relicId);
    sharedRelics.put(relic.relicId, relic);
    addToTierList(relic);
    totalRelicCount += 1;
  }
  
  public static void addRed(AbstractRelic relic)
  {
    if (UnlockTracker.isRelicSeen(relic.relicId)) {
      seenRelics += 1;
    }
    relic.isSeen = UnlockTracker.isRelicSeen(relic.relicId);
    redRelics.put(relic.relicId, relic);
    addToTierList(relic);
    redList.add(relic);
    totalRelicCount += 1;
  }
  
  public static void addGreen(AbstractRelic relic)
  {
    if (UnlockTracker.isRelicSeen(relic.relicId)) {
      seenRelics += 1;
    }
    relic.isSeen = UnlockTracker.isRelicSeen(relic.relicId);
    greenRelics.put(relic.relicId, relic);
    addToTierList(relic);
    greenList.add(relic);
    totalRelicCount += 1;
  }
  
  public static void addBlue(AbstractRelic relic)
  {
    if (UnlockTracker.isRelicSeen(relic.relicId)) {
      seenRelics += 1;
    }
    relic.isSeen = UnlockTracker.isRelicSeen(relic.relicId);
    blueRelics.put(relic.relicId, relic);
    addToTierList(relic);
    blueList.add(relic);
    totalRelicCount += 1;
  }
  
  public static void addToTierList(AbstractRelic relic)
  {
    switch (relic.tier)
    {
    case STARTER: 
      starterList.add(relic);
      break;
    case COMMON: 
      commonList.add(relic);
      break;
    case UNCOMMON: 
      uncommonList.add(relic);
      break;
    case RARE: 
      rareList.add(relic);
      break;
    case SHOP: 
      shopList.add(relic);
      break;
    case SPECIAL: 
      specialList.add(relic);
      break;
    case BOSS: 
      bossList.add(relic);
      break;
    case DEPRECATED: 
      logger.info(relic.relicId + " is deprecated.");
      break;
    default: 
      logger.info(relic.relicId + " is undefined tier.");
    }
  }
  
  public static AbstractRelic getRelic(String key)
  {
    if (sharedRelics.containsKey(key)) {
      return (AbstractRelic)sharedRelics.get(key);
    }
    if (redRelics.containsKey(key)) {
      return (AbstractRelic)redRelics.get(key);
    }
    if (greenRelics.containsKey(key)) {
      return (AbstractRelic)greenRelics.get(key);
    }
    if (blueRelics.containsKey(key)) {
      return (AbstractRelic)blueRelics.get(key);
    }
    return new Circlet();
  }
  
  public static void populateRelicPool(ArrayList<String> pool, AbstractRelic.RelicTier tier, PlayerClass c)
  {
    for (Entry<String, AbstractRelic> r : sharedRelics.entrySet()) {
      if ((((AbstractRelic)r.getValue()).tier == tier) && (
        (!UnlockTracker.isRelicLocked((String)r.getKey())) || (Settings.isDailyRun))) {
        pool.add(r.getKey());
      }
    }
    switch (c)
    {
    case IRONCLAD: 
      for (Entry<String, AbstractRelic> r : redRelics.entrySet()) {
        if ((((AbstractRelic)r.getValue()).tier == tier) && (
          (!UnlockTracker.isRelicLocked((String)r.getKey())) || (Settings.isDailyRun))) {
          pool.add(r.getKey());
        }
      }
      break;
    case THE_SILENT: 
      for (Entry<String, AbstractRelic> r : greenRelics.entrySet()) {
        if ((((AbstractRelic)r.getValue()).tier == tier) && (
          (!UnlockTracker.isRelicLocked((String)r.getKey())) || (Settings.isDailyRun))) {
          pool.add(r.getKey());
        }
      }
      break;
    case CROWBOT: 
      for (Entry<String, AbstractRelic> r : blueRelics.entrySet()) {
        if ((((AbstractRelic)r.getValue()).tier == tier) && (
          (!UnlockTracker.isRelicLocked((String)r.getKey())) || (Settings.isDailyRun))) {
          pool.add(r.getKey());
        }
      }
    }
  }
  
  public static void addSharedRelics(ArrayList<AbstractRelic> relicPool)
  {
    logger.info("[RELIC] Adding " + sharedRelics.size() + " shared relics...");
    for (Entry<String, AbstractRelic> r : sharedRelics.entrySet()) {
      relicPool.add(r.getValue());
    }
  }
  
  public static void addClassSpecificRelics(ArrayList<AbstractRelic> relicPool)
  {
    switch (com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.chosenClass)
    {
    case IRONCLAD: 
      logger.info("[RELIC] Adding " + redRelics.size() + " red relics...");
      for (Entry<String, AbstractRelic> r : redRelics.entrySet()) {
        relicPool.add(r.getValue());
      }
      break;
    case THE_SILENT: 
      logger.info("[RELIC] Adding " + greenRelics.size() + " red relics...");
      for (Entry<String, AbstractRelic> r : greenRelics.entrySet()) {
        relicPool.add(r.getValue());
      }
      break;
    case CROWBOT: 
      logger.info("[RELIC] Adding " + blueRelics.size() + " red relics...");
      for (Entry<String, AbstractRelic> r : blueRelics.entrySet()) {
        relicPool.add(r.getValue());
      }
      break;
    }
  }
  
  public static void uploadRelicData()
  {
    ArrayList<String> data = new ArrayList();
    for (Entry<String, AbstractRelic> r : sharedRelics.entrySet()) {
      data.add(((AbstractRelic)r.getValue()).debugDetailedRelicData("All"));
    }
    for (Entry<String, AbstractRelic> r : redRelics.entrySet()) {
      data.add(((AbstractRelic)r.getValue()).debugDetailedRelicData("Red"));
    }
    for (Entry<String, AbstractRelic> r : greenRelics.entrySet()) {
      data.add(((AbstractRelic)r.getValue()).debugDetailedRelicData("Green"));
    }
    for (Entry<String, AbstractRelic> r : blueRelics.entrySet()) {
      data.add(((AbstractRelic)r.getValue()).debugDetailedRelicData("Blue"));
    }
    LeaderboardPoster poster = new LeaderboardPoster();
    poster.setValues(LeaderboardPoster.LeaderboardDataType.RELIC_DATA, "", AbstractRelic.debugDetailedRelicDataHeader(), data, false);
    Thread t = new Thread(poster);
    t.run();
  }
  
  public static ArrayList<AbstractRelic> sortByName(ArrayList<AbstractRelic> group, boolean ascending)
  {
    ArrayList<AbstractRelic> tmp = new ArrayList();
    for (AbstractRelic r : group)
    {
      int addIndex = 0;
      for (AbstractRelic r2 : tmp)
      {
        if (!ascending ? 
          r.name.compareTo(r2.name) < 0 : 
          
          r.name.compareTo(r2.name) > 0) {
          break;
        }
        addIndex++;
      }
      tmp.add(addIndex, r);
    }
    return tmp;
  }
  
  public static ArrayList<AbstractRelic> sortByStatus(ArrayList<AbstractRelic> group, boolean ascending)
  {
    ArrayList<AbstractRelic> tmp = new ArrayList();
    for (AbstractRelic r : group)
    {
      int addIndex = 0;
      for (AbstractRelic r2 : tmp)
      {
        if (!ascending)
        {
          String a;
          if (UnlockTracker.isRelicLocked(r.relicId))
          {
            a = "LOCKED";
          }
          else
          {
            if (UnlockTracker.isRelicSeen(r.relicId)) {
              a = "UNSEEN";
            } else {
              a = "SEEN";
            }
          }
          String b;
          if (UnlockTracker.isRelicLocked(r2.relicId))
          {
            b = "LOCKED";
          }
          else
          {
            if (UnlockTracker.isRelicSeen(r2.relicId)) {
              b = "UNSEEN";
            } else {
              b = "SEEN";
            }
          }
          if (a.compareTo(b) > 0) {
            break;
          }
        }
        else
        {
          String a;
          if (UnlockTracker.isRelicLocked(r.relicId))
          {
            a = "LOCKED";
          }
          else
          {
            if (UnlockTracker.isRelicSeen(r.relicId)) {
              a = "UNSEEN";
            } else {
              a = "SEEN";
            }
          }
          String b;
          if (UnlockTracker.isRelicLocked(r2.relicId))
          {
            b = "LOCKED";
          }
          else
          {
            if (UnlockTracker.isRelicSeen(r2.relicId)) {
              b = "UNSEEN";
            } else {
              b = "SEEN";
            }
          }
          if (a.compareTo(b) < 0) {
            break;
          }
        }
        addIndex++;
      }
      tmp.add(addIndex, r);
    }
    return tmp;
  }
  
  public static void unlockAndSeeAllRelics()
  {
    for (String s : UnlockTracker.lockedRelics) {
      UnlockTracker.hardUnlockOverride(s);
    }
    for (Entry<String, AbstractRelic> r : sharedRelics.entrySet()) {
      UnlockTracker.markRelicAsSeen((String)r.getKey());
    }
    for (Entry<String, AbstractRelic> r : redRelics.entrySet()) {
      UnlockTracker.markRelicAsSeen((String)r.getKey());
    }
    for (Entry<String, AbstractRelic> r : greenRelics.entrySet()) {
      UnlockTracker.markRelicAsSeen((String)r.getKey());
    }
    for (Entry<String, AbstractRelic> r : blueRelics.entrySet()) {
      UnlockTracker.markRelicAsSeen((String)r.getKey());
    }
  }
}
