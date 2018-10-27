package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;

import java.util.*;

public class WispExhaustAction extends AbstractGameAction
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    public static int numExhausted;
    
    
    public WispExhaustAction(final AbstractCreature target, final AbstractCreature source) {
        this.p = (AbstractPlayer)target;
        this.setValues(target, source, 1);
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.EXHAUST;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            }
			//AbstractDungeon.handCardSelectScreen.open(ExhaustAction.TEXT[0], this.amount, this.anyNumber, this.canPickZero);
			final CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (final AbstractCard c2 : this.p.drawPile.group) {
				if (c2.type != AbstractCard.CardType.CURSE) {
					tmp.addToRandomSpot(c2);
				}
			}
			for (final AbstractCard c2 : this.p.discardPile.group) {
				if (c2.type != AbstractCard.CardType.CURSE) {
					tmp.addToRandomSpot(c2);
				}
			}
			AbstractDungeon.gridSelectScreen.open(tmp, 1, WispExhaustAction.TEXT[0], false);
			this.tickDuration();
			return;
        }
		if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
			for (final AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
				this.p.drawPile.moveToExhaustPile(c);
				this.p.discardPile.moveToExhaustPile(c);
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.isDone = true;
		}
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = WispExhaustAction.uiStrings.TEXT;
    }
}
