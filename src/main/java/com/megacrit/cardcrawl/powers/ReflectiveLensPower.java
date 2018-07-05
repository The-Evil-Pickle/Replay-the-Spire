package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.badlogic.gdx.graphics.*;
import basemod.*;
import replayTheSpire.*;

public class ReflectiveLensPower extends AbstractPower
{
    public static final String POWER_ID = "Reflective Lens";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
	
    public ReflectiveLensPower(final AbstractCreature owner) {
		this(owner, 1);
	}
    
    public ReflectiveLensPower(final AbstractCreature owner, final int amount) {
        this.name = ReflectiveLensPower.NAME;
        this.ID = "Reflective Lens";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("lens");
    }
    
	@Override
    protected void loadRegion(final String fileName) {
        this.region48 = ReplayTheSpireMod.powerAtlas.findRegion("48/" + fileName);
		this.region128 = ReplayTheSpireMod.powerAtlas.findRegion("128/" + fileName);
    }
	
    @Override
    public void updateDescription() {
        this.description = ReflectiveLensPower.DESCRIPTIONS[0] + this.amount + ReflectiveLensPower.DESCRIPTIONS[1];
    }
    /*
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
		//int glasscount = 0;
		float speedTime = 0.6f / AbstractDungeon.player.orbs.size();
		for (int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
			AbstractOrb o = AbstractDungeon.player.orbs.get(i);
			if (o != null && (o instanceof GlassOrb)) {
				OrbFlareEffect orbEffect = new OrbFlareEffect(o, OrbFlareEffect.OrbFlareColor.LIGHTNING);
				ReflectionHacks.setPrivate((Object)orbEffect, (Class)AbstractGameEffect.class, "color", (Object)(new Color(1.0f, 1.0f, 1.0f, 0.75f)));
				AbstractDungeon.actionManager.addToBottom(new VFXAction(orbEffect, speedTime));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ReflectionPower(AbstractDungeon.player, this.amount + o.passiveAmount), this.amount + o.passiveAmount));
				//glasscount += this.amount + o.passiveAmount;
			}
		}
    }
    */
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Reflective Lens");
        NAME = ReflectiveLensPower.powerStrings.NAME;
        DESCRIPTIONS = ReflectiveLensPower.powerStrings.DESCRIPTIONS;
    }
}
