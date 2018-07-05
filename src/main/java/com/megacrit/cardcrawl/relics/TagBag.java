package com.megacrit.cardcrawl.relics;

public class TagBag extends AbstractRelic
{
    public static final String ID = "Blue Doll";
    
    public TagBag() {
        super("Blue Doll", "blueDoll.png", RelicTier.COMMON, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new TagBag();
    }
}
