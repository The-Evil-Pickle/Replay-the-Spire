package replayTheSpire.patches;

import com.megacrit.cardcrawl.events.city.*;
import java.util.*;
import com.megacrit.cardcrawl.relics.*;

import replayTheSpire.ReplayTheSpireMod;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.mod.replay.relics.BlankCodex;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(clz = CursedTome.class, method = "randomBook")
public class CursedTomePatch
{
    @SpireInsertPatch(rloc = 1, localvars = { "possibleBooks" })
    public static void Insert(final CursedTome __instance, @ByRef final ArrayList<AbstractRelic>[] possibleBooks) {
        if (ReplayTheSpireMod.foundmod_infinite && !AbstractDungeon.player.hasRelic(BlankCodex.ID)) {
            possibleBooks[0].add(RelicLibrary.getRelic(BlankCodex.ID).makeCopy());
        }
    }
}