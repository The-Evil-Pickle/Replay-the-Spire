package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.ActionType;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;

import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class MimicHandCardAction extends AbstractGameAction
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final float DURATION_PER_CARD = 0.25f;
    private AbstractPlayer p;
    private boolean isRandom;
    private ArrayList<AbstractCard> cannotDuplicate;
    
    public MimicHandCardAction(final AbstractCreature source, final boolean isRandom) {
        this.isRandom = isRandom;
        this.cannotDuplicate = new ArrayList<AbstractCard>();
        this.setValues(AbstractDungeon.player, source, 1);
        this.actionType = ActionType.DRAW;
        this.duration = Settings.ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.size() == 1) {
                this.amount = this.p.hand.size();
                for (int tmp = this.p.hand.size(), i = 0; i < tmp; ++i) {
                    final AbstractCard c = this.p.hand.getTopCard();
					AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
                }
                CardCrawlGame.dungeon.checkForPactAchievement();
                return;
            }
            if (!this.isRandom) {
                AbstractDungeon.handCardSelectScreen.open(MimicHandCardAction.TEXT[0], 1, false, false, false, false);
                this.tickDuration();
                return;
            }
			AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(this.p.hand.getRandomCard(true).makeStatEquivalentCopy()));
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (final AbstractCard c2 : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                this.p.hand.moveToExhaustPile(c2);
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.tickDuration();
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DualWieldAction");
        TEXT = MimicHandCardAction.uiStrings.TEXT;
    }
}
