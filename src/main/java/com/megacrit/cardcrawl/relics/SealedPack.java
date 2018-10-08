package com.megacrit.cardcrawl.relics;

import java.util.ArrayList;
import java.util.List;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.cards.curses.FaultyEquipment;
import com.megacrit.cardcrawl.cards.green.Defend_Green;
import com.megacrit.cardcrawl.cards.green.Strike_Green;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import basemod.ReflectionHacks;
import basemod.helpers.BaseModTags;
import basemod.helpers.BaseModCardTags;
import basemod.helpers.CardTags;
import replayTheSpire.ReplayTheSpireMod;

public class SealedPack extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:SealedPack";

	private boolean cursesSelected;
	private boolean cursesOpened;
	private boolean rewardsOpened;
    public SealedPack() {
        super(ID, "betaRelic.png", RelicTier.BOSS, LandingSound.FLAT);
		this.cursesSelected = true;
		this.cursesOpened = true;
		this.rewardsOpened = true;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    private void SetUpCardType(RewardItem r, int i) {
    	final CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		switch(i) {
			case 0:
		        final List<AbstractCard> list = (List<AbstractCard>)CardLibrary.getAllCards();
		        for (final AbstractCard c : list) {
		            if (c.rarity == AbstractCard.CardRarity.BASIC && !(c instanceof Strike_Red) && !(c instanceof Strike_Green) && !(c instanceof Strike_Blue) && !(c instanceof Defend_Red) && !(c instanceof Defend_Green) && !(c instanceof Defend_Blue) && !CardTags.hasTag(c, BaseModTags.BASIC_DEFEND) && !CardTags.hasTag(c, BaseModTags.BASIC_STRIKE) && !c.hasTag(BaseModCardTags.BASIC_STRIKE) && !c.hasTag(BaseModCardTags.BASIC_DEFEND)) {
		                group.addToBottom(c.makeCopy());
		            }
		        }
	            break;
			case 1:
				for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
					group.addToBottom(c.makeCopy());
				}
				break;
			case 2:
				for (AbstractCard c : AbstractDungeon.srcColorlessCardPool.group) {
					if (c.rarity != AbstractCard.CardRarity.RARE)
						group.addToBottom(c.makeCopy());
				}
				break;
			case 3:
				ArrayList<AbstractCard> blackCards = (ArrayList<AbstractCard>)ReflectionHacks.getPrivateStatic(infinitespire.helpers.CardHelper.class, "blackCards");
				for (AbstractCard c : blackCards) {
					group.addToBottom(c.makeCopy());
				}
				break;
			case 4:
				for (AbstractCard c : AbstractDungeon.srcCurseCardPool.group) {
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
		this.cursesSelected = false;
		this.cursesOpened = false;
		this.rewardsOpened = false;
    }

    @Override
    public void update() {
        super.update();
        if (!AbstractDungeon.isScreenUp) {
        	if (!this.cursesOpened) {
        		this.cursesOpened = true;
        		final CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    			for (int i=0; i < 3; i++) {
    				AbstractCard bowlCurse = AbstractDungeon.getCard(AbstractCard.CardRarity.CURSE);
    				while (bowlCurse.rarity == AbstractCard.CardRarity.SPECIAL || bowlCurse.isEthereal || FleetingField.fleeting.get(bowlCurse) || (bowlCurse instanceof FaultyEquipment)) {
    					bowlCurse = AbstractDungeon.getCard(AbstractCard.CardRarity.CURSE);
    				}
    				UnlockTracker.markCardAsSeen(bowlCurse.cardID);
    				tmp.addToTop(bowlCurse);
    			}
    			if (AbstractDungeon.isScreenUp) {
    	            AbstractDungeon.dynamicBanner.hide();
    	            AbstractDungeon.overlayMenu.cancelButton.hide();
    	            AbstractDungeon.previousScreen = AbstractDungeon.screen;
    	        }
    	        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
    	        AbstractDungeon.gridSelectScreen.open(tmp, 1, this.DESCRIPTIONS[2], false, false, false, false);
    			
        	} else if (!this.cursesSelected) {
        		if (AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
            		this.cursesSelected = true;
    				for (final AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
    					if (AbstractDungeon.player.hasRelic("Omamori") && AbstractDungeon.player.getRelic("Omamori").counter != 0) {
    						((Omamori)AbstractDungeon.player.getRelic("Omamori")).use();
    					} else {
    						AbstractCard c = card.makeCopy();
    						if (!SoulboundField.soulbound.get(c)) {
    							SoulboundField.soulbound.set(c, true);
    							c.rawDescription += " NL Soulbound.";
    							c.initializeDescription();
    						}
    						AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
    					}
    				}
    	            AbstractDungeon.gridSelectScreen.selectedCards.clear();	
        		}
        	} else if (!this.rewardsOpened) {
        		this.rewardsOpened = true;
        		AbstractDungeon.combatRewardScreen.open(this.DESCRIPTIONS[1]);
                AbstractDungeon.combatRewardScreen.rewards.clear();
                for (int i = 0; i < 4; ++i) {
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
        return new SealedPack();
    }
}
