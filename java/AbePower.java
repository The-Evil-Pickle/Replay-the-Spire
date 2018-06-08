package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.badlogic.gdx.graphics.*;
import basemod.*;
import ReplayTheSpireMod.*;

public class AbePower extends AbstractPower
{
    public static final String POWER_ID = "AbePower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
	public boolean atActiveDepth;
	
    public AbePower(final AbstractCreature owner) {
		this(owner, 3);
	}
    
    public AbePower(final AbstractCreature owner, final int amount) {
        this.name = AbePower.NAME;
        this.ID = "AbePower";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("deadweight");
        this.loadRegion("inactivedw");
		this.atActiveDepth = false;
        this.type = PowerType.DEBUFF;
        this.priority = 99;
    }
    
	@Override
    protected void loadRegion(final String fileName) {
        this.region48 = ReplayTheSpireMod.powerAtlas.findRegion("48/" + fileName);
		this.region128 = ReplayTheSpireMod.powerAtlas.findRegion("128/" + fileName);
    }
	
    @Override
    public void updateDescription() {
        this.description = AbePower.DESCRIPTIONS[0] + this.amount + AbePower.DESCRIPTIONS[1];
    }
    
    @Override
    public void onSpecificTrigger() {
		this.updateValue();
        if (this.atActiveDepth) {
			this.flash();
            AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(this.owner, AbePower.NAME));
        }
    }
	
	public void updateValue() {
		if (AbstractDungeon.player == null) {
			this.updateValue(0);
		}
		if (AbstractDungeon.player.hasPower("PondfishDrowning")) {
			this.updateValue(AbstractDungeon.player.getPower("PondfishDrowning").amount);
		}
		this.updateValue(0);
	}
	
	public void updateValue(int val) {
		if (val >= this.amount) {
			if (!this.atActiveDepth) {				
				this.atActiveDepth = true;
				this.loadRegion("deadweight");
				this.flash();
			}
		} else {
			if (this.atActiveDepth) {	
				this.atActiveDepth = false;
				this.loadRegion("inactivedw");
				this.flash();
			}
		}
	}
	/*
    @Override
    public float atDamageGive(final float damage, final DamageInfo.DamageType type) {
        if (!this.atActiveDepth || type != DamageInfo.DamageType.NORMAL) {
            return damage;
        }
        return 0f;
    }
    */
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("AbePower");
        NAME = AbePower.powerStrings.NAME;
        DESCRIPTIONS = AbePower.powerStrings.DESCRIPTIONS;
    }
}
