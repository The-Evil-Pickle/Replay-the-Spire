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

@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.PotionHelper", method = "getPotion")
public class ReplayPotionHelperPatch {
	
	public static boolean isRando = false;
	
	public static AbstractPotion Postfix(AbstractPotion __result, final String name) {
		if (!isRando) {
			isRando = true;
			return ReplayTheSpireMod.getRandomPotion();
		}
		isRando = false;
		return __result;
	}
	
	/*
	public static AbstractPotion Postfix(AbstractPotion __result) {
		return ReplayTheSpireMod.getRandomPotion();
	}
	public static AbstractPotion Postfix(AbstractPotion __result, Random __rng) {
		return ReplayTheSpireMod.getRandomPotion(__rng);
	}
	public static AbstractPotion Postfix(AbstractPotion __result, ArrayList __list) {
		return __result;
	}
	*/
}