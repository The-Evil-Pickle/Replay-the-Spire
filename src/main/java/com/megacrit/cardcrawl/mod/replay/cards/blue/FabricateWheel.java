package com.megacrit.cardcrawl.mod.replay.cards.blue;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.defect.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.DiscoverPerminantAction;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PrayerWheel;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import basemod.abstracts.*;

public class FabricateWheel extends CustomCard
{
	public static final String ID = "ReplayTheSpireMod:Fabricate Wheel";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 3;
    
    public FabricateWheel() {
        super(ID, FabricateWheel.NAME, "cards/replay/replayBetaSkill.png", FabricateWheel.COST, FabricateWheel.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
		FleetingField.fleeting.set(this, true);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new PrayerWheel());
    	if (this.upgraded) {
    		AbstractDungeon.actionManager.addToBottom(new DiscoverPerminantAction(this.color, CardRarity.RARE));
    	}
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new FabricateWheel();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = FabricateWheel.cardStrings.NAME;
        DESCRIPTION = FabricateWheel.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = FabricateWheel.cardStrings.UPGRADE_DESCRIPTION;
    }
    
    @SpirePatch(cls = "com.megacrit.cardcrawl.screens.CombatRewardScreen", method = "setupItemReward")
    public static class PatchTheWheelYo {
    	@SpireInsertPatch(rloc = 3)
    	public static void Insert(final CombatRewardScreen __instance) {
    		 if ((AbstractDungeon.getCurrRoom().event == null || (AbstractDungeon.getCurrRoom().event != null && !AbstractDungeon.getCurrRoom().event.noCardsInRewards)) && !(AbstractDungeon.getCurrRoom() instanceof TreasureRoom) && !(AbstractDungeon.getCurrRoom() instanceof RestRoom)) {
    			 if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom && AbstractDungeon.player.hasRelic("Prayer Wheel") && !(AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) && !(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)) {
    				 boolean gotem = false;
    				 for (AbstractRelic r : AbstractDungeon.player.relics) {
    					 if (r.relicId.equals(PrayerWheel.ID)) {
    						 if (gotem) {
    							RewardItem cardReward = new RewardItem();
			                    if (cardReward.cards.size() > 0) {
			                    	__instance.rewards.add(cardReward);
			                    }
    						 } else {
    							 gotem = true;
    						 }
    					 }
    				}
    			}
    		}
    	}
    }
    
}
