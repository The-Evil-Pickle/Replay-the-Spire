package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.rooms.MonsterRoom;

import replayTheSpire.ReplayTheSpireMod;
import replayTheSpire.replayxover.infinitebs;

public class PotionGetPatch {
	@SpirePatch(cls = "com.megacrit.cardcrawl.characters.AbstractPlayer", method = "obtainPotion", paramtypez = {AbstractPotion.class})
	public static class PotionGetPatchA {
		public static boolean Postfix (boolean result, AbstractPlayer __Instance, AbstractPotion p) {
			if (result && !(p instanceof PotionSlot) && ReplayTheSpireMod.foundmod_infinite && AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null && !(AbstractDungeon.getCurrRoom() instanceof MonsterRoom)) {
				infinitebs.TriggerPotionQuest();
			}
			return result;
		}
	}
	/*@SpirePatch(cls = "com.megacrit.cardcrawl.characters.AbstractPlayer", method = "obtainPotion", paramtypez = {AbstractPotion.class, int.class})
	public static class PotionGetPatchB {
		public static void Prefix (AbstractPlayer __Instance, AbstractPotion p, int slot) {
			if (slot <= __Instance.potionSlots && ReplayTheSpireMod.foundmod_infinite && AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null && !(AbstractDungeon.getCurrRoom() instanceof MonsterRoom)) {
				infinitebs.TriggerPotionQuest();
			}
		}
	}*/
}
