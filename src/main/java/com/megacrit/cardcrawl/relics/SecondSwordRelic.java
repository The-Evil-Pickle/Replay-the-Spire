package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.monsters.*;

import basemod.helpers.CardTags;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;

public class SecondSwordRelic extends AbstractRelic
{
    public static final String ID = "Another Sword";
    
    public SecondSwordRelic() {
        super("Another Sword", "secondsword.png", RelicTier.RARE, LandingSound.SOLID);
        this.counter = -1;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
	
	@Override
    public void atTurnStart() {
		if (this.counter > 0) {
			this.counter--;
			if (this.counter <= 0) {
				AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
				this.pulse = true;
			}
		}
    }
	
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (!card.purgeOnUse && (PerfectedStrike.isStrike(card) || card.hasTag(AbstractCard.CardTags.STRIKE)) && this.counter <= 0) {
            this.flash();
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster)action.target;
            }
            final AbstractCard tmp = card.makeStatEquivalentCopy();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = Settings.WIDTH / 2.0f - 300.0f * Settings.scale;
            tmp.target_y = Settings.HEIGHT / 2.0f;
            tmp.freeToPlayOnce = true;
            if (m != null) {
                tmp.calculateCardDamage(m);
            }
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, m, card.energyOnUse));
			this.counter = 1 + Math.max(0, card.costForTurn);
			this.pulse = false;
        }
    }
    
    @Override
    public void atPreBattle() {
		this.counter = 1;
    }
	
	@Override
	public void onVictory() {
		this.counter = -1;
	}
    
    @Override
    public AbstractRelic makeCopy() {
        return new SecondSwordRelic();
    }
}
