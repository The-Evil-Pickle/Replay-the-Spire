package replayTheSpire.patches;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.ui.buttons.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.colorless.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.screens.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import basemod.*;
import java.util.*;

import replayTheSpire.ReplayTheSpireMod;

@SpirePatch(cls = "com.megacrit.cardcrawl.actions.GameActionManager", method = "incrementDiscard")
public class OnManualDiscardPatch {
	
	public static void Postfix(final boolean endOfTurn) {
		if (!AbstractDungeon.actionManager.turnHasEnded && !endOfTurn) {
			if (AbstractDungeon.player.hasPower("Scrap Shanks")) {
				AbstractDungeon.player.getPower("Scrap Shanks").onSpecificTrigger();
				//AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shiv(), AbstractDungeon.player.getPower("Scrap Shanks").amount));
			}
		}
	}
	
}