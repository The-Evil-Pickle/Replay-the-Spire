package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;

public class ChewingGum extends AbstractRelic
{
    public static final String ID = "ChewingGum";
	public static final int SLIMED = 3;
    
    public ChewingGum() {
        super("ChewingGum", "chewingGum.png", RelicTier.BOSS, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + ChewingGum.SLIMED + this.DESCRIPTIONS[1];
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
    
    /*@Override
    public void atBattleStartPreDraw() {
        this.flash();
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(AbstractDungeon.player, AbstractDungeon.player, new Slimed(), ChewingGum.SLIMED, true, false));
	}*/
	
    @Override
    public void onShuffle() {
        this.flash();
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Slimed(), ChewingGum.SLIMED, true, false));
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new ChewingGum();
    }
}
