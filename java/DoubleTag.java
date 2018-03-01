package com.megacrit.cardcrawl.shop;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.helpers.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.math.*;

public class DoubleTag
{
    public AbstractCard card;
    private Texture img;
    private static final float W;
    
    public DoubleTag(final AbstractCard c) {
        this.card = c;
        this.img = ImageMaster.loadImage("images/npcs/2for1Tag.png");
    }
    
    public void render(final SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.img, this.card.current_x + 30.0f * Settings.scale + (this.card.drawScale - 0.75f) * 60.0f * Settings.scale, this.card.current_y + 70.0f * Settings.scale + (this.card.drawScale - 0.75f) * 90.0f * Settings.scale, DoubleTag.W * this.card.drawScale, DoubleTag.W * this.card.drawScale);
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, (MathUtils.cosDeg((float)(System.currentTimeMillis() / 5L % 360L)) + 1.25f) / 3.0f));
        sb.draw(this.img, this.card.current_x + 30.0f * Settings.scale + (this.card.drawScale - 0.75f) * 60.0f * Settings.scale, this.card.current_y + 70.0f * Settings.scale + (this.card.drawScale - 0.75f) * 90.0f * Settings.scale, DoubleTag.W * this.card.drawScale, DoubleTag.W * this.card.drawScale);
        sb.setBlendFunction(770, 771);
    }
    
    static {
        W = 128.0f * Settings.scale;
    }
}
