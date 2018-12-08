package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.relics.*;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;

import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.panelUI.ReplayIntSliderSetting;
import replayTheSpire.panelUI.ReplayRelicSetting;

public class RingOfChallenge extends ReplayAbstractRelic implements ClickableRelic
{
    public static final String ID = "ReplayTheSpireMod:Ring of Challenge";
    
    private int usedThisBattle;
    private boolean isFirstTurn;

    public static final ReplayIntSliderSetting SETTING_MAX_PER_BATTLE = new ReplayIntSliderSetting("Cring_Challenge_PerBattle", "Limit Per Battle", 3, 1, 5);
    public static final ReplayIntSliderSetting SETTING_GOLD_GAIN = new ReplayIntSliderSetting("Cring_Challenge_Gold", "Gold", 10, 0, 100);
    public static final ReplayIntSliderSetting SETTING_RELIC_INTERVAL = new ReplayIntSliderSetting("Cring_Challenge_Relic", "Uses For Relic", 10, 1, 15);
    public ArrayList<ReplayRelicSetting> BuildRelicSettings() {
  		ArrayList<ReplayRelicSetting> r = new ArrayList<ReplayRelicSetting>();
  		r.add(SETTING_MAX_PER_BATTLE);
  		r.add(SETTING_GOLD_GAIN);
  		r.add(SETTING_RELIC_INTERVAL);
  		return r;
  	}
    public RingOfChallenge() {
        super(ID, "betaRelic.png", RelicTier.SPECIAL, LandingSound.CLINK);
        this.usedThisBattle = 0;
        this.counter = 0;
        this.isFirstTurn = false;
        this.SettingsPriorety++;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.CLICKABLE_DESCRIPTIONS()[0] + this.DESCRIPTIONS[0] + SETTING_MAX_PER_BATTLE.value + this.DESCRIPTIONS[1] + SETTING_GOLD_GAIN.value + this.DESCRIPTIONS[2] + SETTING_RELIC_INTERVAL.value + this.DESCRIPTIONS[3];
    }

    @Override
    public void atBattleStart() {
    	this.usedThisBattle = 0;
    	this.isFirstTurn = true;
    	this.pulse = true;
    	this.beginPulse();
    }
    @Override
    public void onVictory() {
    	this.usedThisBattle = 0;
    	this.isFirstTurn = false;
    	this.pulse = false;
    }
    
    
    @Override
    public void onPlayerEndTurn() {
    	this.isFirstTurn = false;
    	this.pulse = false;
    }
    @Override
    public AbstractRelic makeCopy() {
        return new RingOfChallenge();
    }
	@Override
	public void onRightClick() {
		if (this.isObtained && this.usedThisBattle < SETTING_MAX_PER_BATTLE.value) {
			this.counter++;
			this.usedThisBattle++;
			
		}
	}
}