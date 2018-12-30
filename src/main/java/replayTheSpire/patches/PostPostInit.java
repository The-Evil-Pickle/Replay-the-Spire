package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import replayTheSpire.ReplayTheSpireMod;

@SpirePatch(cls = "com.megacrit.cardcrawl.core.CardCrawlGame", method = "create")
public class PostPostInit
{
    public static void Postfix(final Object __obj_instance) {
    	ReplayTheSpireMod.postPostInit();
        ReplayTheSpireMod.purgeBossToggle();
    }
}