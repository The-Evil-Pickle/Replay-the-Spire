package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
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
