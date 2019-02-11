package com.megacrit.cardcrawl.mod.replay.events;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.mod.replay.events.*;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import replayTheSpire.ReplayTheSpireMod;
//import replayTheSpire.replayxover.infinitebs;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.core.*;
import java.util.*;

public class TeleportEvent extends AbstractImageEvent
{
    public static final String ID = "ReplayTeleport";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String DIALOG_1;
    private CurScreen screen;
	private MapRoomNode teleDest;
    public static boolean hasWarped = false;
    private boolean warpedTo;
    public TeleportEvent(MapRoomNode teleDest) {
    	super(NAME, DIALOG_1, null);
    	this.screen = CurScreen.INTRO;
        this.imageEventText.setDialogOption(TeleportEvent.OPTIONS[0]);
        this.teleDest = teleDest;
        this.warpedTo = hasWarped;
        if (hasWarped) {
        	hasWarped = false;
        	AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        	this.openMap();
        	imageEventText.clear();
        }
    }
    
    protected void buttonEffect(final int buttonPressed) {
    	if (this.warpedTo) {
    		return;
    	}
    	MapRoomNode room = AbstractDungeon.getCurrMapNode();
    	MapEdge edge = new MapEdge(room.x, room.y, room.offsetX, room.offsetY, teleDest.x, teleDest.y, teleDest.offsetX, teleDest.offsetY, false);
    	room.addEdge(edge);
    	edge.markAsTaken();
    	AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
    	AbstractDungeon.nextRoom = teleDest;
        AbstractDungeon.pathX.add(teleDest.x);
        AbstractDungeon.pathY.add(teleDest.y);
        CardCrawlGame.metricData.path_taken.add(AbstractDungeon.nextRoom.getRoom().getMapSymbol());
        hasWarped = true;
        if (!AbstractDungeon.isDungeonBeaten) {
            AbstractDungeon.nextRoomTransitionStart();
            CardCrawlGame.music.fadeOutTempBGM();
        }
    	//AbstractDungeon.setCurrMapNode(teleDest);
    	//AbstractDungeon.currMapNode = teleDest;
    	AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
    	this.openMap();
    	imageEventText.clear();
        //if (ReplayTheSpireMod.foundmod_infinite) {
        	//infinitebs.TriggerPortalQuest();
        //}
    }
    
    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("ReplayTeleport");
        NAME = TeleportEvent.eventStrings.NAME;
        DESCRIPTIONS = TeleportEvent.eventStrings.DESCRIPTIONS;
        OPTIONS = TeleportEvent.eventStrings.OPTIONS;
        DIALOG_1 = TeleportEvent.DESCRIPTIONS[0];
    }
    private enum CurScreen
    {
        INTRO;
    }
}