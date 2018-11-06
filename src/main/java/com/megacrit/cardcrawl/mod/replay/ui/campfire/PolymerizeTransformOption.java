package com.megacrit.cardcrawl.mod.replay.ui.campfire;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import replayTheSpire.ReplayTheSpireMod;

public class PolymerizeTransformOption extends AbstractCampfireOption
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private boolean makeFree;
    public PolymerizeTransformOption() {
    	this(false);
    }
    public PolymerizeTransformOption(boolean makeFree) {
        this.label = PolymerizeTransformOption.TEXT[0];
        this.description = PolymerizeTransformOption.TEXT[1];
        this.img = ReplayTheSpireMod.polymerizeButton;
    	this.makeFree = makeFree;
    	if (makeFree) {
        	this.description += TEXT[3];
        }
    }
    
    @Override
    public void useOption() {
        if (this.usable) {
            AbstractDungeon.effectList.add(new CampfireTransformEffect());
        }
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Polymerize Option");
        TEXT = PolymerizeTransformOption.uiStrings.TEXT;
    }
}