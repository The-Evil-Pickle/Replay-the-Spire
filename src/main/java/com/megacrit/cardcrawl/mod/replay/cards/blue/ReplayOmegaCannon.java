package com.megacrit.cardcrawl.mod.replay.cards.blue;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAndDeckAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.cards.status.BackFire;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import basemod.abstracts.CustomCard;

public class ReplayOmegaCannon
extends CustomCard
{
public static final String ID = "ReplayTheSpireMod:Omega Cannon";
private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
public static final String NAME = cardStrings.NAME;
public static final String DESCRIPTION = cardStrings.DESCRIPTION;
private static final int COST = 2;

public ReplayOmegaCannon()
{
  super(ID, NAME, "cards/replay/omegaCannon.png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.BLUE, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL_ENEMY);
  
  this.baseDamage = 6;
  this.isMultiDamage = true;
  this.baseMagicNumber = 4;
  this.magicNumber = this.baseMagicNumber;
}

@Override
public void use(final AbstractPlayer p, final AbstractMonster m) {
    AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1f));
    for (int i=0; i < this.magicNumber; i++) {
    	AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
    }
    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new BackFire(), 2));
}

public AbstractCard makeCopy()
{
  return new ReplayOmegaCannon();
}

public void upgrade()
{
  if (!this.upgraded)
  {
    upgradeName();
    //upgradeMagicNumber(1);
    //upgradeDamage(-1);
    upgradeDamage(1);
  }
}

}
