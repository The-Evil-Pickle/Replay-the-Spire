package com.megacrit.cardcrawl.mod.replay.potions;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
//import com.megacrit.cardcrawl.potions.PotionRarity;

import java.util.ArrayList;

public class AdrenalinePotion
  extends AbstractPotion
{
  public static final String POTION_ID = "Adrenaline Potion";
  private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("Adrenaline Potion");
  public static final String NAME = potionStrings.NAME;
  public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
  
  public int secondPotency;
  
  public AdrenalinePotion()
  {
    super(NAME, "Adrenaline Potion", PotionRarity.COMMON, AbstractPotion.PotionSize.BOLT, AbstractPotion.PotionColor.EXPLOSIVE);
	String p = "s ";
	this.secondPotency = 1;
	this.potency = this.getPotency();
	
	if (this.potency == 1)
	{
		p = " ";
	}
    this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1] + p + DESCRIPTIONS[2] + this.secondPotency + DESCRIPTIONS[3]);
    this.isThrown = false;
    this.tips.add(new PowerTip(this.name, this.description));
  }
  
	@Override
	public int getPotency(final int ascensionLevel) {
		return 2;//(ascensionLevel < 11) ? 2 : 1;
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