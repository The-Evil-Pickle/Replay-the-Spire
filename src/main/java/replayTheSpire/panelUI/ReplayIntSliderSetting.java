package replayTheSpire.panelUI;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.random.Random;

import basemod.IUIElement;
import basemod.ModSlider;
import replayTheSpire.ReplayTheSpireMod;

public class ReplayIntSliderSetting extends ReplayRelicSetting {
	public AdvModSlider slider;
	public int value;
	public float multi;
	public int min;
	public String suf;

	public ReplayIntSliderSetting(String id, String name, int defaultProperty, final int multi) {
		this(id, name, defaultProperty, 0, multi);
	}
	public ReplayIntSliderSetting(String id, String name, int defaultProperty, final int min, final int multi) {
		this(id, name, defaultProperty, min, multi, "");
	}
	public ReplayIntSliderSetting(String id, String name, int defaultProperty, final int min, final int multi, final String suf) {
		super(id, name, Integer.toString(defaultProperty));
		this.multi = multi;
		this.min = min;
		this.suf = suf;
		this.value = defaultProperty;
	}

	@Override
	public void LoadFromData(SpireConfig config) {
		this.value = config.getInt(this.settingsId);
		if (config.getBool(this.settingsId + ReplayTheSpireMod.DEFAULTSETTINGSUFFIX)) {
			this.value = Integer.parseInt(defaultProperty);
		}
		if (this.slider != null) {
			this.slider.setValue(this.value);
		}
	}

	@Override
	public void SaveToData(SpireConfig config) {
		config.setInt(this.settingsId, this.value);
		config.setBool(this.settingsId + ReplayTheSpireMod.DEFAULTSETTINGSUFFIX, this.value == Integer.parseInt(defaultProperty));
	}
	@Override
	public void LoadFromData(Prefs config) {
		this.value = config.getInteger(this.settingsId, Integer.parseInt(this.defaultProperty));
		if (this.slider != null) {
			this.slider.setValue(this.value);
		}
	}
	@Override
	public void SaveToData(Prefs config) {
		config.putInteger(this.settingsId, this.value);
	}

	@Override
	public ArrayList<IUIElement> GenerateElements(float x, float y) {
		this.slider = new AdvModSlider(this.name, x + 125.0f, y, this.min, this.multi, this.suf, ReplayTheSpireMod.settingsPanel, (me) -> {
			this.value = Math.round(me.value * me.multiplier + this.min);
			ReplayTheSpireMod.saveSettingsData();
		});
		this.slider.setValue(this.value);
		this.elements.add(this.slider);
		return this.elements;
	}
	
	public boolean testChance(Random rng) {
		return rng.random(100) < this.value;
	}
	@Override
	public void ResetToDefault() {
		this.value = Integer.parseInt(defaultProperty);
		if (this.slider != null) {
			this.slider.setValue(this.value);
		}
		ReplayTheSpireMod.saveSettingsData();
	}

}
