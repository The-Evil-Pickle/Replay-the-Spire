package com.megacrit.cardcrawl.mod.replay.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;

public class FakeNeowRoom extends AbstractRoom {
    private EventRoom fakeRoom;

    public FakeNeowRoom()
    {
        phase = RoomPhase.EVENT;
        this.setMapImg(ImageMaster.loadImage("images/ui/map/ALWAYS.png"), ImageMaster.loadImage("images/ui/map/ALWAYSOUTLINE.png"));
        this.setMapSymbol("WH");

        fakeRoom = new EventRoom();
    }

    @Override
    public AbstractCard.CardRarity getCardRarity(int roll)
    {
        return fakeRoom.getCardRarity(roll);
    }

    @Override
    public void onPlayerEntry()
    {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        event = fakeRoom.event = new NeowEvent(false);
        fakeRoom.event.onEnterRoom();
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