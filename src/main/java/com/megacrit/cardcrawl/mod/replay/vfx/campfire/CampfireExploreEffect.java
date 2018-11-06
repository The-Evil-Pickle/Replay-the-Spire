package com.megacrit.cardcrawl.mod.replay.vfx.campfire;

import basemod.CustomEventRoom;
import replayTheSpire.patches.NeowRewardPatches;

import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.events.beyond.*;
import com.megacrit.cardcrawl.events.exordium.*;
import com.megacrit.cardcrawl.events.city.*;

import java.util.ArrayList;

import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.core.*;

public class CampfireExploreEffect extends AbstractGameEffect
{
    private static final float DUR = 2.0f;
    private Color screenColor;
    
    public static final ArrayList<String> bannedEvents;
    
    static {
    	bannedEvents = new ArrayList<String>();
    	bannedEvents.add(SecretPortal.ID);
    	bannedEvents.add(Mushrooms.ID);
    	bannedEvents.add(MaskedBandits.ID);
    	bannedEvents.add(Falling.ID);
    	bannedEvents.add(GoopPuddle.ID);
    }
    
    public CampfireExploreEffect() {
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = 2.0f;
        this.screenColor.a = 0.0f;
        if (AbstractDungeon.getCurrRoom() instanceof RestRoom)
        ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
    }

    public static String generateEventName() {
    	if (AbstractDungeon.getCurrRoom() instanceof NeowRoom) {
    		return NeowRewardPatches.possibleEvents.get(NeowEvent.rng.random(NeowRewardPatches.possibleEvents.size() - 1));
    	}
        if (AbstractDungeon.eventRng.random(1.0f) < 0.25f) {
            if (!AbstractDungeon.shrineList.isEmpty() || !AbstractDungeon.specialOneTimeEventList.isEmpty()) {
                return getShrine();
            }
            if (!AbstractDungeon.eventList.isEmpty()) {
                return getEvent();
            }
            return null;
        }
        else {
            final String retVal = getEvent();
            if (retVal == null) {
                return getShrine();
            }
            return retVal;
        }
    }
    
    public static String getShrine() {
        final ArrayList<String> tmp = new ArrayList<String>();
        tmp.addAll(AbstractDungeon.shrineList);
        for (final String s : AbstractDungeon.specialOneTimeEventList) {
            final String e = s;
            if (bannedEvents.contains(s)) {
            	continue;
            }
            switch (s) {
                case "Fountain of Cleansing": {
                    if (AbstractDungeon.player.isCursed()) {
                        tmp.add(e);
                        continue;
                    }
                    continue;
                }
                case "Duplicator": {
                    if (AbstractDungeon.id.equals("TheCity") || AbstractDungeon.id.equals("TheBeyond")) {
                        tmp.add(e);
                        continue;
                    }
                    continue;
                }
                case "Knowing Skull": {
                    if (AbstractDungeon.id.equals("TheCity") && AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth / 2) {
                        tmp.add(e);
                        continue;
                    }
                    continue;
                }
                case "N'loth": {
                    if ((AbstractDungeon.id.equals("TheCity") || AbstractDungeon.id.equals("TheCity")) && AbstractDungeon.player.relics.size() >= 2) {
                        tmp.add(e);
                        continue;
                    }
                    continue;
                }
                case "The Joust": {
                    if (AbstractDungeon.id.equals("TheCity") && AbstractDungeon.player.gold >= 50) {
                        tmp.add(e);
                        continue;
                    }
                    continue;
                }
                case "The Woman in Blue": {
                    if (AbstractDungeon.player.gold >= 50) {
                        tmp.add(e);
                        continue;
                    }
                    continue;
                }
                default: {
                    tmp.add(e);
                    continue;
                }
            }
        }
        final String tmpKey = tmp.get(AbstractDungeon.eventRng.random(tmp.size() - 1));
        AbstractDungeon.shrineList.remove(tmpKey);
        AbstractDungeon.specialOneTimeEventList.remove(tmpKey);
        return tmpKey;
    }
    
    public static String getEvent() {
        final ArrayList<String> tmp = new ArrayList<String>();
        for (final String s : AbstractDungeon.eventList) {
            final String e = s;
            if (bannedEvents.contains(s)) {
            	continue;
            }
            switch (s) {
                case "Dead Adventurer": {
                    if (AbstractDungeon.floorNum > 6) {
                        tmp.add(e);
                        continue;
                    }
                    continue;
                }
                case "The Moai Head": {
                    if (!AbstractDungeon.player.hasRelic("Golden Idol") && AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth > 0.5f) {
                        continue;
                    }
                    tmp.add(e);
                    continue;
                }
                default: {
                    tmp.add(e);
                    continue;
                }
            }
        }
        if (tmp.isEmpty()) {
            return getShrine();
        }
        final String tmpKey = tmp.get(AbstractDungeon.eventRng.random(tmp.size() - 1));
        AbstractDungeon.eventList.remove(tmpKey);
        return tmpKey;
    }
    
    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.updateBlackScreenColor();
        if (this.duration < 1.0f) {
            this.isDone = true;
            CardCrawlGame.music.unsilenceBGM();
            String eventName = generateEventName();
            AbstractDungeon.eventList.add(0, eventName);
            final MapRoomNode cur = AbstractDungeon.currMapNode;
            final MapRoomNode node = new MapRoomNode(cur.x, cur.y);
            node.room = (AbstractRoom)new CustomEventRoom();
            final ArrayList<MapEdge> curEdges = (ArrayList<MapEdge>)cur.getEdges();
            for (final MapEdge edge : curEdges) {
                node.addEdge(edge);
            }
            AbstractDungeon.previousScreen = null;
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.dungeonMapScreen.closeInstantly();
            AbstractDungeon.closeCurrentScreen();
            AbstractDungeon.topPanel.unhoverHitboxes();
            AbstractDungeon.fadeIn();
            //AbstractDungeon.effectList.clear();
            //AbstractDungeon.topLevelEffects.clear();
            //AbstractDungeon.topLevelEffectsQueue.clear();
            //AbstractDungeon.effectsQueue.clear();
            AbstractDungeon.dungeonMapScreen.dismissable = true;
            AbstractDungeon.setCurrMapNode(AbstractDungeon.nextRoom = node);
            AbstractDungeon.getCurrRoom().onPlayerEntry();
            AbstractDungeon.scene.nextRoom(node.room);
            AbstractDungeon.rs = AbstractDungeon.RenderScene.EVENT;
        }
    }
    
    private void updateBlackScreenColor() {
        if (this.duration > 1.5f) {
            this.screenColor.a = Interpolation.fade.apply(1.0f, 0.0f, (this.duration - 1.5f) * 2.0f);
        }
        else if (this.duration < 1.0f) {
            this.screenColor.a = Interpolation.fade.apply(0.0f, 1.0f, this.duration);
        }
        else {
            this.screenColor.a = 1.0f;
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0f, 0.0f, Settings.WIDTH, Settings.HEIGHT);
    }
}
