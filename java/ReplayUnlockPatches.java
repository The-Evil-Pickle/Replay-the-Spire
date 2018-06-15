package ReplayTheSpireMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.saveAndContinue.*;

import ReplayTheSpireMod.*;

public class ReplayUnlockPatches {
    @SpirePatch(cls = "com.megacrit.cardcrawl.unlock.UnlockTracker", method = "refresh")
    public static class Refresh {
        
        public static void Postfix() {
            ReplayTheSpireMod.receiveEditUnlocks();
        }
        
    }
}