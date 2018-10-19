package com.megacrit.cardcrawl.relics;

import java.util.AbstractList;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.*;

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
    public int onPlayerHeal(final int healAmount) {
        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.flash();
            return MathUtils.ceil((float)healAmount / 2.0f);
        }
        return healAmount;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new DimensionalGlitch();
    }
}
