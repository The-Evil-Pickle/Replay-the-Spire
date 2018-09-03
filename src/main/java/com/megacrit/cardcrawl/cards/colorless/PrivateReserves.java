package com.megacrit.cardcrawl.cards.colorless;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.actions.unique.*;

public class PrivateReserves
  extends CustomCard
{
  public static final String ID = "ReplayTheSpireMod:Private Reserves";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
  private static final int COST = 0;
  
  public PrivateReserves()
  {
    super(ID, NAME, "cards/replay/replayBetaSkill.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.NONE);

    this.retain = true;
    this.exhaust = true;
  }

  @Override
  public void applyPowers() {
	  super.applyPowers();
      this.retain = true;
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
	  AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
	  if (this.upgraded) {
		  AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
	  }
  }
  
  public AbstractCard makeCopy()
  {
    return new PrivateReserves();
  }
  
  public void upgrade()
  {
    if (!this.upgraded)
    {
      upgradeName();
      this.rawDescription = UPGRADE_DESCRIPTION;
      initializeDescription();
    }
  }
}
