package com.megacrit.cardcrawl.mod.replay.actions.replayxover;

import com.megacrit.cardcrawl.core.*;

import beaked.actions.ReplenishWitherAction;
import beaked.cards.AbstractWitherCard;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.*;

public class WingHatAction extends AbstractGameAction
{
    public WingHatAction() {
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.WAIT;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
        	for (AbstractCard c : AbstractDungeon.player.hand.group) {
            	if (c instanceof AbstractWitherCard) {
            		AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            		AbstractDungeon.actionManager.addToTop(new ReplenishWitherAction((AbstractWitherCard)c));
            	}
            }
            this.isDone = true;
        }
    }
}
