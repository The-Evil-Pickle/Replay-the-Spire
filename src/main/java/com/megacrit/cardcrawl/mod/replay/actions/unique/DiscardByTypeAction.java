package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;
import java.util.*;

public class DiscardByTypeAction extends AbstractGameAction
{
	private AbstractCard.CardType cardType;
	private boolean inverted;
    public DiscardByTypeAction(final AbstractCreature source, final AbstractCard.CardType cardType, final int amount) {
    	this(source, cardType, amount, false);
    }
    public DiscardByTypeAction(final AbstractCreature source, final AbstractCard.CardType cardType, final int amount, final boolean invert) {
        this.source = source;
        this.amount = amount;
        this.inverted = invert;
        this.cardType = cardType;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
        	ArrayList<AbstractCard> validTargets = new ArrayList<AbstractCard>();
        	for (final AbstractCard c : AbstractDungeon.player.hand.group) {
                if ((c.type == this.cardType) != this.inverted) {
                	validTargets.add(c);
                }
            }
        	if (this.amount < 0 || this.amount >= validTargets.size()) {
        		for (final AbstractCard c : validTargets) {
        			AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(c));
                }
        	} else {
        		Collections.shuffle(validTargets);
        		for (int j = 0; j < this.amount && j < validTargets.size(); j++) {
        			AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(validTargets.get(j)));
                }
        	}
            
            this.isDone = true;
        }
    }
}
