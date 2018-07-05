package replayTheSpire.patches;
import replayTheSpire.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.green.*;
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
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.vfx.*;
import basemod.*;
import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.cards.green.CorpseExplosion", method = "use")
public class ReplayCorpseExplosionPatch {
	
	public static void Replace(CorpseExplosion __instance, final AbstractPlayer p, final AbstractMonster m) {
		if (__instance.upgraded) {
			if (m.getPower("Poison") == null) {
				AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0f, CorpseExplosion.EXTENDED_DESCRIPTION[0], true));
				return;
			}
			int pAmnt = m.getPower("Poison").amount;
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, "Poison"));
			for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
			  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new NecroticPoisonPower(mo, p, pAmnt), pAmnt, AbstractGameAction.AttackEffect.POISON));
			}
		} else {
			for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
				if (mo.getPower("Poison") != null) {
					int pAmnt = mo.getPower("Poison").amount;
					AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mo, p, "Poison"));
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new NecroticPoisonPower(mo, p, pAmnt), pAmnt, AbstractGameAction.AttackEffect.POISON));
				}
			}
		}
		/*
		__instance.exhaust = false;
		if (m.getPower("Poison") == null) {
            AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0f, CorpseExplosion.EXTENDED_DESCRIPTION[0], true));
            return;
        }
		__instance.exhaust = __instance.upgraded == false;
		int pAmnt = m.getPower("Poison").amount;
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, "Poison"));
		for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
		  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new NecroticPoisonPower(mo, p, pAmnt), pAmnt, AbstractGameAction.AttackEffect.POISON));
		}
		*/
	}
	
}