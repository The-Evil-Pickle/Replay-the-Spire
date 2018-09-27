package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class BasicFromDeckToHandAction extends AbstractGameAction
{
	private boolean isUpgraded;
    private AbstractPlayer p;
    
    public BasicFromDeckToHandAction(final int amount, final boolean isUpgraded) {
        this.setValues(this.p = AbstractDungeon.player, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
		this.isUpgraded = isUpgraded;
    }
    
    @Override
    public void update() {
        if (this.duration != Settings.ACTION_DUR_MED) {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                for (final AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    c.unhover();
                    if (this.p.hand.size() == 10) {
                        this.p.drawPile.moveToDiscardPile(c);
                        this.p.createHandIsFullDialog();
                    }
                    else {
                        this.p.drawPile.removeCard(c);
                        this.p.hand.addToTop(c);
        				if (this.isUpgraded) {
        					c.setCostForTurn(-99);
        				}
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
        for (final AbstractCard c2 : this.p.drawPile.group) {
            if (c2.rarity == AbstractCard.CardRarity.BASIC || c2.cardID == "Ghost Defend" || c2.cardID == "Ghost Swipe") {
                tmp.addToRandomSpot(c2);
            }
        }
        if (tmp.size() == 0) {
            this.isDone = true;
            return;
        }
        if (tmp.size() == 1) {
            final AbstractCard card = tmp.getTopCard();
            if (this.p.hand.size() == 10) {
                this.p.drawPile.moveToDiscardPile(card);
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
                this.p.drawPile.removeCard(card);
                AbstractDungeon.player.hand.addToTop(card);
                AbstractDungeon.player.hand.refreshHandLayout();
				if (this.isUpgraded) {
					card.setCostForTurn(-99);
				}
                AbstractDungeon.player.hand.applyPowers();
            }
            this.isDone = true;
            return;
        }
        AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose", false);
        this.tickDuration();
    }
    
}
