package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.shop.*;

import basemod.ReflectionHacks;
import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.panelUI.ReplayBooleanSetting;
import replayTheSpire.panelUI.ReplayIntSliderSetting;
import replayTheSpire.panelUI.ReplayRelicSetting;

public class BargainBundle extends ReplayAbstractRelic
{
    public static final String ID = "BargainBundle";
    public static final int DISCOUNT = 100;
    public static final ReplayIntSliderSetting SETTING_DISCOUNT = new ReplayIntSliderSetting("BargainBundle_Discount", "Discount", 100, 200);
    public static final ReplayIntSliderSetting SETTING_PRICE = new ReplayIntSliderSetting("BargainBundle_Price", "Price", 200, 300);
    public static final ReplayBooleanSetting SETTING_RELICS = new ReplayBooleanSetting("BargainBundle_Relics", "Discounts Relics", false);

    public ArrayList<ReplayRelicSetting> BuildRelicSettings() {
    	ArrayList<ReplayRelicSetting> settings = new ArrayList<ReplayRelicSetting>();
    	settings.add(SETTING_RELICS);
    	settings.add(SETTING_DISCOUNT);
    	settings.add(SETTING_PRICE);
		return settings;
	}
    public BargainBundle() {
        super(ID, "bargainBundle.png", RelicTier.SHOP, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
    	if (SETTING_RELICS.value) {
    		return this.DESCRIPTIONS[1] + SETTING_DISCOUNT.value + this.DESCRIPTIONS[2];
    	}
        return this.DESCRIPTIONS[0] + SETTING_DISCOUNT.value + this.DESCRIPTIONS[2];
    }

    @Override
    public void onEquip() {
    	ShopScreen.actualPurgeCost = Math.max(ShopScreen.actualPurgeCost - SETTING_DISCOUNT.value, 0);
    	ShopScreen shop = AbstractDungeon.shopScreen;
    	if (shop == null) {
    		return;
    	}
    	final ArrayList<AbstractCard> coloredCards = (ArrayList<AbstractCard>)ReflectionHacks.getPrivate((Object)shop, (Class)ShopScreen.class, "coloredCards");
    	final ArrayList<AbstractCard> colorlessCards = (ArrayList<AbstractCard>)ReflectionHacks.getPrivate((Object)shop, (Class)ShopScreen.class, "colorlessCards");
    	final ArrayList<StorePotion> potions = (ArrayList<StorePotion>)ReflectionHacks.getPrivate((Object)shop, (Class)ShopScreen.class, "potions");
    	final ArrayList<StoreRelic> relics = (ArrayList<StoreRelic>)ReflectionHacks.getPrivate((Object)shop, (Class)ShopScreen.class, "relics");
    	for (AbstractCard c : coloredCards) {
    		c.price = Math.max(c.price - SETTING_DISCOUNT.value, 0);
    	}
    	for (AbstractCard c : colorlessCards) {
    		c.price = Math.max(c.price - SETTING_DISCOUNT.value, 0);
    	}
    	for (StorePotion c : potions) {
    		c.price = Math.max(c.price - SETTING_DISCOUNT.value, 0);
    	}
    	if (SETTING_RELICS.value) {
    		for (StoreRelic r : relics) {
    			r.price = Math.max(r.price - SETTING_DISCOUNT.value, 0);
    		}
    	}
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new BargainBundle();
    }
    @Override
    public boolean canSpawn() {
    	return (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom() instanceof ShopRoom);
    }
    @Override
    public int getPrice() {
    	return SETTING_PRICE.value;
    }
}
