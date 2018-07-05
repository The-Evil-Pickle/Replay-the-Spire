package com.megacrit.cardcrawl.powers;

import replayTheSpire.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LifeLinkPower
  extends AbstractPower
{
  public static final String POWER_ID = "Life Bind";
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Life Bind");
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
  
  public static final Logger logger = LogManager.getLogger(LifeLinkPower.class.getName());
  private boolean sender = false;
  
  public LifeLinkPower(AbstractCreature owner, int amount, boolean sender)
  {
    this.name = NAME;
    this.ID = "Life Bind";
    this.owner = owner;
    this.amount = amount;
	this.sender = sender;
	if (this.sender){
		this.description = (DESCRIPTIONS[0]);
	} else {
		this.description = (DESCRIPTIONS[1]);
	}
    loadRegion("lifebind");
	//this.img = new Texture("img/powers/LifeBind.png");
	
	if (!this.sender){
		this.type = AbstractPower.PowerType.DEBUFF;
	}
	//logger.info("LBP");
	//logger.info(this.owner.name);
	//logger.info(this.sender);
  }
  
	@Override
    protected void loadRegion(final String fileName) {
        this.region48 = ReplayTheSpireMod.powerAtlas.findRegion("48/" + fileName);
		this.region128 = ReplayTheSpireMod.powerAtlas.findRegion("128/" + fileName);
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
  
  public int onAttacked(final DamageInfo info, final int damageAmount) {
	  //logger.info("LB damage");
	  //logger.info(this.owner.name);
	  //logger.info(damageAmount);
	if (!this.sender || damageAmount <= 0){
		//logger.info("!sender");
		return damageAmount;
	}
	//logger.info("CP1");
	for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
      if (!m.isDeadOrEscaped()) {
		  //logger.info("Checking " + m.name);
		if (m.hasPower("Life Bind")) {
			//logger.info("Has Power");
			DamageInfo newinfo = new DamageInfo(this.owner, damageAmount * m.getPower("Life Bind").amount, DamageInfo.DamageType.THORNS);
			newinfo.applyEnemyPowersOnly(m);
			AbstractDungeon.actionManager.addToTop(new DamageAction(m, newinfo, AbstractGameAction.AttackEffect.FIRE));
		}
      }
    }
        return damageAmount;
  }
  
}
