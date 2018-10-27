package com.megacrit.cardcrawl.mod.replay.cards.green;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.powers.NecroticPoisonPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType;
import basemod.*;
import basemod.abstracts.*;

public class DrainingMist
  extends CustomCard
{
  public static final String ID = "Draining Mist";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Draining Mist");
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
  private static final int COST = 3;
  private static final int POOL = 1;
  private int strengthmod = -1;
  
  public DrainingMist()
  {
    super("Draining Mist", NAME, "cards/replay/drainingMist.png", DrainingMist.COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.GREEN, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY);
    
    this.exhaust = true;
    this.baseMagicNumber = 2;
    this.magicNumber = this.baseMagicNumber;
	this.strengthmod = -1;
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
    //AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.NORMAL), 1.5F));
	
	for (int i=0; i < this.magicNumber; i++) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.getMonsters().getRandomMonster(true), p, new NecroticPoisonPower(AbstractDungeon.getMonsters().getRandomMonster(true), p, 3), 3, AbstractGameAction.AttackEffect.POISON));
		if (!Settings.FAST_MODE) {
			AbstractDungeon.actionManager.addToBottom(new WaitAction(0.2f));
		}
	}
    for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
      AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new StrengthPower(mo, this.strengthmod), this.strengthmod, true, AbstractGameAction.AttackEffect.NONE));
    }
  }
  
  public AbstractCard makeCopy()
  {
    return new DrainingMist();
  }
  
  public void upgrade()
  {
    if (!this.upgraded)
    {
      upgradeName();
      upgradeMagicNumber(1);
	  this.strengthmod -= 1;
      this.rawDescription = UPGRADE_DESCRIPTION;
      initializeDescription();
    }
  }
}
