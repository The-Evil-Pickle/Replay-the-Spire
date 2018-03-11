package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.math.RandomXS128;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity;
import com.megacrit.cardcrawl.potions.AncientPotion;
import com.megacrit.cardcrawl.potions.BlockPotion;
import com.megacrit.cardcrawl.potions.DexterityPotion;
import com.megacrit.cardcrawl.potions.EnergyPotion;
import com.megacrit.cardcrawl.potions.ElixirPotion;
import com.megacrit.cardcrawl.potions.ExplosivePotion;
import com.megacrit.cardcrawl.potions.FirePotion;
import com.megacrit.cardcrawl.potions.HealthPotion;
import com.megacrit.cardcrawl.potions.PoisonPotion;
import com.megacrit.cardcrawl.potions.PotionPlaceholder;
import com.megacrit.cardcrawl.potions.RegenPotion;
import com.megacrit.cardcrawl.potions.StrengthPotion;
import com.megacrit.cardcrawl.potions.SwiftPotion;
import com.megacrit.cardcrawl.potions.WeakenPotion;

import com.megacrit.cardcrawl.potions.AdrenalinePotion;
import com.megacrit.cardcrawl.potions.DeathPotion;
import com.megacrit.cardcrawl.potions.IronSkinPotion;
import com.megacrit.cardcrawl.potions.ThornsPotion;
import com.megacrit.cardcrawl.potions.ToxicPotion;
import com.megacrit.cardcrawl.potions.VenomPotion;
import com.megacrit.cardcrawl.potions.DoomPotion;
import com.megacrit.cardcrawl.potions.SpiritPotion;

