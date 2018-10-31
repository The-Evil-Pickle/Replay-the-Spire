package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import replayTheSpire.panelUI.ReplayIntSliderSetting;
import replayTheSpire.panelUI.ReplayRelicSetting;


public class RingingSoul extends AbstractRelic {
	public static final String ID = "Replay:Ringing Soul";
    
	public static final int HPGAIN = 3;
    
    public RingingSoul() {
        super(ID, "betaRelic.png", RelicTier.UNCOMMON, LandingSound.CLINK);
        this.pulse = true;
    }

    @Override
    public void onEnterRoom(final AbstractRoom room) {
    	this.pulse = true;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + HPGAIN + this.DESCRIPTIONS[1];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new RingingSoul();
    }

    @Override
    public void onObtainCard(final AbstractCard card) {
    	if (this.pulse) {
    		AbstractDungeon.player.increaseMaxHp(HPGAIN, true);
    		this.pulse = false;
    	}
    }
}
