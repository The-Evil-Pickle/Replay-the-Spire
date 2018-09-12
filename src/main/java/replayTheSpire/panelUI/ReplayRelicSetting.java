package replayTheSpire.panelUI;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.helpers.Prefs;

import basemod.IUIElement;

public abstract class ReplayRelicSetting {
	public String settingsId;
	public String name;
	public String defaultProperty;
	public ArrayList<IUIElement> elements;
	public float elementHeight;
	
	public ReplayRelicSetting(String id, String name, String defaultProperty) {
		this.settingsId = id;
		this.name = name;
		this.defaultProperty = defaultProperty;
		this.elements = new ArrayList<IUIElement>();
		this.elementHeight = 50.0f;
	}
	
	public abstract void LoadFromData(SpireConfig config);
	public abstract void LoadFromData(Prefs config);

	public abstract void SaveToData(SpireConfig config);
	public abstract void SaveToData(Prefs config);
	
	public abstract ArrayList<IUIElement> GenerateElements(float x, float y);
}
