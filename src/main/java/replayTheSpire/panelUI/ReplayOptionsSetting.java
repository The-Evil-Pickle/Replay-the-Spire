package replayTheSpire.panelUI;

import java.util.*;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Prefs;

import basemod.IUIElement;
import basemod.ModLabel;
import basemod.ModToggleButton;
import replayTheSpire.ReplayTheSpireMod;

public class ReplayOptionsSetting extends ReplayRelicSetting {

	public int value;
	public List<String> optionStrings;
	ArrayList<ModToggleButton> buttons;
	
	public ReplayOptionsSetting(String id, String name, int defaultProperty, List<String> options) {
		super(id, name, Integer.toString(defaultProperty));
		this.value = defaultProperty;
		this.optionStrings = options;
		this.elementHeight += 50.0f * options.size();
	}

	@Override
	public void LoadFromData(SpireConfig config) {
		this.value = config.getInt(this.settingsId);
		if (config.getBool(this.settingsId + ReplayTheSpireMod.DEFAULTSETTINGSUFFIX)) {
			this.value = Integer.parseInt(defaultProperty);
		}
		if (this.buttons != null) {
			for (int i=0; i < this.buttons.size(); i++) {
				this.buttons.get(i).enabled = (i == this.value);
			}
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
		if (this.buttons != null) {
			for (int i=0; i < this.buttons.size(); i++) {
				this.buttons.get(i).enabled = (i == this.value);
			}
		}
	}

	@Override
	public void SaveToData(Prefs config) {
		config.putInteger(this.settingsId, this.value);
	}
	@Override
	public ArrayList<IUIElement> GenerateElements(float x, float y) {
		this.buttons = new ArrayList<ModToggleButton>();
		this.elements.add(new ModLabel(this.name, x, y, Color.WHITE, FontHelper.buttonLabelFont, ReplayTheSpireMod.settingsPanel, (me) -> {}));
		for (int i=0; i < this.optionStrings.size(); i++) {
			y -= 50.0f;
			this.elements.add(new ModLabel(this.optionStrings.get(i), x + 40.0f, y + 8.0f, Color.WHITE, FontHelper.buttonLabelFont, ReplayTheSpireMod.settingsPanel, (me) -> {}));
			ModToggleButton button = new ModToggleButton(x, y, this.value == i, false, ReplayTheSpireMod.settingsPanel, (me) -> {
				boolean nonEnabled = true;
				for (ModToggleButton b : this.buttons) {
					if (b.enabled) {
						nonEnabled = false;
					}
				}
				if (nonEnabled) {
					me.enabled = true;
				} else {
					for (int j=0; j < this.buttons.size(); j++) {
						ModToggleButton b = this.buttons.get(j);
						if (b == me) {
							this.value = j;
						} else {
							b.enabled = false;
						}
					}
				}
				ReplayTheSpireMod.saveSettingsData();
			});
			this.buttons.add(button);
			this.elements.add(button);
		}
		return this.elements;
	}

	@Override
	public void ResetToDefault() {
		this.value = Integer.parseInt(defaultProperty);
		if (this.buttons != null) {
			for (int i=0; i < this.buttons.size(); i++) {
				this.buttons.get(i).enabled = (i == this.value);
			}
		}
		ReplayTheSpireMod.saveSettingsData();
	}

	
}