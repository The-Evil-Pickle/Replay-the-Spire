package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import java.util.ArrayList;

public class AdrenalinePotion
  extends AbstractPotion
{
  public static final String POTION_ID = "Adrenaline Potion";
  private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("Adrenaline Potion");
  public static final String NAME = potionStrings.NAME;
  public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
  
  private int secondPotency = 1;
  
  public AdrenalinePotion()
  {
    super(NAME, "Adrenaline Potion", AbstractPotion.PotionSize.T, AbstractPotion.PotionColor.EXPLOSIVE);
	String p = "s ";
    if (AbstractDungeon.ascensionLevel >= 11) {
      this.potency = 1;
	  this.secondPotency = 1;
	  this.rarity = AbstractPotion.PotionRarity.UNCOMMON;
    } else {
      this.potency = 2;
	  this.secondPotency = 1;
    }
	
	if (this.potency == 1)
	{
		p = " ";
	}
    this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1] + p + DESCRIPTIONS[2] + this.secondPotency + DESCRIPTIONS[3]);
    this.isThrown = false;
    this.tips.add(new PowerTip(this.name, this.description));
  }
  
  public void use(AbstractCreature target)
  {
    AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, this.potency));
	AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.secondPotency));
  }
  
  public void upgradePotion()
  {
	this.secondPotency += 1;
	this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1] + "(s) " + DESCRIPTIONS[2] + this.secondPotency + DESCRIPTIONS[3]);
    this.tips.clear();
    this.tips.add(new PowerTip(this.name, this.description));
  }
  
  public AbstractPotion makeCopy()
  {
    return new AdrenalinePotion();
  }
}