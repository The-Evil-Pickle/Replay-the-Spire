package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.defect.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;

import org.apache.logging.log4j.*;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;

public class ForgedInHellfirePower extends AbstractPower implements CloneablePowerInterface
{
    private static final Logger logger;
    public static final String POWER_ID = "Replay:Forged in Hellfire";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public ForgedInHellfirePower(final AbstractCreature owner, final int amt) {
        this.name = ForgedInHellfirePower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        this.updateDescription();
        this.loadRegion("flameBarrier");
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        
    }
    public boolean patchAttacked(final DamageInfo info) {
    	int dmg = info.output;
    	dmg *= 100;
    	dmg /= this.amount;
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
		return new ForgedInHellfirePower(owner, amount);
	}
}
