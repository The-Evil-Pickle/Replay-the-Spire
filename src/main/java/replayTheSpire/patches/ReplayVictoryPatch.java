package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.ui.*;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.screens.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.core.*;
import replayTheSpire.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.rooms.VictoryRoom", method = "onPlayerEntry")
public class ReplayVictoryPatch {
	
	public static void Postfix(VictoryRoom __Instance) {
		if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Snecko Eye")) {
			ReplayTheSpireMod.completeAchievement("complete_eye");
		}
	}
	
}