package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;

import org.apache.logging.log4j.*;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;

public class OffTheRailsPower extends AbstractPower implements CloneablePowerInterface
{
    private static final Logger logger;
    public static final String POWER_ID = "Replay:Off the Rails";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public OffTheRailsPower(final AbstractCreature owner, final int reduceTo) {
        this.name = OffTheRailsPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = reduceTo;
        this.updateDescription();
        this.loadRegion("dexterity");
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        
    }
    
    @Override
    public void stackPower(final int stackAmount) {
        if (this.amount <= 0) {
            OffTheRailsPower.logger.info(this.name + " does not stack");
            return;
        }
        this.fontScale = 8.0f;
        this.amount -= stackAmount;
        this.updateDescription();
    }
    public boolean patchAttacked(final DamageInfo info) {
        if (detectedAoE) {
        	int dmg = info.output;
        	if (this.amount < dmg) {
        		dmg = this.amount;
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
    static {
        logger = LogManager.getLogger(OffTheRailsPower.class.getName());
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = OffTheRailsPower.powerStrings.NAME;
        DESCRIPTIONS = OffTheRailsPower.powerStrings.DESCRIPTIONS;
    }
	@Override
	public AbstractPower makeCopy() {
		return new OffTheRailsPower(owner, amount);
	}
}
