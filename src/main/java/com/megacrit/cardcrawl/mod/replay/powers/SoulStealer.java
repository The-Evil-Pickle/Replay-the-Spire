package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;

import java.util.ArrayList;
import java.util.Collections;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SoulStealer extends AbstractPower
{
    public static final String POWER_ID = "FF_Thievery";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public SoulStealer(final AbstractCreature owner, final int amt) {
        this.name = SoulStealer.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        this.updateDescription();
        this.loadRegion("noPain");
    }

    @Override
    public void onAttack(final DamageInfo info, final int damageAmount, final AbstractCreature target) {
        if (damageAmount > 0 && target != this.owner && info.type == DamageInfo.DamageType.NORMAL) {
            ArrayList<AbstractPower> buffs = new ArrayList<AbstractPower>();
            for (AbstractPower p : target.powers) {
            	if ((p instanceof CloneablePowerInterface) && p.type == AbstractPower.PowerType.BUFF && p.amount > 0) {
            		buffs.add(p);
            	}
            }
            for (int i=0; i < this.amount; i++) {
            	Collections.shuffle(buffs);
            	for (AbstractPower p : buffs) {
            		AbstractCreature originOwner = p.owner;
            		p.owner = this.owner;
            		AbstractPower pc = ((CloneablePowerInterface)p).makeCopy();
            		p.owner = originOwner;
            		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(target, this.owner, p.ID, 1));
            		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, pc, 1));
            		if (pc.amount > 1 && !this.owner.hasPower(pc.ID)) {
            			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, pc.ID, pc.amount - 1));
            		}
            	}
            }
        }
    }
    
    @Override
    public void updateDescription() {
        this.description = SoulStealer.DESCRIPTIONS[0] + this.owner.name + SoulStealer.DESCRIPTIONS[1] + this.amount + SoulStealer.DESCRIPTIONS[2];
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = SoulStealer.powerStrings.NAME;
        DESCRIPTIONS = SoulStealer.powerStrings.DESCRIPTIONS;
    }
}
