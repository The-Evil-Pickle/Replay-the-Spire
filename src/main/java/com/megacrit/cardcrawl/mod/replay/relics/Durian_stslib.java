package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import replayTheSpire.ReplayTheSpireMod;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;

public class Durian_stslib extends Durian implements OnReceivePowerRelic
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

	@Override
	public boolean onReceivePower(AbstractPower powerToApply, AbstractCreature source) {
		if (powerToApply.type == AbstractPower.PowerType.DEBUFF && !powerToApply.ID.equals(LoseStrengthPower.POWER_ID) && !powerToApply.ID.equals(LoseDexterityPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, Math.abs(powerToApply.amount)));
			AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		}
		return true;
	}
}
