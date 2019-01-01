package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;

public class DoomedPower
  extends AbstractPower
{
  public static final String POWER_ID = "Doomed";
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Doomed");
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
  private int damage;
  
  public DoomedPower(AbstractCreature owner, int amnt)
  {
    this.name = NAME;
    this.ID = "Doomed";
    this.owner = owner;
    this.amount = amnt;
    this.type = AbstractPower.PowerType.DEBUFF;
    this.damage = 999;
    updateDescription();
    loadRegion("corruption");
  }
  
  public void updateDescription()
  {
      this.description = (DESCRIPTIONS[0] + this.damage + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]);
  }
  
  public void stackPower(int stackAmount)
  {
	this.fontScale = 8.0F;
	if (stackAmount < amount){
		amount = stackAmount;
	} else {
		if (amount > 1){
			amount = amount - 1;
		}
	}
	this.damage += 1001;
  }
  
  public void atEndOfTurn(boolean isPlayer)
  {
    flash();
	if (amount == 1)
	{
	  AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.owner, this.damage, AbstractGameAction.AttackEffect.FIRE));
	  AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Doomed"));
	} else {
	  amount = amount - 1;
	  updateDescription();
	}
  }
}
