package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.ChameleonRing;
import com.megacrit.cardcrawl.relics.PotionBelt;
import com.megacrit.cardcrawl.relics.Sozu;
import com.megacrit.cardcrawl.relics.ToyOrnithopter;
import com.megacrit.cardcrawl.rooms.RestRoom;

public class SozuPatches {
	public static boolean lookingForCheck = false;
	public static boolean ShouldSozuActivate() {
		if (!lookingForCheck) {
			return true;
		}
		lookingForCheck = false;
		if (AbstractDungeon.player == null) {
			return true;
		}
		if (AbstractDungeon.player.hasRelic(ChameleonRing.ID) && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom() instanceof RestRoom) {
			AbstractDungeon.player.getRelic(ChameleonRing.ID).flash();
			return false;
		}
		if (AbstractDungeon.player.hasRelic(PotionBelt.ID)) {
			int emptySlots = 0;
			for (AbstractPotion p : AbstractDungeon.player.potions) {
				if (p instanceof PotionSlot) {
					emptySlots++;
				}
			}
			if (emptySlots >= 3) {
				return false;
			}
		}
		if (AbstractDungeon.player.hasRelic(ToyOrnithopter.ID)) {
			AbstractDungeon.player.heal(ToyOrnithopter.HEAL_AMT);
		}
		return true;
	}
	//@SpirePatch(cls = "com.megacrit.cardcrawl.characters.AbstractPlayer", method = "hasRelic", paramtypez = {String.class})
	public static class HasSozu {
		public static Boolean Postfix(Boolean __result, AbstractPlayer __instance, String relic) {
			if (__result && relic.equals(Sozu.ID)) {
				return ShouldSozuActivate();
			}
			return __result;
		}
	}
	
	
}
