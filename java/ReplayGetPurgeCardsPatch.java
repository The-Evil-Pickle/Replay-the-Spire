package ReplayTheSpireMod.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.cards.CardGroup", method = "getPurgeableCards")
public class ReplayGetPurgeCardsPatch {
	
	public static CardGroup Postfix(CardGroup __result, CardGroup __instance) {
		final CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (final AbstractCard c : __result.group) {
            if (!c.cardID.equals("Faulty Equipment")) {
                retVal.group.add(c);
            }
        }
		return retVal;
	}
	
}