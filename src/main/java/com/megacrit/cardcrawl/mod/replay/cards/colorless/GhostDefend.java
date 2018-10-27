package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import basemod.*;
import basemod.abstracts.*;

public class GhostDefend
  extends CustomCard
{
  public static final String ID = "Ghost Defend";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Ghost Defend");
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  private static final int BLOCK_AMT = 5;
  private static final int UPG_BLOCK = 3;
  private static final int POOL = 0;
  
  public GhostDefend()
  {
    super("Ghost Defend", NAME, "cards/replay/defend.png", 1, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.SELF);
    
    this.baseBlock = 5;
    this.exhaust = true;
    this.isEthereal = true;
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
  }
  
  public void triggerOnEndOfPlayerTurn()
  {
    AbstractDungeon.actionManager.addToTop(new ExhaustAllEtherealAction());
  }
  
  public AbstractCard makeCopy()
  {
    return new GhostDefend();
  }
  
  public void upgrade()
  {
    if (!this.upgraded)
    {
      upgradeName();
      upgradeBlock(3);
    }
  }
}