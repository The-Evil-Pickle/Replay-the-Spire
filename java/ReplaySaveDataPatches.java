package ReplayTheSpireMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.saveAndContinue.*;

import ReplayTheSpireMod.*;

public class ReplaySaveDataPatches {
    @SpirePatch(cls = "com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue", method = "loadSaveFile")
    public static class LoadGame {
        
        public static void Prefix(AbstractPlayer.PlayerClass pClass) {
            ReplayTheSpireMod.loadRunData();
        }
        
        public static void Prefix(String filePath) {
            ReplayTheSpireMod.loadRunData();
        }
    }
    
    @SpirePatch(cls = "com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue", method = "save")
    public static class SaveGame {
        
        public static void Prefix(SaveFile save) {
            ReplayTheSpireMod.saveRunData()();
        }
    }
    
    @SpirePatch(cls = "com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue", method = "deleteSave")
    public static class DeleteSave {
        public static void Prefix(AbstractPlayer.PlayerClass pClass) {
            ReplayTheSpireMod.clearRunData();
        }
    }
}