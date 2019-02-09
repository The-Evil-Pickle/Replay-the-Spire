package replayTheSpire.patches;
import com.megacrit.cardcrawl.audio.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.badlogic.gdx.audio.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.audio.TempMusic", method = "getSong")
public class ReplayMusicPatch {
	
	public static Music Postfix(Music __result, TempMusic __instance, final String key) {
		if (key.contains("replay/")) {
			return MainMusic.newMusic("audio/music/" + key);
		}
		switch (key) {
            case "replay/NRG_Stratosphere_Smackdown.ogg": {
                return MainMusic.newMusic("audio/music/replay/NRG_Stratosphere_Smackdown.ogg");
            }
            case "replay/Shanty_for_a_Poorly_Drawn_Pirate.ogg": {
                return MainMusic.newMusic("audio/music/replay/Shanty_for_a_Poorly_Drawn_Pirate.ogg");
            }
			default: {
				return __result;
			}
		}
		
	}
	
}