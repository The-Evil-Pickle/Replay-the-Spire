package ReplayTheSpireMod.patches;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.potions.*;
import ReplayTheSpireMod.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.potions.AbstractPotion", method = "getPotency", paramtypes={})
public class ReplayPotencyPatch {
	
	public static int Postfix(int __result, AbstractPotion __instance) {
		int r = __result;
		if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Chameleon Ring")) {
			switch(__instance.ID) {
				case "AttackPotion":
					__instance.description = "Add a random Upgraded Attack card to your hand, it costs #b0 this turn.";
					return r;
				case "SkillPotion":
					__instance.description = "Add a random Upgraded Skill card to your hand, it costs #b0 this turn.";
					return r;
				case "PowerPotion":
					__instance.description = "Add a random Upgraded Power card to your hand, it costs #b0 this turn.";
					return r;
				case "Doom Potion":
				case "Venom Potion":
					//negative potency effect
					r = (__result * 3) / 4;
					if (r == __result) {
						r--;
					}
					if (r < 0) {
						r = 0;
					}
					return r;
				case "Death Potion":
					((DeathPotion)__instance).secondPotency -= 2;
					return __result + 10;
				case "Adrenaline Potion":
					((AdrenalinePotion)__instance).secondPotency += 1;
				default: 
					r = (__result * 3) / 2;
					if (r == __result) {
						r++;
					}
					return r;
			}
		}
		return __result;
	}
	
}