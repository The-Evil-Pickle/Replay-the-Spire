package ReplayTheSpireMod.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "initializeSpecialOneTimeEventList")
public class ReplayDungeonSpecialPatch {
	
	public static void Postfix(AbstractDungeon __instance) {
		__instance.specialOneTimeEventList.add("Trapped Chest");
		//__instance.specialOneTimeEventList.add("Chaos Ring Event");
		__instance.specialOneTimeEventList.add("Stuck");
	}
	
}