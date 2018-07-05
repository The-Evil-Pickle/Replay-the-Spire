package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
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
