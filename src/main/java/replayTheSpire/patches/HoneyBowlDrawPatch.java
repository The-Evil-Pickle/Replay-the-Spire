package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.ui.buttons.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.localization.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;
import basemod.*;
import replayTheSpire.ReplayTheSpireMod;

@SpirePatch(cls = "com.megacrit.cardcrawl.ui.buttons.SingingBowlButton", method = "render")
public class HoneyBowlDrawPatch {
	
	public static void Prefix(SingingBowlButton __instance, SpriteBatch sb) {
		if (ReplayTheSpireMod.noSkipRewardsRoom && AbstractDungeon.player.hasRelic("Singing Bowl")) {
			FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, "*and take a card randomly", Settings.WIDTH / 2.0f + 200.0f * Settings.scale, 167.0f * Settings.scale, Color.WHITE.cpy());
		}
	}
	
}