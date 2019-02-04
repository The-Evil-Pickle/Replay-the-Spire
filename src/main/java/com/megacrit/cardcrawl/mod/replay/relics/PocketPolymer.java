package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

public class PocketPolymer extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:PocketPolymizer";
    
    public PocketPolymer() {
        super(ID, "betaRelic.png", RelicTier.RARE, LandingSound.MAGICAL);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new PocketPolymer();
    }
}
