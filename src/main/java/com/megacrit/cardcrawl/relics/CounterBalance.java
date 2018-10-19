package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.dungeons.*;

public class CounterBalance extends AbstractRelic
{
    public static final String ID = "Counterbalance";
    
    public CounterBalance() {
        super("Counterbalance", "equilibrium.png", RelicTier.RARE, LandingSound.HEAVY);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void onEnterRoom(final AbstractRoom room) {
        if (room instanceof MonsterRoomElite || room instanceof MonsterRoomBoss || room.eliteTrigger) {
            this.pulse = true;
            this.beginPulse();
        }
        else {
            this.pulse = false;
        }
    }
    
    @Override
    public void onVictory() {
    	this.pulse = false;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CounterBalance();
    }
}
