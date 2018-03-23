package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import java.util.*;

public class AbandonAction extends AbstractGameAction
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean anyNumber;
    private boolean canPickZero;
    public static int numExhausted;
    /*
    public AbandonAction(final AbstractCreature target, final AbstractCreature source, final int amount, final boolean isRandom) {
        this(target, source, amount, isRandom, false, false);
    }
    
    public AbandonAction(final AbstractCreature target, final AbstractCreature source, final int amount, final boolean isRandom, final boolean anyNumber, final boolean canPickZero) {
        this.canPickZero = false;
        this.anyNumber = anyNumber;
        this.canPickZero = canPickZero;
        this.p = (AbstractPlayer)target;
        this.isRandom = isRandom;
        this.setValues(target, source, amount);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
    }
    
    public AbandonAction(final AbstractCreature target, final AbstractCreature source, final int amount, final boolean isRandom, final boolean anyNumber) {
        this(target, source, amount, isRandom, anyNumber, false);
    }
	*/
	public AbandonAction(final AbstractCreature target, final AbstractCreature source, final int amount, final boolean isUpgraded) {
		this.canPickZero = false;
        this.anyNumber = isUpgraded;
        this.canPickZero = isUpgraded;
        this.p = (AbstractPlayer)target;
        this.isRandom = false;
        this.setValues(target, source, amount);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
	}
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            }
            if (!this.anyNumber && this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                ExhaustAction.numExhausted = this.amount;
                for (int tmp = this.p.hand.size(), i = 0; i < tmp; ++i) {
                    final AbstractCard c = this.p.hand.getTopCard();
                    this.p.hand.moveToExhaustPile(c);
                }
                CardCrawlGame.dungeon.checkForPactAchievement();
                return;
            }
            if (!this.isRandom) {
                ExhaustAction.numExhausted = this.amount;
                AbstractDungeon.handCardSelectScreen.open(AbandonAction.TEXT[0], this.amount, this.anyNumber, this.canPickZero);
                this.tickDuration();
                return;
            }
            for (int j = 0; j < this.amount; ++j) {
                this.p.hand.moveToExhaustPile(this.p.hand.getRandomCard(false));
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (final AbstractCard c2 : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                this.p.hand.moveToExhaustPile(c2);
				AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.p, 1));
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.tickDuration();
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = AbandonAction.uiStrings.TEXT;
    }
}
