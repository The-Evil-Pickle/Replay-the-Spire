package ReplayTheSpireMod.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
import ReplayTheSpireMod.patches.ReplayRewardSkipPositionPatch;

@SpirePatch(cls = "com.megacrit.cardcrawl.ui.buttons.SingingBowlButton", method = "onClick")
public class HoneyBowlPostPatch {
	
	public static void Postfix(SingingBowlButton __instance) {
		if (AbstractDungeon.player.hasRelic("Honey Jar")) {
			ReplayRewardSkipPositionPatch.Postfix(AbstractDungeon.combatRewardScreen);
			AbstractDungeon.player.getRelic("Honey Jar").flash();
		}
	}
	
}