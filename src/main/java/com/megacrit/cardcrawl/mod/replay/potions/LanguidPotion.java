package com.megacrit.cardcrawl.mod.replay.potions;

import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.potions.AbstractPotion;
//import com.megacrit.cardcrawl.potions.PotionRarity;

import replayTheSpire.*;
import java.util.ArrayList;

public class LanguidPotion
  extends AbstractPotion
{
  public static final String POTION_ID = "Languid Potion";
  private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("Languid Potion");
  public static final String NAME = potionStrings.NAME;
  public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
  
  public LanguidPotion()
  {
    super(NAME, "Languid Potion", PotionRarity.UNCOMMON, AbstractPotion.PotionSize.JAR, AbstractPotion.PotionColor.POISON);
	this.potency = this.getPotency();
    this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);// + DESCRIPTIONS[1] + (this.secondPotency - 1) + DESCRIPTIONS[2] + this.secondPotency + DESCRIPTIONS[3]
    this.isThrown = true;
    this.targetRequired = true;
    this.tips.add(new PowerTip(this.name, this.description));
    this.tips.add(new PowerTip(TipHelper.capitalize("languid"), GameDictionary.keywords.get("languid")));
    
  }
  
  public void use(AbstractCreature target)
  {
	  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new LanguidPower(target, this.potency, false), this.potency));
  }
  @Override
    public int getPotency(final int ascensionLevel) {
        return 3;
    }
  public AbstractPotion makeCopy()
  {
    return new LanguidPotion();
  }
  
  
}