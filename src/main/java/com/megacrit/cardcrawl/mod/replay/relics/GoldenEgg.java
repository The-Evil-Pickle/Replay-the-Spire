package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.ShopRoom;

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
        if ((c.rarity == AbstractCard.CardRarity.RARE || AbstractDungeon.getCurrRoom() instanceof ShopRoom) && c.canUpgrade() && !c.upgraded) {
            c.upgrade();
            this.flash();
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new GoldenEgg();
    }
}
