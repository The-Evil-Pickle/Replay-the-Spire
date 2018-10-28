package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThieveryPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BloodyIdol;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.*;

import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.ReplayTheSpireMod;
import replayTheSpire.panelUI.ReplayIntSliderSetting;
import replayTheSpire.panelUI.ReplayRelicSetting;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.*;

public class Bandana extends ReplayAbstractRelic
{
    public static final String ID = "Bandana";
	public static final ReplayIntSliderSetting SETTING_DURATION = new ReplayIntSliderSetting("Bandana_Duration", "Effect Duration", 3, 10);
	public static final ReplayIntSliderSetting SETTING_POWER = new ReplayIntSliderSetting("Bandana_Power", "Effect Strength", 2, 5);
	public ArrayList<ReplayRelicSetting> BuildRelicSettings() {
    	ArrayList<ReplayRelicSetting> settings = new ArrayList<ReplayRelicSetting>();
    	settings.add(SETTING_DURATION);
    	settings.add(SETTING_POWER);
		return settings;
	}
	private boolean firstTurn = false;
    private String synstr;
    public Bandana() {
        super(ID, "bandana.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
    	this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip("Thievery", "Each time you deal unblocked #yAttack damage, gain gold."));
        this.tips.add(new PowerTip("Synergy", this.DESCRIPTIONS[3] + this.synstr + this.DESCRIPTIONS[4]));
        this.initializeTips();
    }
    
    public String getUpdatedDescription() {
        this.synstr = FontHelper.colorString((new BloodyIdol()).name, "y");
        return this.DESCRIPTIONS[0] + SETTING_POWER.value + DESCRIPTIONS[1] + SETTING_DURATION.value + DESCRIPTIONS[2] + this.synstr + ".";
    }
    
    public void atBattleStart() {
        this.flash();
		this.pulse = true;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThieveryPower(AbstractDungeon.player, SETTING_POWER.value), SETTING_POWER.value));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
    
    public void onAttack(final DamageInfo damageInfo, final int n, final AbstractCreature abstractCreature) {
		if (this.counter > 0 && damageInfo.type == DamageInfo.DamageType.NORMAL) {
			final AbstractPlayer player = AbstractDungeon.player;
			//player.gold += Bandana.POWER;
			CardCrawlGame.sound.play("GOLD_JINGLE");
			for (int i = 0; i < SETTING_POWER.value; ++i) {
				AbstractDungeon.effectList.add(new GainPennyEffect(abstractCreature.hb.cX, abstractCreature.hb.cY));
			}
        }
    }
    
	@Override
    public void atPreBattle() {
        this.firstTurn = true;
		this.counter = SETTING_DURATION.value;
    }
    
    @Override
    public void atTurnStart() {
        if (this.counter > 0 && !this.firstTurn) {
            this.counter--;
			if (this.counter <= 0) {
				AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, "Thievery"));
				this.pulse = false;
				this.counter = -1;
			}
        }
        this.firstTurn = false;
    }
	
    @Override
    public void onVictory() {
        this.pulse = false;
		this.counter = -1;
    }
	
    public AbstractRelic makeCopy() {
        return new Bandana();
    }
}