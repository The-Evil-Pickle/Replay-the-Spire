package com.megacrit.cardcrawl.helpers;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.rooms.*;
import java.util.*;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.events.thebottom.*;
import com.megacrit.cardcrawl.events.thecity.*;
import com.megacrit.cardcrawl.events.shrines.*;
import com.megacrit.cardcrawl.events.thebeyond.*;
import org.apache.logging.log4j.*;

public class EventHelper
{
    private static final Logger logger;
    private static final float BASE_ELITE_CHANCE = 0.02f;
    private static final float BASE_MONSTER_CHANCE = 0.1f;
    private static final float BASE_SHOP_CHANCE = 0.03f;
    private static final float BASE_TREASURE_CHANCE = 0.02f;
    private static final float RAMP_ELITE_CHANCE = 0.02f;
    private static final float RAMP_MONSTER_CHANCE = 0.1f;
    private static final float RAMP_SHOP_CHANCE = 0.03f;
    private static final float RAMP_TREASURE_CHANCE = 0.02f;
    private static final float RESET_ELITE_CHANCE = 0.0f;
    private static final float RESET_MONSTER_CHANCE = 0.1f;
    private static final float RESET_SHOP_CHANCE = 0.03f;
    private static final float RESET_TREASURE_CHANCE = 0.02f;
    private static float ELITE_CHANCE;
    private static float MONSTER_CHANCE;
    private static float SHOP_CHANCE;
    public static float TREASURE_CHANCE;
    
    public static RoomResult roll() {
        final float roll = AbstractDungeon.eventRng.random();
        EventHelper.logger.info("ROLL: " + roll);
        EventHelper.logger.info("ELIT: " + EventHelper.ELITE_CHANCE);
        EventHelper.logger.info("MNST: " + EventHelper.MONSTER_CHANCE);
        EventHelper.logger.info("SHOP: " + EventHelper.SHOP_CHANCE);
        EventHelper.logger.info("TRSR: " + EventHelper.TREASURE_CHANCE);
        final RoomResult[] possibleResults = new RoomResult[100];
        Arrays.fill(possibleResults, RoomResult.EVENT);
        int eliteSize = (int)(EventHelper.ELITE_CHANCE * 100.0f);
        if (AbstractDungeon.getCurrMapNode().y < 6) {
            eliteSize = 0;
        }
        final int monsterSize = (int)(EventHelper.MONSTER_CHANCE * 100.0f);
        int shopSize = (int)(EventHelper.SHOP_CHANCE * 100.0f);
        if (AbstractDungeon.getCurrRoom() instanceof ShopRoom) {
            shopSize = 0;
        }
        final int treasureSize = (int)(EventHelper.TREASURE_CHANCE * 100.0f);
        int fillIndex = 0;
        Arrays.fill(possibleResults, Math.min(99, fillIndex), Math.min(100, fillIndex + eliteSize), RoomResult.MONSTER);
        fillIndex += eliteSize;
        Arrays.fill(possibleResults, Math.min(99, fillIndex), Math.min(100, fillIndex + monsterSize), RoomResult.MONSTER);
        fillIndex += monsterSize;
        Arrays.fill(possibleResults, Math.min(99, fillIndex), Math.min(100, fillIndex + shopSize), RoomResult.SHOP);
        fillIndex += shopSize;
        Arrays.fill(possibleResults, Math.min(99, fillIndex), Math.min(100, fillIndex + treasureSize), RoomResult.TREASURE);
        RoomResult choice = possibleResults[(int)(roll * 100.0f)];
        if (choice == RoomResult.ELITE) {
            if (AbstractDungeon.player.hasRelic("Juzu Bracelet")) {
                AbstractDungeon.player.getRelic("Juzu Bracelet").flash();
                choice = RoomResult.EVENT;
            }
            EventHelper.ELITE_CHANCE = 0.0f;
        }
        else {
            EventHelper.ELITE_CHANCE += 0.02f;
        }
        if (choice == RoomResult.MONSTER) {
            if (AbstractDungeon.player.hasRelic("Juzu Bracelet")) {
                AbstractDungeon.player.getRelic("Juzu Bracelet").flash();
                choice = RoomResult.EVENT;
            }
            EventHelper.MONSTER_CHANCE = 0.1f;
        }
        else {
            EventHelper.MONSTER_CHANCE += 0.1f;
        }
        if (choice == RoomResult.SHOP) {
            EventHelper.SHOP_CHANCE = 0.03f;
        }
        else {
            EventHelper.SHOP_CHANCE += 0.03f;
        }
        if (choice == RoomResult.TREASURE) {
            if (AbstractDungeon.player.hasRelic("Tiny Chest")) {
                AbstractDungeon.player.getRelic("Tiny Chest").flash();
                EventHelper.TREASURE_CHANCE = 0.120000005f;
            }
            else {
                EventHelper.TREASURE_CHANCE = 0.02f;
            }
        }
        else {
            EventHelper.TREASURE_CHANCE += 0.02f;
        }
        return choice;
    }
    
