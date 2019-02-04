package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.mod.replay.events.shrines.ChaosEvent;
import com.megacrit.cardcrawl.relics.*;
import java.util.*;

public class RingOfAddiction extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:Ring of Addiction";
    private static final int GAIN_POTIONS = 3;
    private static final int GAIN_RELICS = 2;
    private static final int POTION_TRIGGER = 3;
    private static final int HP_LOSS = 2;
	public static final ArrayList<String> greedlist;
	private int relicsLeft;
	private ArrayList<String> greedpool;
	private boolean chuggedThisRoom;
	
    public RingOfAddiction() {
        super(ID, "cring_addiction.png", RelicTier.SPECIAL, LandingSound.FLAT);
		this.counter = POTION_TRIGGER;
		this.relicsLeft = 0;
		this.chuggedThisRoom = true;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + GAIN_POTIONS + this.DESCRIPTIONS[1] + GAIN_RELICS + this.DESCRIPTIONS[2] + POTION_TRIGGER + this.DESCRIPTIONS[3] + HP_LOSS + this.DESCRIPTIONS[4];
    }
	
    @Override
    public void setCounter(final int counter) {
        super.setCounter(counter);
        if (this.counter == 1) {
        	this.beginPulse();
        	this.pulse = true;
        } else {
        	this.pulse = false;
        }
    }
    
    @Override
    public void onEnterRoom(final AbstractRoom room) {
    	if (!this.chuggedThisRoom) {
    		setCounter(this.counter - 1);
        	if (this.counter <= 0) {
        		AbstractDungeon.player.decreaseMaxHealth(HP_LOSS);
        		this.flash();
        		setCounter(POTION_TRIGGER);
        	}
    	}
    	this.chuggedThisRoom = false;
    }
    
    @Override
    public void onUsePotion() {
        this.flash();
        setCounter(POTION_TRIGGER);
        this.chuggedThisRoom = true;
    }
    
    @Override
    public void onEquip() {
		this.counter = POTION_TRIGGER;
		this.greedpool = new ArrayList<String>();
		for (String key : RingOfAddiction.greedlist) {
			/*if (AbstractDungeon.commonRelicPool.contains(key) ||
			AbstractDungeon.uncommonRelicPool.contains(key) ||
			AbstractDungeon.rareRelicPool.contains(key) ||
			AbstractDungeon.bossRelicPool.contains(key) ||
			AbstractDungeon.shopRelicPool.contains(key)) {*/
			if (!AbstractDungeon.player.hasRelic(key)) {
				this.greedpool.add(key);
			}
		}
		Collections.shuffle(this.greedpool);
		this.greedpool.add(TinyHouse.ID);
		this.greedpool.add(RedCirclet.ID);
		this.relicsLeft = GAIN_RELICS;
    }
    

    @Override
    public void update() {
        super.update();
        if (this.relicsLeft > 0 && (!AbstractDungeon.isScreenUp || (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD))) {// && AbstractDungeon.combatRewardScreen != null && AbstractDungeon.combatRewardScreen.rewards.isEmpty()
        	if (!AbstractDungeon.isScreenUp) {
        		AbstractDungeon.combatRewardScreen.open();
                AbstractDungeon.combatRewardScreen.rewards.clear();
        	}
            while (this.relicsLeft > 0) {
            	String gimme = this.greedpool.remove(0);
            	AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(RelicLibrary.getRelic(gimme).makeCopy()));
            	this.relicsLeft--;
            	Iterator<String> s = AbstractDungeon.commonRelicPool.iterator();
                while (s.hasNext()) {
                    final String derp = s.next();
                    if (derp.equals(gimme)) {
                        s.remove();
                        break;
                    }
                }
                s = AbstractDungeon.uncommonRelicPool.iterator();
                while (s.hasNext()) {
                    final String derp = s.next();
                    if (derp.equals(gimme)) {
                        s.remove();
                        break;
                    }
                }
                s = AbstractDungeon.rareRelicPool.iterator();
                while (s.hasNext()) {
                    final String derp = s.next();
                    if (derp.equals(gimme)) {
                        s.remove();
                        break;
                    }
                }
                s = AbstractDungeon.bossRelicPool.iterator();
                while (s.hasNext()) {
                    final String derp = s.next();
                    if (derp.equals(gimme)) {
                        s.remove();
                        break;
                    }
                }
                s = AbstractDungeon.shopRelicPool.iterator();
                while (s.hasNext()) {
                    final String derp = s.next();
                    if (derp.equals(gimme)) {
                        s.remove();
                        break;
                    }
                }
            }
            for (int i=0; i < GAIN_POTIONS; i++) {
            	AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomPotion()));
            }
            AbstractDungeon.combatRewardScreen.positionRewards();
            AbstractDungeon.overlayMenu.proceedButton.setLabel("Continue");
            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25f;
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new RingOfAddiction();
    }
    @Override
    public int getPrice() {
    	return ChaosEvent.RING_SHOP_PRICE;
    }
	
    static {
		greedlist = new ArrayList<String>();
		//greedlist.add(Cauldron.ID);
		greedlist.add(PotionBelt.ID);
		greedlist.add(ToyOrnithopter.ID);
		greedlist.add(WhiteBeast.ID);
		greedlist.add(ChameleonRing.ID);
		if (Loader.isModLoaded("hubris")) {
			greedlist.add("hubris:EmptyBottle");
		}
		if (Loader.isModLoaded("gatherermod")) {
			greedlist.add("MiracleBag");
		}
	}
}
