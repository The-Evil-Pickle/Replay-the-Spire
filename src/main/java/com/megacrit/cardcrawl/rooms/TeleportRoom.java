package com.megacrit.cardcrawl.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.TeleportEvent;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.MapRoomNode;

import replayTheSpire.ReplayTheSpireMod;

public class TeleportRoom extends EventRoom {

    private MapRoomNode teleDest;

	public TeleportRoom(MapRoomNode teleDest) {
    	super();
    	this.teleDest = teleDest;
        this.phase = RoomPhase.EVENT;
        this.mapSymbol = "PTL";
        this.mapImg = ReplayTheSpireMod.portalIcon;
        this.mapImgOutline = ReplayTheSpireMod.portalBG;
    }

    @Override
    public void onPlayerEntry() {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.event = new TeleportEvent(teleDest);
    }
}
