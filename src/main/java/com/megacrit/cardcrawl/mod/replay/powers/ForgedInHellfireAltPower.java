package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.defect.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;
import replayTheSpire.ReplayTheSpireMod;

import org.apache.logging.log4j.*;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;

public class ForgedInHellfireAltPower extends AbstractPower implements CloneablePowerInterface
{
    public static final String POWER_ID = "Replay:Forged in Hellfire Alt";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public ForgedInHellfireAltPower(final AbstractCreature owner, final int amt) {
        this.name = ForgedInHellfireAltPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        this.updateDescription();
        this.loadRegion("flameBarrier");
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + ReplayTheSpireMod.LOC_FULLSTOP;
        
    }
    public boolean patchAttacked(final DamageInfo info) {
    	int dmg = info.output;
    	dmg -= this.amount;
    	if (dmg != info.output) {
    		info.output = dmg;
    		this.flash();
    		return true;
    	}
        return false;
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Replay:Forged in Hellfire");
        NAME = ForgedInHellfireAltPower.powerStrings.NAME;
        DESCRIPTIONS = ForgedInHellfireAltPower.powerStrings.DESCRIPTIONS;
    }

	@Override
	public AbstractPower makeCopy() {
		return new ForgedInHellfireAltPower(owner, amount);
	}
}
