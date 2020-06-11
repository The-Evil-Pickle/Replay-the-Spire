package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.unlock.*;

import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.ReplayTheSpireMod;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.rewards.chests.BossChest;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class AncientBell extends ReplayAbstractRelic
{
    public static final String ID = "Replay:Ancient Bell";
    private boolean cardsReceived;
    private int relicsReceived;
    
    public AncientBell() {
        super(ID, "replaybell.png", RelicTier.BOSS, LandingSound.SOLID);
        this.cardsReceived = true;
        this.relicsReceived = 3;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void onEquip() {
        this.cardsReceived = false;
        this.relicsReceived = 0;
    }
    
    @Override
    public void update() {
        super.update();
        if (!this.cardsReceived && !AbstractDungeon.isScreenUp && AbstractDungeon.player.relics.get(AbstractDungeon.player.relics.size()-1).isDone) {
        	if (this.relicsReceived < 3) {
        		ReplayTheSpireMod.noSkipRewardsRoom = true;
    			if (AbstractDungeon.getCurrRoom() instanceof TreasureRoomBoss) {
    	        	TreasureRoomBoss cRoom = (TreasureRoomBoss)AbstractDungeon.getCurrRoom();
    	        	BossChest chest = (BossChest) cRoom.chest;
    	        	RelicTier tier = RelicTier.COMMON;
    	        	switch(this.relicsReceived) {
    	        	case 1:
    	        		tier = RelicTier.UNCOMMON;
    	        		break;
    	        	case 2:
    	        		tier = RelicTier.RARE;
    	        		break;
    	        	}
    	        	chest.relics.clear();
    	            for (int i = 0; i < 3; ++i) {
    	            	chest.relics.add(AbstractDungeon.returnRandomScreenlessRelic(tier));
    	            }
    	            AbstractDungeon.bossRelicScreen.open(chest.relics);
    	            this.relicsReceived++;
    	        }
        	} else {
        		AbstractDungeon.combatRewardScreen.open();
                AbstractDungeon.combatRewardScreen.rewards.clear();
                for (int i=0; i<3; i++) {
                	RewardItem r = new RewardItem();
                	final CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                	for (AbstractCard c : AbstractDungeon.srcCurseCardPool.group) {
    					group.addToBottom(c.makeCopy());
    				}
                	group.addToBottom(new CurseOfTheBell());
                	for (int c=0; c < r.cards.size(); c++) {
            			r.cards.set(c, group.getRandomCard(AbstractDungeon.cardRng));
            			group.removeCard(r.cards.get(c));
                	}
                	AbstractDungeon.combatRewardScreen.rewards.add(r);
                }
                AbstractDungeon.combatRewardScreen.positionRewards();
                //AbstractDungeon.overlayMenu.proceedButton.setLabel(this.DESCRIPTIONS[2]);
                this.cardsReceived = true;
                AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25f;
        	}
        }
        if (this.hb.hovered && InputHelper.justClickedLeft) {
            CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2f, -0.3f));
            this.flash();
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new AncientBell();
    }

    @Override
    public boolean canSpawn() {
    	if (!(AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom() instanceof TreasureRoomBoss))
    		return false;
    	return true;
    }
}
