package replayTheSpire.patches;

import java.util.*;
import replayTheSpire.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.characters.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.characters.AbstractPlayer", method = "channelOrb")
public class ReplayOrbChannelPatch {
	
	public static void Postfix(AbstractPlayer __Instance, AbstractOrb orb) {
		if (orb instanceof CrystalOrb) {
			AbstractDungeon.actionManager.addToBottom(new CrystalOrbUpdateAction());
		}
	}
	
}