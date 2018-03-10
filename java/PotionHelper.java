package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.math.RandomXS128;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.AncientPotion;
import com.megacrit.cardcrawl.potions.BlockPotion;
import com.megacrit.cardcrawl.potions.DexterityPotion;
import com.megacrit.cardcrawl.potions.EnergyPotion;
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

import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PotionHelper
{
  private static final Logger logger = LogManager.getLogger(PotionHelper.class.getName());
  public static ArrayList<String> potions = new ArrayList();
  
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
	
	potions.add("Adrenaline Potion");
	potions.add("Death Potion");
	potions.add("Ironskin Potion");
	potions.add("Thorns Potion");
	potions.add("Toxic Potion");
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
    String randomKey = (String)potions.get(rng.random.nextInt(potions.size()));
    return getPotion(randomKey);
  }
  
  public static AbstractPotion getRandomPotion()
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
    case "Potion Slot": 
      return null;
    }
    logger.info("MISSING KEY: POTIONHELPER 37: " + name);
    return new FirePotion();
  }
}