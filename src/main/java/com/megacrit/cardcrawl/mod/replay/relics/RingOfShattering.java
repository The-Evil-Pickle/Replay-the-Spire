package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.defect.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
//import com.megacrit.cardcrawl.relics.LandingSound;
//import com.megacrit.cardcrawl.relics.RelicTier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.core.*;

public class RingOfShattering extends AbstractRelic
{
    public static final String ID = "Ring of Shattering";
    private static final int SLOTS = 2;
    private static final int FOCUS = 3;
    private boolean firstTurn;
    
    public RingOfShattering() {
        super("Ring of Shattering", "cring_shattering.png", RelicTier.SPECIAL, LandingSound.FLAT);
        this.firstTurn = true;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + RingOfShattering.FOCUS + this.DESCRIPTIONS[1] + RingOfShattering.SLOTS + this.DESCRIPTIONS[2];
    }
	
    @Override
    public void atPreBattle() {
        this.firstTurn = true;
    }
    
    @Override
    public void atTurnStart() {
        if (this.firstTurn) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, RingOfShattering.FOCUS), RingOfShattering.FOCUS));
            AbstractDungeon.actionManager.addToBottom(new DecreaseMaxOrbAction(RingOfShattering.SLOTS));
            this.firstTurn = false;
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new RingOfShattering();
    }
}
