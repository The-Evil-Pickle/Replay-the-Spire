package ReplayTheSpireMod.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.ui.*;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.screens.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.core.*;
import ReplayTheSpireMod.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.screens.CombatRewardScreen", method = "rewardViewUpdate")
public class ReplayRewardSkipPositionPatch {
	
	public static float HIDE_X = -1.0f;
	public static float SHOW_X = -1.0f;
	
	public static void Postfix(CombatRewardScreen __Instance) {
		if (AbstractDungeon.player.hasRelic("Honey Jar")) {
			if (ReplayRewardSkipPositionPatch.HIDE_X == -1.0f) {
				ReplayRewardSkipPositionPatch.HIDE_X = AbstractDungeon.topPanel.mapHb.cX - 400.0f * Settings.scale;
				ReplayRewardSkipPositionPatch.SHOW_X = AbstractDungeon.topPanel.mapHb.cX;
			}
			if (__Instance.rewards.isEmpty()) {
				AbstractDungeon.overlayMenu.proceedButton.show();
				AbstractDungeon.topPanel.mapHb.move(ReplayRewardSkipPositionPatch.SHOW_X, AbstractDungeon.topPanel.mapHb.cY);
			} else {
				AbstractDungeon.overlayMenu.proceedButton.hide();
				AbstractDungeon.topPanel.mapHb.move(ReplayRewardSkipPositionPatch.HIDE_X, AbstractDungeon.topPanel.mapHb.cY);
			}
		}
	}
	
}