package com.megacrit.cardcrawl.mod.replay.cards.red;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.*;
import basemod.abstracts.*;

public class Eclipse
  extends CustomCard
{
  public static final String ID = "Eclipse";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
  private static final int COST = 3;
  
  public Eclipse()
  {
    super(ID, NAME, "cards/replay/eclipse.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.RED, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.NONE);
    this.baseMagicNumber = 0;
    this.magicNumber = this.baseMagicNumber;
    this.exhaust = true;
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
    //AbstractDungeon.actionManager.addToBottom(new ExhaustAction(p, p, this.magicNumber, false, true, false));
	AbstractDungeon.actionManager.addToBottom(new EclipseAction(p, this.magicNumber));
	if (this.magicNumber > 0) {
		AbstractDungeon.actionManager.addToBottom(new ReplayRefundAction(this, this.magicNumber));
	}
    //AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
  }
  
  public AbstractCard makeCopy()
  {
    return new Eclipse();
  }
  
  public void upgrade()
  {
    if (!this.upgraded)
    {
      upgradeName();
      upgradeMagicNumber(1);
      this.rawDescription = UPGRADE_DESCRIPTION;
      initializeDescription();
    }
  }
}
