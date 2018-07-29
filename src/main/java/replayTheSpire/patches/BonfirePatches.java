package replayTheSpire.patches;

import java.util.*;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.random.Random;

import replayTheSpire.ReplayTheSpireMod;

import com.megacrit.cardcrawl.map.*;

public class BonfirePatches {
	
	public static MapRoomNode bonfireNode;
	
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "generateMap")
	public static class PickBonfirePatch {
		
		public static void Postfix() {
			
			ArrayList<MapRoomNode> finalFires = new ArrayList<MapRoomNode>();
			for (MapRoomNode node : AbstractDungeon.map.get(AbstractDungeon.map.size() - 1) ) {
				
				if (node != null && node.room != null && node.hasEdges()) {
					finalFires.add(node);
				}
			}
			bonfireNode = finalFires.get(AbstractDungeon.mapRng.random(0, finalFires.size() - 1));
			if (!AbstractDungeon.player.hasRelic("Painkiller Herb")) {
				bonfireNode.room.setMapImg(ReplayTheSpireMod.bonfireIcon, ReplayTheSpireMod.bonfireBG);
				if (ReplayTheSpireMod.foundmod_colormap) {
					bonfireNode.room.setMapSymbol("R_BF");
				}
			}
		}
	}
	
}
