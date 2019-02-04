package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.events.shrines.ChaosEvent;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
//import com.megacrit.cardcrawl.relics.LandingSound;
//import com.megacrit.cardcrawl.relics.RelicTier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.*;

import java.util.*;
import tobyspowerhouse.powers.*;

public class RingOfPanic extends AbstractRelic
{
    public static final String ID = "Ring of Panic";
    private static final int CONF = 3;
    private int turnCount;
    public RingOfPanic() {
        super("Ring of Panic", "cring_panic.png", RelicTier.SPECIAL, LandingSound.FLAT);
        this.turnCount = 0;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + RingOfPanic.CONF + this.DESCRIPTIONS[1];
    }
	
    @Override
    public void atPreBattle() {
        this.flash();
		this.pulse = true;
        this.turnCount = 0;
		AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TPH_ConfusionPower(AbstractDungeon.player, RingOfPanic.CONF, false), RingOfPanic.CONF, true));
    }

    @Override
    public int getPrice() {
    	return ChaosEvent.RING_SHOP_PRICE;
    }
    @Override
    public void atTurnStart() {
		this.turnCount++;
        if (this.turnCount < 3) {
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(2));
        } else if (this.turnCount == 3) {
			AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
			this.pulse = false;
		}
    }
	
    @Override
    public void onVictory() {
		this.pulse = false;
	}
    @Override
    public AbstractRelic makeCopy() {
        return new RingOfPanic();
    }
}
