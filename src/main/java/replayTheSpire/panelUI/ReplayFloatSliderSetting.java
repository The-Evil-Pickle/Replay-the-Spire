package replayTheSpire.panelUI;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;

import basemod.IUIElement;
import basemod.ModSlider;
import replayTheSpire.ReplayTheSpireMod;

public class ReplayFloatSliderSetting extends ReplayRelicSetting {
	public ModSlider slider;
	public float value;
	public float multi;
	public String suf;
	
	public ReplayFloatSliderSetting(String id, String name, float defaultProperty) {
		this(id, name, defaultProperty, true);
	}
	public ReplayFloatSliderSetting(String id, String name, float defaultProperty, boolean isPercentage) {
		this(id, name, defaultProperty, (isPercentage) ? 100.0f: 1.0f, (isPercentage) ? "%": "");
	}
	public ReplayFloatSliderSetting(String id, String name, float defaultProperty, final float multi, final String suf) {
		super(id, name, Float.toString(defaultProperty));
	}

	@Override
	public void LoadFromData(SpireConfig config) {
		this.value = config.getFloat(this.settingsId);
		if (this.slider != null) {
			this.slider.value = this.value;
		}
	}

	@Override
	public void SaveToData(SpireConfig config) {
		config.setFloat(this.settingsId, this.value);
	}

	@Override
	public ArrayList<IUIElement> GenerateElements(float x, float y) {
		this.slider = new ModSlider(this.name, x, y, this.multi, this.suf, ReplayTheSpireMod.settingsPanel, (me) -> {
			this.value = me.value;
			ReplayTheSpireMod.saveSettingsData();
		});
		this.slider.setValue(this.value);
		this.elements.add(this.slider);
		return this.elements;
	}

}
