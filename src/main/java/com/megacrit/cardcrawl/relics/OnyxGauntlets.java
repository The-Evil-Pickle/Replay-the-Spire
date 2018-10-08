package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.actions.common.*;

import java.util.ArrayList;
import java.util.Collections;

import com.megacrit.cardcrawl.actions.*;

public class OnyxGauntlets extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:Onyx Gauntlets";
    
    public OnyxGauntlets() {
        super(ID, "betaRelic.png", RelicTier.BOSS, LandingSound.HEAVY);
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
    	}
    	AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
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
