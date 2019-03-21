package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.mod.replay.potions.*;
import com.megacrit.cardcrawl.mod.replay.relics.HoneyJar;

import replayTheSpire.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.random.Random;

import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "nextRoomTransition", paramtypez = {})
public class ReplayNextRoomPatch {
	
	public static void Prefix() {
		ReplayTheSpireMod.clearShielding();
		ReplayTheSpireMod.noSkipRewardsRoom = (AbstractDungeon.player.hasRelic(HoneyJar.ID) && AbstractDungeon.player.getRelic(HoneyJar.ID).counter < -1);
		RenderHandPatch.plsDontRenderHand = false;
		BeyondScenePatch.bg_controller = null;
	}
}