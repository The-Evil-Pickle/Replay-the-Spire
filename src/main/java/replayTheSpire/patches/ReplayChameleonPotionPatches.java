package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;

import replayTheSpire.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.DiscoveryUpgradedAction;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.potions.*;
import com.megacrit.cardcrawl.potions.AttackPotion;
import com.megacrit.cardcrawl.potions.PowerPotion;
import com.megacrit.cardcrawl.potions.SkillPotion;
import com.megacrit.cardcrawl.core.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

public class ReplayChameleonPotionPatches
{
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.potions.AttackPotion", method = "use")
	public static class ReplayAttackPotionPatch {
		public static SpireReturn Prefix(AttackPotion __instance, final AbstractCreature target)
		{
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Chameleon Ring")) {
				AbstractDungeon.actionManager.addToBottom(new DiscoveryUpgradedAction(AbstractCard.CardType.ATTACK));
				return SpireReturn.Return(null);
			}
		    return SpireReturn.Continue();
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.potions.SkillPotion", method = "use")
	public static class ReplaySkillPotionPatch {
		public static SpireReturn Prefix(SkillPotion __instance, final AbstractCreature target)
		{
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Chameleon Ring")) {
				AbstractDungeon.actionManager.addToBottom(new DiscoveryUpgradedAction(AbstractCard.CardType.SKILL));
				return SpireReturn.Return(null);
			}
		    return SpireReturn.Continue();
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.potions.PowerPotion", method = "use")
	public static class ReplayPowerPotionPatch {
		public static SpireReturn Prefix(PowerPotion __instance, final AbstractCreature target)
		{
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Chameleon Ring")) {
				AbstractDungeon.actionManager.addToBottom(new DiscoveryUpgradedAction(AbstractCard.CardType.POWER));
				return SpireReturn.Return(null);
			}
		    return SpireReturn.Continue();
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.potions.AttackPotion", method = SpirePatch.CONSTRUCTOR)
	public static class AttackPotionConstPatch {
		@SpireInsertPatch(rloc = 4)
		public static void Insert(AttackPotion __instance) {
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Chameleon Ring")) {
				__instance.description = "Choose #b1 of #b3 random #yAttack cards to #yUpgrade and add to your hand, it costs #b0 this turn.";
			}
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.potions.SkillPotion", method = SpirePatch.CONSTRUCTOR)
	public static class SkillPotionConstPatch {
		@SpireInsertPatch(rloc = 4)
		public static void Insert(SkillPotion __instance) {
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Chameleon Ring")) {
				__instance.description = "Choose #b1 of #b3 random #ySkill cards to #yUpgrade and add to your hand, it costs #b0 this turn.";
			}
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.potions.PowerPotion", method = SpirePatch.CONSTRUCTOR)
	public static class PowerPotionConstPatch {
		@SpireInsertPatch(rloc = 4)
		public static void Insert(PowerPotion __instance) {
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Chameleon Ring")) {
				__instance.description = "Choose #b1 of #b3 random #yPower cards to #yUpgrade and add to your hand, it costs #b0 this turn.";
			}
		}
	}
}
