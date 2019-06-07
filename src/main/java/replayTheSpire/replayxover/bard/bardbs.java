package replayTheSpire.replayxover.bard;

import com.evacipated.cardcrawl.mod.bard.helpers.MelodyManager;

public class bardbs {
	public static void AddMelodyStrings(String jsonPath) {
		MelodyManager.loadMelodyStrings(jsonPath+"ReplayMelodyStrings.json");
	}
	public static void AddNote() {
		MelodyManager.addNote(OrbNote.get());
	}
}
