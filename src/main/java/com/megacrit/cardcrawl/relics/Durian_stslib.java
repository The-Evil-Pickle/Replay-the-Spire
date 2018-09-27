package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;

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
