package ReplayTheSpireMod.patches;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.audio.*;
import com.megacrit.cardcrawl.helpers.*;
import ReplayTheSpireMod.*;
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

public class ReplayCityScenePatch {
	
	public static boolean renderFishFG = false;
	public static final TextureAtlas fishAtlas = new TextureAtlas(Gdx.files.internal("images/replayScenes/fishfight.atlas"));
	public static final TextureAtlas.AtlasRegion fishFG = ReplayCityScenePatch.fishAtlas.findRegion("mod/fg");
	
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TheCityScene", method = "randomizeScene")
	public static class CitySceneRandomizePatch {
		public static void Postfix(TheCityScene __instance) {
			if (AbstractDungeon.getCurrRoom().monsters.getMonster("PondfishBoss") != null) {//AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && 
				ReplayCityScenePatch.renderFishFG = true;
			}
			else {
				ReplayCityScenePatch.renderFishFG = false;
			}
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TheCityScene", method = "renderCombatRoomFg")
	public static class CitySceneFGPatch {
		public static void Prefix(TheCityScene __instance, final SpriteBatch sb) {
			if (ReplayCityScenePatch.renderFishFG) {
				sb.draw(ReplayCityScenePatch.fishFG.getTexture(), ReplayCityScenePatch.fishFG.offsetX * Settings.scale, ReplayCityScenePatch.fishFG.offsetY * Settings.scale + AbstractDungeon.sceneOffsetY, 0.0f, 0.0f, ReplayCityScenePatch.fishFG.packedWidth, ReplayCityScenePatch.fishFG.packedHeight, Settings.scale, Settings.scale, 0.0f, ReplayCityScenePatch.fishFG.getRegionX(), ReplayCityScenePatch.fishFG.getRegionY(), ReplayCityScenePatch.fishFG.getRegionWidth(), ReplayCityScenePatch.fishFG.getRegionHeight(), false, false);
			}
			//__instance.renderAtlasRegionIf(sb, ReplayCityScenePatch.fishFG, ReplayCityScenePatch.renderFishFG);
		}
	}
	
}