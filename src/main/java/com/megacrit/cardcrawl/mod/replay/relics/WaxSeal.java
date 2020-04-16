package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.relics.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.cards.curses.DebtCurseIOU;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StoreRelic;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;

import basemod.ReflectionHacks;

public class WaxSeal extends AbstractRelic implements ClickableRelic
{
    public static final String ID = "Replay:Wax Seal";
    public final static float DISCOUNT = 0.2F;
    public WaxSeal() {
        super(ID, "waxSeal.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[3] + (Math.round(DISCOUNT * 100F)) + this.DESCRIPTIONS[4] + this.DESCRIPTIONS[0] + this.DESCRIPTIONS[1] + DebtCurseIOU.GOLD_COST + this.DESCRIPTIONS[2];
    }

    @Override
    public void onRightClick() {
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom() instanceof ShopRoom) {
            this.flash();
            if (AbstractDungeon.player.hasRelic(Omamori.ID) && AbstractDungeon.player.getRelic(Omamori.ID).counter > 0) {
            	AbstractDungeon.player.getRelic(Omamori.ID).flash();
            	AbstractDungeon.player.getRelic(Omamori.ID).counter--;
            } else {
            	AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(new DebtCurseIOU(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            }
            AbstractDungeon.player.gainGold(DebtCurseIOU.GOLD_COST);
        }
    }

    @Override
    public void onEquip() {
    	ShopScreen shop = AbstractDungeon.shopScreen;
    	if (shop == null) {
    		return;
    	}
    	final ArrayList<StoreRelic> relics = (ArrayList<StoreRelic>)ReflectionHacks.getPrivate((Object)shop, (Class)ShopScreen.class, "relics");
		for (StoreRelic r : relics) {
			r.price = Math.round(r.price * (1F -  DISCOUNT));
		}
    }
    @Override
    public AbstractRelic makeCopy() {
        return new WaxSeal();
    }
    
    @Override
    public int getPrice() {
        return 80;
    }
}
