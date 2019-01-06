package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.unlock.*;

import java.util.ArrayList;
import java.util.Collections;

public class OnyxGauntlets extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:Onyx Gauntlets";
    private static ArrayList<AbstractCard> cardsIncreased = new ArrayList<AbstractCard>();
    public OnyxGauntlets() {
        super(ID, "hamsterWheel.png", RelicTier.BOSS, LandingSound.HEAVY);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void onCardDraw(final AbstractCard drawnCard) {
    	if (drawnCard == null) {
    		return;
    	}
    	if (drawnCard.costForTurn >= 0) {
    		drawnCard.setCostForTurn(drawnCard.costForTurn + 1);
    		for (AbstractCard c : cardsIncreased) {
    			if (c == drawnCard) {
    				drawnCard.setCostForTurn(drawnCard.costForTurn + 1);
    			}
    		}
    		cardsIncreased.add(drawnCard);
    	}
    	AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
    }

    @Override
    public void atTurnStart() {
    	cardsIncreased.clear();
    }
    
    @Override
    public void atBattleStartPreDraw() {
    	for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
    		if (c.cost == -1 && c.rawDescription.contains("X")) {
    			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(AbstractDungeon.returnRandomCurse(), 1, true, true));
    		}
    	}
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new OnyxGauntlets();
    }
}
