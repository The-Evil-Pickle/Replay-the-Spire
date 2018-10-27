package com.megacrit.cardcrawl.mod.replay.rooms;

import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.RestRoom;

import coloredmap.ColoredRoom;
import replayTheSpire.ReplayTheSpireMod;

@ColoredRoom
public class PsuedoBonfireRoom extends RestRoom {
	
	//this class is never used, it just exists so the Colored Maps mod will recognize Bonfire rooms as different from Rest rooms.
	public PsuedoBonfireRoom() {
		super();
		ReplayTheSpireMod.bonfireIcon = ImageMaster.loadImage("images/ui/map/replay_bonfire.png");
		ReplayTheSpireMod.bonfireBG = ImageMaster.loadImage("images/ui/map/replay_bonfireOutline.png");
		this.mapSymbol = "R_BF";
        this.mapImg = ReplayTheSpireMod.bonfireIcon;
        this.mapImgOutline = ReplayTheSpireMod.bonfireBG;
	}
	
}
