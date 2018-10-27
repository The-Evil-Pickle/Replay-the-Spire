package com.megacrit.cardcrawl.mod.replay.ui.campfire;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.mod.replay.vfx.campfire.CampfireBurnResetEffect;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepScreenCoverEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import basemod.ReflectionHacks;
import replayTheSpire.ReplayTheSpireMod;

public class ReBottleOption extends AbstractCampfireOption
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractRelic bottle;
    private boolean makeFree;
    private static final float NORM_SCALE;
    private static final float HOVER_SCALE;
    
    public ReBottleOption(AbstractRelic bottle, boolean makeFree) {
    	this.bottle = bottle;
    	this.makeFree = makeFree;
        this.label = TEXT[0] + bottle.name + TEXT[1];
        this.description = TEXT[2];
        if (makeFree) {
        	this.description += TEXT[3];
        }
        this.img = ReplayTheSpireMod.rebottleButton;
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
    
    @Override
    public void render(final SpriteBatch sb) {
    	super.render(sb);
    	float scale = (float)ReflectionHacks.getPrivate(this, AbstractCampfireOption.class, "scale");
    	final Vector2 tmp = new Vector2(-105.0f, 64);
        tmp.scl(scale);
        sb.draw(this.bottle.img, this.hb.cX + tmp.x - 64.0f, this.hb.cY + tmp.y - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, scale, scale, 0.0f, 0, 0, 128, 128, false, false);
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Re-Bottle Option");
        TEXT = ReBottleOption.uiStrings.TEXT;
        NORM_SCALE = 0.9f * Settings.scale;
        HOVER_SCALE = Settings.scale;
    }
}
