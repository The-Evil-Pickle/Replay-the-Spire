package com.megacrit.cardcrawl.mod.replay.powers;

import replayTheSpire.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.*;

import basemod.interfaces.CloneablePowerInterface;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;

public class NecroticPoisonPower
  extends AbstractPower implements HealthBarRenderPower, CloneablePowerInterface
{
  public static final String POWER_ID = "Necrotic Poison";
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Necrotic Poison");
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
  private AbstractCreature source;
  
  public NecroticPoisonPower(AbstractCreature owner, AbstractCreature source, int poisonAmt)
  {
    this.name = NAME;
    this.ID = "Necrotic Poison";
    this.owner = owner;
    this.source = source;
    this.amount = poisonAmt;
    updateDescription();
    loadRegion("poison");
	//this.img = new Texture("img/powers/NecroticPoison.png");
    this.type = AbstractPower.PowerType.DEBUFF;
    
    this.isTurnBased = true;
  }
  
	@Override
    protected void loadRegion(final String fileName) {
        this.region48 = ReplayTheSpireMod.powerAtlas.findRegion("48/" + fileName);
		this.region128 = ReplayTheSpireMod.powerAtlas.findRegion("128/" + fileName);
    }
	
  public void updateDescription()
  {
    if ((this.owner == null) || (this.owner.isPlayer)) {
      this.description = (DESCRIPTIONS[0] + (this.amount * 2) + DESCRIPTIONS[1] + DESCRIPTIONS[3]);
    } else {
      this.description = (DESCRIPTIONS[2] + (this.amount * 2) + DESCRIPTIONS[1] + DESCRIPTIONS[3]);
    }
  }
  
  public void atStartOfTurn()
  {
    if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
      if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
      {
        flashWithoutSound();
        AbstractDungeon.actionManager.addToBottom(new NecroPoisonLoseHpAction(this.owner, this.source, this.amount * 2, AbstractGameAction.AttackEffect.POISON));
		if (this.owner.hasPower("Poison")){
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, null, new PoisonPower(this.owner, this.source, 1), 1, AbstractGameAction.AttackEffect.NONE));
		}
      }
    }
  }
  /*
  public void atEndOfTurn(final boolean isPlayer)
  {
    if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
      if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
      {
        flashWithoutSound();
        AbstractDungeon.actionManager.addToBottom(new NecroPoisonLoseHpAction(this.owner, this.source, this.amount * 2, AbstractGameAction.AttackEffect.POISON));
      }
    }
  }
  */
	@Override
	public Color getColor() {
		return Color.PURPLE;
	}

	@Override
	public int getHealthBarAmount() {
		return this.amount * 2;
	}

	@Override
	public AbstractPower makeCopy() {
		return new NecroticPoisonPower(owner, source, amount);
	}
}
