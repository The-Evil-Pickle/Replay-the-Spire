package com.megacrit.cardcrawl.mod.replay.cards.colorless.mimic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;

public class MimicCopiedAttack
extends CustomCard
{
public static final String ID = "ReplayMimic_AttackMimicry";
private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
public static final String NAME = cardStrings.NAME;
public static final String DESCRIPTION = cardStrings.DESCRIPTION;
private static final int COST = 2;

public MimicCopiedAttack(String name, String desc, int cost, int damage, int multiattack, int block)
{
  super(ID, NAME, "cards/replay/replayBetaSkill.png", cost, desc, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.ENEMY);
  this.baseDamage = damage;
  this.baseMagicNumber = multiattack;
  this.magicNumber = multiattack;
  this.baseBlock = block;
}


public void use(AbstractPlayer p, AbstractMonster m)
{
  for (int i = 0; i < this.magicNumber; i++) {
	  AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
  }
  if (this.baseBlock > 0) {
	  AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
  }
}

public void triggerOnEndOfPlayerTurn()
{
  AbstractDungeon.actionManager.addToTop(new ExhaustAllEtherealAction());
}

public AbstractCard makeCopy()
{
  return new MimicCopiedAttack(this.name, this.rawDescription, this.cost, this.baseDamage, this.baseMagicNumber, this.baseBlock);
}

public void upgrade()
{
  if (!this.upgraded)
  {
    upgradeName();
    this.upgradeDamage((this.baseDamage + 2) / 3);
  }
}
}