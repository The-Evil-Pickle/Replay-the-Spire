package replayTheSpire.patches;

import java.util.*;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.CertainFuture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;

import basemod.ReflectionHacks;
import replayTheSpire.ReplayTheSpireMod;

import com.megacrit.cardcrawl.map.*;
import com.megacrit.cardcrawl.mod.replay.rooms.TeleportRoom;

public class TeleporterPatches {
	
	public static MapRoomNode teleporterLeft;
	public static MapRoomNode teleporterRight;
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "generateMap")
	public static class PickTeleportersPatch {
		
		public static void Postfix() {
			
			if (ReplayTheSpireMod.SETTING_ROOMS_PORTAL.testChance(AbstractDungeon.mapRng) && !ModHelper.isModEnabled(CertainFuture.ID)) {
				
				int overRow = AbstractDungeon.mapRng.random(2, AbstractDungeon.map.size() - 1);
				ArrayList<MapRoomNode> teleporterRow = new ArrayList<MapRoomNode>();
				int lowestnull = AbstractDungeon.map.size() + 1;
				for (int i=0; i < AbstractDungeon.map.get(overRow).size(); i++) {
					MapRoomNode node = AbstractDungeon.map.get(overRow).get(i);
					if (node != null && node.room != null && node.hasEdges()) {
						teleporterRow.add(node);
					} else if (i < lowestnull) {
						lowestnull = i;
					}
					
				}
				MapRoomNode roomLeft = teleporterRow.get(0);
				MapRoomNode roomRight = teleporterRow.get(teleporterRow.size() - 1);
	
				ArrayList<MapRoomNode> underRow = new ArrayList<MapRoomNode>();
				for (MapRoomNode node : AbstractDungeon.map.get(overRow - 1)) {
					
					if (node != null && node.room != null && node.hasEdges()) {
						underRow.add(node);
					}
					
				}
				MapRoomNode underRoomLeft = underRow.get(0);
				MapRoomNode underRoomRight = underRow.get(underRow.size() - 1);
				
	            teleporterLeft = new MapRoomNode(lowestnull, roomLeft.y);
	            teleporterLeft.offsetX -= (lowestnull - (roomLeft.x - 1)) * Settings.scale * 128.0f;
	            teleporterLeft.offsetY = roomLeft.offsetY;
	            teleporterRight = new MapRoomNode(roomRight.x + 1, roomRight.y);
	            teleporterRight.offsetY = roomRight.offsetY;
	            teleporterLeft.room = new TeleportRoom(teleporterRight);
	            teleporterRight.room = new TeleportRoom(teleporterLeft);
				
	            
	            
	            teleporterLeft.addEdge(new MapEdge(teleporterLeft.x, teleporterLeft.y, teleporterLeft.offsetX, teleporterLeft.offsetY, roomLeft.x, roomLeft.y, roomLeft.offsetX, roomLeft.offsetY, false));
	            underRoomLeft.addEdge(new MapEdge(underRoomLeft.x, underRoomLeft.y, underRoomLeft.offsetX, underRoomLeft.offsetY, teleporterLeft.x, teleporterLeft.y, teleporterLeft.offsetX, teleporterLeft.offsetY, false));
	            teleporterRight.addEdge(new MapEdge(teleporterRight.x, teleporterRight.y, teleporterRight.offsetX, teleporterRight.offsetY, roomRight.x, roomRight.y, roomRight.offsetX, roomRight.offsetY, false));
	            underRoomRight.addEdge(new MapEdge(underRoomRight.x, underRoomRight.y, underRoomRight.offsetX, underRoomRight.offsetY, teleporterRight.x, teleporterRight.y, teleporterRight.offsetX, teleporterRight.offsetY, false));
	            
	            
	            
	            //final ArrayList<MapRoomNode> visibleMapNodes = (ArrayList<MapRoomNode>)ReflectionHacks.getPrivate((Object)AbstractDungeon.dungeonMapScreen, (Class)DungeonMapScreen.class, "visibleMapNodes");
	            if (teleporterRight.x >= AbstractDungeon.map.get(overRow).size() || AbstractDungeon.map.get(overRow).get(teleporterRight.x) != null && AbstractDungeon.map.get(overRow).get(teleporterRight.x).hasEdges()) {
	            	AbstractDungeon.map.get(overRow).add(teleporterRight);
	            } else {
	            	AbstractDungeon.map.get(overRow).set(teleporterRight.x, teleporterRight);
	            }
	            if (teleporterLeft.x <= 0 || teleporterLeft.x >= AbstractDungeon.map.get(overRow).size() || AbstractDungeon.map.get(overRow).get(teleporterLeft.x) != null && AbstractDungeon.map.get(overRow).get(teleporterLeft.x).hasEdges()) {
	            	AbstractDungeon.map.get(overRow).add(teleporterLeft);
	            } else {
	            	AbstractDungeon.map.get(overRow).set(teleporterLeft.x, teleporterLeft);
	            }

	            ReplayTheSpireMod.logger.debug("Poral A : (" + teleporterLeft.x + ", " + teleporterLeft.y + ")");
	            ReplayTheSpireMod.logger.debug("Poral B : (" + teleporterRight.x + ", " + teleporterRight.y + ")");
	            
				if (!AbstractDungeon.player.hasRelic("Painkiller Herb")) {
					teleporterLeft.room.setMapImg(ReplayTheSpireMod.portalIcon, ReplayTheSpireMod.portalBG);
					teleporterRight.room.setMapImg(ReplayTheSpireMod.portalIcon, ReplayTheSpireMod.portalBG);
				}
			}
		}
	}
}
