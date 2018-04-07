package com.megacrit.cardcrawl.relics;

public class GremlinFood extends AbstractRelic
{
    public static final String ID = "Gremlin Food";
    
    public GremlinFood() {
        super("Gremlin Food", "betaRelic.png", RelicTier.COMMON, LandingSound.MAGICAL);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new GremlinFood();
    }
}
