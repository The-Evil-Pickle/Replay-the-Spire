package replayTheSpire.patches;

import java.util.*;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.neow.*;
import com.megacrit.cardcrawl.neow.NeowReward.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import basemod.abstracts.CustomCard;
import replayTheSpire.ReplayTheSpireMod;

public class NeowRewardPatches {
	
	////////////BANNED STARTING RELICS/////
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "returnRandomRelicKey")
	public static class BannedStartingRelics {
		
		static ArrayList<String> startingRelicBlacklist = new ArrayList<String>();//you can't get these relics from neow
		static ArrayList<String> outOfPlaceRelicBlacklist = new ArrayList<String>();//you can't get these boss/shop relics outside the correct rooms for any reason
		static {
			startingRelicBlacklist.add(Ectoplasm.ID);
			startingRelicBlacklist.add(BottledFlame.ID);
			startingRelicBlacklist.add(BottledLightning.ID);
			startingRelicBlacklist.add(BottledTornado.ID);
			startingRelicBlacklist.add(BottledSteam.ID);
			startingRelicBlacklist.add(BottledFlurry.ID);
			outOfPlaceRelicBlacklist.add(GrabBag.ID);//uses the boss chest, so doesn't work outside boss treasure rooms
			outOfPlaceRelicBlacklist.add(BargainBundle.ID);//directly impacts shop purchased from, so won't work if obtained outside a shop
		}
		
		public static String Postfix(String __Result, final AbstractRelic.RelicTier tier) {
			if (UnlockTracker.isRelicLocked(__Result) && !Settings.treatEverythingAsUnlocked()) {
				return AbstractDungeon.returnRandomRelicKey(tier);
			}
			AbstractRoom room = AbstractDungeon.getCurrRoom();
			if (room != null) {
				if (room instanceof NeowRoom) {
					if (startingRelicBlacklist.contains(__Result) || outOfPlaceRelicBlacklist.contains(__Result)) {
						return AbstractDungeon.returnRandomRelicKey(tier);
					}
				} else {
					switch (tier) {
						case SHOP:
							if (!(room instanceof ShopRoom)) {
								if (outOfPlaceRelicBlacklist.contains(__Result)) {
									return AbstractDungeon.returnRandomRelicKey(tier);
								}
							}
							break;
						case BOSS:
							if (!(room instanceof TreasureRoomBoss)) {
								if (outOfPlaceRelicBlacklist.contains(__Result)) {
									return AbstractDungeon.returnRandomRelicKey(tier);
								}
							}
							break;
					}
				}
			}
			
			return __Result;
		}
	}
	
	////////////NEW OPTIONS////////////////
	
	@SpireEnum
    public static NeowReward.NeowRewardDrawback BASIC_CARDS;
	
	@SpireEnum
	public static NeowReward.NeowRewardType COLORLESS_CARD;
	
	public static final int NUM_BASIC_CARDS = 3;
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.neow.NeowReward", method = "getRewardDrawbackOptions")
	public static class DrawbackPatch {
		
		public static ArrayList<NeowRewardDrawbackDef> Postfix(ArrayList<NeowRewardDrawbackDef> __result, NeowReward __instance) {
			__result.add(new NeowRewardDrawbackDef(BASIC_CARDS, "[ #rObtain #r" + NUM_BASIC_CARDS + " #rrandom #rbasic #rcards "));
			
			return __result;
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.neow.NeowReward", method = "getRewardOptions")
	public static class RewardsPatch {
		
		public static ArrayList<NeowRewardDef> Postfix(ArrayList<NeowRewardDef> __result, NeowReward __instance, final int category) {

			if (category == 0) {
				__result.add(new NeowRewardDef(COLORLESS_CARD, "[ #gObtain #ga #grandom #gcolorless #gCard ]"));
			}
			
			if (__instance.drawback == BASIC_CARDS) {
				for (NeowRewardDef r : __result) {
					if (r.type == NeowRewardType.REMOVE_TWO) {
						__result.remove(r);
						break;
					}
				}
				for (NeowRewardDef r : __result) {
					if (r.type == NeowRewardType.TRANSFORM_TWO_CARDS) {
						__result.remove(r);
						break;
					}
				}
			}
			
			return __result;
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.neow.NeowReward", method = "activate")
	public static class ActivatePatch {
		
		public static void Postfix(NeowReward __instance) {
			
			if (__instance.type == COLORLESS_CARD) {
				AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.returnTrulyRandomColorlessCard(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F)); 
			}
			
			if (__instance.drawback == NeowRewardPatches.BASIC_CARDS) {
				final ArrayList<AbstractCard> basicCards = new ArrayList<AbstractCard>();
		        final List<AbstractCard> list = (List<AbstractCard>)CardLibrary.getAllCards();
		        for (final AbstractCard c : list) {
		            if (c.rarity == AbstractCard.CardRarity.BASIC && ((AbstractDungeon.player != null && AbstractDungeon.player.masterMaxOrbs > 0) || !(c instanceof Dualcast) && !(c instanceof Zap))) {
		            	basicCards.add(c);
		            	//make strikes and defends 2x as common as other cards
		            	if (c instanceof CustomCard) {
		            		CustomCard cc = (CustomCard)c;
		            		if (cc.isDefend() || cc.isStrike()) {
		            			basicCards.add(c.makeCopy());
		            		}
		            	} else if (c.cardID.toLowerCase().contains("strike") || c.cardID.toLowerCase().contains("defend")) {
		            		basicCards.add(c.makeCopy());
		            	} 
		            }
		        }
		        for (int i=0; i < NeowRewardPatches.NUM_BASIC_CARDS; i++) {
		        	AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(basicCards.get(NeowEvent.rng.random(0, basicCards.size() - 1)).makeCopy(), (Settings.WIDTH / (NeowRewardPatches.NUM_BASIC_CARDS + 2)) * (i+2), Settings.HEIGHT / 3.0F));
		        }
			}
			
			
			
		}
	}
}
