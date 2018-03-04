package com.megacrit.cardcrawl.relics;

public class Arrowhead extends AbstractRelic
{
    public static final String ID = "Arrowhead";
    
    public Arrowhead() {
        super("Arrowhead", "arrowhead.png", RelicTier.RARE, LandingSound.HEAVY);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new Arrowhead();
    }
}
