package com.megacrit.cardcrawl.relics;

public class ChameleonRing extends AbstractRelic
{
    public static final String ID = "Chameleon Ring";
    
    public ChameleonRing() {
        super("Chameleon Ring", "serpentRing.png", RelicTier.RARE, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new ChameleonRing();
    }
}
