package replayTheSpire.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.mod.replay.cards.AbstractTripotentialCard;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

import basemod.ReflectionHacks;

public class TripotentialPatches {
	public static AbstractTripotentialCard upgradePreviewCardZero;
	public static AbstractTripotentialCard upgradePreviewCardTwo;

	//@SpirePatch(cls = "com.megacrit.cardcrawl.screens.select.GridCardSelectScreen", method = "render")
	public static class GridScreenRenderPatch {
		
		public static void Postfix(GridCardSelectScreen _Instance, SpriteBatch sb) {
			if (_Instance.confirmScreenUp && _Instance.forUpgrade && upgradePreviewCardZero != null && upgradePreviewCardTwo != null) {
				upgradePreviewCardZero.current_x = Settings.WIDTH * 0.63f;
				upgradePreviewCardZero.current_y = Settings.HEIGHT / 1.5f;
				upgradePreviewCardZero.target_x = Settings.WIDTH * 0.63f;
				upgradePreviewCardZero.target_y = Settings.HEIGHT / 1.5f;
				upgradePreviewCardZero.render(sb);
				upgradePreviewCardZero.updateHoverLogic();
				upgradePreviewCardZero.renderCardTip(sb);
				upgradePreviewCardTwo.current_x = Settings.WIDTH * 0.63f;
				upgradePreviewCardTwo.current_y = Settings.HEIGHT / 3.0f;
				upgradePreviewCardTwo.target_x = Settings.WIDTH * 0.63f;
				upgradePreviewCardTwo.target_y = Settings.HEIGHT / 3.0f;
				upgradePreviewCardTwo.render(sb);
				upgradePreviewCardTwo.updateHoverLogic();
				upgradePreviewCardTwo.renderCardTip(sb);
			}
		}
	}
	//@SpirePatch(cls = "com.megacrit.cardcrawl.screens.select.GridCardSelectScreen", method = "update")
	public static class GridScreenUpdatePatch {
		
		//@SpireInsertPatch(rloc = 56)
		public static void PreviewCreation(GridCardSelectScreen _Instance) {
			AbstractCard hoveredCard = (AbstractCard)ReflectionHacks.getPrivate((Object)_Instance, (Class)GridCardSelectScreen.class, "hoveredCard");
			if (hoveredCard != null && hoveredCard instanceof AbstractTripotentialCard) {
				(upgradePreviewCardZero = (AbstractTripotentialCard)(hoveredCard.makeStatEquivalentCopy())).upgrade(0);
				(upgradePreviewCardTwo = (AbstractTripotentialCard)(hoveredCard.makeStatEquivalentCopy())).upgrade(2);
			} else {
				upgradePreviewCardZero = null;
				upgradePreviewCardTwo = null;
			}
		}
		
		public static void Postfix(GridCardSelectScreen _Instance) {
			if (!_Instance.isJustForConfirming && !(_Instance.anyNumber && _Instance.confirmButton.hb.clicked) && _Instance.confirmScreenUp && _Instance.forUpgrade && upgradePreviewCardZero != null && upgradePreviewCardTwo != null) {
				upgradePreviewCardZero.update();
				upgradePreviewCardTwo.update();
			}
		}
	}
	//@SpirePatch(cls = "com.megacrit.cardcrawl.screens.select.GridCardSelectScreen", method = "cancelUpgrade")
	public static class CancelUpgradePatch {
		public static void Postfix(GridCardSelectScreen _Instance) {
			upgradePreviewCardZero = null;
			upgradePreviewCardTwo = null;
		}
	}
	
}
