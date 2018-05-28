package ReplayTheSpireMod.patches;

import java.util.*;
import ReplayTheSpireMod.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.powers.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.orbs.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.characters.AbstractPlayer", method = "channelOrb")
public class ReplayOrbChannelPatch {
	
	public static void Postfix(AbstractPlayer __Instance, AbstractOrb orb) {
		if (orb instanceof CrystalOrb) {
			AbstractDungeon.actionManager.addToBottom(new CrystalOrbUpdateAction());
		}
	}
	
}