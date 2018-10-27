package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.*;

import basemod.BaseMod;
import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.ReplayTheSpireMod;
import replayTheSpire.panelUI.ReplayBooleanSetting;
import replayTheSpire.panelUI.ReplayIntSliderSetting;
import replayTheSpire.panelUI.ReplayRelicSetting;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.RetainCardPower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;

import java.util.*;

public class HoneyJar extends ReplayAbstractRelic
{
    public static final String ID = "Honey Jar";

    public static final ReplayIntSliderSetting SETTING_DRAW = new ReplayIntSliderSetting("Honeyjar_Draw", "Cards Drawn", 1, 3);
    public static final ReplayIntSliderSetting SETTING_RETAIN = new ReplayIntSliderSetting("Honeyjar_Retain", "Cards Retained", 1, 3);
    public static final ReplayBooleanSetting SETTING_CHOICE = new ReplayBooleanSetting("Honeyjar_Choice", "Extra Card Reward Choice", true);
    public static final ReplayIntSliderSetting SETTING_POTION = new ReplayIntSliderSetting("Honeyjar_Potion", "Potion Slots", 1, 3);
    public static final ReplayIntSliderSetting SETTING_HAND = new ReplayIntSliderSetting("Honeyjar_Hand", "Max Hand Size Bonus", 0, 5);
    public static final ReplayIntSliderSetting SETTING_ENERGY = new ReplayIntSliderSetting("Honeyjar_Energy", "Energy Per Turn", 0, 2);
    public static final ReplayIntSliderSetting SETTING_UPGRADE = new ReplayIntSliderSetting("Honeyjar_Upgrade", "Upgrade random card every __ rooms", 0, 5);
    public static final ReplayBooleanSetting SETTING_FLOOR = new ReplayBooleanSetting("Honeyjar_Floor", "Downside lasts only 1 floor.", false);

    public ArrayList<ReplayRelicSetting> BuildRelicSettings() {
    	ArrayList<ReplayRelicSetting> settings = new ArrayList<ReplayRelicSetting>();
    	settings.add(SETTING_FLOOR);
    	settings.add(SETTING_DRAW);
    	settings.add(SETTING_RETAIN);
    	settings.add(SETTING_HAND);
    	settings.add(SETTING_CHOICE);
    	settings.add(SETTING_POTION);
    	settings.add(SETTING_ENERGY);
		return settings;
	}
    public HoneyJar() {
        super("Honey Jar", "honeyJar.png", RelicTier.BOSS, LandingSound.FLAT);
        this.counter = -3;
    }

    @Override
    public void justEnteredRoom(final AbstractRoom room) {
        if (this.counter == -3 && room instanceof TreasureRoomBoss) {
        	this.counter = -2;
        } else if (this.counter == -2 && !(room instanceof TreasureRoomBoss) && !(room instanceof MonsterRoomBoss)) {
        	this.counter = -1;
        }
    }
    @Override
    public String getUpdatedDescription() {
    	String desc = (SETTING_FLOOR.value) ? DESCRIPTIONS[1] : DESCRIPTIONS[0];
    	if (SETTING_POTION.value > 0) {
    		desc = DESCRIPTIONS[8] + SETTING_POTION.value + DESCRIPTIONS[(SETTING_POTION.value > 1) ? 10 : 9] +  " NL " + desc;
    	}
    	if (SETTING_CHOICE.value) {
    		desc = DESCRIPTIONS[7] + " NL " + desc;
    	}
    	if (SETTING_HAND.value > 0) {
    		desc = DESCRIPTIONS[11] + SETTING_HAND.value + ". NL " + desc;
    	}
    	if (SETTING_RETAIN.value > 0) {
    		desc = DESCRIPTIONS[6] + SETTING_RETAIN.value + DESCRIPTIONS[(SETTING_RETAIN.value > 1) ? 5 : 4] + " NL " + desc;
    	}
    	if (SETTING_DRAW.value > 0) {
    		desc = DESCRIPTIONS[2] + SETTING_DRAW.value + DESCRIPTIONS[3] + DESCRIPTIONS[(SETTING_DRAW.value > 1) ? 5 : 4] + " NL " + desc;
    	}
    	if (SETTING_ENERGY.value > 0) {
    		String estring = "";
    		for (int i=0; i < SETTING_ENERGY.value; i++) {
    			estring += "[E]";
    		}
    		desc = DESCRIPTIONS[12] + estring + DESCRIPTIONS[13] + " NL " + desc;
    	}
        return desc;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new HoneyJar();
    }
	
    @Override
    public void atBattleStart() {
    	if (SETTING_RETAIN.value > 0) {
    		this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RetainCardPower(AbstractDungeon.player, SETTING_RETAIN.value), SETTING_RETAIN.value));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    	}
    }
	
    @Override
    public void onEquip() {
        final AbstractPlayer player = AbstractDungeon.player;
        player.masterHandSize += SETTING_DRAW.value;
        for (int i=0; i < SETTING_POTION.value; i++) {
            player.potionSlots += 1;
            AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));	
        }
        BaseMod.MAX_HAND_SIZE += SETTING_HAND.value;
        if (SETTING_ENERGY.value > 0) {
        	final EnergyManager energy = AbstractDungeon.player.energy;
            energy.energyMaster += SETTING_ENERGY.value;
        }
        ReplayTheSpireMod.noSkipRewardsRoom = true;
    }
    
    @Override
    public void onUnequip() {
        final AbstractPlayer player = AbstractDungeon.player;
        player.masterHandSize -= SETTING_DRAW.value;
        BaseMod.MAX_HAND_SIZE -= SETTING_HAND.value;
        if (SETTING_ENERGY.value > 0) {
        	final EnergyManager energy = AbstractDungeon.player.energy;
            energy.energyMaster -= SETTING_ENERGY.value;
        }
    }
}
