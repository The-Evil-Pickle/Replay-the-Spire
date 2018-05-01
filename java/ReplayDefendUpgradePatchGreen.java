package ReplayTheSpireMod.patches;
import ReplayTheSpireMod.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.ui.buttons.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.localization.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.screens.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import basemod.*;
import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.cards.green.Defend_Green", method = "upgrade")
public class ReplayDefendUpgradePatchGreen {
	
	public static void Replace(Defend_Green __instance) {
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("Simple Rune"))
		{
			__instance.baseBlock += 3 + __instance.timesUpgraded;
			__instance.upgradedBlock = true;
			__instance.timesUpgraded += 1;
			__instance.upgraded = true;
			__instance.name = (__instance.NAME + "+" + __instance.timesUpgraded);
			ReplayTheSpireMod.InitCardTitle(__instance);
		} else {
			if (!__instance.upgraded)
			{
			    __instance.timesUpgraded++;
				__instance.upgraded = true;
				__instance.name += "+";
				ReplayTheSpireMod.InitCardTitle(__instance);
				__instance.baseBlock += 3;
				__instance.upgradedBlock = true;
			}
		}
	}
	
}