package com.megacrit.cardcrawl.ui.campfire;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.Sozu;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.vfx.campfire.*;

import replayTheSpire.patches.SozuPatches;

import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.core.*;

public class ChameleonBrewOption extends AbstractCampfireOption
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    boolean sozuMode;
    
    public ChameleonBrewOption() {
        this.label = ChameleonBrewOption.TEXT[0];
        this.sozuMode = false;
        //this.sozuMode = AbstractDungeon.player.hasRelic(Sozu.ID);
        if (this.sozuMode) {
        	this.description = ChameleonBrewOption.TEXT[2];
        } else {
        	this.description = ChameleonBrewOption.TEXT[1];
        }
        this.img = ImageMaster.CAMPFIRE_BREW_BUTTON;
    }
    
    @Override
    public void useOption() {
        AbstractDungeon.effectList.add(new CampfireBubbleEffect(true));
		AbstractDungeon.getCurrRoom().rewards.clear();
		AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
		if (this.sozuMode) {
			SozuPatches.lookingForCheck = true;
		} else {
			AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
		}
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
		AbstractDungeon.combatRewardScreen.open();
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Brew Option");
        TEXT = ChameleonBrewOption.uiStrings.TEXT;
    }
}
