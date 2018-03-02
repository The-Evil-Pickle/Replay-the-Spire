package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;

public class ElectricBlood extends AbstractRelic
{
    public static final String ID = "Electric Blood";
    
    public ElectricBlood() {
        super("Electric Blood", "electricBlood.png", RelicTier.BOSS, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(final AbstractCard targetCard, final UseCardAction useCardAction) {
		AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, targetCard.costForTurn));
    }
    
    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        ++energy.energyMaster;
		++energy.energyMaster;
    }
    
    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        --energy.energyMaster;
		--energy.energyMaster;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new ElectricBlood();
    }
}
