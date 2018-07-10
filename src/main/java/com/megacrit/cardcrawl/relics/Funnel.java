package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.ui.panels.*;

public class Funnel extends AbstractRelic
{
    public static final String ID = "ReplayFunnel";
	private int turnlimitmarker;
	
    public Funnel() {
        super("ReplayFunnel", "replay_funnel.png", RelicTier.COMMON, LandingSound.SOLID);
		this.turnlimitmarker = 3;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
	
	private boolean canConserveEnergy() {
		if (AbstractDungeon.player == null) {
			return false;
		}
		return (AbstractDungeon.player.hasRelic("Ice Cream") || AbstractDungeon.player.hasPower("Conserve"));
	}
	
    @Override
    public void onEnergyRecharge() {
        if (this.canConserveEnergy()) {
			this.turnlimitmarker = EnergyPanel.totalCount;
		}
    }
    
    @Override
    public void onPlayerEndTurn() {
		int energycounter = EnergyPanel.totalCount;
		if (this.canConserveEnergy()) {
			energycounter = Math.min(energycounter, AbstractDungeon.player.energy.energyMaster + Math.max(EnergyPanel.totalCount - this.turnlimitmarker, 0));
		}
        if (energycounter > 0) {
            this.flash();
            this.stopPulse();
			
            AbstractDungeon.actionManager.addToTop((AbstractGameAction)new GainBlockAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, energycounter * 4));
            AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, (AbstractRelic)this));
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new Funnel();
    }
}