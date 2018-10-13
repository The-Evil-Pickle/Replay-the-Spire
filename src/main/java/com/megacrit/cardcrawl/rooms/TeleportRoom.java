package com.megacrit.cardcrawl.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.TeleportEvent;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.MapRoomNode;

import coloredmap.ColoredRoom;
import replayTheSpire.ReplayTheSpireMod;
import replayTheSpire.replayxover.infinitebs;

@ColoredRoom
public class TeleportRoom extends AbstractRoom
{
    private EventRoom fakeRoom;
    private MapRoomNode teleDest;
    
    public TeleportRoom() {
    	super();
		ReplayTheSpireMod.portalIcon = ImageMaster.loadImage("images/ui/map/replay_portal.png");
		ReplayTheSpireMod.portalBG = ImageMaster.loadImage("images/ui/map/replay_portalOutline.png");
        this.phase = RoomPhase.EVENT;
        this.mapSymbol = "PTL";
        this.mapImg = ReplayTheSpireMod.portalIcon;
        this.mapImgOutline = ReplayTheSpireMod.portalBG;
        fakeRoom = new EventRoom();
	}
    public TeleportRoom(MapRoomNode teleDest)
    {
    	super();
    	this.teleDest = teleDest;
        this.phase = RoomPhase.EVENT;
        this.mapSymbol = "PTL";
        this.mapImg = ReplayTheSpireMod.portalIcon;
        this.mapImgOutline = ReplayTheSpireMod.portalBG;
        fakeRoom = new EventRoom();
    }

    @Override
    public void onPlayerEntry()
    {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        event = fakeRoom.event = new TeleportEvent(teleDest);
        fakeRoom.event.onEnterRoom();
        if (ReplayTheSpireMod.foundmod_infinite) {
        	infinitebs.TriggerPortalQuest();
        }
    }

    @Override
    public AbstractCard.CardRarity getCardRarity(int roll)
    {
        return fakeRoom.getCardRarity(roll);
    }

    @Override
    public void update()
    {
        fakeRoom.update();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        fakeRoom.render(sb);
        fakeRoom.renderEventTexts(sb);
    }

    @Override
    public void renderAboveTopPanel(SpriteBatch sb)
    {
        fakeRoom.renderAboveTopPanel(sb);
    }
}