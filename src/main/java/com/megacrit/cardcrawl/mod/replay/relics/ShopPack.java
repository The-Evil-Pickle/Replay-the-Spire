package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class ShopPack extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:ShopPack";

	private boolean rewardsOpened;
    public ShopPack() {
        super(ID, "replay_sealedPack.png", RelicTier.SHOP, LandingSound.FLAT);
		this.rewardsOpened = true;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 3 + this.DESCRIPTIONS[1];
    }
    private void SetUpCardType(RewardItem r, int i) {
    	final CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		switch(i) {
			case 0:
				for (AbstractCard c : AbstractDungeon.srcColorlessCardPool.group) {
					if (c.rarity == AbstractCard.CardRarity.RARE)
						group.addToBottom(c.makeCopy());
				}
	            break;
			default:
				for (AbstractCard c : AbstractDungeon.srcColorlessCardPool.group) {
					if (c.rarity != AbstractCard.CardRarity.RARE || AbstractDungeon.cardRng.randomBoolean())
						group.addToBottom(c.makeCopy());
				}
				break;
		}
    	for (int c=0; c < r.cards.size(); c++) {
    			r.cards.set(c, group.getRandomCard(AbstractDungeon.cardRng));
    			group.removeCard(r.cards.get(c));
    	}
    }
    
    @Override
    public void onEquip() {
		this.rewardsOpened = false;
    }

    @Override
    public void update() {
        super.update();
        if (!AbstractDungeon.isScreenUp) {
        	if (!this.rewardsOpened) {
        		this.rewardsOpened = true;
        		AbstractDungeon.combatRewardScreen.open(this.DESCRIPTIONS[3]);
                AbstractDungeon.combatRewardScreen.rewards.clear();
                for (int i = 0; i < 3; ++i) {
                	RewardItem r = new RewardItem();
                	SetUpCardType(r, i);
                	AbstractDungeon.combatRewardScreen.rewards.add(r);
                }
                AbstractDungeon.combatRewardScreen.positionRewards();
                AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.0f;
	            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        	}
        }
    }
    @Override
    public AbstractRelic makeCopy() {
        return new ShopPack();
    }
    @Override
    public int getPrice() {
    	return 125;
    }
}
