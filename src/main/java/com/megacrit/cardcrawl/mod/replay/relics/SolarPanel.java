package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.relics.AbstractRelic;
//import com.megacrit.cardcrawl.relics.LandingSound;
//import com.megacrit.cardcrawl.relics.RelicTier;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.*;

public class SolarPanel extends AbstractRelic
{
    public static final String ID = "Solar Panel";
    private static final int HEALTH_AMT = 2;
    
    public SolarPanel() {
        super("Solar Panel", "solarpanel.png", RelicTier.UNCOMMON, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + SolarPanel.HEALTH_AMT + this.DESCRIPTIONS[1];
    }
    
    @Override
    public void onVictory() {
        final AbstractPlayer p = AbstractDungeon.player;
		int orbcount = 0;
		for (int i = 0; i < p.orbs.size(); ++i) {
			if ((p.orbs.get(i) instanceof Lightning)) {
				orbcount += SolarPanel.HEALTH_AMT;
			}
		}
		if (orbcount <= 0) {
			return;
		}
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(p, this));
        if (p.currentHealth > 0) {
            p.heal(orbcount);
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new SolarPanel();
    }
}