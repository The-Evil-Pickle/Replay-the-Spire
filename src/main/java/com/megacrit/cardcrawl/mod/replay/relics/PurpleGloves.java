package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.DivinityStance;

import basemod.*;
import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.ReplayTheSpireMod;
import replayTheSpire.panelUI.ReplayIntSliderSetting;
import replayTheSpire.panelUI.ReplayRelicSetting;

public class PurpleGloves extends ReplayAbstractRelic
{
    public static final String ID = "Replay:Purple Gloves";
    public static final ReplayIntSliderSetting SETTING_MAX_SIZE = new ReplayIntSliderSetting("Gloves_Size", "Hand Size", 2, 1, 5);
    public static final ReplayIntSliderSetting SETTING_DRAW_CALM = new ReplayIntSliderSetting("Gloves_Draw_Calm", "Draw on exit Calm", 1, 1, 3);
    public static final ReplayIntSliderSetting SETTING_DRAW_DIVINITY = new ReplayIntSliderSetting("Gloves_Draw_Divinity", "Draw on enter Divinity", 2, 1, 3);
    public ArrayList<ReplayRelicSetting> BuildRelicSettings() {
    	ArrayList<ReplayRelicSetting> settings = new ArrayList<ReplayRelicSetting>();
    	settings.add(SETTING_MAX_SIZE);
    	settings.add(SETTING_DRAW_CALM);
    	settings.add(SETTING_DRAW_DIVINITY);
		return settings;
	}
    public PurpleGloves() {
        super(ID, "betaRelic.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
    	return this.DESCRIPTIONS[0] + SETTING_MAX_SIZE.value + this.DESCRIPTIONS[1] + SETTING_DRAW_CALM.value + (SETTING_DRAW_CALM.value > 1 ? this.DESCRIPTIONS[4] : this.DESCRIPTIONS[3]) + this.DESCRIPTIONS[2] + SETTING_DRAW_DIVINITY.value + (SETTING_DRAW_DIVINITY.value > 1 ? this.DESCRIPTIONS[4] : this.DESCRIPTIONS[3]);
    }
    
    @Override
    public void onEquip() {
        BaseMod.MAX_HAND_SIZE += SETTING_MAX_SIZE.value;
    }

    @Override
    public void onUnequip() {
        BaseMod.MAX_HAND_SIZE -= SETTING_MAX_SIZE.value;
    }

    @Override
    public void onChangeStance(final AbstractStance prevStance, final AbstractStance newStance) {
    	if (prevStance.ID == CalmStance.STANCE_ID) {
    		this.addToBot(new DrawCardAction(SETTING_DRAW_CALM.value));
    	}
    	if (newStance.ID == DivinityStance.STANCE_ID) {
    		this.addToBot(new DrawCardAction(SETTING_DRAW_DIVINITY.value));
    	}
    }
    @Override
    public AbstractRelic makeCopy() {
        return new PurpleGloves();
    }
}
