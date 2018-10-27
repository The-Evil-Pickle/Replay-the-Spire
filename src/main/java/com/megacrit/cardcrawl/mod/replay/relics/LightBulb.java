package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.ui.panels.*;

public class LightBulb extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:Lightbulb";
	
    public LightBulb() {
        super(ID, "replay_lightbulb.png", RelicTier.COMMON, LandingSound.CLINK);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
	
    @Override
    public void onPlayerEndTurn() {
    	if (EnergyPanel.totalCount > 0) {
    		this.flash();
    		AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, EnergyPanel.totalCount)));
    	}
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new LightBulb();
    }
    
    @Override
    public int getPrice() {
    	return 200;
    }
}