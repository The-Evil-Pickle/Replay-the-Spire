package com.megacrit.cardcrawl.mod.replay.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import java.util.*;

public class StealBlockAction extends AbstractGameAction
{
    public StealBlockAction(final AbstractCreature target, final AbstractCreature source, final int amount) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.BLOCK;
    }
    
    @Override
    public void update() {
        if (this.duration == 0.5f) {
            if (this.target.currentBlock == 0) {
                this.isDone = true;
                return;
            }
            this.target.loseBlock(this.amount);
			this.source.addBlock(this.amount);
            for (final AbstractCard c : AbstractDungeon.player.hand.group) {
                c.applyPowers();
            }
        }
        this.tickDuration();
    }
}
