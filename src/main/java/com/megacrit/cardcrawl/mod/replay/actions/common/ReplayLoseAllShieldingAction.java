package com.megacrit.cardcrawl.mod.replay.actions.common;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

import replayTheSpire.ReplayTheSpireMod;

public class ReplayLoseAllShieldingAction extends AbstractGameAction
{
    public ReplayLoseAllShieldingAction(final AbstractCreature target, final AbstractCreature source) {
        this.setValues(target, source);
    }
    
    public void update() {
    	ReplayTheSpireMod.clearShielding(target);
        this.isDone = true;
    }
}
