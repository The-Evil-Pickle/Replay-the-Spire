package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CursedKey;
import com.megacrit.cardcrawl.rewards.chests.BossChest;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.ReplayTheSpireMod;
import replayTheSpire.panelUI.ReplayBooleanSetting;
import replayTheSpire.panelUI.ReplayRelicSetting;

public class GrabBag extends ReplayAbstractRelic
{
    public static final String ID = "Replay:Grab Bag";

    boolean hasRelicOne;
    boolean hasRelicTwo;
    boolean hasRelicThree;
    boolean hasRelicFour;
    public static ArrayList<String> energyRelics = new ArrayList<String>();
    public static ArrayList<String> nonEnergyRelics = new ArrayList<String>();
    public static ReplayBooleanSetting DOUBLE_RELICS = new ReplayBooleanSetting("grabBag_double", "Double energy relics, lose 1 energy", true);
    public static ReplayBooleanSetting DOUBLE_RELICS_2 = new ReplayBooleanSetting("grabBag_noneng", "Double non-energy relics", false);
    public ArrayList<ReplayRelicSetting> BuildRelicSettings() {
    	ArrayList<ReplayRelicSetting> settings = new ArrayList<ReplayRelicSetting>();
    	settings.add(DOUBLE_RELICS);
    	settings.add(DOUBLE_RELICS_2);
		return settings;
	}
    public GrabBag() {
        super(ID, "grabBag.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.FLAT);
        this.hasRelicOne = true;
        this.hasRelicTwo = true;
        this.hasRelicThree = true;
        this.hasRelicFour = true;
    }
    
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[DOUBLE_RELICS.value ? 1 : 0] + this.DESCRIPTIONS[DOUBLE_RELICS_2.value ? 3 : 2] + this.DESCRIPTIONS[4];
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
    	if ((DOUBLE_RELICS_2.value ? !this.hasRelicFour : !this.hasRelicTwo) && AbstractDungeon.isScreenUp == false && AbstractDungeon.player.relics.get(AbstractDungeon.player.relics.size()-1).isDone) {
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
    		} else if (!this.hasRelicThree) {
    			ReplayTheSpireMod.noSkipRewardsRoom = true;
    			if (AbstractDungeon.getCurrRoom() instanceof TreasureRoomBoss) {
    	        	TreasureRoomBoss cRoom = (TreasureRoomBoss)AbstractDungeon.getCurrRoom();
    	        	BossChest chest = (BossChest) cRoom.chest;
    	        	chest.relics.clear();
    	            for (int i = 0; i < 3; ++i) {
    	            	chest.relics.add(RelicLibrary.getRelic(GrabBag.getRandomKey(GrabBag.energyRelics)));
    	            }
    	            AbstractDungeon.bossRelicScreen.open(chest.relics);
    	            this.hasRelicThree = true;
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
    		} else if (!this.hasRelicFour) {
    			ReplayTheSpireMod.noSkipRewardsRoom = true;
    			if (AbstractDungeon.getCurrRoom() instanceof TreasureRoomBoss) {
    	        	TreasureRoomBoss cRoom = (TreasureRoomBoss)AbstractDungeon.getCurrRoom();
    	        	BossChest chest = (BossChest) cRoom.chest;
    	        	chest.relics.clear();
    	            for (int i = 0; i < 3; ++i) {
    	            	chest.relics.add(RelicLibrary.getRelic(GrabBag.getRandomKey(GrabBag.nonEnergyRelics)));
    	            }
    	            AbstractDungeon.bossRelicScreen.open(chest.relics);
    	            this.hasRelicFour = true;
    	        }
    		}
    	}
    }
    @Override
    public void onUnequip() {
        if (DOUBLE_RELICS.value) {
	    	final EnergyManager energy = AbstractDungeon.player.energy;
	        ++energy.energyMaster;
        }
    }
    
    public static void buildLists() {
    	GrabBag.energyRelics.clear();
        GrabBag.nonEnergyRelics.clear();
        String ckd = ((new CursedKey()).DESCRIPTIONS[1]) + ((new CursedKey()).DESCRIPTIONS[0]);
        String akd = "Gain [E] at the start of each turn.";
        String bkd = (new ChewingGum()).DESCRIPTIONS[0];
        String energyDesc = (ckd.substring(0, ckd.indexOf("[")));
        int endex = ckd.indexOf(ReplayTheSpireMod.LOC_FULLSTOP);
        int stardex = 0;
        boolean orthz = false;
        String energyDesc2 = "[E]";
        if (endex < 0) {
        	endex = ckd.indexOf("]") + 2;
        	stardex = 1;
        	orthz = true;
        	if (endex < 0) {
        		return;
        	}
        } else {
        	energyDesc2 = (ckd.substring(ckd.indexOf("]") + 1, endex));
        }

        String energyDescAlt = (akd.substring(stardex, akd.indexOf("[")));
        String energyDescAlt2 = (akd.substring(akd.indexOf("]") + 1, akd.indexOf(".")));
        String energyDescB = (bkd.substring(0, bkd.indexOf("[")));
        for (AbstractRelic r : RelicLibrary.bossList) {
        	if (!(r instanceof GrabBag || r instanceof AncientBell)) {
        		if (r.canSpawn()) {
            		boolean g1 = false;
                	boolean g2 = false;
                	for (String d : r.DESCRIPTIONS) {
                		if (d.contains(energyDesc) || d.contains(energyDescAlt) || d.contains(energyDescB)) {
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
                			if (r instanceof OnyxGauntlets || r instanceof CursedDEight || r.relicId.equals("RNG:RNG")) {
                				GrabBag.energyRelics.add(r.relicId);
                			} else if (r instanceof DrinkMe) {
                				GrabBag.nonEnergyRelics.add(r.relicId);
                			}
                		}
                	} else {
                		if (!(orthz && g2)) {
                			GrabBag.nonEnergyRelics.add(r.relicId);
                		}
                	}
            	}
        	}
        }
    }
    
    @Override
    public void onEquip() {
        this.hasRelicOne = false;
        this.hasRelicTwo = false;
        if (DOUBLE_RELICS.value) {
        	this.hasRelicThree = false;
            final EnergyManager energy = AbstractDungeon.player.energy;
            --energy.energyMaster;
        }
        if (DOUBLE_RELICS_2.value)
            this.hasRelicFour = false;
        GrabBag.buildLists();
    }
    
    public AbstractRelic makeCopy() {
        return new GrabBag();
    }
    
    @Override
    public int getPrice() {
    	return 0;
    }
    
    @Override
    public boolean canSpawn() {
    	if (!(AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom() instanceof TreasureRoomBoss))
    		return false;
    	
    	GrabBag.buildLists();
    	int numcheck = DOUBLE_RELICS.value ? 6 : 3;
    	return (GrabBag.energyRelics.size() >= numcheck && GrabBag.nonEnergyRelics.size() >= numcheck);
    }
}
