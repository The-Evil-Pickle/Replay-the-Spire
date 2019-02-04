package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.mod.replay.events.shrines.ChaosEvent;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.*;

public class RingOfCollecting extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:Ring of Collecting";
	
    public RingOfCollecting() {
        super(ID, "cring_collecting.png", RelicTier.SPECIAL, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onChestOpen(final boolean bossChest) {
        if (!bossChest) {
        	ArrayList<AbstractRelic> rings = ChaosEvent.getRingPool();
        	if (rings.size() > 0) {
        		this.flash();
                AbstractDungeon.getCurrRoom().addRelicToRewards(rings.get(AbstractDungeon.relicRng.random(0, rings.size() - 1)));
        	}
        }
    }
    @Override
    public AbstractRelic makeCopy() {
        return new RingOfCollecting();
    }

    @Override
    public int getPrice() {
    	return ChaosEvent.RING_SHOP_PRICE;
    }
    @SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "returnEndRandomRelicKey")
    public static class ShopInitPatch {
    	public static String PostFix(String __result, final AbstractRelic.RelicTier tier) {
    		//50% chance to return a ring instead of a normal shop relic while the player has Ring of Collecting
    		if (AbstractDungeon.player != null && tier == AbstractRelic.RelicTier.SHOP && AbstractDungeon.player.hasRelic(RingOfCollecting.ID) && AbstractDungeon.relicRng.randomBoolean()) {
				ArrayList<AbstractRelic> rings = ChaosEvent.getRingPool();
				if (rings.size() > 0) {
					AbstractDungeon.player.getRelic(RingOfCollecting.ID).flash();
					return rings.get(AbstractDungeon.relicRng.random(0, rings.size() - 1)).relicId;
				}
    		}
    		return __result;
    	}
    }
    
    
}
