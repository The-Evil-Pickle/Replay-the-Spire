package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.ActionType;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.ui.panels.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;

public class BlackTransmuteAction extends AbstractGameAction
{
    private boolean freeToPlayOnce;
    private boolean upgraded;
    private AbstractPlayer p;
    private int energyOnUse;
    
    public BlackTransmuteAction(final AbstractPlayer p, final boolean upgraded, final boolean freeToPlayOnce, final int energyOnUse) {
        this.p = p;
        this.upgraded = upgraded;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }
    
    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if (effect > 0) {
            for (int i = 0; i < effect; ++i) {
                final AbstractCard c = infinitespire.helpers.CardHelper.getRandomBlackCard().makeCopy();
                if (c.cost > 0) {
                	c.setCostForTurn(c.cost - 1);
                }
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, 1));
            }
            if (!this.freeToPlayOnce) {
            	//if (this.upgraded && EnergyPanel.totalCount > 0) {
            		//this.p.energy.use(EnergyPanel.totalCount - 1);
                //} else {
                	this.p.energy.use(EnergyPanel.totalCount);
                //}
                
            }
        }
        this.isDone = true;
    }
}
