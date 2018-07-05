package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import java.util.*;

public class RingOfGreed extends AbstractRelic
{
    public static final String ID = "Ring of Greed";
    private static final int GAIN_GOLD = 50;
    private static final int GAIN_RELICS = 2;
    private static final int GOLD_TRIGGER = 150;
	public static final ArrayList<String> greedlist;
	private int relicsLeft;
	private ArrayList<String> greedpool;
	
    public RingOfGreed() {
        super("Ring of Greed", "cring_greed.png", RelicTier.SPECIAL, LandingSound.FLAT);
		this.counter = GOLD_TRIGGER;
		this.relicsLeft = 0;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + GAIN_GOLD + this.DESCRIPTIONS[1] + GAIN_RELICS + this.DESCRIPTIONS[2] + GOLD_TRIGGER + this.DESCRIPTIONS[3];
    }
    
    public void onPlayerGainGold(int amount) {
		if (AbstractDungeon.player.hasRelic("Ectoplasm")) {
			return;
		}
        this.flash();
        this.counter -= amount;
		while (this.counter <= 0) {
			this.counter += GOLD_TRIGGER;
			final AbstractCard greedCurse = AbstractDungeon.getCard(AbstractCard.CardRarity.CURSE);
			UnlockTracker.markCardAsSeen(greedCurse.cardID);
			AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(greedCurse, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, false));
		}
    }
	
    @Override
    public void onEquip() {
		this.counter = GOLD_TRIGGER;
		AbstractDungeon.player.gainGold(GAIN_GOLD);
		this.greedpool = new ArrayList<String>();
		for (String key : RingOfGreed.greedlist) {
			if (AbstractDungeon.commonRelicPool.contains(key) ||
			AbstractDungeon.uncommonRelicPool.contains(key) ||
			AbstractDungeon.rareRelicPool.contains(key) ||
			AbstractDungeon.shopRelicPool.contains(key)) {
				this.greedpool.add(key);
			}
		}
		if (!AbstractDungeon.id.equals("Exordium") && !AbstractDungeon.player.hasRelic("Golden Idol")) {
			this.greedpool.add("Golden Idol");
		}
		Collections.shuffle(this.greedpool);
		this.relicsLeft = GAIN_RELICS;
		for (Iterator<String> iterator = this.greedpool.iterator(); iterator.hasNext(); ) {
			String gimme = iterator.next();
			if (this.relicsLeft > 0) {
				this.relicsLeft--;
				/*if (AbstractDungeon.commonRelicPool.contains(gimme)) {
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
				}*/
				AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, RelicLibrary.getRelic(gimme).makeCopy());
				iterator.remove();
			}
		}
		/*for (int i=0; i < GAIN_RELICS; i++) {
			String gimme = greedpool.remove(0);
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
		}*/
	}
	
	
	/*@Override
	public void update() {
        super.update();
		if (this.relicsLeft > 0 && !AbstractDungeon.isScreenUp) {
            AbstractDungeon.combatRewardScreen.rewards.clear();
            AbstractDungeon.combatRewardScreen.open();
			while (this.relicsLeft > 0) {
				String gimme = this.greedpool.remove(0);
				AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(RelicLibrary.getRelic(gimme).makeCopy()));
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
				this.relicsLeft--;
			}
            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25f;
		}
		
	}*/
    
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
