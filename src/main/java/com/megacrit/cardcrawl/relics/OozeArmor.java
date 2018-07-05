package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;

public class OozeArmor extends AbstractRelic
{
    public static final String ID = "Ooze Armor";
    
    public OozeArmor() {
        this(null);
    }
    
    public OozeArmor(final AbstractPlayer.PlayerClass c) {
        super("Ooze Armor", "betaRelic.png", RelicTier.BOSS, LandingSound.HEAVY);
    }
    
    @Override
    public String getUpdatedDescription() {
    	return this.DESCRIPTIONS[3] + this.DESCRIPTIONS[0];
    }
    
    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        ++energy.energyMaster;
		//final AbstractPlayer player = AbstractDungeon.player;
        //player.masterHandSize += 1;
    }
    
    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        --energy.energyMaster;
		//final AbstractPlayer player = AbstractDungeon.player;
        //player.masterHandSize -= 1;
    }
    
	@Override
    public void atTurnStart() {
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SlowPower(AbstractDungeon.player, -2), -2));
    }
	
    @Override
    public void atBattleStart() {
        this.flash();
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SlowPower(AbstractDungeon.player, 0), 0));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MalleablePower(AbstractDungeon.player, 4)));
        //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MetallicizePower(AbstractDungeon.player, 2), 2));
        //AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(AbstractDungeon.player, AbstractDungeon.player, new Slimed(), 2, true, false));
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
	
    @Override
    public AbstractRelic makeCopy() {
        return new OozeArmor();
    }
    
}
