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
        this.setValues(this.p = AbstractDungeon.player, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }
    
    @Override
    public void update() {
        if (this.duration != Settings.ACTION_DUR_MED) {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (final AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    this.p.hand.addToHand(c);
                    if (AbstractDungeon.player.hasPower("Corruption") && c.type == AbstractCard.CardType.SKILL) {
                        c.setCostForTurn(-9);
                    }
                    this.p.exhaustPile.removeCard(c);
                    c.unhover();
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
                this.p.exhaustPile.group.addAll(this.exhumes);
                this.exhumes.clear();
                for (final AbstractCard c : this.p.exhaustPile.group) {
                    c.unhover();
                    c.target_x = CardGroup.DISCARD_PILE_X;
                    c.target_y = 0.0f;
                }
            }
            this.tickDuration();
            return;
        }
        if (AbstractDungeon.player.hand.size() == 10) {
            AbstractDungeon.player.createHandIsFullDialog();
            this.isDone = true;
            return;
        }
        if (this.p.exhaustPile.isEmpty()) {
            this.isDone = true;
            return;
        }
        if (this.p.exhaustPile.size() == 1) {
            final AbstractCard c2 = this.p.exhaustPile.getTopCard();
            c2.unfadeOut();
            this.p.hand.addToHand(c2);
            if (AbstractDungeon.player.hasPower("Corruption") && c2.type == AbstractCard.CardType.SKILL) {
                c2.setCostForTurn(-9);
            }
            this.p.exhaustPile.removeCard(c2);
            c2.unhover();
            c2.fadingOut = false;
            this.isDone = true;
        }
        else {
            for (final AbstractCard c : this.p.exhaustPile.group) {
                c.stopGlowing();
                c.unhover();
                c.unfadeOut();
            }
            final Iterator<AbstractCard> c3 = this.p.exhaustPile.group.iterator();
            while (c3.hasNext()) {
                final AbstractCard derp = c3.next();
                if (derp.cardID.equals("Exhume")) {
                    c3.remove();
                    this.exhumes.add(derp);
                }
            }
            if (this.p.exhaustPile.isEmpty()) {
                this.p.exhaustPile.group.addAll(this.exhumes);
                this.exhumes.clear();
                this.isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, this.amount, true, ExhumeAction.TEXT[0]);
            this.tickDuration();
        }
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
        TEXT = MultiExhumeAction.uiStrings.TEXT;
    }
}
