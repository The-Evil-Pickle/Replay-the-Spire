package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.*;

import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.panelUI.ReplayIntSliderSetting;
import theAct.cards.fungalobungalofunguyfuntimes.SS_Clouding;
import theAct.cards.fungalobungalofunguyfuntimes.SS_Leeching;
import theAct.cards.fungalobungalofunguyfuntimes.SS_Toxin;

import com.megacrit.cardcrawl.core.*;

public class FungalCore extends ReplayAbstractRelic
{
    public static final String ID = "Replay:Fungal Core";
	public static final ReplayIntSliderSetting SPORES_AMT = new ReplayIntSliderSetting("fungalcore_spores", "Spore Count", 5, 2, 10);
    
    public FungalCore() {
        super(ID, "fungalCore.png", RelicTier.BOSS, LandingSound.FLAT);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(this.DESCRIPTIONS[2], this.DESCRIPTIONS[3]));
        this.initializeTips();
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + SPORES_AMT.value + this.DESCRIPTIONS[1];
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
    public void atBattleStartPreDraw() {
        this.flash();
        ArrayList<AbstractCard> sporesList = new ArrayList<AbstractCard>();
        sporesList.add(new SS_Clouding());
        sporesList.add(new SS_Leeching());
        sporesList.add(new SS_Toxin());
        for (int i=0; i<SPORES_AMT.value; i++) {
        	AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(sporesList.get(AbstractDungeon.miscRng.random(sporesList.size()-1)).makeCopy(), 1, true, true));
        }
	}
    
    @Override
    public AbstractRelic makeCopy() {
        return new FungalCore();
    }
}
