package ReplayTheSpireMod.patches;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
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
import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MonsterHelper", method = "getEncounter")
public class ReplayEncounterPatch {
	
	public static MonsterGroup Postfix(MonsterGroup __result, final String key) {
		switch (key) {
            case "Pondfish": {
                return new MonsterGroup(new AbstractMonster[] {new CaptainAbe(170.0f, -55.0f), new PondfishBoss(0.0f, -650.0f)});
            }
			default: {
				return __result;
			}
		}
		
	}
	
}