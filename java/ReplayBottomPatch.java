package ReplayTheSpireMod.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.Exordium", method = "initializeEventList")
public class ReplayBottomPatch {
	
	public static void Postfix(AbstractDungeon __instance) {
		__instance.eventList.add("Mirror Mist");
	}
	
}