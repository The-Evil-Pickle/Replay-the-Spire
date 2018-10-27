package com.megacrit.cardcrawl.mod.replay.ui.campfire;

import java.util.ArrayList;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.mod.replay.relics.BranchBurnRelic;
import com.megacrit.cardcrawl.mod.replay.vfx.campfire.CampfireExploreEffect;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect;

import basemod.CustomEventRoom;
import replayTheSpire.ReplayTheSpireMod;

public class BonfireExploreOption extends AbstractCampfireOption
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    
    public BonfireExploreOption() {
        this.label = BonfireExploreOption.TEXT[0];
        this.description = BonfireExploreOption.TEXT[1];
        this.img = ReplayTheSpireMod.exploreButton;
    }
    
    @Override
    public void useOption() {
    	AbstractDungeon.effectList.add(new CampfireExploreEffect());
        /*final MapRoomNode cur = AbstractDungeon.currMapNode;
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
        AbstractDungeon.effectList.clear();
        AbstractDungeon.topLevelEffects.clear();
        AbstractDungeon.topLevelEffectsQueue.clear();
        AbstractDungeon.effectsQueue.clear();
        AbstractDungeon.dungeonMapScreen.dismissable = true;
        AbstractDungeon.setCurrMapNode(AbstractDungeon.nextRoom = node);
        AbstractDungeon.getCurrRoom().onPlayerEntry();
        AbstractDungeon.scene.nextRoom(node.room);
        AbstractDungeon.rs = AbstractDungeon.RenderScene.EVENT;*/
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Explore Option");
        TEXT = BonfireExploreOption.uiStrings.TEXT;
    }
}