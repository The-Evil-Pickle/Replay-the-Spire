package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
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
        return this.DESCRIPTIONS[0] + RingOfMisfortune.BLOCK + this.DESCRIPTIONS[1];
    }
    
    
    public void atBattleStartPreDraw() {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(this.getCurse(), 1));
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.flash();
    }
    
    public void onCardDraw(final AbstractCard drawnCard) {
        if (drawnCard.type == AbstractCard.CardType.CURSE) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, RingOfMisfortune.BLOCK));
            this.flash();
        }
    }
    
    protected AbstractCard getCurse() {
        final AbstractCard[] pool = { new Decay(), new Doubt(), new Injury(), new Normality(), new Regret(), new Voices(), new LoomingEvil(), new Overencumbered(), new Delirium(), new SpreadingInfection(), new Sickly()};
        return pool[MathUtils.random(0, pool.length - 1)];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new RingOfMisfortune();
    }
}
