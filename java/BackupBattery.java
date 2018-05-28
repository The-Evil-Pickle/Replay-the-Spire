package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.vfx.*;
import com.badlogic.gdx.graphics.*;

public class BackupBattery extends AbstractRelic
{
    public static final String ID = "Backup Battery";
	public static final int MAXIMUM = 5;
	public static final int POWER = 1;
	private boolean firstTurn = false;
    
    public BackupBattery() {
        super("Backup Battery", "betaRelic.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + BackupBattery.POWER + DESCRIPTIONS[1] + BackupBattery.MAXIMUM + DESCRIPTIONS[2];
    }
    
    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToTop((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new RetainSomeEnergyPower((AbstractCreature)AbstractDungeon.player, BackupBattery.POWER), 1));
        AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, (AbstractRelic)this));
    }
	
    @Override
	public void onEvokeOrb(final AbstractOrb ammo) {
		if (this.counter < BackupBattery.MAXIMUM && ammo.ID.equals("Plasma")) {
			this.counter++;
			AbstractDungeon.actionManager.addToTop((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new RetainSomeEnergyPower((AbstractCreature)AbstractDungeon.player, BackupBattery.POWER), 1));
			AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, (AbstractRelic)this));
		}
    }
	
    @Override
    public void onVictory() {
		this.counter = BackupBattery.POWER;
    }
	
    @Override
    public AbstractRelic makeCopy() {
        return new BackupBattery();
    }
}