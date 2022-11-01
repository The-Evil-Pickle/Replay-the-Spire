package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.curses.AbeCurse;
import com.megacrit.cardcrawl.mod.replay.cards.status.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.ui.*;
import com.megacrit.cardcrawl.relics.*;

import java.util.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import java.io.*;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import basemod.*;
import replayTheSpire.ReplayTheSpireMod;

public class CursedDEight extends AbstractRelic
{
    public static final String ID = "Replay:Cursed D8";
    
    public CursedDEight() {
        super(ID, "betaRelic.png", RelicTier.BOSS, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
    	if (ReplayTheSpireMod.foundmod_downfall)
    		return this.DESCRIPTIONS[0].replace("Echo", "gremlin:Echo");
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
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WhispersOfEvilPower(AbstractDungeon.player, 1), 1));
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CursedDEight();
    }
}
