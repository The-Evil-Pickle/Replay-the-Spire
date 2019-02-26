package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.status.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.powers.MalleablePower;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.SlowPower2;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;

public class OozeArmor extends AbstractRelic
{
    public static final String ID = "Ooze Armor";
    
    public OozeArmor() {
        this(null);
    }
    
    public OozeArmor(final AbstractPlayer.PlayerClass c) {
        super("Ooze Armor", "oozeArmor.png", RelicTier.BOSS, LandingSound.HEAVY);
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
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SlowPower2(AbstractDungeon.player, -3), -3));
    }
	
    @Override
    public void atBattleStart() {
        this.flash();
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SlowPower2(AbstractDungeon.player, 0), 0));
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
