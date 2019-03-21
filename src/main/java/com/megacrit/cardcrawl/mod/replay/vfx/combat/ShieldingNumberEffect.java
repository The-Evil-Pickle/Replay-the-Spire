package com.megacrit.cardcrawl.mod.replay.vfx.combat;

import com.megacrit.cardcrawl.vfx.combat.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.core.*;

public class ShieldingNumberEffect extends DamageNumberEffect
{
    private Color originalColor;
    
    public ShieldingNumberEffect(final AbstractCreature target, final float x, final float y, final int amt) {
        super(target, x, y, amt);
        this.color = Settings.BLUE_TEXT_COLOR.cpy();
        this.originalColor = this.color.cpy();
    }
    
    public void update() {
        super.update();
        this.color = this.originalColor.cpy();
    }
}