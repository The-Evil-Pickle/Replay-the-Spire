package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.ui.panels.*;

public class Funnel extends AbstractRelic
{
    public static final String ID = "Funnel";
    
    public Funnel() {
        super("Funnel", "funnel.png", RelicTier.UNCOMMON, LandingSound.SOLID);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void onPlayerEndTurn() {
        if (EnergyPanel.totalCount > 0) {
            this.flash();
            this.stopPulse();
            AbstractDungeon.actionManager.addToTop((AbstractGameAction)new GainBlockAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, EnergyPanel.totalCount * 4));
            AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, (AbstractRelic)this));
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new Funnel();
    }
}