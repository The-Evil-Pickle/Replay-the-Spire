package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.*;

public class GoldenEgg extends AbstractRelic
{
    public static final String ID = "Golden Egg";
    
    public GoldenEgg() {
        super("Golden Egg", "goldEgg.png", RelicTier.UNCOMMON, LandingSound.SOLID);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void onObtainCard(final AbstractCard c) {
        if (c.rarity == AbstractCard.CardRarity.RARE && c.canUpgrade() && !c.upgraded) {
            c.upgrade();
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new GoldenEgg();
    }
}
