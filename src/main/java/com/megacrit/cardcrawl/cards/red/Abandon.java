package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.unique.AbandonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.cards.status.Void;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import basemod.*;
import basemod.abstracts.*;
import replayTheSpire.variables.Exhaustive;

public class Abandon
  extends CustomCard
{
  public static final String ID = "Abandon";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Abandon");
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
  private static final int COST = 0;
  
  public Abandon()
  {
    super("Abandon", NAME, "cards/replay/abandon.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.RED, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.NONE);
    this.baseMagicNumber = 3;
    this.magicNumber = this.baseMagicNumber;
    Exhaustive.setBaseValue(this, 3);
    this.isEthereal = true;
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
	AbstractDungeon.actionManager.addToBottom(new AbandonAction(p, p, this.magicNumber, true));
	if (this.upgraded) {
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Dazed(), 1));
	} else {
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Void(), 1));
	}
	Exhaustive.increment(this);
  }
  
  public AbstractCard makeCopy()
  {
    return new Abandon();
  }
  
  public void triggerOnEndOfPlayerTurn()
  {
    AbstractDungeon.actionManager.addToTop(new ExhaustAllEtherealAction());
  }
  
  public void upgrade()
  {
    if (!this.upgraded)
    {
      upgradeName();
      //upgradeMagicNumber(2);
      Exhaustive.upgrade(this, 1);
      this.rawDescription = UPGRADE_DESCRIPTION;
      initializeDescription();
    }
  }
}
