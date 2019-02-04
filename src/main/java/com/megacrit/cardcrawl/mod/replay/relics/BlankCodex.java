package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.replayxover.DiscoverBlackCardToDeckAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BlankCodex extends AbstractRelic
{
	public static final String ID = "Replay:Blank Codex";
	
	public BlankCodex()
	{
		super(ID, "replay_codex.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.FLAT);
	}
	
	public String getUpdatedDescription()
	{
		return this.DESCRIPTIONS[0];
	}
	
	@Override
    public void onShuffle() {
		this.flash();
		AbstractDungeon.actionManager.addToBottom(new DiscoverBlackCardToDeckAction(null, 3, false, true));
    }
	@Override
	public void atBattleStart()
	{
		this.flash();
		AbstractDungeon.actionManager.addToBottom(new DiscoverBlackCardToDeckAction(null, 3, false, true));
	}
	public AbstractRelic makeCopy()
	{
		return new BlankCodex();
	}
}
