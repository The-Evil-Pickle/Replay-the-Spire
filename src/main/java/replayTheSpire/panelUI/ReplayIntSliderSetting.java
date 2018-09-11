package replayTheSpire.panelUI;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;

import basemod.IUIElement;
import basemod.ModSlider;
import replayTheSpire.ReplayTheSpireMod;

public class ReplayIntSliderSetting extends ReplayRelicSetting {
	public ModSlider slider;
	public int value;
	public float multi;
	
	public ReplayIntSliderSetting(String id, String name, int defaultProperty, final int multi) {
		super(id, name, Integer.toString(defaultProperty));
		this.multi = multi;
	}

	@Override
	public void LoadFromData(SpireConfig config) {
		this.value = config.getInt(this.settingsId);
		if (this.slider != null) {
			this.slider.value = ((float)this.value) / this.multi;
		}
	}

	@Override
	public void SaveToData(SpireConfig config) {
		config.setInt(this.settingsId, this.value);
	}

	@Override
	public ArrayList<IUIElement> GenerateElements(float x, float y) {
		this.slider = new ModSlider(this.name, x, y, this.multi, "", ReplayTheSpireMod.settingsPanel, (me) -> {
			this.value = Math.round(me.value * me.multiplier);
			ReplayTheSpireMod.saveSettingsData();
		});
		this.slider.setValue(((float)this.value) / this.multi);
		this.elements.add(this.slider);
		return this.elements;
	}

}
