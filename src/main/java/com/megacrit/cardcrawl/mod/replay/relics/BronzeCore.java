package com.megacrit.cardcrawl.mod.replay.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.BronzeOrb;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.StasisPower;
import com.megacrit.cardcrawl.relics.*;


public class BronzeCore extends AbstractRelic
{
    public static final String ID = "Replay:Bronze Core";
    private AbstractMonster orbBoi;
    public BronzeCore() {
        super(ID, "bronzeCoreOrb.png", RelicTier.BOSS, LandingSound.SOLID);
        this.orbBoi = null;
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
    public void atPreBattle() {
        this.orbBoi = new BronzeOrb(-600.0f, 200.0f, 0);
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(this.orbBoi, true, 0));
    }
    @Override
    public void onVictory() {
        this.orbBoi = null;
    }
    @Override
    public void onMonsterDeath(final AbstractMonster m) {
        if (m.currentHealth <= 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead() && this.orbBoi != null && !this.orbBoi.isDeadOrEscaped()) {
        	for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
        		if (monster != null && !monster.isDeadOrEscaped() && !monster.hasPower(MinionPower.POWER_ID)) {
        			return;
        		}
        	}
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new SuicideAction(this.orbBoi));
        }
    }
    @Override
    public AbstractRelic makeCopy() {
        return new BronzeCore();
    }
    
    @SpirePatch(cls = "com.megacrit.cardcrawl.powers.StasisPower", method = SpirePatch.CONSTRUCTOR)
    public static class StasisPatch {
    	public static void Postfix(StasisPower power, final AbstractCreature owner, final AbstractCard card) {
    		if(AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(ID)) {
    			power.ID = power.ID + "-" + card.uuid.toString();
    		}
    	}
    }
}
