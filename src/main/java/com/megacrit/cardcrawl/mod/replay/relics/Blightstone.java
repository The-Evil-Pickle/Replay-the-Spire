package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.blights.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;

import basemod.ReflectionHacks;

public class Blightstone extends AbstractRelic
{
    public static final String ID = "Blightstone";
    public static final int DISCOUNT = 100;
    
    public Blightstone() {
        super(ID, "betaRelic.png", RelicTier.BOSS, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
    	if (AbstractDungeon.getCurrRoom() instanceof TreasureRoomBoss) {
    		
    	}
    	
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new BargainBundle();
    }
}
