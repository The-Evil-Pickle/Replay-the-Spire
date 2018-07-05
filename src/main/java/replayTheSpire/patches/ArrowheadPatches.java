package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.campfire.*;

import basemod.ReflectionHacks;

public class ArrowheadPatches {

	public static boolean hasSecondUpgrade;
	public static boolean didSecondUpgrade;
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect", method = "update")
	public static class VfxUpdatePatch {
		
		@SpireInsertPatch(rloc = 17)
		public static void Insert(CampfireSmithEffect __Instance) {
			if (ArrowheadPatches.hasSecondUpgrade && (boolean)ReflectionHacks.getPrivate((Object)__Instance, (Class)CampfireSmithEffect.class, "selectedCard")) {
				ReflectionHacks.setPrivate((Object)__Instance, (Class)CampfireSmithEffect.class, "openedScreen", (Object)false);
				ReflectionHacks.setPrivate((Object)__Instance, (Class)CampfireSmithEffect.class, "selectedCard", (Object)false);
				ReflectionHacks.setPrivate((Object)__Instance, (Class)CampfireSmithEffect.class, "screenColor", (Object)(AbstractDungeon.fadeColor.cpy()));
				ArrowheadPatches.hasSecondUpgrade = false;
				ArrowheadPatches.didSecondUpgrade = true;
			}
		}
		
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect", method = SpirePatch.CONSTRUCTOR)
	public static class VfxConstPatch {
		
		public static void Postfix(CampfireSmithEffect __Instance) {
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
