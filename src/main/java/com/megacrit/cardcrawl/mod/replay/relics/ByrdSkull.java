package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ByrdSkull extends AbstractRelic
{
    public static final String ID = "Byrd Skull";
    
    public ByrdSkull() {
        super("Byrd Skull", "betaRelic.png", RelicTier.RARE, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.isInnate && card.type != AbstractCard.CardType.CURSE) {
            this.flash();
			AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
			AbstractDungeon.actionManager.addToTop(new ReplayRefundAction(card, 1));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new ByrdSkull();
    }
}
