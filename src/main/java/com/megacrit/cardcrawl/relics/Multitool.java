package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

public class Multitool extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:Multitool";
    
    public Multitool() {
        super(ID, "betaRelic.png", RelicTier.RARE, LandingSound.CLINK);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new Multitool();
    }
}
