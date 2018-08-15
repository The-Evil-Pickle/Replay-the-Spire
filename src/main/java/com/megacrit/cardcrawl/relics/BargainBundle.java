package com.megacrit.cardcrawl.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.shop.*;

import basemod.ReflectionHacks;

public class BargainBundle extends AbstractRelic
{
    public static final String ID = "BargainBundle";
    public static final int DISCOUNT = 100;
    
    public BargainBundle() {
        super(ID, "betaRelic.png", RelicTier.SHOP, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + DISCOUNT + this.DESCRIPTIONS[1];
    }

    @Override
    public void onEquip() {
    	ShopScreen.actualPurgeCost = Math.max(ShopScreen.actualPurgeCost - DISCOUNT, 0);
    	ShopScreen shop = AbstractDungeon.shopScreen;
    	if (shop == null) {
    		return;
    	}
    	final ArrayList<AbstractCard> coloredCards = (ArrayList<AbstractCard>)ReflectionHacks.getPrivate((Object)shop, (Class)ShopScreen.class, "coloredCards");
    	final ArrayList<AbstractCard> colorlessCards = (ArrayList<AbstractCard>)ReflectionHacks.getPrivate((Object)shop, (Class)ShopScreen.class, "colorlessCards");
    	final ArrayList<StorePotion> potions = (ArrayList<StorePotion>)ReflectionHacks.getPrivate((Object)shop, (Class)ShopScreen.class, "potions");
    	for (AbstractCard c : coloredCards) {
    		c.price = Math.max(c.price - DISCOUNT, 0);
    	}
    	for (AbstractCard c : colorlessCards) {
    		c.price = Math.max(c.price - DISCOUNT, 0);
    	}
    	for (StorePotion c : potions) {
    		c.price = Math.max(c.price - DISCOUNT, 0);
    	}
    	
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new BargainBundle();
    }
}
