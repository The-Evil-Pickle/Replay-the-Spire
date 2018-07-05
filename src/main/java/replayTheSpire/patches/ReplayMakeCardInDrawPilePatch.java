package replayTheSpire.patches;

import java.util.*;
import replayTheSpire.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.powers.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import basemod.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect", method = "update")
public class ReplayMakeCardInDrawPilePatch {
	
	public static void Prefix(ShowCardAndAddToDrawPileEffect __Instance) {
		if (__Instance != null && AbstractDungeon.player != null && __Instance.duration >= 1.5f - Gdx.graphics.getDeltaTime() && !__Instance.isDone) {
			if (AbstractDungeon.player.hasRelic(QuantumEgg.ID)) {
                final AbstractCard c = (AbstractCard)ReflectionHacks.getPrivate((Object)__Instance, (Class)ShowCardAndAddToDrawPileEffect.class, "card");
				if (c != null && c.canUpgrade()) {
					AbstractCard srcCard = null;
					for (AbstractCard realcard : AbstractDungeon.player.drawPile.group) {
						if (c.name == realcard.name 
						&& c.timesUpgraded == realcard.timesUpgraded 
						&& c.inBottleLightning == realcard.inBottleLightning 
						&& c.inBottleFlame == realcard.inBottleFlame  
						&& c.inBottleTornado == realcard.inBottleTornado  
						&& c.baseBlock == realcard.baseBlock  
						&& c.baseDamage == realcard.baseDamage  
						&& c.baseMagicNumber == realcard.baseMagicNumber  
						&& c.misc == realcard.misc  
						&& c.cost == realcard.cost ) {
							srcCard = realcard;
							break;
						}
					}
					if (srcCard != null && srcCard.canUpgrade()) {
						srcCard.upgrade();
						c.upgrade();
						ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_flashRelic(QuantumEgg.ID);
					}
				}
            }
		}
	}
	
}