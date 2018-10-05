package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class MultiExhumeAction extends AbstractGameAction
{
    private AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private ArrayList<AbstractCard> exhumes;
    
    public MultiExhumeAction(final int amount) {
        this.exhumes = new ArrayList<AbstractCard>();
        this.setValues(this.p = AbstractDungeon.player, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    
    @Override
    public void update() {
        if (this.duration != Settings.ACTION_DUR_MED) {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                for (final AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    c.unhover();
                    if (this.p.hand.size() == 10) {
                        this.p.exhaustPile.moveToDiscardPile(c);
                        this.p.createHandIsFullDialog();
                    }
                    else {
                        this.p.exhaustPile.removeCard(c);
                        this.p.hand.addToTop(c);
                    }
                    this.p.hand.refreshHandLayout();
                    this.p.hand.applyPowers();
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
            }
            this.tickDuration();
            return;
        }
        final CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (final AbstractCard c2 : this.p.exhaustPile.group) {
            tmp.addToRandomSpot(c2);
        }
        if (tmp.size() == 0) {
            this.isDone = true;
            return;
        }
        if (tmp.size() == 1) {
            final AbstractCard card = tmp.getTopCard();
            if (this.p.hand.size() == 10) {
                this.p.exhaustPile.moveToDiscardPile(card);
                this.p.createHandIsFullDialog();
            }
            else {
                card.unhover();
                card.lighten(true);
                card.setAngle(0.0f);
                card.drawScale = 0.12f;
                card.targetDrawScale = 0.75f;
                card.current_x = CardGroup.DRAW_PILE_X;
                card.current_y = CardGroup.DRAW_PILE_Y;
                this.p.exhaustPile.removeCard(card);
                AbstractDungeon.player.hand.addToTop(card);
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.player.hand.applyPowers();
            }
            this.isDone = true;
            return;
        }
        if (tmp.size() <= this.amount) {
            for (int i = 0; i < tmp.size(); ++i) {
                final AbstractCard card2 = tmp.getNCardFromTop(i);
                if (this.p.hand.size() == 10) {
                    this.p.exhaustPile.moveToDiscardPile(card2);
                    this.p.createHandIsFullDialog();
                }
                else {
                    card2.unhover();
                    card2.lighten(true);
                    card2.setAngle(0.0f);
                    card2.drawScale = 0.12f;
                    card2.targetDrawScale = 0.75f;
                    card2.current_x = CardGroup.DRAW_PILE_X;
                    card2.current_y = CardGroup.DRAW_PILE_Y;
                    this.p.exhaustPile.removeCard(card2);
                    AbstractDungeon.player.hand.addToTop(card2);
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.applyPowers();
                }
            }
            this.isDone = true;
            return;
        }
        if (this.amount == 1) {
            AbstractDungeon.gridSelectScreen.open(tmp, this.amount, SeekAction.TEXT[0], false);
        }
        else {
            AbstractDungeon.gridSelectScreen.open(tmp, this.amount, SeekAction.TEXT[1], false);
        }
        this.tickDuration();
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
        TEXT = MultiExhumeAction.uiStrings.TEXT;
    }
}
