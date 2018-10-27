package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class ReduceCostToForTurnAction extends AbstractGameAction
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> invalidCards;
    private boolean isAll;
    private AbstractCard.CardType typeRestriction;
    
    public ReduceCostToForTurnAction(final int amount) {
    	this(amount, false);
    }
    public ReduceCostToForTurnAction(final int amount, final boolean isAll) {
    	this(amount, isAll, null);
    }
    public ReduceCostToForTurnAction(final int amount, final boolean isAll, final AbstractCard.CardType typeRestriction) {
        this.invalidCards = new ArrayList<AbstractCard>();
        this.isAll = false;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_FAST;
        this.isAll = isAll;
        this.typeRestriction = typeRestriction;
    }
    
    private boolean isValidCard(AbstractCard c) {
    	return (c.costForTurn > this.amount && (this.typeRestriction == null || this.typeRestriction == c.type));
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.isAll) {
                for (final AbstractCard c : this.p.hand.group) {
                    if (this.isValidCard(c)) {
                        c.setCostForTurn(this.amount);
                        c.superFlash();
                    }
                }
                this.isDone = true;
                return;
            }
            for (final AbstractCard c : this.p.hand.group) {
                if (!this.isValidCard(c)) {
                    this.invalidCards.add(c);
                }
            }
            if (this.invalidCards.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.group.size() - this.invalidCards.size() == 1) {
                for (final AbstractCard c : this.p.hand.group) {
                	if (this.isValidCard(c)) {
                        c.setCostForTurn(this.amount);
                        c.superFlash();
                        this.isDone = true;
                        return;
                    }
                }
            }
            this.p.hand.group.removeAll(this.invalidCards);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(ReduceCostToForTurnAction.TEXT[0], 1, false, false, false, false);
                this.tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1) {
                this.p.hand.getTopCard().upgrade();
                this.p.hand.getTopCard().superFlash();
                this.returnCards();
                this.isDone = true;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (final AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
            	c.setCostForTurn(this.amount);
                c.superFlash();
                this.p.hand.addToTop(c);
            }
            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.tickDuration();
    }
    
    private void returnCards() {
        for (final AbstractCard c : this.invalidCards) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ReduceCostToForTurnAction");
        TEXT = ReduceCostToForTurnAction.uiStrings.TEXT;
    }
}
