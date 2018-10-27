package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.defect.RaiderMaskAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
//import com.megacrit.cardcrawl.relics.LandingSound;
//import com.megacrit.cardcrawl.relics.RelicTier;

public class RaidersMask extends AbstractRelic
{
    public static final String ID = "Raider's Mask";
    
    public RaidersMask() {
        super("Raider's Mask", "raidmask.png", RelicTier.SHOP, LandingSound.SOLID);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new RaidersMask();
    }
    
    @Override
    public void onPlayerEndTurn() {
    	AbstractDungeon.actionManager.addToBottom(new RaiderMaskAction());
    }
}
