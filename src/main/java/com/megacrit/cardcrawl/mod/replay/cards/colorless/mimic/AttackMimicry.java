package com.megacrit.cardcrawl.mod.replay.cards.colorless.mimic;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.colorless.GhostDefend;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class AttackMimicry 
extends CustomCard
{
public static final String ID = "ReplayMimic_AttackMimicry";
private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
public static final String NAME = cardStrings.NAME;
public static final String DESCRIPTION = cardStrings.DESCRIPTION;
private static final int COST = 2;

public AttackMimicry()
{
  super(ID, NAME, "cards/replay/replayBetaSkill.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.SELF);
  
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
  return new AttackMimicry();
}

public void upgrade()
{
  if (!this.upgraded)
  {
    upgradeName();
  }
}
}