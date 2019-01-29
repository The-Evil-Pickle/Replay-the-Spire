package com.megacrit.cardcrawl.mod.replay.vfx.combat;

import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;

public class ColorSmokeBombEffect extends AbstractGameEffect
{
    private float x;
    private float y;
    private Color color;
    
    public ColorSmokeBombEffect(final float x, final float y, final Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.duration = 0.2f;
    }
    
    @Override
    public void update() {
        if (this.duration == 0.2f) {
            CardCrawlGame.sound.play("ATTACK_WHIFF_2");
            for (int i = 0; i < 90; ++i) {
                AbstractDungeon.effectsQueue.add(new ColorSmokeBlur(this.x, this.y, this.color));
            }
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0f) {
            CardCrawlGame.sound.play("APPEAR");
            this.isDone = true;
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
    }
    
    @Override
    public void dispose() {
    }
}
