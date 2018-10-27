package replayTheSpire.patches;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.MTSClassLoader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.evacipated.cardcrawl.modthespire.patcher.PrefixPatchInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.cards.green.Defend_Green;
import com.megacrit.cardcrawl.cards.green.Strike_Green;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.relics.SimpleRune;

import basemod.BaseMod;
import basemod.helpers.*;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import replayTheSpire.ReplayTheSpireMod;

public class SimplicityRunePatches {
	@SpirePatch(
	        cls="com.megacrit.cardcrawl.cards.AbstractCard",
	        method="canUpgrade"
	)
	public static class CanUpgradePatch {
		public static boolean Postfix(boolean __Result, AbstractCard card) {
			if (!__Result && ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(SimpleRune.ID)) {
				if (card instanceof Strike_Red || card instanceof Strike_Green || card instanceof Strike_Blue || card instanceof Defend_Red || card instanceof Defend_Green || card instanceof Defend_Blue) {
					return true;
				}
				if (card.hasTag(BaseModCardTags.BASIC_STRIKE) || card.hasTag(BaseModCardTags.BASIC_DEFEND)) {
					card.upgraded = false;
					return true;
				}
			}
			return __Result;
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "upgradeName")
	public static class ReplayPostNameUpgradePatch {
		
		@SpireInsertPatch(rloc=4)
		public static void Insert(AbstractCard c) {
			if (AbstractDungeon.player != null && c.rarity == AbstractCard.CardRarity.BASIC && AbstractDungeon.player.hasRelic("Simple Rune")) {
				if(c.hasTag(BaseModCardTags.BASIC_STRIKE)) {//if (c.type == AbstractCard.CardType.ATTACK && c.cardID.toLowerCase().contains("strike")) {
					c.name = c.makeCopy().name + "+" + c.timesUpgraded;
					if (c.baseDamage > 0) {
						c.baseDamage += (c.timesUpgraded - 1);
					}
				} else {
					if(c.hasTag(BaseModCardTags.BASIC_DEFEND)) {//if (c.type == AbstractCard.CardType.SKILL && c.cardID.toLowerCase().contains("defend")) {
						c.name = c.makeCopy().name + "+" + c.timesUpgraded;
						if (c.baseBlock > 0) {
							c.baseBlock += (c.timesUpgraded - 1);
						}
					}
				}
			}
		}
		
		public static void PostFix(AbstractCard c) {
			if (AbstractDungeon.player != null && c.rarity == AbstractCard.CardRarity.BASIC && AbstractDungeon.player.hasRelic("Simple Rune")) {
				if (c.hasTag(BaseModCardTags.BASIC_STRIKE) || c.hasTag(BaseModCardTags.BASIC_DEFEND)) {
					c.upgraded = false;
				}
			}
		}
		
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.red.Strike_Red", method = "upgrade")
	public static class St_R {
		public static SpireReturn Prefix(AbstractCard card) {
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(SimpleRune.ID)) {
				card.baseDamage += 3 + card.timesUpgraded;
		        card.upgradedDamage = true;
				card.upgraded = true;
		        card.timesUpgraded++;
		        card.name = Strike_Red.NAME + "+" + card.timesUpgraded;
				return SpireReturn.Return(null);
			}
			return SpireReturn.Continue();
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.green.Strike_Green", method = "upgrade")
	public static class St_G {
		public static SpireReturn Prefix(AbstractCard card) {
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(SimpleRune.ID)) {
				card.baseDamage += 3 + card.timesUpgraded;
		        card.upgradedDamage = true;
				card.upgraded = true;
		        card.timesUpgraded++;
		        card.name = Strike_Green.NAME + "+" + card.timesUpgraded;
				return SpireReturn.Return(null);
			}
			return SpireReturn.Continue();
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.blue.Strike_Blue", method = "upgrade")
	public static class St_B {
		public static SpireReturn Prefix(AbstractCard card) {
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(SimpleRune.ID)) {
				card.baseDamage += 3 + card.timesUpgraded;
		        card.upgradedDamage = true;
				card.upgraded = true;
		        card.timesUpgraded++;
		        card.name = Strike_Blue.NAME + "+" + card.timesUpgraded;
				return SpireReturn.Return(null);
			}
			return SpireReturn.Continue();
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.red.Defend_Red", method = "upgrade")
	public static class Df_R {
		public static SpireReturn Prefix(AbstractCard card) {
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(SimpleRune.ID)) {
				card.baseBlock += 3 + card.timesUpgraded;
		        card.upgradedBlock = true;
				card.upgraded = true;
		        card.timesUpgraded++;
		        card.name = Defend_Red.NAME + "+" + card.timesUpgraded;
				return SpireReturn.Return(null);
			}
			
			return SpireReturn.Continue();
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.green.Defend_Green", method = "upgrade")
	public static class Df_G {
		public static SpireReturn Prefix(AbstractCard card) {
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(SimpleRune.ID)) {
				card.baseBlock += 3 + card.timesUpgraded;
		        card.upgradedBlock = true;
				card.upgraded = true;
		        card.timesUpgraded++;
		        card.name = Defend_Green.NAME + "+" + card.timesUpgraded;
				return SpireReturn.Return(null);
			}
			
			return SpireReturn.Continue();
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.blue.Defend_Blue", method = "upgrade")
	public static class Df_B {
		public static SpireReturn Prefix(AbstractCard card) {
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(SimpleRune.ID)) {
				card.baseBlock += 3 + card.timesUpgraded;
		        card.upgradedBlock = true;
				card.upgraded = true;
		        card.timesUpgraded++;
		        card.name = Defend_Blue.NAME + "+" + card.timesUpgraded;
				return SpireReturn.Return(null);
			}
			
			return SpireReturn.Continue();
		}
	}
}
