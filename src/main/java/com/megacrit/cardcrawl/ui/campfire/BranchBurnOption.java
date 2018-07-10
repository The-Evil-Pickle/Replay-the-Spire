package com.megacrit.cardcrawl.ui.campfire;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.vfx.campfire.*;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.core.*;
import replayTheSpire.*;

public class BranchBurnOption extends AbstractCampfireOption
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    
    public BranchBurnOption() {
        this.label = BranchBurnOption.TEXT[0];
        this.description = BranchBurnOption.TEXT[1];
        this.img = ImageMaster.CAMPFIRE_BREW_BUTTON;
    }
    
    @Override
    public void useOption() {
		ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_loseRelic("Dead Branch");
		AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new BranchBurnRelic());
		this.usable = false;
		//((RestRoom)AbstractDungeon.getCurrRoom()).campfireUI.reopen();
		//((RestRoom)AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.INCOMPLETE;
		AbstractDungeon.effectList.add(new CampfireSmithEffect());
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Burn Branch Option");
        TEXT = BranchBurnOption.uiStrings.TEXT;
    }
}
