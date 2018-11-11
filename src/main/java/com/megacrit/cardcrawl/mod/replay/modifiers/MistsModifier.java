package com.megacrit.cardcrawl.mod.replay.modifiers;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RunModStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.map.RoomTypeAssigner;
import com.megacrit.cardcrawl.mod.replay.events.thebottom.MirrorMist;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.EventRoom;

public class MistsModifier extends AbstractDailyMod {
	public static final String ID = "replay:Mists";
    private static final RunModStrings modStrings = CardCrawlGame.languagePack.getRunModString(ID);
    public static final String NAME = modStrings.NAME;
    public static final String DESC = modStrings.DESCRIPTION;
    public static boolean hasGottenEvent = false;

    public MistsModifier() {
        super(ID, NAME, DESC, null, true);
        this.img = ImageMaster.loadImage("images/relics/ironCoreOrb.png");
    }
    
    @SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "generateMap")
	public static class MapGenPatch {
    	@SpireInsertPatch(rloc=20)
    	public static void Insert() {
    		if (CardCrawlGame.trial != null && CardCrawlGame.trial.dailyModIDs().contains(MistsModifier.ID) && !hasGottenEvent) {
        		//modifer active
        		RoomTypeAssigner.assignRowAsRoomType(AbstractDungeon.map.get(0), EventRoom.class);
    		}
    	}
    }
    @SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "generateEvent")
	public static class EventListPatch {
    	public static SpireReturn<AbstractEvent> Prefix(final Random rng) {
    		if (CardCrawlGame.trial != null && CardCrawlGame.trial.dailyModIDs().contains(MistsModifier.ID) && !hasGottenEvent) {
    			AbstractDungeon.eventList.remove(MirrorMist.ID);
    			return SpireReturn.Return(new MirrorMist());
    		}
    		return SpireReturn.Continue();
    	}
    }
}
