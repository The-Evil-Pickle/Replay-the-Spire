package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

public class WaspNest extends AbstractRelic
{
	public static final String ID = "Replay:WaspNest";
	public static final int THORNS_AMT = 2;
	
	public WaspNest()
	{
		super(ID, "betaRelic.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
	}
	
	public String getUpdatedDescription()
	{
		return this.DESCRIPTIONS[0] + THORNS_AMT + this.DESCRIPTIONS[1];
	}
	
	public void onLoseHp(int damageAmount) {
		if (damageAmount > 0 && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, THORNS_AMT), THORNS_AMT));
		}
	}
	
	public AbstractRelic makeCopy()
	{
		return new WaspNest();
	}
}
