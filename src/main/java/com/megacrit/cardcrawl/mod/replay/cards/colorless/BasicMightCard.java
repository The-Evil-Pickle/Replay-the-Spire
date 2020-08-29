package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import com.evacipated.cardcrawl.mod.stslib.variables.RefundVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.MightXAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;

public class BasicMightCard
  extends CustomCard
{
  public static final String ID = "Replay:BasicMightCard";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  private static final int COST = -1;
  
  public BasicMightCard()
  {
    super(ID, NAME, "cards/replay/might.png", BasicMightCard.COST, DESCRIPTION, AbstractCard.CardType.POWER, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.NONE);
    RefundVariable.setBaseValue(this, 2);
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
    AbstractDungeon.actionManager.addToBottom(new MightXAction(AbstractDungeon.player, this.freeToPlayOnce, this.energyOnUse));
  }
  
  public AbstractCard makeCopy()
  {
    return new BasicMightCard();
  }
  
  public void upgrade()
  {
    if (!this.upgraded)
    {
      upgradeName();
      RefundVariable.upgrade(this, 1);
    }
  }
}