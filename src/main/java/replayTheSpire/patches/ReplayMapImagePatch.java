package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.rooms.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import replayTheSpire.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.rooms.AbstractRoom", method = "getMapImg")
public class ReplayMapImagePatch {
	
	public static Texture Postfix(Texture __result, AbstractRoom __Instance) {
		if (AbstractDungeon.player.hasRelic("Painkiller Herb")) {
			if (__Instance.getMapSymbol() == "?" || __Instance.getMapSymbol() == "M") {
				return ImageMaster.MAP_NODE_EVENT;
			}
			if (__Instance.getMapSymbol() == "T" || __Instance.getMapSymbol() == "$" || __Instance.getMapSymbol() == "R") {
				return ImageMaster.MAP_NODE_TREASURE;
			}
		}
		if (__Instance.getMapSymbol() == "PTL") {
			return ReplayTheSpireMod.portalIcon;
		}
		return __result;
	}
	
}