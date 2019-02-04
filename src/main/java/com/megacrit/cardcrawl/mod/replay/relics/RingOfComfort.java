package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.events.shrines.ChaosEvent;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.badlogic.gdx.math.MathUtils;

public class RingOfComfort extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:Ring of Comfort";
    private static final float PERCENT = 0.5f;
    private static final float MULTIPLIER = 2.0f;
    public RingOfComfort() {
        super(ID, "cring_shattering.png", RelicTier.SPECIAL, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + Math.round((MULTIPLIER - 1.0f) * 100.0f) + this.DESCRIPTIONS[1] + Math.round(PERCENT * 100.0f) + this.DESCRIPTIONS[2];
    }
	
    public static boolean forceRest() {
    	return (AbstractDungeon.player != null && (AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth) < PERCENT);
    }
    @Override
    public void onBloodied() {
        this.flash();
        this.pulse = true;
    }
    
    @Override
    public void onNotBloodied() {
        this.stopPulse();
    }

    @Override
    public int getPrice() {
    	return ChaosEvent.RING_SHOP_PRICE;
    }
    @Override
    public int onPlayerHeal(final int healAmount) {
        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.flash();
            return MathUtils.round(healAmount * MULTIPLIER);
        }
        return healAmount;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new RingOfComfort();
    }
}
