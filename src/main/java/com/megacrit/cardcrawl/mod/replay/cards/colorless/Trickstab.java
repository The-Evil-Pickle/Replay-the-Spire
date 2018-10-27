package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.CustomCard;

public class Trickstab
  extends CustomCard
{
  public static final String ID = "ReplayTheSpireMod:Trickstab";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  private static final int ATTACK_DMG = 4;
  
  public Trickstab()
  {
    super(ID, NAME, "cards/replay/replayBetaAttack.png", 1, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
    
    this.baseDamage = ATTACK_DMG;
    this.baseBlock = ATTACK_DMG;
    this.baseMagicNumber = 2;
    this.magicNumber = this.baseMagicNumber;
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
	  for (int i=0; i < this.magicNumber; i++) {
		  AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), (i % 2 == 0) ? AbstractGameAction.AttackEffect.SLASH_VERTICAL : AbstractGameAction.AttackEffect.BLUNT_LIGHT));
	  }
  }
  
  public AbstractCard makeCopy()
  {
    return new Trickstab();
  }

  @Override
  public void applyPowers() {
	  this.isBlockModified = false;
      float tmp = this.baseBlock;
      for (final AbstractPower p : AbstractDungeon.player.powers) {
          tmp = p.modifyBlock(tmp);
          if (this.baseBlock == MathUtils.floor(tmp)) {
              continue;
          }
          this.isBlockModified = true;
      }
      if (tmp < 0.0f) {
          tmp = 0.0f;
      }
      this.block = MathUtils.floor(tmp);
      this.baseDamage = this.block;
      super.applyPowers();
      if (this.isBlockModified) {
    	  this.isDamageModified = true;
      }
  }
  @Override
  public void calculateCardDamage(final AbstractMonster mo) {
	  this.isBlockModified = false;
      float tmp = this.baseBlock;
      for (final AbstractPower p : AbstractDungeon.player.powers) {
          tmp = p.modifyBlock(tmp);
          if (this.baseBlock == MathUtils.floor(tmp)) {
              continue;
          }
          this.isBlockModified = true;
      }
      if (tmp < 0.0f) {
          tmp = 0.0f;
      }
      this.block = MathUtils.floor(tmp);
      this.baseDamage = this.block;
      super.calculateCardDamage(mo);
  }
  public void upgrade()
  {
    if (!this.upgraded)
    {
      upgradeName();
      upgradeMagicNumber(1);
    }
  }
}