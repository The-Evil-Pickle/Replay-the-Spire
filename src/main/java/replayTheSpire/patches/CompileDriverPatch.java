package replayTheSpire.patches;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.AbeCurse;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.rooms.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import replayTheSpire.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.actions.defect.CompileDriverAction", method = "update")
public class CompileDriverPatch {
	
	public static void Replace(CompileDriverAction __Instance) {
		ArrayList<String> orbList = new ArrayList<String>();
		for (final AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o.ID != null && !o.ID.equals(EmptyOrbSlot.ORB_ID) && !orbList.contains(o.ID)) {
            	orbList.add(o.ID);
            }
        }
		final int toDraw = orbList.size() * __Instance.amount;
        if (toDraw > 0) {
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(__Instance.source, toDraw));
        }
        __Instance.isDone = true;
	}
	
}