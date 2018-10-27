package com.megacrit.cardcrawl.mod.replay.ui.campfire;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.relics.*;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.mod.replay.vfx.campfire.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.core.*;
import replayTheSpire.*;

public class BranchBurnOption extends AbstractCampfireOption
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    
    public BranchBurnOption() {
        this.label = BranchBurnOption.TEXT[0];
        this.description = BranchBurnOption.TEXT[1];
        this.img = ImageMaster.CAMPFIRE_RITUAL_BUTTON;
    }
    
    @Override
    public void useOption() {
		ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_loseRelic("Dead Branch");
		AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new BranchBurnRelic());
    	AbstractDungeon.effectList.add(new CampfireBurnResetEffect(this));
		this.usable = false;
		//((RestRoom)AbstractDungeon.getCurrRoom()).campfireUI.reopen();
		//((RestRoom)AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.INCOMPLETE;
		/*AbstractDungeon.effectList.add(new CampfireSmithEffect());
		if (!AbstractDungeon.gridSelectScreen.confirmScreenUp) {
            AbstractDungeon.closeCurrentScreen();
            if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
                final RestRoom r = (RestRoom)AbstractDungeon.getCurrRoom();
                r.campfireUI.reopen();
            }
            return;
        }
        AbstractDungeon.gridSelectScreen.cancelUpgrade();*/
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Burn Branch Option");
        TEXT = BranchBurnOption.uiStrings.TEXT;
    }
}
