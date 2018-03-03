package com.megacrit.cardcrawl.dungeons;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.scenes.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.random.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.map.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.gashapon.*;
import com.megacrit.cardcrawl.screens.saveAndContinue.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.unlock.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import com.megacrit.cardcrawl.helpers.*;

public class Exordium extends AbstractDungeon
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public static final String NAME;
    public static final String ID = "Exordium";
    
    public Exordium(final AbstractPlayer p, final ArrayList<String> emptyList) {
        super(Exordium.NAME, "Exordium", p, emptyList);
        this.initializeRelicList();
        if (Exordium.scene != null) {
            Exordium.scene.dispose();
        }
        (Exordium.scene = new TheBottomScene()).randomizeScene();
        Exordium.fadeColor = Color.valueOf("1e0f0aff");
        this.initializeSpecialOneTimeEventList();
        this.initializeLevelSpecificChances();
        Exordium.mapRng = new Random(Settings.seed);
        generateMap();
        CardCrawlGame.music.changeBGM(Exordium.id);
        AbstractDungeon.currMapNode = new MapRoomNode(0, -1);
        if (Settings.isShowBuild || !TipTracker.tips.get("NEOW_SKIP")) {
            AbstractDungeon.currMapNode.room = new EmptyRoom();
        }
        else {
            AbstractDungeon.currMapNode.room = new GashaponRoom(false);
            if (!Settings.isDailyRun) {
                final SaveFile saveFile = new SaveFile(SaveFile.SaveType.ENTER_ROOM);
                SaveAndContinue.save(saveFile);
                AbstractDungeon.effectList.add(new GameSavedEffect());
            }
        }
    }
    
    public Exordium(final AbstractPlayer p, final SaveFile saveFile) {
        super(Exordium.NAME, p, saveFile);
        CardCrawlGame.dungeon = this;
        if (Exordium.scene != null) {
            Exordium.scene.dispose();
        }
        Exordium.scene = new TheBottomScene();
        Exordium.fadeColor = Color.valueOf("1e0f0aff");
        this.initializeLevelSpecificChances();
        Exordium.miscRng = new Random(Settings.seed + saveFile.floor_num);
        CardCrawlGame.music.changeBGM(Exordium.id);
        Exordium.mapRng = new Random(Settings.seed);
        generateMap();
        Exordium.firstRoomChosen = true;
        this.populatePathTaken(saveFile);
        if (saveFile.floor_num == 0) {
            AbstractDungeon.firstRoomChosen = false;
        }
    }
    
    @Override
    protected void initializeLevelSpecificChances() {
        Exordium.shopRoomChance = 0.05f;
        Exordium.restRoomChance = 0.12f;
        Exordium.treasureRoomChance = 0.0f;
        Exordium.eventRoomChance = 0.22f;
        Exordium.eliteRoomChance = 0.08f;
        Exordium.smallChestChance = 50;
        Exordium.mediumChestChance = 33;
        Exordium.largeChestChance = 17;
        Exordium.smallPotionChance = 90;
        Exordium.mediumPotionChance = 10;
        Exordium.largePotionChance = 0;
        Exordium.commonRelicChance = 50;
        Exordium.uncommonRelicChance = 33;
        Exordium.rareRelicChance = 17;
        Exordium.colorlessRareChance = 0.3f;
        Exordium.cardUpgradedChance = 0.0f;
    }
    
    @Override
    protected void generateMonsters() {
        final ArrayList<MonsterInfo> monsters = new ArrayList<MonsterInfo>();
        monsters.add(new MonsterInfo("Cultist", 2.0f));
        monsters.add(new MonsterInfo("Jaw Worm", 2.0f));
        monsters.add(new MonsterInfo("2 Louse", 2.0f));
        monsters.add(new MonsterInfo("Small Slimes", 2.0f));
        MonsterInfo.normalizeWeights(monsters);
        this.populateMonsterList(monsters, 3, false);
        monsters.clear();
        monsters.add(new MonsterInfo("Blue Slaver", 2.0f));
        monsters.add(new MonsterInfo("Gremlin Gang", 1.0f));
        monsters.add(new MonsterInfo("Looter", 2.0f));
        monsters.add(new MonsterInfo("Large Slime", 2.0f));
        monsters.add(new MonsterInfo("Lots of Slimes", 1.0f));
        monsters.add(new MonsterInfo("Exordium Thugs", 1.5f));
        monsters.add(new MonsterInfo("Exordium Wildlife", 1.5f));
        monsters.add(new MonsterInfo("Red Slaver", 1.0f));
        monsters.add(new MonsterInfo("3 Louse", 2.0f));
        monsters.add(new MonsterInfo("2 Fungi Beasts", 2.0f));
        MonsterInfo.normalizeWeights(monsters);
        this.populateFirstStrongEnemy(monsters, this.generateExclusions());
        this.populateMonsterList(monsters, 12, false);
        monsters.clear();
        monsters.add(new MonsterInfo("Gremlin Nob", 1.0f));
        monsters.add(new MonsterInfo("Lagavulin", 1.0f));
        monsters.add(new MonsterInfo("3 Sentries", 1.0f));
        MonsterInfo.normalizeWeights(monsters);
        this.populateMonsterList(monsters, 5, true);
    }
    
    @Override
    protected ArrayList<String> generateExclusions() {
        final ArrayList<String> retVal = new ArrayList<String>();
        final String s = Exordium.monsterList.get(Exordium.monsterList.size() - 1);
        switch (s) {
            case "Looter": {
                retVal.add("Exordium Thugs");
            }
            case "Jaw Worm": {}
            case "Blue Slaver": {
                retVal.add("Red Slaver");
                retVal.add("Exordium Thugs");
                break;
            }
            case "2 Louse": {
                retVal.add("3 Louse");
                break;
            }
            case "Small Slimes": {
                retVal.add("Large Slime");
                retVal.add("Lots of Slimes");
                break;
            }
        }
        return retVal;
    }
    
    @Override
    protected void initializeBoss() {
        if (!UnlockTracker.isBossSeen("GUARDIAN")) {
            Exordium.bossList.add("The Guardian");
        }
        else if (!UnlockTracker.isBossSeen("GHOST")) {
            Exordium.bossList.add("Hexaghost");
        }
        else if (!UnlockTracker.isBossSeen("SLIME")) {
            Exordium.bossList.add("Slime Boss");
        }
        else {
            Exordium.bossList.add("The Guardian");
            Exordium.bossList.add("Hexaghost");
            Exordium.bossList.add("Slime Boss");
            Collections.shuffle(Exordium.bossList, new java.util.Random(Exordium.monsterRng.randomLong()));
        }
        if (Settings.isDemo) {
            Exordium.bossList.clear();
            Exordium.bossList.add("Hexaghost");
        }
    }
    
    @Override
    protected void initializeEventList() {
        Exordium.eventList.add("Big Fish");
        Exordium.eventList.add("The Cleric");
        Exordium.eventList.add("Dead Adventurer");
        Exordium.eventList.add("Golden Idol");
        Exordium.eventList.add("Golden Wing");
        Exordium.eventList.add("World of Goop");
        Exordium.eventList.add("Liars Game");
        Exordium.eventList.add("Living Wall");
        Exordium.eventList.add("Mushrooms");
        Exordium.eventList.add("Scrap Ooze");
        Exordium.eventList.add("Shining Light");
		Exordium.eventList.add("Stuck");
    }
    
    @Override
    protected void initializeShrineList() {
        Exordium.shrineList.add("Match and Keep!");
        Exordium.shrineList.add("Golden Shrine");
        Exordium.shrineList.add("Transmorgrifier");
        Exordium.shrineList.add("Purifier");
        Exordium.shrineList.add("Upgrade Shrine");
        Exordium.shrineList.add("Wheel of Change");
    }
    
    @Override
    protected void initializeEventImg() {
        if (Exordium.eventImg != null) {
            Exordium.eventImg.dispose();
            Exordium.eventImg = null;
        }
        Exordium.eventImg = ImageMaster.loadImage("images/ui/event/panel.png");
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Exordium");
        TEXT = Exordium.uiStrings.TEXT;
        NAME = Exordium.TEXT[0];
    }
}
