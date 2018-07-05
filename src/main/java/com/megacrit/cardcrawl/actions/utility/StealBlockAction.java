package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.cards.*;
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
