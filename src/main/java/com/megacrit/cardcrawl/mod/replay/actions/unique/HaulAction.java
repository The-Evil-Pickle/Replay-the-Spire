package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.dungeons.*;

import basemod.BaseMod;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cards.*;
import com.badlogic.gdx.*;

public class HaulAction extends AbstractGameAction
{
    
    public HaulAction() {
        if (AbstractDungeon.player.hasPower("No Draw")) {
            AbstractDungeon.player.getPower("No Draw").flash();
            this.setValues(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.player.drawPile.size());
            this.isDone = true;
            this.duration = 0.0f;
            this.actionType = ActionType.WAIT;
            return;
        }
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.player.drawPile.size());
        this.actionType = ActionType.DRAW;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        }
        else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }
    }
    
    @Override
    public void update() {
        if (this.amount <= 0) {
            this.isDone = true;
            return;
        }
        final int deckSize = AbstractDungeon.player.drawPile.size();
        final int discardSize = AbstractDungeon.player.discardPile.size();
        if (SoulGroup.isActive()) {
            return;
        }
        if (deckSize + discardSize == 0) {
            this.isDone = true;
            return;
        }
        if (AbstractDungeon.player.hand.size() == 10) {
            AbstractDungeon.player.createHandIsFullDialog();
            this.isDone = true;
            return;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0f) {
            if (Settings.FAST_MODE) {
                this.duration = Settings.ACTION_DUR_XFAST;
            }
            else {
                this.duration = Settings.ACTION_DUR_FASTER;
            }
            if (!AbstractDungeon.player.drawPile.isEmpty()) {
            	if (AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE) {
            		AbstractCard c = AbstractDungeon.player.drawPile.getTopCard();
            		AbstractDungeon.player.drawPile.moveToDiscardPile(c);
                    GameActionManager.incrementDiscard(false);
                    c.triggerOnManualDiscard();
            	} else {
            		AbstractDungeon.player.draw();
                    AbstractDungeon.player.hand.refreshHandLayout();
            	}
            }
            else {
                this.isDone = true;
            }
            if (this.amount == 0) {
                this.isDone = true;
            }
        }
    }
}
