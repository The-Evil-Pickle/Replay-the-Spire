package ReplayTheSpireMod.patches;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.audio.*;
import com.megacrit.cardcrawl.helpers.*;
import ReplayTheSpireMod.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.monsters.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.monsters.thetop.*;
import com.megacrit.cardcrawl.monsters.exordium.*;
import com.megacrit.cardcrawl.monsters.city.*;
import com.megacrit.cardcrawl.monsters.replay.*;
import com.megacrit.cardcrawl.monsters.beyond.*;
import com.megacrit.cardcrawl.metrics.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.*;
import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.audio.TempMusic", method = "getSong")
public class ReplayMusicPatch {
	
	public static Music Postfix(Music __result, TempMusic __instance, final String key) {
		switch (key) {
            case "PONDFISH_THEME": {
                return Gdx.audio.newMusic(Gdx.files.internal("audio/replay/NRG_Stratosphere_Smackdown.ogg"));
            }
            case "PIRATE_JOKE": {
                return Gdx.audio.newMusic(Gdx.files.internal("audio/replay/Shanty_for_a_Poorly_Drawn_Pirate.ogg"));
            }
			default: {
				return __result;
			}
		}
		
	}
	
}