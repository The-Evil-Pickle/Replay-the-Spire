package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.*;

import basemod.interfaces.CloneablePowerInterface;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;

public class DecrepitPower extends AbstractPower implements CloneablePowerInterface
{
    public static final String POWER_ID = "Decrepit";
    public static final String NAME = "Decrepit";
    public static final String[] DESCRIPTIONS;
    public static final String IMG = "powers/decrepit.png";
    private boolean justApplied;
    
    static {
        DESCRIPTIONS = new String[] { "All damage from attacks is increased by #b" };
    }
    
    public DecrepitPower(final AbstractCreature owner, final int amount, final boolean justApplied) {
        this.justApplied = false;
        this.name = "Decrepit";
        this.ID = "Decrepit";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/decrepit.png");
        this.isTurnBased = true;
        this.type = AbstractPower.PowerType.DEBUFF;
        this.justApplied = justApplied;
    }
    
    public void updateDescription() {
        this.description = String.valueOf(DecrepitPower.DESCRIPTIONS[0]) + this.amount + ".";
    }
    
    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        if (AbstractDungeon.player.hasRelic("WalkingCane")) {
            AbstractDungeon.player.getRelic("WalkingCane").flash();
            return;
        }
        if (this.amount <= 1) {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, "Decrepit"));
        }
        else {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ReducePowerAction(this.owner, this.owner, "Decrepit", 1));
        }
    }
    
    public float atDamageReceive(final float damage, final DamageInfo.DamageType damageType) {
        if (damageType == DamageInfo.DamageType.NORMAL) {
            return damage + this.amount;
        }
        return damage;
    }

	@Override
	public AbstractPower makeCopy() {
		return new DecrepitPower(owner, amount, justApplied);
	}
}
