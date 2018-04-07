package ReplayTheSpireMod.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.potions.*;
import ReplayTheSpireMod.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.random.Random;
import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.PotionHelper", method = "getRandomPotion")
public class ReplayPotionHelperPatch {
	
	public static AbstractPotion Postfix(AbstractPotion __result, PotionHelper __Instance) {
		return ReplayTheSpireMod.getRandomPotion();
	}
	public static AbstractPotion Postfix(AbstractPotion __result, PotionHelper __Instance, Random __rng) {
		return ReplayTheSpireMod.getRandomPotion(__rng);
	}
	public static AbstractPotion Postfix(AbstractPotion __result, ArrayList __list) {
		return __result;
	}
	
}