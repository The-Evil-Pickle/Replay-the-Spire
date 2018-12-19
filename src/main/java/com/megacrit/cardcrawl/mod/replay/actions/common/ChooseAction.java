package com.megacrit.cardcrawl.mod.replay.actions.common;

import java.util.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.*;


//stole this code from mad science mod. Thanks Twanvl!
public class ChooseAction extends AbstractGameAction
{
    AbstractCard baseCard;
    AbstractMonster target;
    CardGroup choices;
    ArrayList<Runnable> actions;
    String message;
    
    public ChooseAction(final AbstractCard baseCard, final AbstractMonster target, final String message) {
        this(baseCard, target, message, 1);
    }

    public ChooseAction(final AbstractCard baseCard, final AbstractMonster target, final String message, final int toChoose) {
        this.choices = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        this.actions = new ArrayList<Runnable>();
        this.setValues((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, 1);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.baseCard = baseCard;
        this.message = message;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.amount = toChoose;
    }
    public void add(final String name, final String description, final Runnable action) {
        final AbstractCard choice = this.baseCard.makeStatEquivalentCopy();
        choice.name = name;
        choice.rawDescription = description;
        choice.initializeDescription();
        if (this.target != null) {
            choice.calculateCardDamage(this.target);
        }
        else {
            choice.applyPowers();
        }
        this.choices.addToTop(choice);
        this.actions.add(action);
        this.amount = 1;
    }
    
    public void update() {
        if (this.choices.isEmpty()) {
            this.tickDuration();
            this.isDone = true;
            return;
        }
        if (this.duration != Settings.ACTION_DUR_FASTER) {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            	for (int j = 0; j < AbstractDungeon.gridSelectScreen.selectedCards.size(); j++) {
            		final AbstractCard pick = AbstractDungeon.gridSelectScreen.selectedCards.get(j);
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    final int i = this.choices.group.indexOf(pick);
                    this.actions.get(i).run();
            	}
            }
            this.tickDuration();
            return;
        }
        if (this.choices.size() > this.amount) {
            AbstractDungeon.gridSelectScreen.open(this.choices, this.amount, this.message, false, false, false, false);
            this.tickDuration();
            return;
        }
        for (int i=0; i < this.amount && i < this.actions.size(); i++) {
        	this.actions.get(0).run();
        }
        this.tickDuration();
        this.isDone = true;
    }
}
