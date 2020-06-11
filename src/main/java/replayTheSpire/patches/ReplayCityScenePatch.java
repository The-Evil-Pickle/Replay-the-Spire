package replayTheSpire.patches;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.rooms.MonsterRoom;

import replayTheSpire.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.scenes.*;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;

public class ReplayCityScenePatch {
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TheCityScene", method = "randomizeScene")
	public static class CitySceneRandomizePatch {
		public static void Postfix(TheCityScene __instance) {
			if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom && AbstractDungeon.getCurrRoom().monsters.getMonster("PondfishBoss") != null) {//AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && 
				ReplayTheSpireMod.renderFishFG = true;
			}
			else {
				ReplayTheSpireMod.renderFishFG = false;
			}
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TheCityScene", method = "renderCombatRoomFg")
	public static class CitySceneFGPatch {
		public static void Prefix(TheCityScene __instance, final SpriteBatch sb) {
			if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom && ReplayTheSpireMod.renderFishFG) {
				sb.setColor(Color.WHITE);
				sb.draw(ReplayTheSpireMod.fishFG.getTexture(), ReplayTheSpireMod.fishFG.offsetX * Settings.scale, ReplayTheSpireMod.fishFG.offsetY * Settings.scale + AbstractDungeon.sceneOffsetY, 0.0f, 0.0f, ReplayTheSpireMod.fishFG.packedWidth, ReplayTheSpireMod.fishFG.packedHeight, Settings.scale, Settings.scale, 0.0f, ReplayTheSpireMod.fishFG.getRegionX(), ReplayTheSpireMod.fishFG.getRegionY(), ReplayTheSpireMod.fishFG.getRegionWidth(), ReplayTheSpireMod.fishFG.getRegionHeight(), false, false);
			}
			//__instance.renderAtlasRegionIf(sb, ReplayCityScenePatch.fishFG, ReplayCityScenePatch.renderFishFG);
		}
	}
	
}