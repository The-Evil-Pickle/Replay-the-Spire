package com.megacrit.cardcrawl.events;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.*;
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
    
    public TeleportEvent(MapRoomNode teleDest) {
    	super(NAME, DIALOG_1, null);
    	this.screen = CurScreen.INTRO;
        this.imageEventText.setDialogOption(TeleportEvent.OPTIONS[0]);
        this.teleDest = teleDest;
    }
    
    protected void buttonEffect(final int buttonPressed) {
    	AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
    	AbstractDungeon.currMapNode = teleDest;
    	this.openMap();
    	imageEventText.clear();
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