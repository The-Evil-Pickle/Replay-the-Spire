package ReplayTheSpireMod.patches;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.potions.*;
import ReplayTheSpireMod.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

public class ReplaySimplicityRunePatches
{
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.potions.AttackPotion", method = "use")
	public static class ReplayAttackPotionPatch {
		public static void Replace(AttackPotion __instance, final AbstractCreature target) {
			final AbstractCard c = AbstractDungeon.returnTrulyRandomCard(AbstractCard.CardType.ATTACK, AbstractDungeon.cardRandomRng).makeCopy();
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Chameleon Ring")) {
				c.upgrade();
			}
			c.setCostForTurn(-99);
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
		}
		
	}
}
