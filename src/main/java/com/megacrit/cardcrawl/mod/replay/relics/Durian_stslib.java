package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;

public class Durian_stslib extends Durian
{
    public static final String ID = "Durian";
	
    public Durian_stslib() {
    	super();
    }

    @Override
    public void onDebuffRecieved(ApplyPowerAction __instance) {
    	AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(__instance.target, __instance.target, Math.abs(__instance.amount)));
    }
	
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[1] + Durian.HP_BOOST + ".";
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new Durian_stslib();
    }
}
