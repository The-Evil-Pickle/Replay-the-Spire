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
import com.megacrit.cardcrawl.relics.SimpleRune;

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
				if ((CardTags.hasTag(card, BaseModTags.BASIC_STRIKE) || CardTags.hasTag(card, BaseModTags.BASIC_DEFEND))) {
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
				if(CardTags.hasTag(c, BaseModTags.BASIC_STRIKE)) {//if (c.type == AbstractCard.CardType.ATTACK && c.cardID.toLowerCase().contains("strike")) {
					c.name = c.makeCopy().name + "+" + c.timesUpgraded;
					if (c.baseDamage > 0) {
						c.baseDamage += (c.timesUpgraded - 1);
					}
				} else {
					if(CardTags.hasTag(c, BaseModTags.BASIC_DEFEND)) {//if (c.type == AbstractCard.CardType.SKILL && c.cardID.toLowerCase().contains("defend")) {
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
				if (CardTags.hasTag(c, BaseModTags.BASIC_STRIKE) || CardTags.hasTag(c, BaseModTags.BASIC_DEFEND)) {
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
	/*
	public static void TryPatchingShit() {
		try {
			TryPatchingCard(new Strike_Red());
			TryPatchingCard(new Strike_Green());
			TryPatchingCard(new Strike_Blue());
			TryPatchingCard(new Defend_Red());
			TryPatchingCard(new Defend_Green());
			TryPatchingCard(new Defend_Blue());
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SpirePatch(
	        cls="basemod.BaseMod",
	        method="addCard"
	)
	public static class PatchBasemodAddCard {
		public static void Postfix(AbstractCard c) {
			try {
				if(CardTags.hasTag(c, BaseModTags.BASIC_STRIKE) || CardTags.hasTag(c, BaseModTags.BASIC_DEFEND)) {
					TryPatchingCard(c);
				}
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static CtMethod ctMethodPrefix;
	private static URL[] buildUrlArray(final ModInfo[] modInfos) throws MalformedURLException {
        final URL[] urls = new URL[modInfos.length + 1];
        for (int i = 0; i < modInfos.length; ++i) {
            urls[i] = modInfos[i].jarURL;
        }
        urls[modInfos.length] = new File(Loader.STS_JAR).toURI().toURL();
        return urls;
    }
	@SuppressWarnings("deprecation")
	public static void TryPatchingCard(AbstractCard c) throws NotFoundException {
		ReplayTheSpireMod.logger.debug(c.getClass().getName());
		//ClassPool pool = new ClassPool(ClassPool.getDefault());
		CtClass ctClsToPatch = Loader.getClassPool().get(c.getClass().getName());
		CtMethod ctMethodToPatch = ctClsToPatch.getDeclaredMethod("upgrade");
        PrefixPatchInfo patch = new PrefixPatchInfo(ctMethodToPatch, ctMethodPrefix);
        if (Loader.DEBUG) {
        	patch.debugPrint();
        }
		try {
			patch.doPatch();
			ctClsToPatch.toClass(new MTSClassLoader(Loader.class.getResourceAsStream(Loader.COREPATCHES_JAR), buildUrlArray(Loader.MODINFOS), Loader.class.getClassLoader()));
		} catch (PatchingException | CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static SpireReturn Prefix(AbstractCard card) {
		if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(SimpleRune.ID)) {
			ReplayTheSpireMod.logger.debug("HAVE_RUNE");
			if (CardTags.hasTag(card, BaseModTags.BASIC_STRIKE) || card instanceof Strike_Red) {
				ReplayTheSpireMod.logger.debug("STRIKE");
				card.baseDamage += 3 + card.timesUpgraded;
		        card.upgradedDamage = true;
			} else if (CardTags.hasTag(card, BaseModTags.BASIC_DEFEND)) {
				ReplayTheSpireMod.logger.debug("DEFEND");
				card.baseBlock += 3 + card.timesUpgraded;
		        card.upgradedBlock = true;
			}
			card.upgraded = true;
	        card.timesUpgraded++;
	        card.name = Strike_Red.NAME + "+" + card.timesUpgraded;
			return SpireReturn.Return(null);
		}
		
		return SpireReturn.Continue();
	}
	static {
		
		//CtClass[] cArg = new CtClass[1];
        //cArg[0] = ClassPool.getDefault().makeClass(AbstractCard.class.getName());
        try {
			ctMethodPrefix = Loader.getClassPool().get(SimplicityRunePatches.class.getName()).getDeclaredMethod("Prefix");
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
}
