package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(
        cls="com.megacrit.cardcrawl.cards.AbstractCard",
        method=SpirePatch.CLASS
)
public class BottlePatches {
	    public static SpireField<Boolean> example = new SpireField<>(() -> false);
}
