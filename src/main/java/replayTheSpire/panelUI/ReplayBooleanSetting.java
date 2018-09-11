package replayTheSpire.panelUI;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.helpers.FontHelper;

import basemod.IUIElement;
import basemod.ModLabel;
import basemod.ModToggleButton;
import replayTheSpire.ReplayTheSpireMod;

public class ReplayBooleanSetting extends ReplayRelicSetting {

	boolean value;
	ModToggleButton button;
	
	public ReplayBooleanSetting(String id, String name, Boolean defaultProperty) {
		super(id, name, Boolean.toString(defaultProperty));
		this.value = defaultProperty;
	}

	@Override
	public void LoadFromData(SpireConfig config) {
		this.value = config.getBool(this.settingsId);
		if (this.button != null) {
			this.button.enabled = this.value;
		}
	}

	@Override
	public void SaveToData(SpireConfig config) {
		config.setBool(this.settingsId, this.value);
	}

	@Override
	public ArrayList<IUIElement> GenerateElements(float x, float y) {
		this.elements.add(new ModLabel(this.name, x + 40.0f, y + 8.0f, Color.WHITE, FontHelper.buttonLabelFont, ReplayTheSpireMod.settingsPanel, (me) -> {}));
		this.button = new ModToggleButton(x, y, this.value, false, ReplayTheSpireMod.settingsPanel, (me) -> {
			this.value = me.enabled;
			ReplayTheSpireMod.saveSettingsData();
		});
		this.button.enabled = this.value;
		this.elements.add(this.button);
		return this.elements;
	}
	
}
