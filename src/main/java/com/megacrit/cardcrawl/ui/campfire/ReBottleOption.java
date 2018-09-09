package com.megacrit.cardcrawl.ui.campfire;

import java.util.ArrayList;
import java.util.Collections;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.campfire.CampfireBurnResetEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepScreenCoverEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import replayTheSpire.ReplayTheSpireMod;

public class ReBottleOption extends AbstractCampfireOption
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractRelic bottle;
    private boolean makeFree;
    
    public ReBottleOption(AbstractRelic bottle, boolean makeFree) {
    	this.bottle = bottle;
    	this.makeFree = makeFree;
        this.label = TEXT[0] + bottle.name + TEXT[1];
        this.description = TEXT[2];
        if (makeFree) {
        	this.description += TEXT[3];
        }
        this.img = bottle.img;//ReplayTheSpireMod.multitaskButton;
    }
    
    @Override
    public void useOption() {
    	this.bottle.onUnequip();
    	this.bottle.onEquip();
    	if (this.makeFree) {
    		AbstractDungeon.effectList.add(new CampfireBurnResetEffect(this));
    		this.usable = false;
    	} else {
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
    	}
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Re-Bottle Option");
        TEXT = ReBottleOption.uiStrings.TEXT;
    }
}
