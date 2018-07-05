package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.ui.buttons.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.localization.*;
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
import replayTheSpire.patches.ReplayRewardSkipPositionPatch;

@SpirePatch(cls = "com.megacrit.cardcrawl.ui.buttons.SingingBowlButton", method = "onClick")
public class HoneyBowlPostPatch {
	
	public static void Postfix(SingingBowlButton __instance) {
		if (ReplayTheSpireMod.noSkipRewardsRoom) {
			ReplayRewardSkipPositionPatch.Postfix(AbstractDungeon.combatRewardScreen);
			ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_flashRelic("Honey Jar");
		}
	}
	
}