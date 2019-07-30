package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ReflectionPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

public class MirrorPendant extends AbstractRelic {
	public static final String ID = "Replay:Kaleidoscope";
    
    public MirrorPendant() {
        super(ID, "kaleidoscope.png", RelicTier.RARE, LandingSound.MAGICAL);
    }

	@Override
	public AbstractRelic makeCopy() {
		return new MirrorPendant();
	}

    @Override
    public void atTurnStart() {
        this.counter = 0;
    }
    @Override
    public void onVictory() {
        this.counter = -1;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(final AbstractCard targetCard, final UseCardAction useCardAction) {
		if (targetCard != null && targetCard.baseBlock > 0 && targetCard.rawDescription.contains("!B!")) {
			this.counter++;
			if (this.counter >= 3) {
				this.counter = 0;
				AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ReflectionPower(AbstractDungeon.player, 1), 1));
			}
		}
    }
}
