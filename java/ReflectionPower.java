package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ReflectionPower
  extends AbstractPower
{
  public static final String POWER_ID = "Reflection";
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Reflection");
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
  
  public ReflectionPower(AbstractCreature owner)
  {
    this.name = NAME;
    this.ID = "Reflection";
    this.owner = owner;
    this.amount = -1;
    this.description = DESCRIPTIONS[0];
    loadRegion("reflection");
  }
  
  public void updateDescription()
  {
    this.description = DESCRIPTIONS[1];
  }
}
