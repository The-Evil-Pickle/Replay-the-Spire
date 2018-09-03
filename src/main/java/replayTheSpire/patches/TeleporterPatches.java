package replayTheSpire.patches;

import java.util.*;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.TeleportRoom;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;

import basemod.ReflectionHacks;
import replayTheSpire.ReplayTheSpireMod;

import com.megacrit.cardcrawl.map.*;

public class TeleporterPatches {
	
	public static MapRoomNode teleporterLeft;
	public static MapRoomNode teleporterRight;
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "generateMap")
	public static class PickTeleportersPatch {
		
		public static void Postfix() {
			
			if (AbstractDungeon.mapRng.random(0, 100) > 33) {
				
				int overRow = AbstractDungeon.mapRng.random(2, AbstractDungeon.map.size() - 1);
				
				ArrayList<MapRoomNode> teleporterRow = new ArrayList<MapRoomNode>();
				for (MapRoomNode node : AbstractDungeon.map.get(overRow)) {
					
					if (node != null && node.room != null && node.hasEdges()) {
						teleporterRow.add(node);
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
				
	            teleporterLeft = new MapRoomNode(roomLeft.x - 1, roomLeft.y);
	            teleporterRight = new MapRoomNode(roomRight.x + 1, roomRight.y);
	            teleporterLeft.room = new TeleportRoom(teleporterRight);
	            teleporterRight.room = new TeleportRoom(teleporterLeft);
				
	            
	            
	            teleporterLeft.addEdge(new MapEdge(teleporterLeft.x, teleporterLeft.y, teleporterLeft.offsetX, teleporterLeft.offsetY, roomLeft.x, roomLeft.y, roomLeft.offsetX, roomLeft.offsetY, false));
	            underRoomLeft.addEdge(new MapEdge(underRoomLeft.x, underRoomLeft.y, underRoomLeft.offsetX, underRoomLeft.offsetY, teleporterLeft.x, teleporterLeft.y, teleporterLeft.offsetX, teleporterLeft.offsetY, false));
	            teleporterRight.addEdge(new MapEdge(teleporterRight.x, teleporterRight.y, teleporterRight.offsetX, teleporterRight.offsetY, roomRight.x, roomRight.y, roomRight.offsetX, roomRight.offsetY, false));
	            underRoomRight.addEdge(new MapEdge(underRoomRight.x, underRoomRight.y, underRoomRight.offsetX, underRoomRight.offsetY, teleporterRight.x, teleporterRight.y, teleporterRight.offsetX, teleporterRight.offsetY, false));
	            
	            
	            
	            final ArrayList<MapRoomNode> visibleMapNodes = (ArrayList<MapRoomNode>)ReflectionHacks.getPrivate((Object)AbstractDungeon.dungeonMapScreen, (Class)DungeonMapScreen.class, "visibleMapNodes");
	            
	            AbstractDungeon.map.get(overRow).add(teleporterLeft);
	            AbstractDungeon.map.get(overRow).add(teleporterRight);
	            
				if (!AbstractDungeon.player.hasRelic("Painkiller Herb")) {
					teleporterLeft.room.setMapImg(ReplayTheSpireMod.portalIcon, ReplayTheSpireMod.portalBG);
					teleporterRight.room.setMapImg(ReplayTheSpireMod.portalIcon, ReplayTheSpireMod.portalBG);
				}
			}
		}
	}
}
