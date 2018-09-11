package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.actions.common.*;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.*;

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
        final AbstractCard c = this.possibleCards.get(AbstractDungeon.cardRandomRng.random(this.possibleCards.size() - 1)).makeCopy();
        if (c.cost != -1) {
            c.setCostForTurn(0);
        }
        UnlockTracker.markCardAsSeen(c.cardID);
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c));
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
