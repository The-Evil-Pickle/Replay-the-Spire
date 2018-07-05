package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.ui.buttons.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.localization.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.screens.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import basemod.*;
import replayTheSpire.ReplayTheSpireMod;

import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.ui.buttons.SingingBowlButton", method = "onClick")
public class HoneyBowlPatch {
	
	public static void Prefix(SingingBowlButton __instance) {
		if (ReplayTheSpireMod.noSkipRewardsRoom) {
			final RewardItem reward = (RewardItem)ReflectionHacks.getPrivate((Object)__instance, (Class)SingingBowlButton.class, "rItem");
			final ArrayList<AbstractCard> rewardcards = (ArrayList<AbstractCard>)ReflectionHacks.getPrivate((Object)reward, (Class)RewardItem.class, "cards");
			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(rewardcards.get(AbstractDungeon.cardRng.random(rewardcards.size() - 1)).makeStatEquivalentCopy(), Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
			ReplayRewardSkipPositionPatch.Postfix(AbstractDungeon.combatRewardScreen);
		}
	}
	
}