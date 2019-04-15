package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
//import com.megacrit.cardcrawl.relics.LandingSound;
//import com.megacrit.cardcrawl.relics.RelicTier;

import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.ReplayTheSpireMod;
import replayTheSpire.panelUI.ReplayRelicSetting;

public class TagBag extends ReplayAbstractRelic
{
    public static final String ID = "Blue Doll";
    
    public TagBag() {
        super("Blue Doll", Settings.language.toString().equals("ZHS") ? "blueDoll_zhs.png" : "blueDoll.png", RelicTier.COMMON, LandingSound.FLAT);
        this.SettingsPriorety = 0;
    }
    
    public ArrayList<String> GetSettingStrings() {
  		ArrayList<String> s = new ArrayList<String>();
  		s.add(CardCrawlGame.languagePack.getUIString("Replay:SettingsNames").TEXT[14]);
  		return s;
  	}
    public ArrayList<ReplayRelicSetting> BuildRelicSettings() {
  	  ArrayList<ReplayRelicSetting> r = new ArrayList<ReplayRelicSetting>();
  	  r.add(ReplayTheSpireMod.SETTING_TAG_NORMAL_CHANCE);
  	  r.add(ReplayTheSpireMod.SETTING_TAG_DOUBLE_CHANCE);
  	  r.add(ReplayTheSpireMod.SETTING_TAG_SPECIAL_CHANCE);
  	  r.add(ReplayTheSpireMod.SETTING_TAG_SCRAMBLE_CHANCE);
  		return r;
  	}
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new TagBag();
    }
}
