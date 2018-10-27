package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
//import com.megacrit.cardcrawl.relics.LandingSound;
//import com.megacrit.cardcrawl.relics.RelicTier;
import com.megacrit.cardcrawl.unlock.*;

import java.util.ArrayList;
import java.util.Collections;

public class TigerMarble extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:TigerMarble";
    private ArrayList<AbstractCard> possibleCards;
    
    public TigerMarble() {
        super(ID, "tigerMarble.png", RelicTier.COMMON, LandingSound.SOLID);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void atBattleStart() {
        this.flash();
        if (this.possibleCards == null || this.possibleCards.isEmpty()) {
        	this.onEquip();
        }
        final AbstractCard c = this.possibleCards.get(AbstractDungeon.cardRandomRng.random(this.possibleCards.size() - 1)).makeCopy();
        if (c.cost != -1) {
            c.setCostForTurn(0);
            c.freeToPlayOnce = true;
        }
        UnlockTracker.markCardAsSeen(c.cardID);
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c));
        Collections.shuffle(this.possibleCards);
    }
    
    @Override
    public void onEquip() {
    	this.possibleCards = new ArrayList<AbstractCard>();
    	for (final AbstractCard c : AbstractDungeon.srcCommonCardPool.group) {
            if (c.exhaust && c.cost <= 2) {
            	this.possibleCards.add(c);
            	this.possibleCards.add(c);
            }
        }
        for (final AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
            if (c.exhaust && c.cost <= 2) {
            	this.possibleCards.add(c);
            	this.possibleCards.add(c);
            }
        }
        for (final AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
            if (c.exhaust && c.cost <= 2) {
            	this.possibleCards.add(c);
            	this.possibleCards.add(c);
            }
        }
        for (final AbstractCard c : AbstractDungeon.srcColorlessCardPool.group) {
            if (c.exhaust && c.cost <= 2) {
            	this.possibleCards.add(c);
            }
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new TigerMarble();
    }
}
