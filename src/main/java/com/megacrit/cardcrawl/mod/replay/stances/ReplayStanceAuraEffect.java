package com.megacrit.cardcrawl.mod.replay.stances;

import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;

public class ReplayStanceAuraEffect extends StanceAuraEffect
{
    public ReplayStanceAuraEffect(final String stanceId) {
    	super(stanceId);
        this.color = new Color(MathUtils.random(0.1f, 0.2f), MathUtils.random(0.6f, 0.7f), MathUtils.random(0.0f, 0.1f), 0.0f);
    }
}
