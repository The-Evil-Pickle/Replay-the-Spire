package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.events.shrines.ChaosEvent;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import java.util.*;

public class RingOfExchange extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:Ring of Exchange";
	
    public RingOfExchange() {
        super(ID, "cring_exchange.png", RelicTier.SPECIAL, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    @Override
    public void onEquip() {
    	ArrayList<String> comboPool = new ArrayList<String>();
    	while (AbstractDungeon.shopRelicPool.size() > 1) {
    		comboPool.add(AbstractDungeon.shopRelicPool.remove(0));
    	}
    	while (AbstractDungeon.bossRelicPool.size() > 3) {
    		comboPool.add(AbstractDungeon.bossRelicPool.remove(0));
    	}
    	Collections.shuffle(comboPool, AbstractDungeon.relicRng.random);
    	boolean toShop = false;
    	for (String s : comboPool) {
    		if (s != null && !s.isEmpty()) {
    			(toShop ? AbstractDungeon.shopRelicPool : AbstractDungeon.bossRelicPool).add(0, s);
    			toShop = !toShop;
    		}
    	}
    }

    @Override
    public int getPrice() {
    	return ChaosEvent.RING_SHOP_PRICE;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new RingOfExchange();
    }
	
}
