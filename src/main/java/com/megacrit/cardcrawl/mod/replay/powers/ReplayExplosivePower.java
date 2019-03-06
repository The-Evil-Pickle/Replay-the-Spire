package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import basemod.interfaces.CloneablePowerInterface;

import com.megacrit.cardcrawl.actions.animations.*;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class ReplayExplosivePower extends TwoAmountPower implements CloneablePowerInterface
{
    public static final String POWER_ID = "Replay:Explosive";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public ReplayExplosivePower(final AbstractCreature owner, final int countdown, final int damage) {
        this.name = ReplayExplosivePower.NAME;
        this.ID = "Replay:Explosive";
        this.owner = owner;
        this.amount = countdown;
        this.amount2 = damage;
        this.updateDescription();
        this.loadRegion("explosive");
    }
    
    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = ReplayExplosivePower.DESCRIPTIONS[3] + this.amount2 + ReplayExplosivePower.DESCRIPTIONS[2];
        }
        else {
            this.description = ReplayExplosivePower.DESCRIPTIONS[0] + this.amount + ReplayExplosivePower.DESCRIPTIONS[1] + this.amount2 + ReplayExplosivePower.DESCRIPTIONS[2];
        }
    }
    
    @Override
    public void duringTurn() {
        if (this.amount == 1 && !this.owner.isDying) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(this.owner.hb.cX, this.owner.hb.cY), 0.1f));
            AbstractDungeon.actionManager.addToBottom(new SuicideAction((AbstractMonster)this.owner));
            final DamageInfo damageInfo = new DamageInfo(this.owner, this.amount2, DamageInfo.DamageType.THORNS);
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, damageInfo, AbstractGameAction.AttackEffect.FIRE, true));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
            this.updateDescription();
        }
    }
    @Override
    public void stackPower(final int stackAmount) {
    	if (stackAmount > 0) {
    		this.amount2 += stackAmount;
    	}
        this.fontScale = 8.0f;
    }
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Explosive");
        NAME = ReplayExplosivePower.powerStrings.NAME;
        DESCRIPTIONS = ReplayExplosivePower.powerStrings.DESCRIPTIONS;
    }

	@Override
	public AbstractPower makeCopy() {
		return new ReplayExplosivePower(this.owner, this.amount, this.amount2);
	}
}
