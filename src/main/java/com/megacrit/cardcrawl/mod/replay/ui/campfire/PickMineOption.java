package com.megacrit.cardcrawl.mod.replay.ui.campfire;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireBubbleEffect;

import replayTheSpire.ReplayTheSpireMod;

public class PickMineOption extends AbstractCampfireOption
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    
    public PickMineOption() {
        this.label = PickMineOption.TEXT[0];
        this.description = PickMineOption.TEXT[1];
        this.img = ReplayTheSpireMod.mineButton;
    }
    
    @Override
    public void useOption() {
		AbstractDungeon.effectList.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
		AbstractDungeon.getCurrRoom().rewards.clear();
		AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(AbstractDungeon.miscRng.random(50, 100)));
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
		AbstractDungeon.combatRewardScreen.open();
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Mine Option");
        TEXT = PickMineOption.uiStrings.TEXT;
    }
}
