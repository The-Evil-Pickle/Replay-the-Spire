package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.defect.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;

import com.megacrit.cardcrawl.dungeons.*;

import org.apache.logging.log4j.*;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;

public class ForgedInHellfirePower extends TwoAmountPower implements CloneablePowerInterface
{
    private static final Logger logger;
    public static final String POWER_ID = "Replay:Forged in Hellfire";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public ForgedInHellfirePower(final AbstractCreature owner, final int reduceBy, final int reduceTo) {
        this.name = ForgedInHellfirePower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = reduceBy;
        this.amount2 = reduceTo;
        this.updateDescription();
        this.loadRegion("flameBarrier");
    }

    @Override
    public void updateDescription() {
        this.description = "";
        if (this.amount > 0) {
        	this.description += DESCRIPTIONS[0] + DESCRIPTIONS[2] + this.amount + ((this.amount2 > 0) ? DESCRIPTIONS[3] + (this.amount2 + this.amount) : ".");
        }
        if (this.amount2 > 0) {
        	this.description += ((this.amount > 0) ? DESCRIPTIONS[4] : DESCRIPTIONS[0]) + DESCRIPTIONS[1] + this.amount2 + ((this.amount > 0) ? DESCRIPTIONS[5] + (this.amount2 + this.amount) : ".");
        }
        
    }
    
    @Override
    public void stackPower(final int stackAmount) {
        if (this.amount == -1) {
            ForgedInHellfirePower.logger.info(this.name + " does not stack");
            return;
        }
        this.fontScale = 8.0f;
        if (stackAmount > 0) {
        	this.amount2 = Math.min(amount2, stackAmount);
        } else {
        	this.amount -= stackAmount;
        }
        this.updateDescription();
    }
    /*@Override
    public float atDamageReceive(final float damageAmount, final DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.THORNS && damageAmount > 0) {
        	float dmg = damageAmount;
        	if (this.amount > 0) {
        		dmg = Math.max(0, dmg - this.amount);
        	}
        	if (this.amount2 < dmg) {
        		dmg = this.amount2;
        	}
        	if (dmg != damageAmount) {
        		this.flash();
        		return dmg;
        	}
        }
        return damageAmount;
    }*/
    public boolean patchAttacked(final DamageInfo info) {
    	int dmg = info.output;
    	if (this.amount > 0) {
    		dmg = Math.max(0, dmg - this.amount);
    	}
    	if (this.amount2 < dmg) {
    		dmg = this.amount2;
    	}
    	if (dmg != info.output) {
    		info.output = dmg;
    		this.flash();
    		return true;
    	}
        return false;
    }
    
    static {
        logger = LogManager.getLogger(ForgedInHellfirePower.class.getName());
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = ForgedInHellfirePower.powerStrings.NAME;
        DESCRIPTIONS = ForgedInHellfirePower.powerStrings.DESCRIPTIONS;
    }

	@Override
	public AbstractPower makeCopy() {
		return new ForgedInHellfirePower(owner, amount, amount2);
	}
}
