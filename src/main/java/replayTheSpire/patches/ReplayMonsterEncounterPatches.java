package replayTheSpire.patches;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.map.*;
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
import java.util.*;

public class ReplayMonsterEncounterPatches {
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MonsterHelper", method = "getEncounter")
	public static class ReplayEncounterPatch {
		public static MonsterGroup Postfix(MonsterGroup __result, final String key) {
			switch (key) {
				case "Pondfish": {
					return new MonsterGroup(new AbstractMonster[] {new CaptainAbe(170.0f, -55.0f), new PondfishBoss(0.0f, -650.0f)});
				}
				case "Fading Forest": {
					return new MonsterGroup(new AbstractMonster[] {new FadingForestBoss()});
				}
				default: {
					return __result;
				}
			}
			
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MonsterHelper", method = "getMonster")
	public static class ReplayGetMonsterPatch {
		public static AbstractMonster Postfix(AbstractMonster __result, final String key) {
			if (__result instanceof ApologySlime) {
				switch (key) {
					case "Pondfish": case "PondfishBoss": {
						return new PondfishBoss(0.0f, -650.0f);
					}
					case "CaptainAbe": {
						return new CaptainAbe(170.0f, -55.0f);
					}
					case "Fading Forest": case "FadingForest": {
						return new FadingForestBoss();
					}
					default: {
						return __result;
					}
				}
			}
			return __result;
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "setBoss")
	public static class ReplaySetBossPatch {
		public static void Postfix(AbstractDungeon __Instance, final String key) {
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Painkiller Herb")) {
				DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/OldBossIcon.png");
				DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/OldBossIcon.png");
			} else {
				if (key.equals("Pondfish")) {
					DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/pondfish.png");
					DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/pondfish.png");
				}
				if (key.equals("Fading Forest")) {
					DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/OldBossIcon.png");
					DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/OldBossIcon.png");
				}
			}
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.Exordium", method = "initializeBoss")
	public static class ReplayExordiumBossListPatch {
		public static void Prefix(Exordium __Instance) {
			//Exordium.bossList.add("Fading Forest");
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.TheCity", method = "initializeBoss")
	public static class ReplayCityBossListPatch {
		public static void Prefix(TheCity __Instance) {
			TheCity.bossList.add("Pondfish");
		}
	}
	
	
}