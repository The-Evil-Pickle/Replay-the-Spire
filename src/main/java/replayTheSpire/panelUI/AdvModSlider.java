package replayTheSpire.panelUI;

import java.util.function.*;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.helpers.input.*;

import basemod.ModPanel;
import basemod.ModSlider;

import com.megacrit.cardcrawl.helpers.*;

public class AdvModSlider extends ModSlider
{
    public float min;
    public AdvModSlider(String lbl, float posX, float posY, float min, float max, String suf, ModPanel p, Consumer<ModSlider> changeAction) {
		super(lbl, posX, posY, max - min, suf, p, changeAction);
		this.min = min;
	}

	@Override
    public void render(final SpriteBatch sb) {
		float realVal = this.value;
		this.value = (this.value * this.multiplier + this.min) / this.multiplier;
		super.render(sb);
		this.value = realVal;
    }

    public void setValue(final int val) {
        super.setValue(((float)val - this.min) / (this.multiplier));
    }
    public void setValue(final float val) {
        super.setValue(val - (this.min / this.multiplier));
    }
}
