package com.megacrit.cardcrawl.relics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.rewards.chests.BossChest;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import replayTheSpire.ReplayTheSpireMod;

public class GrabBag extends AbstractRelic
{
    public static final String ID = "Replay_Grab Bag";
    
    boolean hasRelicOne;
    boolean hasRelicTwo;
    public static ArrayList<String> energyRelics = new ArrayList<String>();
    public static ArrayList<String> nonEnergyRelics = new ArrayList<String>();
    
    
    public GrabBag() {
        super(ID, "grabBag.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.FLAT);
        this.hasRelicOne = true;
        this.hasRelicTwo = true;
    }
    
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    public static String getRandomKey(ArrayList<String> whiteList) {
    	String retVal;
    	retVal = "Red Circlet";
    	if (AbstractDungeon.bossRelicPool.isEmpty()) {
            return retVal;
        } else {
        	for (String r : AbstractDungeon.bossRelicPool) {
        		if (whiteList.contains(r)) {
        			retVal = r;
        			AbstractDungeon.bossRelicPool.remove(r);
        			break;
        		}
        	}
        	
        }
        return retVal;
    }
    
    public void update() {
    	super.update();
    	if (!this.hasRelicTwo && AbstractDungeon.isScreenUp == false && AbstractDungeon.player.relics.get(AbstractDungeon.player.relics.size()-1).isDone) {
    		if (!this.hasRelicOne) {
    			ReplayTheSpireMod.noSkipRewardsRoom = true;
    			if (AbstractDungeon.getCurrRoom() instanceof TreasureRoomBoss) {
    	        	TreasureRoomBoss cRoom = (TreasureRoomBoss)AbstractDungeon.getCurrRoom();
    	        	BossChest chest = (BossChest) cRoom.chest;
    	        	chest.relics.clear();
    	            for (int i = 0; i < 3; ++i) {
    	            	chest.relics.add(RelicLibrary.getRelic(GrabBag.getRandomKey(GrabBag.energyRelics)));
    	            }
    	            AbstractDungeon.bossRelicScreen.open(chest.relics);
    	            this.hasRelicOne = true;
    	        }
    		} else if (!this.hasRelicTwo) {
    			ReplayTheSpireMod.noSkipRewardsRoom = true;
    			if (AbstractDungeon.getCurrRoom() instanceof TreasureRoomBoss) {
    	        	TreasureRoomBoss cRoom = (TreasureRoomBoss)AbstractDungeon.getCurrRoom();
    	        	BossChest chest = (BossChest) cRoom.chest;
    	        	chest.relics.clear();
    	            for (int i = 0; i < 3; ++i) {
    	            	chest.relics.add(RelicLibrary.getRelic(GrabBag.getRandomKey(GrabBag.nonEnergyRelics)));
    	            }
    	            AbstractDungeon.bossRelicScreen.open(chest.relics);
    	            this.hasRelicTwo = true;
    	        }
    		}
    	}
    }
    
    public void onEquip() {
        this.hasRelicOne = false;
        this.hasRelicTwo = false;
        GrabBag.energyRelics.clear();
        GrabBag.nonEnergyRelics.clear();
        String ckd = ((new CursedKey()).DESCRIPTIONS[1]) + ((new CursedKey()).DESCRIPTIONS[0]);
        String akd = "Gain [E] at the start of each turn.";
        String energyDesc = (ckd.substring(0, ckd.indexOf("[")));
        String energyDesc2 = (ckd.substring(ckd.indexOf("]") + 2, ckd.indexOf(".")));
        String energyDescAlt = (akd.substring(0, akd.indexOf("[")));
        String energyDescAlt2 = (akd.substring(akd.indexOf("]") + 2, akd.indexOf(".")));
        for (AbstractRelic r : RelicLibrary.bossList) {
        	boolean g1 = false;
        	boolean g2 = false;
        	for (String d : r.DESCRIPTIONS) {
        		if (d.contains(energyDesc) || d.contains(energyDescAlt)) {
        			g1 = true;
        		}
        		if (d.contains(energyDesc2) || d.contains(energyDescAlt2)) {
        			g2 = true;
        		}
        	}
        	if (g1) {
        		if (g2) {
        			GrabBag.energyRelics.add(r.relicId);
        		} else {
        			if (r instanceof OnyxGauntlets || r.relicId.equals("RNG:RNG")) {
        				GrabBag.energyRelics.add(r.relicId);
        			}
        		}
        	} else {
        		GrabBag.nonEnergyRelics.add(r.relicId);
        	}
        }
    }
    
    public AbstractRelic makeCopy() {
        return new GrabBag();
    }
}
