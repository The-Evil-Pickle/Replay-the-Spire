package replayTheSpire.patches;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.audio.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.helpers.*;
import replayTheSpire.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.monsters.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.monsters.thetop.*;
import com.megacrit.cardcrawl.monsters.exordium.*;
import com.megacrit.cardcrawl.monsters.city.*;
import com.megacrit.cardcrawl.monsters.replay.*;
import com.megacrit.cardcrawl.monsters.beyond.*;
import com.megacrit.cardcrawl.metrics.*;
import com.megacrit.cardcrawl.scenes.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.*;
import java.util.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.vfx.scene.*;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.rooms.*;

public class ReplayBottomScenePatch {
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TheBottomScene", method = "randomizeScene")
	public static class BottomSceneRandomizePatch {
		public static void Postfix(TheBottomScene __instance) {
			if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom() instanceof MonsterRoom && AbstractDungeon.getCurrRoom().monsters.getMonster("FadingForestBoss") != null) {//AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && 
				ReplayTheSpireMod.renderForestBG = true;
			}
			else {
				ReplayTheSpireMod.renderForestBG = false;
			}
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TheBottomScene", method = "renderCombatRoomBg")
	public static class BottomSceneBGPatch {
		public static void Postfix(TheBottomScene __instance, final SpriteBatch sb) {
			if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom() instanceof MonsterRoom && ReplayTheSpireMod.renderForestBG && ReplayTheSpireMod.forestBG != null) {
				AbstractMonster fb = ForestEventAction.forest;
				if (fb != null && !fb.isDead && !fb.escaped) {
					sb.draw(ReplayTheSpireMod.forestBG, fb.drawX - ReplayTheSpireMod.forestBG.getWidth() * Settings.scale / 2.0f + fb.animX, fb.drawY + fb.animY + AbstractDungeon.sceneOffsetY, ReplayTheSpireMod.forestBG.getWidth() * Settings.scale, ReplayTheSpireMod.forestBG.getHeight() * Settings.scale, 0, 0, ReplayTheSpireMod.forestBG.getWidth(), ReplayTheSpireMod.forestBG.getHeight(), false, false);
				}
			}
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TheBottomScene", method = "renderCombatRoomFg")
	public static class BottomSceneFGPatch {
		public static void Postfix(TheBottomScene __instance, final SpriteBatch sb) {
			if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom() instanceof MonsterRoom && ForestEventAction.forest != null) {
				ForestEventAction.forest.imageEventText.render(sb);
				//sb.draw(ReplayTheSpireMod.fishFG.getTexture(), ReplayTheSpireMod.fishFG.offsetX * Settings.scale, ReplayTheSpireMod.fishFG.offsetY * Settings.scale + AbstractDungeon.sceneOffsetY, 0.0f, 0.0f, ReplayTheSpireMod.fishFG.packedWidth, ReplayTheSpireMod.fishFG.packedHeight, Settings.scale, Settings.scale, 0.0f, ReplayTheSpireMod.fishFG.getRegionX(), ReplayTheSpireMod.fishFG.getRegionY(), ReplayTheSpireMod.fishFG.getRegionWidth(), ReplayTheSpireMod.fishFG.getRegionHeight(), false, false);
			}
		}
	}
	
}