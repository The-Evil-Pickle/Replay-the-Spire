package com.megacrit.cardcrawl.mod.replay.vfx.combat;

import com.megacrit.cardcrawl.vfx.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;

public class ColorSmokeBlur extends AbstractGameEffect
{
    private float x;
    private float y;
    private float vY;
    private float aV;
    private float startDur;
    private float targetScale;
    private TextureAtlas.AtlasRegion img;
    
    public ColorSmokeBlur(final float x, final float y, Color color) {
        this.color = color;
        this.color.r = this.color.r * MathUtils.random(0.95f, 1.05f);
        this.color.g = this.color.g * MathUtils.random(0.95f, 1.05f);
        this.color.b = this.color.b * MathUtils.random(0.95f, 1.05f);
        this.color.a = 1.0f;
        if (MathUtils.randomBoolean()) {
            this.img = ImageMaster.EXHAUST_L;
            this.duration = MathUtils.random(2.0f, 2.5f);
            this.targetScale = MathUtils.random(0.8f, 2.2f);
        }
        else {
            this.img = ImageMaster.EXHAUST_S;
            this.duration = MathUtils.random(2.0f, 2.5f);
            this.targetScale = MathUtils.random(0.8f, 1.2f);
        }
        this.startDur = this.duration;
        this.x = x + MathUtils.random(-180.0f * Settings.scale, 150.0f * Settings.scale) - this.img.packedWidth / 2.0f;
        this.y = y + MathUtils.random(-240.0f * Settings.scale, 150.0f * Settings.scale) - this.img.packedHeight / 2.0f;
        this.scale = 0.01f;
        this.rotation = MathUtils.random(360.0f);
        this.aV = MathUtils.random(-250.0f, 250.0f);
        this.vY = MathUtils.random(1.0f * Settings.scale, 5.0f * Settings.scale);
    }
    
    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0f) {
            this.isDone = true;
        }
        this.x += MathUtils.random(-2.0f * Settings.scale, 2.0f * Settings.scale);
        this.x += this.vY;
        this.y += MathUtils.random(-2.0f * Settings.scale, 2.0f * Settings.scale);
        this.y += this.vY;
        this.rotation += this.aV * Gdx.graphics.getDeltaTime();
        this.scale = Interpolation.exp10Out.apply(0.01f, this.targetScale, 1.0f - this.duration / this.startDur);
        if (this.duration < 0.33f) {
            this.color.a = this.duration * 3.0f;
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0f, this.img.packedHeight / 2.0f, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale, this.rotation);
    }
    
    @Override
    public void dispose() {
    }
}