    public static void resetProbabilities() {
        EventHelper.ELITE_CHANCE = 0.0f;
        EventHelper.MONSTER_CHANCE = 0.1f;
        EventHelper.SHOP_CHANCE = 0.03f;
        if (AbstractDungeon.player.hasRelic("Tiny Chest")) {
            EventHelper.TREASURE_CHANCE = 0.120000005f;
        }
        else {
            EventHelper.TREASURE_CHANCE = 0.02f;
        }
    }
    
    public static void setChances(final ArrayList<Float> chances) {
        EventHelper.ELITE_CHANCE = chances.get(0);
        EventHelper.MONSTER_CHANCE = chances.get(1);
        EventHelper.SHOP_CHANCE = chances.get(2);
        EventHelper.TREASURE_CHANCE = chances.get(3);
    }
    
    public static ArrayList<Float> getChances() {
        final ArrayList<Float> chances = new ArrayList<Float>();
        chances.add(EventHelper.ELITE_CHANCE);
        chances.add(EventHelper.MONSTER_CHANCE);
        chances.add(EventHelper.SHOP_CHANCE);
        chances.add(EventHelper.TREASURE_CHANCE);
        return chances;
    }
    
    public static AbstractEvent getEvent(final String key) {
        switch (key) {
            case "Accursed Blacksmith": {
                return new AccursedBlacksmith();
            }
            case "Bonfire Elementals": {
                return new Bonfire();
            }
            case "Fountain of Cleansing": {
                return new FountainOfCurseRemoval();
            }
            case "Duplicator": {
                return new Duplicator();
            }
            case "Lab": {
                return new Lab();
            }
            case "Match and Keep!": {
                return new GremlinMatchGame();
            }
            case "Golden Shrine": {
                return new GoldShrine();
            }
            case "Purifier": {
                return new PurificationShrine();
            }
            case "Transmorgrifier": {
                return new Transmogrifier();
            }
            case "Wheel of Change": {
                return new GremlinWheelGame();
            }
            case "Upgrade Shrine": {
                return new UpgradeShrine();
            }
            case "Big Fish": {
                return new BigFish();
            }
            case "The Cleric": {
                return new Cleric();
            }
            case "Dead Adventurer": {
                return new DeadAdventurer();
            }
            case "Golden Wing": {
                return new GoldenWing();
            }
            case "Golden Idol": {
                return new GoldenIdolEvent();
            }
            case "World of Goop": {
                return new GoopPuddle();
            }
            case "Forgotten Altar": {
                return new ForgottenAltar();
            }
            case "Scrap Ooze": {
                return new ScrapOoze();
            }
            case "Liars Game": {
                return new Sssserpent();
            }
            case "Living Wall": {
                return new LivingWall();
            }
            case "Mushrooms": {
                return new Mushrooms();
            }
            case "N'loth": {
                return new Nloth();
            }
            case "Shining Light": {
                return new ShiningLight();
            }
            case "Vampires": {
                return new Vampires();
            }
            case "Addict": {
                return new Addict();
            }
            case "Back to Basics": {
                return new BackToBasics();
            }
            case "Beggar": {
                return new Beggar();
            }
            case "Cursed Tome": {
                return new CursedTome();
            }
            case "Drug Dealer": {
                return new DrugDealer();
            }
            case "Knowing Skull": {
                return new KnowingSkull();
            }
            case "Masked Bandits": {
                return new MaskedBandits();
            }
            case "The Library": {
                return new TheLibrary();
            }
            case "The Mausoleum": {
                return new TheMausoleum();
            }
            case "The Joust": {
                return new TheJoust();
            }
            case "The Woman in Blue": {
                return new WomanInBlue();
            }
            case "Mysterious Sphere": {
                return new MysteriousSphere();
            }
            case "Tomb of Lord Red Mask": {
                return new TombRedMask();
            }
            case "Falling": {
                return new Falling();
            }
            case "Stuck": {
                return new Stuck();
            }
            case "Winding Halls": {
                return new WindingHalls();
            }
            case "The Moai Head": {
                return new MoaiHead();
            }
            default: {
                EventHelper.logger.info("---------------------------\nERROR: Unspecified key: " + key + " in EventHelper.\n---------------------------");
                return null;
            }
        }
    }
    
    static {
        logger = LogManager.getLogger(EventHelper.class.getName());
        EventHelper.ELITE_CHANCE = 0.02f;
        EventHelper.MONSTER_CHANCE = 0.1f;
        EventHelper.SHOP_CHANCE = 0.03f;
        EventHelper.TREASURE_CHANCE = 0.02f;
    }
    
    public enum RoomResult
    {
        EVENT, 
        ELITE, 
        TREASURE, 
        SHOP, 
        MONSTER;
    }
}
