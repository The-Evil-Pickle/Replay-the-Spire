package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import java.util.*;

public class PainkillerHerb extends AbstractRelic
{
    public static final String ID = "Painkiller Herb";
    private static final int HP_AMT = 20;
    private static final int HEAL_AMT = 6;
    
    public PainkillerHerb() {
        super("Painkiller Herb", "betaRelic.png", RelicTier.BOSS, LandingSound.FLAT);
    }
    /*
    @Override
    public String getUpdatedDescription() {
		String desc = this.DESCRIPTIONS[2];
		
        return this.DESCRIPTIONS[0];// + PainkillerHerb.HEAL_AMT + this.DESCRIPTIONS[2];
    }
    */
	
    @Override
    public String getUpdatedDescription() {
        if (AbstractDungeon.player != null) {
            return this.setDescription(AbstractDungeon.player.chosenClass);
        }
        return this.setDescription(null);
    }
    
    private String setDescription(final AbstractPlayer.PlayerClass c) {
        if (c == null) {
            return this.DESCRIPTIONS[2] + this.DESCRIPTIONS[0] + PainkillerHerb.HP_AMT + this.DESCRIPTIONS[1];
        }
        switch (c) {
            case IRONCLAD: {
                return this.DESCRIPTIONS[2] + this.DESCRIPTIONS[0] + PainkillerHerb.HP_AMT + this.DESCRIPTIONS[1];
            }
            case THE_SILENT: {
                return this.DESCRIPTIONS[3] + this.DESCRIPTIONS[0] + PainkillerHerb.HP_AMT + this.DESCRIPTIONS[1];
            }
            case CROWBOT: {
                return this.DESCRIPTIONS[4] + this.DESCRIPTIONS[0] + PainkillerHerb.HP_AMT + this.DESCRIPTIONS[1];
            }
            default: {
                return this.DESCRIPTIONS[2] + this.DESCRIPTIONS[0] + PainkillerHerb.HP_AMT + this.DESCRIPTIONS[1];
            }
        }
    }
	
    @Override
    public void atTurnStart() {
        if (this.counter == -1) {
            this.counter += 2;
        }
        else {
            ++this.counter;
        }
        if (this.counter == 2) {
            this.counter = 0;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
    }
	
    @Override
    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(PainkillerHerb.HP_AMT, true);
		this.counter = 0;
        //final EnergyManager energy = AbstractDungeon.player.energy;
        //++energy.energyMaster;
        //AbstractDungeon.getCurrRoom().addGoldToRewards(30);
    }
    /*
    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        --energy.energyMaster;
    }
	*/
    /*
    @Override
    public void onEnterRoom(final AbstractRoom room) {
        if (room instanceof ShopRoom) {
            this.flash();
            AbstractDungeon.player.heal(PainkillerHerb.HEAL_AMT);
        }
    }
	*/
    @Override
    public AbstractRelic makeCopy() {
        return new PainkillerHerb();
    }
}
