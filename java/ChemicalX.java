package com.megacrit.cardcrawl.relics;

public class ChemicalX extends AbstractRelic
{
    public static final String ID = "Chemical X";
    public static final int BOOST = 2;
    
    public ChemicalX() {
        super("Chemical X", "chemicalX.png", RelicTier.SHOP, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + ChemicalX.BOOST + ".";
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new ChemicalX();
    }
}
