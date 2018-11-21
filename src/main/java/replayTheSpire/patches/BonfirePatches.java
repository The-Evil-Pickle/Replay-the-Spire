package replayTheSpire.patches;

import java.util.*;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.CertainFuture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.random.Random;

import replayTheSpire.ReplayTheSpireMod;

import com.megacrit.cardcrawl.map.*;

public class BonfirePatches {
	
	public static MapRoomNode bonfireNode;
	
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "generateMap")
	public static class PickBonfirePatch {
		private static final float mapnodespacing = (Settings.scale * 64.0f) * 2.0f;
		private static float getMapNodeX(MapRoomNode node) {
			return node.x * mapnodespacing + node.offsetX;
		}
		public static void Postfix() {
			if (ReplayTheSpireMod.SETTING_ROOMS_PORTAL.testChance(AbstractDungeon.mapRng) && !ModHelper.isModEnabled(CertainFuture.ID)) {
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
				
				
				//this whole giant chunk of code is supposed to make it so you usually have a choice between the bonfire and a normal space, rather than being forced to go the the bonfire if you don't want to
				float bonfireX = getMapNodeX(bonfireNode);
				float leftScore = -1000000000.0f;
				float rightScore = 1000000000.0f;
				MapRoomNode closestLeftNode = null;
				MapRoomNode closestRightNode = null;
				for (MapRoomNode node : AbstractDungeon.map.get(AbstractDungeon.map.size() - 2) ) {
					if (node != null && node.room != null && node.hasEdges()) {
						float nodex = getMapNodeX(node);
						if (nodex < bonfireX && nodex > leftScore) {
							closestLeftNode = node;
							leftScore = nodex;
						} else if (nodex > bonfireX && nodex < rightScore) {
							closestRightNode = node;
							rightScore = nodex;
						}
					}
				}
				leftScore = -1000000000.0f;
				rightScore = 1000000000.0f;
				MapRoomNode adjLeftFire = null;
				MapRoomNode adjRightFire = null;
				for (MapRoomNode node : AbstractDungeon.map.get(AbstractDungeon.map.size() - 1) ) {
					if (node != null && node.room != null && node.hasEdges() && node != bonfireNode) {
						float nodex = getMapNodeX(node);
						if (nodex < bonfireX && nodex > leftScore) {
							adjLeftFire = node;
							leftScore = nodex;
						} else if (nodex > bonfireX && nodex < rightScore) {
							adjRightFire = node;
							rightScore = nodex;
						}
					}
				}
				if (closestLeftNode != null && adjLeftFire != null) {
					if (closestLeftNode.isConnectedTo(bonfireNode) && !closestLeftNode.isConnectedTo(adjLeftFire)) {
						closestLeftNode.addEdge(new MapEdge(closestLeftNode.x, closestLeftNode.y, closestLeftNode.offsetX, closestLeftNode.offsetY, adjLeftFire.x, adjLeftFire.y, adjLeftFire.offsetX, adjLeftFire.offsetY, false));
					}
					if (AbstractDungeon.mapRng.randomBoolean() && closestLeftNode.isConnectedTo(adjLeftFire) && !closestLeftNode.isConnectedTo(bonfireNode)) {
						closestLeftNode.addEdge(new MapEdge(closestLeftNode.x, closestLeftNode.y, closestLeftNode.offsetX, closestLeftNode.offsetY, bonfireNode.x, bonfireNode.y, bonfireNode.offsetX, bonfireNode.offsetY, false));
					}
				}
				if (closestRightNode != null && adjRightFire != null) {
					if (closestRightNode.isConnectedTo(bonfireNode) && !closestRightNode.isConnectedTo(adjRightFire)) {
						closestRightNode.addEdge(new MapEdge(closestRightNode.x, closestRightNode.y, closestRightNode.offsetX, closestRightNode.offsetY, adjRightFire.x, adjRightFire.y, adjRightFire.offsetX, adjRightFire.offsetY, false));
					}
					if (AbstractDungeon.mapRng.randomBoolean() && closestRightNode.isConnectedTo(adjRightFire) && !closestRightNode.isConnectedTo(bonfireNode)) {
						closestRightNode.addEdge(new MapEdge(closestRightNode.x, closestRightNode.y, closestRightNode.offsetX, closestRightNode.offsetY, bonfireNode.x, bonfireNode.y, bonfireNode.offsetX, bonfireNode.offsetY, false));
					}
				}
			} else {
				bonfireNode = null;
			}
		}
	}
	
}
