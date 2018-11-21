package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.Collections;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
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
        this.loadRegion("unawakened");
    }

    @Override
    public void onAttack(final DamageInfo info, final int damageAmount, final AbstractCreature target) {
        if (damageAmount > 0 && target != this.owner && info.type == DamageInfo.DamageType.NORMAL) {
            ArrayList<AbstractPower> buffs = new ArrayList<AbstractPower>();
            for (AbstractPower p : target.powers) {
            	if (p.type == AbstractPower.PowerType.BUFF && p.amount > 0) {
            		buffs.add(p);
            	}
            }
            for (int i=0; i < this.amount; i++) {
            	Collections.shuffle(buffs);
            	for (AbstractPower p : buffs) {
            		if (p.amount > 0) {
            			
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
