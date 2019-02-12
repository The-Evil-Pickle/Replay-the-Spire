package replayTheSpire.replayxover;

import com.evacipated.cardcrawl.mod.hubris.relics.NiceRug;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.actions.unique.SpawnForestMonsterAction;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.eastereggs.GhostMerchant;
import com.megacrit.cardcrawl.monsters.ending.SpireShield;
import com.megacrit.cardcrawl.monsters.ending.SpireSpear;

import replayTheSpire.ReplayTheSpireMod;

public class hubrisbs {
	
	public static void getGhostGuyInHere(float x, float y) {
		AbstractDungeon.actionManager.addToBottom(new SpawnForestMonsterAction(new GhostMerchant(x, y), false));
	}
	public static boolean hasSpawned = false;

	@SpirePatch(cls="com.megacrit.cardcrawl.monsters.ending.SpireSpear", method="die")
    public static class SpearPatch {
    	public static void Postfix(SpireSpear __Instance) {
    		if (ReplayTheSpireMod.foundmod_hubris && ReplayTheSpireMod.foundmod_conspire && AbstractDungeon.player.hasRelic(NiceRug.ID) && !hasSpawned) {
    			hasSpawned = true;
    			hubrisbs.getGhostGuyInHere(70.0f, 10.0f);
    		}
    	}
    }
	@SpirePatch(cls="com.megacrit.cardcrawl.monsters.ending.SpireShield", method="die")
    public static class ShieldPatch {
    	public static void Postfix(SpireShield __Instance) {
    		if (ReplayTheSpireMod.foundmod_hubris && ReplayTheSpireMod.foundmod_conspire && AbstractDungeon.player.hasRelic(NiceRug.ID) && !hasSpawned) {
    			hasSpawned = true;
    			hubrisbs.getGhostGuyInHere(-1000.0f, 15.0f);
    		}
    	}
    }
}
