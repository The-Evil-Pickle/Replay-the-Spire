package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.relics.*;
import com.megacrit.cardcrawl.mod.replay.ui.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.dungeons.*;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;

public class ShuffleRareAction extends AbstractGameAction
{
    private boolean shuffled;
    private boolean vfxDone;
    private int count;
    
    public ShuffleRareAction() {
        this.shuffled = true;
        this.vfxDone = false;
        this.setValues(null, null, this.count = 0);
        this.actionType = ActionType.SHUFFLE;
        for (final AbstractRelic r : AbstractDungeon.player.relics) {
            r.onShuffle();
        }
    }
    
    @Override
    public void update() {
        if (!this.shuffled) {
            this.shuffled = true;
            AbstractDungeon.player.discardPile.shuffle(AbstractDungeon.shuffleRng);
        }
        if (!this.vfxDone) {
            final Iterator<AbstractCard> c = AbstractDungeon.player.discardPile.group.iterator();
            while (c.hasNext()) {
                ++this.count;
                final AbstractCard e = c.next();
				if (e.rarity == AbstractCard.CardRarity.RARE) {
					c.remove();
					if (this.count < 11) {
						AbstractDungeon.getCurrRoom().souls.shuffle(e, false);
					}
					else {
						AbstractDungeon.getCurrRoom().souls.shuffle(e, true);
					}
	                return;
				}
            }
            this.vfxDone = true;
        }
        this.isDone = true;
    }
}
