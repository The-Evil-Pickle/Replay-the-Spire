package replayTheSpire.patches;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.relics.PetGhost;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.events.city.*;
import com.megacrit.cardcrawl.dungeons.*;

import basemod.*;
import com.megacrit.cardcrawl.helpers.*;
import java.util.*;
import java.util.function.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import javassist.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.*;

public class TheJoustNewOption
{
    private static final EventStrings eventStrings;
    private static boolean isUsingOption; 
    
    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("ReplayTheSpireMod:TheJoustExtra");
    }

    @SpirePatch(clz = TheJoust.class, method = "<ctor>")
    public static class ChangeOptionText
    {
        public static void Postfix(final TheJoust __instance) {
            TheJoustNewOption.isUsingOption = true;
        }
    }
    
    private static void SetUpCardType(RewardItem r) {
    	final CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
				group.addToBottom(c.makeCopy());
		}
    	for (int c=0; c < r.cards.size(); c++) {
    			r.cards.set(c, group.getRandomCard(AbstractDungeon.cardRng));
    			group.removeCard(r.cards.get(c));
    	}
    }
    @SpirePatch(clz = TheJoust.class, method = "buttonEffect")
    public static class ChangeOptionEffect
    {
        public static void Postfix(final TheJoust __instance, final int buttonPressed) {
            if (TheJoustNewOption.isUsingOption) {
            	if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(PetGhost.ID)) {
            		__instance.imageEventText.setDialogOption(TheJoustNewOption.eventStrings.OPTIONS[1] + 200 + TheJoustNewOption.eventStrings.OPTIONS[2]);
            	} else {
            		__instance.imageEventText.setDialogOption(TheJoustNewOption.eventStrings.OPTIONS[0], true);
            	}
            	TheJoustNewOption.isUsingOption = false;
            }
        }
        public static SpireReturn Prefix(final TheJoust __instance, final int buttonPressed) {
        	if (ReflectionHacks.getPrivate((Object)__instance, (Class)TheJoust.class, "screen") == null) {
        		AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                AbstractDungeon.dungeonMapScreen.open(false);
        		return SpireReturn.Return((Object)null);
        	}
            if (buttonPressed == 2 && AbstractDungeon.player.hasRelic(PetGhost.ID)) {
                __instance.imageEventText.updateBodyText(TheJoustNewOption.eventStrings.DESCRIPTIONS[0]);
                __instance.imageEventText.clearAllDialogs();
                __instance.imageEventText.setDialogOption(TheJoust.OPTIONS[7]);
                AbstractDungeon.player.loseRelic(PetGhost.ID);
                AbstractDungeon.combatRewardScreen.open();
        		AbstractDungeon.combatRewardScreen.rewards.clear();
        		AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(200));
        		RewardItem rewardCard = new RewardItem();
        		SetUpCardType(rewardCard);
        		AbstractDungeon.combatRewardScreen.rewards.add(rewardCard);
        		AbstractDungeon.combatRewardScreen.positionRewards();
        		AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                ReflectionHacks.setPrivate((Object)__instance, (Class)TheJoust.class, "screen", (Object)null);
                return SpireReturn.Return((Object)null);
            }
            return SpireReturn.Continue();
        }
    }
}
