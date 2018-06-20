package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import java.util.*;

public class RingOfGreed extends AbstractRelic
{
    public static final String ID = "Bloody Idol";
    private static final int GAIN_GOLD = 50;
    private static final int GAIN_RELICS = 2;
    private static final int GOLD_TRIGGER = 150;
	public static final ArrayList<String> greedlist;
	
    
    public RingOfGreed() {
        super("Ring of Greed", "cring_greed.png", RelicTier.SPECIAL, LandingSound.FLAT);
		this.counter = GOLD_TRIGGER;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + GAIN_GOLD + this.DESCRIPTIONS[1] + GAIN_RELICS + this.DESCRIPTIONS[2] + GOLD_TRIGGER + this.DESCRIPTIONS[3];
    }
    
    public void onPlayerGainGold(int amount) {
		if (AbstractDungeon.playerhasRelic("Ectoplasm")) {
			return;
		}
        this.flash();
        this.counter -= amount;
		while (this.counter <= 0) {
			this.counter += GOLD_TRIGGER;
			//give curse
		}
    }
	
    @Override
    public void onEquip() {
		this.counter = GOLD_TRIGGER;
		AbstractDungeon.player.gainGold(GAIN_GOLD);
		ArrayList<String> greedpool = new ArrayList<String>();
		for (String key : greedlist) {
			if (AbstractDungeon.commonRelicPool.contains(key) ||
			AbstractDungeon.uncommonRelicPool.contains(key) ||
			AbstractDungeon.rareRelicPool.contains(key) ||
			AbstractDungeon.shopRelicPool.contains(key)) {
				greedpool.add(key);
			}
		}
		if (!AbstractDungeon.id.equals("Exordium") && !AbstractDungeon.player.hasRelic("Golden Idol")) {
			greedpool.add("Golden Idol");
		}
		greedlist.shuffle();
		for (int i=0; i < GAIN_RELICS; i++) {
			String gimme = greedlist.remove(0);
			if (AbstractDungeon.commonRelicPool.contains(gimme)) {
				AbstractDungeon.commonRelicPool.remove(gimme);
			}
			if (AbstractDungeon.uncommonRelicPool.contains(gimme)) {
				AbstractDungeon.uncommonRelicPool.remove(gimme);
			}
			if (AbstractDungeon.rareRelicPool.contains(gimme)) {
				AbstractDungeon.rareRelicPool.remove(gimme);
			}
			if (AbstractDungeon.shopRelicPool.contains(gimme)) {
				AbstractDungeon.shopRelicPool.remove(gimme);
			}
			AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, RelicLibrary.getRelic(gimme).makeCopy());
		}
	}
    
    @Override
    public AbstractRelic makeCopy() {
        return new RingOfGreed();
    }
	
    static {
		greedlist = new ArrayList<String>();
		greedlist.add("Tiny Chest");
		greedlist.add("Smiling Mask");
		greedlist.add("The Courier");
		greedlist.add("Old Coin");
		greedlist.add("Membership Card");
		greedlist.add("Blue Doll");
		greedlist.add("Bandana");
	}
}
