package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.AbeCurse;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.rooms.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import replayTheSpire.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.CardLibrary", method = "getCurse")
public class GetCursePatch {
	
	public static AbstractCard Postfix(AbstractCard __result) {
		if (__result != null) {
			if (__result.cardID.equals(AbeCurse.ID)) {
				return CardLibrary.getCurse();
			}
		}
		return __result;
	}
	
}