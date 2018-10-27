package replayTheSpire.patches;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
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
import com.megacrit.cardcrawl.characters.*;

public class TheMausoleumNewOption
{
    private static final EventStrings eventStrings;
    private static boolean isUsingOption; 
    
    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("ReplayTheSpireMod:TheMausoleumExtra");
    }
    
    @SpirePatch(clz = TheMausoleum.class, method = "<ctor>")
    public static class ChangeOptionText
    {
        public static void Postfix(final TheMausoleum __instance) {
            if (AbstractDungeon.eventRng.random(2) > 0) {
                __instance.imageEventText.updateDialogOption(0, TheMausoleumNewOption.eventStrings.OPTIONS[0] + (int)ReflectionHacks.getPrivate((Object)__instance, (Class)TheMausoleum.class, "percent") + TheMausoleumNewOption.eventStrings.OPTIONS[1]);
                TheMausoleumNewOption.isUsingOption = true;
            }
            else {
            	TheMausoleumNewOption.isUsingOption = false;
            }
        }
    }
    
    @SpirePatch(clz = TheMausoleum.class, method = "buttonEffect")
    public static class ChangeOptionEffect
    {
        public static SpireReturn Prefix(final TheMausoleum __instance, final int buttonPressed) {
        	if (ReflectionHacks.getPrivate((Object)__instance, (Class)TheMausoleum.class, "screen") == null) {
        		AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                AbstractDungeon.dungeonMapScreen.open(false);
        		return SpireReturn.Return((Object)null);
        	}
            if (TheMausoleumNewOption.isUsingOption && buttonPressed == 0) {
            	boolean result = AbstractDungeon.eventRng.randomBoolean();
                if (AbstractDungeon.ascensionLevel >= 15) {
                    result = true;
                }
                if (result) {
                	__instance.imageEventText.updateBodyText(TheMausoleumNewOption.eventStrings.DESCRIPTIONS[0]);
                	AbstractCard curse = AbstractDungeon.returnRandomCurse();
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, Settings.WIDTH / 2, Settings.HEIGHT / 2));
                    UnlockTracker.markCardAsSeen(curse.cardID);
                }
                else {
                	__instance.imageEventText.updateBodyText(TheMausoleumNewOption.eventStrings.DESCRIPTIONS[1]);
                }
                CardCrawlGame.sound.play("BLUNT_HEAVY");
                CardCrawlGame.screenShake.rumble(2.0f);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier()));
                TheMausoleumNewOption.isUsingOption = false;

                __instance.imageEventText.clearAllDialogs();
                __instance.imageEventText.setDialogOption(TheMausoleum.OPTIONS[2]);
                ReflectionHacks.setPrivate((Object)__instance, (Class)TheMausoleum.class, "screen", (Object)null);
                return SpireReturn.Return((Object)null);
            }
            TheMausoleumNewOption.isUsingOption = false;
            return SpireReturn.Continue();
        }
    }
}
