package com.megacrit.cardcrawl.ui.campfire;

import java.util.ArrayList;
import java.util.Collections;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepScreenCoverEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import replayTheSpire.ReplayTheSpireMod;

public class BonfirePrepareOption extends AbstractCampfireOption
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    
    public BonfirePrepareOption() {
        this.label = RestOption.TEXT[0];
        this.description = BonfirePrepareOption.TEXT[1];
        this.img = ReplayTheSpireMod.multitaskButton;
    }
    
    @Override
    public void useOption() {
    	
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Prepare Option");
        TEXT = BonfirePrepareOption.uiStrings.TEXT;
    }
}
