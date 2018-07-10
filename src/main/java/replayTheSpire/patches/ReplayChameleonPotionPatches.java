package replayTheSpire.patches;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.potions.*;
import replayTheSpire.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

public class ReplayChameleonPotionPatches
{
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.potions.AttackPotion", method = "use")
	public static class ReplayAttackPotionPatch {
		public static void Replace(AttackPotion __instance, final AbstractCreature target) {
			final AbstractCard c = AbstractDungeon.returnTrulyRandomCard(AbstractCard.CardType.ATTACK, AbstractDungeon.cardRandomRng).makeCopy();
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Chameleon Ring")) {
				c.upgrade();
			}
			c.setCostForTurn(-99);
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
		}
		
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.potions.SkillPotion", method = "use")
	public static class ReplaySkillPotionPatch {
		public static void Replace(SkillPotion __instance, final AbstractCreature target) {
			final AbstractCard c = AbstractDungeon.returnTrulyRandomCard(AbstractCard.CardType.SKILL, AbstractDungeon.cardRandomRng).makeCopy();
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Chameleon Ring")) {
				c.upgrade();
			}
			c.setCostForTurn(-99);
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
		}
		
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.potions.PowerPotion", method = "use")
	public static class ReplayPowerPotionPatch {
		public static void Replace(PowerPotion __instance, final AbstractCreature target) {
			final AbstractCard c = AbstractDungeon.returnTrulyRandomCard(AbstractCard.CardType.POWER, AbstractDungeon.cardRandomRng).makeCopy();
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Chameleon Ring")) {
				c.upgrade();
			}
			c.setCostForTurn(-99);
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
		}
		
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.potions.AttackPotion", method = SpirePatch.CONSTRUCTOR)
	public static class AttackPotionConstPatch {
		@SpireInsertPatch(rloc = 4)
		public static void Insert(AttackPotion __instance) {
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Chameleon Ring")) {
				__instance.description = "Add a random #yUpgraded Attack card to your hand, it costs #b0 this turn";
			}
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.potions.SkillPotion", method = SpirePatch.CONSTRUCTOR)
	public static class SkillPotionConstPatch {
		@SpireInsertPatch(rloc = 4)
		public static void Insert(SkillPotion __instance) {
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Chameleon Ring")) {
				__instance.description = "Add a random #yUpgraded Skill card to your hand, it costs #b0 this turn";
			}
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.potions.PowerPotion", method = SpirePatch.CONSTRUCTOR)
	public static class PowerPotionConstPatch {
		@SpireInsertPatch(rloc = 4)
		public static void Insert(PowerPotion __instance) {
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Chameleon Ring")) {
				__instance.description = "Add a random #yUpgraded Power card to your hand, it costs #b0 this turn";
			}
		}
	}
}
