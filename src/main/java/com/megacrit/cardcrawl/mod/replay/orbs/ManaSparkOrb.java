package com.megacrit.cardcrawl.mod.replay.orbs;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import ThMod.powers.Marisa.ChargeUpPower;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.core.*;

public class ManaSparkOrb extends AbstractOrb
{
    public static final String ORB_ID = "ManaSpark";
    //private static final OrbStrings orbString;
    public static final String[] DESC = {"#yPassive: At the start of turn, gain #b", " #yCharge-Up. NL #yEvoke: Gain [e] and #b", " Charge-Up."};
    private static final float ORB_BORDER_SCALE = 1.2f;
    private float vfxTimer;
    private static final float VFX_INTERVAL_TIME = 0.25f;
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;
	public static Texture ORB_TEXTURE = ImageMaster.loadImage("images/orbs/replay/spark.png");
    
    public ManaSparkOrb() {
        this.vfxTimer = 0.5f;
        this.ID = ORB_ID;
        this.img = ManaSparkOrb.ORB_TEXTURE;
        this.name = "Mana-Spark";//HellFireOrb.orbString.NAME;
        this.baseEvokeAmount = 5;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 3;
        this.passiveAmount = this.basePassiveAmount;
        this.updateDescription();
        this.angle = MathUtils.random(360.0f);
        this.channelAnimTimer = 0.5f;
    }
    
    @Override
    public void updateDescription() {
        this.applyFocus();
        this.description = ManaSparkOrb.DESC[0] + this.passiveAmount + ManaSparkOrb.DESC[1] + this.evokeAmount + ManaSparkOrb.DESC[2];
    }
    
    @Override
    public void onEvoke() {
        AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ChargeUpPower(AbstractDungeon.player, this.evokeAmount), this.evokeAmount));
    }
    
    @Override
    public void onStartOfTurn() {
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ChargeUpPower(AbstractDungeon.player, this.passiveAmount), this.passiveAmount));
    }
    
    @Override
    public void updateAnimation() {
    	super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 180.0f;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
            if (MathUtils.randomBoolean()) {
                AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
            }
            this.vfxTimer = MathUtils.random(0.15f, 0.75f);
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, this.c.a / 2.0f));
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale + MathUtils.sin(this.angle / 12.566371f) * 0.04f * Settings.scale, this.scale, this.angle, 0, 0, 96, 96, false, false);
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, this.c.a / 2.0f));
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale + MathUtils.sin(this.angle / 12.566371f) * 0.04f * Settings.scale, -this.angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        this.renderText(sb);
        this.hb.render(sb);
    }
    
    @Override
    public AbstractOrb makeCopy() {
        return new ManaSparkOrb();
    }
    
    @Override
    public void triggerEvokeAnimation() {
    	CardCrawlGame.sound.play("ORB_PLASMA_CHANNEL", 0.1f);
    }
	
    @Override
    public void playChannelSFX() {
    	CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1f);
    }
	/*
    static {
        orbString = CardCrawlGame.languagePack.getOrbString("Hellfire");
        DESC = HellFireOrb.orbString.DESCRIPTION;
    }
	*/
}
