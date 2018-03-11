package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;

public class SneckoScales extends AbstractRelic
{
    public static final String ID = "Snecko Scales";
    
    public SneckoScales() {
        super("Snecko Scales", "betaRelic.png", RelicTier.BOSS, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(final AbstractCard targetCard, final UseCardAction useCardAction) {
		AbstractDungeon.actionManager.addToBottom(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, 1, false));
    }
    
    @Override
    public void onEquip() {
		AbstractDungeon.player.masterHandSize += 3;
    }
    
    @Override
    public void onUnequip() {
		AbstractDungeon.player.masterHandSize -= 3;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new SneckoScales();
    }
}
