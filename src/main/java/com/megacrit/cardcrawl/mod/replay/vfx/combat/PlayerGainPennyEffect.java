package com.megacrit.cardcrawl.mod.replay.vfx.combat;

import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.GainGoldTextEffect;
import com.megacrit.cardcrawl.vfx.ShineLinesEffect;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.core.*;
import java.util.*;
import com.badlogic.gdx.graphics.g2d.*;

public class PlayerGainPennyEffect extends AbstractGameEffect
{
    private static final float GRAVITY;
    private static final float START_VY;
    private static final float START_VY_JITTER;
    private static final float START_VX;
    private static final float START_VX_JITTER;
    private static final float TARGET_JITTER;
    private float rotationSpeed;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float targetX;
    private float targetY;
    private TextureAtlas.AtlasRegion img;
    private float alpha;
    private float suctionTimer;
    private float staggerTimer;
    private boolean showGainEffect;
    private AbstractCreature owner;
    
    public PlayerGainPennyEffect(final AbstractCreature owner, final float x, final float y, final float targetX, final float targetY, final boolean showGainEffect) {
        this.alpha = 0.0f;
        this.suctionTimer = 0.7f;
        if (MathUtils.randomBoolean()) {
            this.img = ImageMaster.COPPER_COIN_1;
        }
        else {
            this.img = ImageMaster.COPPER_COIN_2;
        }
        this.x = x - this.img.packedWidth / 2.0f;
        this.y = y - this.img.packedHeight / 2.0f;
        this.targetX = targetX + MathUtils.random(-PlayerGainPennyEffect.TARGET_JITTER, PlayerGainPennyEffect.TARGET_JITTER);
        this.targetY = targetY + MathUtils.random(-PlayerGainPennyEffect.TARGET_JITTER, PlayerGainPennyEffect.TARGET_JITTER * 2.0f);
        this.showGainEffect = showGainEffect;
        this.owner = owner;
        this.staggerTimer = MathUtils.random(0.0f, 0.5f);
        this.vX = MathUtils.random(PlayerGainPennyEffect.START_VX - 50.0f * Settings.scale, PlayerGainPennyEffect.START_VX_JITTER);
        this.rotationSpeed = MathUtils.random(500.0f, 2000.0f);
        if (MathUtils.randomBoolean()) {
            this.vX = -this.vX;
            this.rotationSpeed = -this.rotationSpeed;
        }
        this.vY = MathUtils.random(PlayerGainPennyEffect.START_VY, PlayerGainPennyEffect.START_VY_JITTER);
        this.scale = Settings.scale;
        this.color = new Color(1.0f, 1.0f, 1.0f, 0.0f);
    }
    
    public PlayerGainPennyEffect(final float x, final float y) {
        this(AbstractDungeon.player, x, y, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true);
    }
    
    @Override
    public void update() {
        if (this.staggerTimer > 0.0f) {
            this.staggerTimer -= Gdx.graphics.getDeltaTime();
            return;
        }
        if (this.alpha != 1.0f) {
            this.alpha += Gdx.graphics.getDeltaTime() * 2.0f;
            if (this.alpha > 1.0f) {
                this.alpha = 1.0f;
            }
            this.color.a = this.alpha;
        }
        this.rotation += Gdx.graphics.getDeltaTime() * this.rotationSpeed;
        this.x += Gdx.graphics.getDeltaTime() * this.vX;
        this.y += Gdx.graphics.getDeltaTime() * this.vY;
        this.vY -= Gdx.graphics.getDeltaTime() * PlayerGainPennyEffect.GRAVITY;
        if (this.suctionTimer > 0.0f) {
            this.suctionTimer -= Gdx.graphics.getDeltaTime();
        }
        else {
            this.vY = MathUtils.lerp(this.vY, 0.0f, Gdx.graphics.getDeltaTime() * 5.0f);
            this.vX = MathUtils.lerp(this.vX, 0.0f, Gdx.graphics.getDeltaTime() * 5.0f);
            this.x = MathUtils.lerp(this.x, this.targetX, Gdx.graphics.getDeltaTime() * 4.0f);
            this.y = MathUtils.lerp(this.y, this.targetY, Gdx.graphics.getDeltaTime() * 4.0f);
            if (Math.abs(this.x - this.targetX) < 20.0f) {
                this.isDone = true;
                if (MathUtils.randomBoolean()) {
                    CardCrawlGame.sound.play("GOLD_GAIN", 0.1f);
                }
                this.owner.gainGold(1);
                AbstractDungeon.effectsQueue.add(new ShineLinesEffect(this.x, this.y));
                boolean textEffectFound = false;
                for (final AbstractGameEffect e : AbstractDungeon.effectList) {
                    if (e instanceof GainGoldTextEffect && ((GainGoldTextEffect)e).ping(1)) {
                        textEffectFound = true;
                        break;
                    }
                }
                if (!textEffectFound) {
                    for (final AbstractGameEffect e : AbstractDungeon.effectsQueue) {
                        if (e instanceof GainGoldTextEffect && ((GainGoldTextEffect)e).ping(1)) {
                            textEffectFound = true;
                        }
                    }
                }
                if (!textEffectFound && this.showGainEffect) {
                    AbstractDungeon.effectsQueue.add(new GainGoldTextEffect(1));
                }
            }
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        if (this.staggerTimer > 0.0f) {
            return;
        }
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0f, this.img.packedHeight / 2.0f, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale, this.rotation);
    }
    
    static {
        GRAVITY = 2000.0f * Settings.scale;
        START_VY = 800.0f * Settings.scale;
        START_VY_JITTER = 400.0f * Settings.scale;
        START_VX = 200.0f * Settings.scale;
        START_VX_JITTER = 300.0f * Settings.scale;
        TARGET_JITTER = 50.0f * Settings.scale;
    }
}
