package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
//import com.megacrit.cardcrawl.relics.LandingSound;
//import com.megacrit.cardcrawl.relics.RelicTier;
import com.megacrit.cardcrawl.core.*;

public class SneckoScales extends AbstractRelic
{
    public static final String ID = "Snecko Scales";
	public static int DRAWCOUNT = 3;
    
    public SneckoScales() {
        super("Snecko Scales", "sneckoScales.png", RelicTier.BOSS, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + SneckoScales.DRAWCOUNT + this.DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(final AbstractCard targetCard, final UseCardAction useCardAction) {
		if (targetCard == null || targetCard.freeToPlayOnce || targetCard.costForTurn <= 0) {
			return;
		}
		AbstractDungeon.actionManager.addToBottom(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, 1, false));
    }
    
    @Override
    public void onEquip() {
		AbstractDungeon.player.masterHandSize += SneckoScales.DRAWCOUNT;
    }
    
    @Override
    public void onUnequip() {
		AbstractDungeon.player.masterHandSize -= SneckoScales.DRAWCOUNT;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new SneckoScales();
    }
}
