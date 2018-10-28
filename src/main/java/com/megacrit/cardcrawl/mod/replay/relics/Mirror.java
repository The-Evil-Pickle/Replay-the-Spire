package com.megacrit.cardcrawl.mod.replay.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import replayTheSpire.ReplayTheSpireMod;

import java.util.Iterator;

public class Mirror extends AbstractRelic implements OnReceivePowerRelic {
	public static final String ID = "Mirror";
    public Mirror()
	{
        super("Mirror", "replay_mirror.png", RelicTier.UNCOMMON, LandingSound.CLINK);
    }
	public String getUpdatedDescription()
	{
		return this.DESCRIPTIONS[0];
	}
	public AbstractRelic makeCopy()
	{
		return new Mirror();
	}
	@Override
	public boolean onReceivePower(AbstractPower powerToApply, AbstractCreature source) {
		if (powerToApply.ID.equals("Weakened")) {
			ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic("Mirror").flash();
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(source, AbstractDungeon.player, new WeakPower(source, powerToApply.amount, true), powerToApply.amount));
		}
		if (powerToApply.ID.equals("Vulnerable")) {
			ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic("Mirror").flash();
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(source, AbstractDungeon.player, new VulnerablePower(source, powerToApply.amount, true), powerToApply.amount));
		}
		return true;
	}
}
