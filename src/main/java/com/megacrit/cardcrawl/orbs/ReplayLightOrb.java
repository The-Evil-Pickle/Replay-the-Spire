package com.megacrit.cardcrawl.orbs;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.helpers.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.RegenPower;

import java.util.*;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;

public class ReplayLightOrb extends AbstractOrb
{
    public static final String ORB_ID = "ReplayLightOrb";
    private static final OrbStrings orbString;
    public static final String[] DESC;
    private float vfxTimer;
    private float vfxIntervalMin;
    private float vfxIntervalMax;
    private static final float PI_DIV_16 = 0.19634955f;
    private static final float ORB_WAVY_DIST = 0.05f;
    private static final float PI_4 = 12.566371f;
    private static final float ORB_BORDER_SCALE = 1.2f;
    
    public ReplayLightOrb() {
        this.vfxTimer = 1.0f;
        this.vfxIntervalMin = 0.15f;
        this.vfxIntervalMax = 0.8f;
        this.ID = ORB_ID;
        this.img = ImageMaster.ORB_LIGHTNING;
        this.name = ReplayLightOrb.orbString.NAME;
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
        this.description = ReplayLightOrb.DESC[0] + this.passiveAmount + ReplayLightOrb.DESC[1] + this.evokeAmount + ReplayLightOrb.DESC[2];
    }
    
    @Override
    public void onEvoke() {
    	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, this.evokeAmount), this.evokeAmount));
    }
    
    @Override
    public void onEndOfTurn() {
    	AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.passiveAmount));
    }
    
    @Override
    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("ORB_PLASMA_EVOKE", 0.1f);
        AbstractDungeon.effectsQueue.add(new LightningOrbActivateEffect(this.cX, this.cY));
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
            this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, this.c.a / 2.0f));
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, this.c.a / 2.0f));
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale + MathUtils.sin(this.angle / 12.566371f) * 0.05f + 0.19634955f, this.scale * 1.2f, this.angle, 0, 0, 96, 96, false, false);
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale * 1.2f, this.scale + MathUtils.sin(this.angle / 12.566371f) * 0.05f + 0.19634955f, -this.angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, this.angle / 12.0f, 0, 0, 96, 96, false, false);
        this.renderText(sb);
        this.hb.render(sb);
    }
    
    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_PLASMA_CHANNEL", 0.1f);
    }
    
    @Override
    public AbstractOrb makeCopy() {
        return new ReplayLightOrb();
    }
    
    static {
        orbString = CardCrawlGame.languagePack.getOrbString("Light");
        DESC = ReplayLightOrb.orbString.DESCRIPTION;
    }
}
