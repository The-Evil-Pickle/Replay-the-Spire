package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;

public class RaidersMask extends AbstractRelic
{
    public static final String ID = "Raider's Mask";
    
    public RaidersMask() {
        super("Raider's Mask", "raidmask.png", RelicTier.UNCOMMON, LandingSound.SOLID);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new RaidersMask();
    }
}
