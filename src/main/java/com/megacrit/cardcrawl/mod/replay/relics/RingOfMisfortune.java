package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.curses.*;
import com.megacrit.cardcrawl.mod.replay.events.shrines.ChaosEvent;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
//import com.megacrit.cardcrawl.relics.LandingSound;
//import com.megacrit.cardcrawl.relics.RelicTier;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.math.*;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;

import java.util.*;

public class RingOfMisfortune extends AbstractRelic
{
    public static final String ID = "Ring of Misfortune";
    private static final int BLOCK = 3;
    
    public RingOfMisfortune() {
        super("Ring of Misfortune", "cring_misfortune.png", RelicTier.SPECIAL, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + RingOfMisfortune.BLOCK + this.DESCRIPTIONS[1] + RingOfMisfortune.BLOCK + this.DESCRIPTIONS[2];
    }
    
    public void atBattleStartPreDraw() {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(AbstractDungeon.returnRandomCurse(), 1));
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.flash();
    }

    @Override
    public int getPrice() {
    	return ChaosEvent.RING_SHOP_PRICE;
    }
    public void onCardDraw(final AbstractCard drawnCard) {
        if (drawnCard.type == AbstractCard.CardType.CURSE || drawnCard.color == AbstractCard.CardColor.CURSE) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
			AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, RingOfMisfortune.BLOCK));
            this.flash();
        } else if (drawnCard.type == AbstractCard.CardType.STATUS) {
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, RingOfMisfortune.BLOCK));
            this.flash();
		}
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new RingOfMisfortune();
    }
}
