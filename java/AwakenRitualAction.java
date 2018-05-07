package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;

public class AwakenRitualAction extends AbstractGameAction
{
    private float startingDuration;
    
    public AwakenRitualAction() {
        this.actionType = ActionType.WAIT;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }
    
    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            for (final AbstractCard c : AbstractDungeon.player.hand.group) {
				if (c instanceof RitualComponent) {
					AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
					final AbstractCard de = new DarkEchoRitualCard();
					if (c.upgraded) {
						de.upgrade();
					}
					AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(de, true));
				}
			}
        }
        this.tickDuration();
    }
}