import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import java.util.ArrayList;
import java.util.Map;
import java.util.EnumMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PotionHelper
{
  private static final Logger logger = LogManager.getLogger(PotionHelper.class.getName());
  public static ArrayList<String> potions = new ArrayList();
  public static EnumMap<AbstractPotion.PotionRarity, ArrayList<String>> potionsByRarity = new EnumMap<AbstractPotion.PotionRarity, ArrayList<String>>(AbstractPotion.PotionRarity.class);
  
  protected static int commonPotionChance;
  protected static int uncommonPotionChance;
  protected static int rarePotionChance;
  protected static int ultraPotionChance;
  protected static int shopPotionChance;
  
  public static void initialize()
  {
    potions.add("Ancient Potion");
    potions.add("Block Potion");
    potions.add("Dexterity Potion");
    potions.add("Energy Potion");
    potions.add("Explosive Potion");
    potions.add("Fire Potion");
    potions.add("Strength Potion");
    potions.add("Regen Potion");
    potions.add("Swift Potion");
    potions.add("Poison Potion");
    potions.add("Weak Potion");
	
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
	
	commonPotionChance = 9;
	uncommonPotionChance = 7;
	rarePotionChance = 4;
	ultraPotionChance = 1;
	shopPotionChance = 10;
	
	
	potionsByRarity = new EnumMap<AbstractPotion.PotionRarity, ArrayList<String>>(AbstractPotion.PotionRarity.class);
	potionsByRarity.put(AbstractPotion.PotionRarity.COMMON, new ArrayList<String>());
	potionsByRarity.put(AbstractPotion.PotionRarity.UNCOMMON, new ArrayList<String>());
	potionsByRarity.put(AbstractPotion.PotionRarity.RARE, new ArrayList<String>());
	potionsByRarity.put(AbstractPotion.PotionRarity.ULTRA, new ArrayList<String>());
	potionsByRarity.put(AbstractPotion.PotionRarity.SPECIAL, new ArrayList<String>());
	potionsByRarity.put(AbstractPotion.PotionRarity.SHOP, new ArrayList<String>());
	for (String s : potions)
	{
		AbstractPotion p = getPotion(s);
		potionsByRarity.get(p.rarity).add(s);
	}
	
	commonPotionChance *= potionsByRarity.get(AbstractPotion.PotionRarity.COMMON).size();
	uncommonPotionChance *= potionsByRarity.get(AbstractPotion.PotionRarity.UNCOMMON).size();
	rarePotionChance *= potionsByRarity.get(AbstractPotion.PotionRarity.RARE).size();
	ultraPotionChance *= potionsByRarity.get(AbstractPotion.PotionRarity.ULTRA).size();
	shopPotionChance *= potionsByRarity.get(AbstractPotion.PotionRarity.SHOP).size();
	
  }
  
  public static AbstractPotion.PotionRarity returnRandomPotionTier(Random rng)
  {
	int tmpShopChance = 0;
	int tmpUltraChance = ultraPotionChance;
	if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP) {
		tmpShopChance = shopPotionChance;
		tmpUltraChance *= 4;
	}
    int roll = rng.random(0, commonPotionChance + uncommonPotionChance + rarePotionChance + tmpUltraChance + tmpShopChance - 1);
    if (roll < commonPotionChance) {
      return AbstractPotion.PotionRarity.COMMON;
    }
    if (roll < commonPotionChance + uncommonPotionChance) {
      return AbstractPotion.PotionRarity.UNCOMMON;
    }
    if (roll < commonPotionChance + uncommonPotionChance + rarePotionChance) {
      return AbstractPotion.PotionRarity.RARE;
    }
    if (roll < commonPotionChance + uncommonPotionChance + rarePotionChance + tmpShopChance) {
      return AbstractPotion.PotionRarity.SHOP;
    }
    return AbstractPotion.PotionRarity.ULTRA;
  }
  
  public static AbstractPotion getRandomPotion(ArrayList<String> exclusion)
  {
    ArrayList<String> potionsTmp = new ArrayList();
    for (String s : potions)
    {
      boolean exclude = false;
      for (String s2 : exclusion) {
        if (s.equals(s2))
        {
          logger.info(s + " EXCLUDED");
          exclude = true;
        }
      }
      if (!exclude) {
        potionsTmp.add(s);
      }
    }
    String randomKey = (String)potionsTmp.get(AbstractDungeon.potionRng.random.nextInt(potionsTmp.size()));
    return getPotion(randomKey);
  }
  
  public static AbstractPotion getRandomPotion(Random rng)
  {
    //String randomKey = (String)potions.get(rng.random.nextInt(potions.size()));
    return getRandomPotion(rng, returnRandomPotionTier(rng));
  }
  
  public static AbstractPotion getRandomPotion()
  {
    //String randomKey = (String)potions.get(AbstractDungeon.potionRng.random.nextInt(potions.size()));
    return getRandomPotion(AbstractDungeon.potionRng);
  }
  
  public static AbstractPotion getRandomPotion(Random rng, AbstractPotion.PotionRarity rarity)
  {
    String randomKey = (String)potionsByRarity.get(rarity).get(AbstractDungeon.potionRng.random.nextInt(potionsByRarity.get(rarity).size()));
    return getPotion(randomKey);
  }
  
  public static AbstractPotion getTrulyRandomPotion()
  {
    String randomKey = (String)potions.get(AbstractDungeon.potionRng.random.nextInt(potions.size()));
    return getPotion(randomKey);
  }
  
  public static void obtainPotion(AbstractPotion potion)
  {
    for (int i = 0; i < AbstractDungeon.player.potions.length; i++) {
      if ((AbstractDungeon.player.potions[i] instanceof PotionPlaceholder))
      {
        AbstractDungeon.player.potions[i] = potion;
        AbstractDungeon.player.potions[i].moveInstantly(TopPanel.POTION_X + Settings.POTION_W * i, Settings.POTION_Y);
        
        AbstractDungeon.player.potions[i].isDone = true;
        AbstractDungeon.player.potions[i].isObtained = true;
        AbstractDungeon.player.potions[i].isAnimating = false;
        AbstractDungeon.player.potions[i].flash();
        break;
      }
    }
  }
  
  public static AbstractPotion getPotion(String name)
  {
    if ((name == null) || (name.equals(""))) {
      return null;
    }
    switch (name)
    {
    case "Block Potion": 
      return new BlockPotion();
    case "Regen Potion": 
      return new RegenPotion();
    case "Dexterity Potion": 
      return new DexterityPotion();
    case "Ancient Potion": 
      return new AncientPotion();
    case "Energy Potion": 
      return new EnergyPotion();
    case "Explosive Potion": 
      return new ExplosivePotion();
    case "Elixir": 
      return new ElixirPotion();
    case "Fire Potion": 
      return new FirePotion();
    case "Health Potion": 
      return new HealthPotion();
    case "Strength Potion": 
      return new StrengthPotion();
    case "Swift Potion": 
      return new SwiftPotion();
    case "Poison Potion": 
      return new PoisonPotion();
    case "Weak Potion": 
      return new WeakenPotion();
	case "Adrenaline Potion":
	  return new AdrenalinePotion();
	case "Death Potion":
	  return new DeathPotion();
	case "Thorns Potion":
	  return new ThornsPotion();
	case "Ironskin Potion":
	  return new IronSkinPotion();
	case "Toxic Potion":
	  return new ToxicPotion();
	case "Doom Potion":
	  return new DoomPotion();
	case "Spirit Potion":
	  return new SpiritPotion();
	case "Venom Potion":
	  return new VenomPotion();
    case "Potion Slot": 
      return null;
    }
    logger.info("MISSING KEY: POTIONHELPER 37: " + name);
    return new FirePotion();
  }
}