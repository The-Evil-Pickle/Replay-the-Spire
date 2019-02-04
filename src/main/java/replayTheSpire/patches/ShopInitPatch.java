package replayTheSpire.patches;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.relics.RingOfCollecting;
import com.megacrit.cardcrawl.mod.replay.relics.WaxSeal;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StoreRelic;

import basemod.ReflectionHacks;

@SpirePatch(cls = "com.megacrit.cardcrawl.shop.ShopScreen", method = "init")
public class ShopInitPatch {
	public static void PostFix(ShopScreen __instance, final ArrayList<AbstractCard> coloredCards, final ArrayList<AbstractCard> colorlessCards) {
		if (AbstractDungeon.player != null) {
			if (AbstractDungeon.player.hasRelic(WaxSeal.ID)) {
				for (final StoreRelic r : (ArrayList<StoreRelic>)ReflectionHacks.getPrivate(__instance, ShopScreen.class, "relics")) {
		            r.price = MathUtils.round(r.price * (1F - WaxSeal.DISCOUNT));
		        }
			}
		}
	}
}
