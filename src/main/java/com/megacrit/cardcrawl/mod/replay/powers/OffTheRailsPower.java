package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.defect.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.ReflectionHacks;
import replayTheSpire.patches.BottlePatches;

import com.megacrit.cardcrawl.dungeons.*;

import org.apache.logging.log4j.*;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;

public class OffTheRailsPower extends TwoAmountPower
{
    private static final Logger logger;
    public static final String POWER_ID = "Replay:Off the Rails";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public OffTheRailsPower(final AbstractCreature owner, final int reduceBy, final int reduceTo) {
        this.name = OffTheRailsPower.NAME;
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
            OffTheRailsPower.logger.info(this.name + " does not stack");
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
        if (detectedAoE && damageAmount > 0) {
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
        if (detectedAoE) {
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
        }
        return false;
    }
    public static boolean detectedAoE = false;
    @SpirePatch(cls="com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction", method="update")
    public static class DamageAllPatch {
    	public static void Prefix(DamageAllEnemiesAction __Instance) {
    		OffTheRailsPower.detectedAoE = true;
    	}
    	public static void Postfix(DamageAllEnemiesAction __Instance) {
    		OffTheRailsPower.detectedAoE = false;
    	}
    }
    /*@SpirePatch(cls="com.megacrit.cardcrawl.cards.AbstractCard", method="calculateCardDamage")
    public static class CardDamagePatch {
    	public static void Prefix(AbstractCard __Instance, final AbstractMonster mo) {
    		OffTheRailsPower.detectedAoE = (boolean)ReflectionHacks.getPrivate(__Instance, AbstractCard.class, "isMultiDamage");
    	}
    	public static void Postfix(DamageAllEnemiesAction __Instance) {
    		OffTheRailsPower.detectedAoE = false;
    	}
    }*/
    static {
        logger = LogManager.getLogger(OffTheRailsPower.class.getName());
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = OffTheRailsPower.powerStrings.NAME;
        DESCRIPTIONS = OffTheRailsPower.powerStrings.DESCRIPTIONS;
    }
}
