package com.megacrit.cardcrawl.rooms;

import coloredmap.ColoredRoom;
import replayTheSpire.ReplayTheSpireMod;

@ColoredRoom
public class PsuedoBonfireRoom extends RestRoom {
	
	//this class is never used, it just exists so the Colored Maps mod will recognize Bonfire rooms as different from Rest rooms.
	public PsuedoBonfireRoom() {
		super();
		this.mapSymbol = "R_BF";
        this.mapImg = ReplayTheSpireMod.bonfireIcon;
        this.mapImgOutline = ReplayTheSpireMod.bonfireBG;
	}
	
}
