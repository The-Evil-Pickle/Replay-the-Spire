package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import infinitespire.powers.CriticalPower;

import java.util.ArrayList;

public class LuckySlots extends AbstractRelic
{
	public static final String ID = "Replay:Lucky Slots";
	public static final int TICTACTOE = 3;
	public static final int GOLD_AMT = 7;
	boolean gotGold;
	int realCounter;
	public LuckySlots()
	{
		super(ID, "betaRelic.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.CLINK);
		this.gotGold = false;
		this.counter = -1;
		this.realCounter = 0;
	}
	
	public String getUpdatedDescription()
	{
		return this.DESCRIPTIONS[0] + TICTACTOE + this.DESCRIPTIONS[1] + GOLD_AMT + this.DESCRIPTIONS[2];
	}

	@Override
	public void onLoseHp(int damageAmount) {
		if (damageAmount == 7) {
			flash();
			AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CriticalPower(AbstractDungeon.player)));
			this.realCounter++;
			if (!this.gotGold) {
				if (this.counter <= 0) {
					this.counter = 7;
				} else {
					this.counter *= 10;
					this.counter += 7;
				}
				if (this.realCounter == TICTACTOE) {
					AbstractDungeon.player.gainGold(GOLD_AMT);
					this.gotGold = true;
				}
			}
		}
	}
	
	@Override
	public void onVictory() {
		this.gotGold = false;
		this.counter = -1;
		this.realCounter = 0;
	}
	
	public AbstractRelic makeCopy()
	{
		return new LuckySlots();
	}
	@Override
	public int getPrice() {
		return 77;
	}
}
