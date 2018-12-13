package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.relics.*;

public class ByrdFeeder extends AbstractRelic
{
    public static final String ID = "Replay:Byrd Feeder";
    
    public ByrdFeeder() {
        super(ID, "betaRelic.png", RelicTier.COMMON, LandingSound.SOLID);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new ByrdFeeder();
    }
}
