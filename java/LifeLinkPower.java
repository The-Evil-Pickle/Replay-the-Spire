package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class LifeLinkPower
  extends AbstractPower
{
  public static final String POWER_ID = "Life Bind";
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Life Bind");
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
  
  private boolean sender = false;
  
  public LifeLinkPower(AbstractCreature owner, int amount, boolean sender)
  {
    this.name = NAME;
    this.ID = "Life Bind";
    this.owner = owner;
    this.amount = amount;
	this.sender = sender;
	if (sender){
		this.description = (DESCRIPTIONS[0]);
	} else {
		this.description = (DESCRIPTIONS[1]);
	}
    //loadRegion("hex");
	this.img = new Texture("img/powers/LifeBind.png");
	if (!sender){
		this.type = AbstractPower.PowerType.DEBUFF;
	}
  }
  
  public void stackPower(int stackAmount)
  {
	if (this.sender){
		return;
	}
    if (this.amount == -1)
    {
      return;
    }
    this.fontScale = 8.0F;
    this.amount += stackAmount;
  }
  
  public void onLoseHp(int damageAmount) {
	if (!this.sender){
		return;
	}
	for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
      if (!m.isDeadOrEscaped()) {
		if (m.hasPower("Life Bind")) {
			DamageInfo info = new DamageInfo(this.owner, damageAmount * m.getPower("Life Bind").amount, DamageInfo.DamageType.THORNS);
			info.applyEnemyPowersOnly(m);
			AbstractDungeon.actionManager.addToTop(new DamageAction(m, info, AbstractGameAction.AttackEffect.FIRE));
		}
      }
    }
  }
  
}
