package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.rooms.*;
import replayTheSpire.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.rooms.AbstractRoom", method = "getMapSymbol")
public class ReplayMapSymbolPatch {
	
	public static String Postfix(String __result, AbstractRoom __Instance) {
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("Painkiller Herb")) {
			if (__result == "M") {// || __result == "E"
				return "?";
			}
			if (__result == "$" || __result == "R") {
				return "T";
			}
		}
		return __result;
	}
	
}