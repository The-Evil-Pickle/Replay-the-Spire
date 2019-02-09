package com.megacrit.cardcrawl.mod.replay.actions.replayxover;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.localization.*;

import runesmith.actions.EnhanceCard;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class MistArmamentsBAction extends AbstractGameAction
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotUpgrade;
    private boolean upgraded;
    
    public MistArmamentsBAction(final boolean armamentsPlus) {
        this.cannotUpgrade = new ArrayList<AbstractCard>();
        this.upgraded = false;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = armamentsPlus;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.upgraded) {
                for (final AbstractCard c : this.p.hand.group) {
                    if (c.canUpgrade()) {
                        c.upgrade();
                        c.superFlash();
                    } else if (EnhanceCard.canEnhance(c)) {
                		EnhanceCard.enhance(c);
                		c.superFlash();
                    }
                }
                this.isDone = true;
                return;
            }
            for (final AbstractCard c : this.p.hand.group) {
                if (!c.canUpgrade() && !EnhanceCard.canEnhance(c)) {
                    this.cannotUpgrade.add(c);
                }
            }
            if (this.cannotUpgrade.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.group.size() - this.cannotUpgrade.size() == 1) {
                for (final AbstractCard c : this.p.hand.group) {
                    if (c.canUpgrade()) {
                        c.upgrade();
                        c.superFlash();
                        this.isDone = true;
                        return;
                    } else if (EnhanceCard.canEnhance(c)) {
                		EnhanceCard.enhance(c);
                		c.superFlash();
                		this.isDone = true;
                        return;
                    }
                }
            }
            this.p.hand.group.removeAll(this.cannotUpgrade);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(MistArmamentsBAction.TEXT[0], 1, false, false, false, true);
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
            	if (c.canUpgrade()) {
                    c.upgrade();
                    c.superFlash();
                } else if (EnhanceCard.canEnhance(c)) {
            		EnhanceCard.enhance(c);
            		c.superFlash();
                }
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
        for (final AbstractCard c : this.cannotUpgrade) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ArmamentsAction");
        TEXT = MistArmamentsBAction.uiStrings.TEXT;
    }
}
