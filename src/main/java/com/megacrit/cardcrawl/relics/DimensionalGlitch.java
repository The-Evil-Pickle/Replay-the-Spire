package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DimensionalGlitch extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:DimensionalGlitch";
    
    public DimensionalGlitch() {
        super(ID, "dimensionGlitch.png", RelicTier.BOSS, LandingSound.MAGICAL);
    }
	
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        ++energy.energyMaster;
    }
    
    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        --energy.energyMaster;
    }
	
    @Override
    public AbstractRelic makeCopy() {
        return new DimensionalGlitch();
    }
}
