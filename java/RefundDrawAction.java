package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.ui.panels.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class RefundDrawAction extends AbstractGameAction
{
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotUpgrade;
	private int energyOnUse;
	private boolean freeToPlayOnce;
	private int refundAmount;
    
    public RefundDrawAction(final AbstractPlayer p, final int refundAmount, final boolean freeToPlayOnce, final int energyOnUse) {
        this.cannotUpgrade = new ArrayList<AbstractCard>();
        this.refundAmount = 0;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = p;
		this.refundAmount = refundAmount;
		this.freeToPlayOnce = freeToPlayOnce;
		this.energyOnUse = energyOnUse;
        this.duration = Settings.ACTION_DUR_XFAST;
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
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.p, effect));
            if (!this.freeToPlayOnce) {
                this.p.energy.use(Math.max(0, EnergyPanel.totalCount - this.refundAmount));
            }
        }
        this.isDone = true;
    }
    
    
}
