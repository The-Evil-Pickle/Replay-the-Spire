package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.campfire.*;

import basemod.ReflectionHacks;
import replayTheSpire.ReplayTheSpireMod;

public class ArrowheadPatches {

	public static boolean hasSecondUpgrade;
	public static boolean didSecondUpgrade;
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect", method = "update")
	public static class VfxUpdatePatch {
		
		@SpireInsertPatch(rloc = 18)
		public static void Insert(CampfireSmithEffect __Instance) {
			ReplayTheSpireMod.logger.info("line 18");
			ReplayTheSpireMod.logger.info(ArrowheadPatches.hasSecondUpgrade);
			ReplayTheSpireMod.logger.info(ArrowheadPatches.didSecondUpgrade);
			//ReplayTheSpireMod.logger.info((boolean)ReflectionHacks.getPrivate((Object)__Instance, (Class)CampfireSmithEffect.class, "selectedCard"));
			if (ArrowheadPatches.hasSecondUpgrade) {// && (boolean)ReflectionHacks.getPrivate((Object)__Instance, (Class)CampfireSmithEffect.class, "selectedCard")
				ReplayTheSpireMod.logger.info("[[[[ACTIVE]]]]");
				ReflectionHacks.setPrivate((Object)__Instance, (Class)CampfireSmithEffect.class, "openedScreen", (Object)false);
				ReflectionHacks.setPrivate((Object)__Instance, (Class)CampfireSmithEffect.class, "selectedCard", (Object)false);
				ReflectionHacks.setPrivate((Object)__Instance, (Class)CampfireSmithEffect.class, "screenColor", (Object)(AbstractDungeon.fadeColor.cpy()));
				ReflectionHacks.setPrivate((Object)__Instance, (Class)CampfireSmithEffect.class, "duration", (Object)1.5f);
				//AbstractDungeon.effectList.add(new CampfireSmithEffect());
				ArrowheadPatches.hasSecondUpgrade = false;
				ArrowheadPatches.didSecondUpgrade = true;
			}
		}
		
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect", method = SpirePatch.CONSTRUCTOR)
	public static class VfxConstPatch {
		
		public static void Postfix(CampfireSmithEffect __Instance) {
			ReplayTheSpireMod.logger.info("CAMPFIRE CONST");
			ArrowheadPatches.didSecondUpgrade = false;
			if (AbstractDungeon.player.hasRelic("Arrowhead")) {
				ArrowheadPatches.hasSecondUpgrade = true;
	        }
	        else {
	        	ArrowheadPatches.hasSecondUpgrade = false;
	        }
		}
		
	}
	
	
}
