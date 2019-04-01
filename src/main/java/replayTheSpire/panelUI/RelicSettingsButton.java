package replayTheSpire.panelUI;

import basemod.*;
import replayTheSpire.ReplayTheSpireMod;

import com.megacrit.cardcrawl.helpers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.core.*;

public class RelicSettingsButton implements IUIElement
{
	public static final float DEFAULT_X = 450.0f;
	public static final float DEFAULT_Y = 625.0f;
	public static final float DEFAULT_W = 100.0f;
	public static final float DEFAULT_H = 100.0f;
	public static RelicSettingsButton activeButton;
	public Texture image;
    public Texture outline;
    public float x;
    public float y;
    public float w;
    public float h;
    private Hitbox hitbox;
    private IUIElement resetButton;
    public AbstractRelic relic;
    public List<IUIElement> elements;
    public List<ReplayRelicSetting> settings;
    public boolean isSelected;
    private Color rendColor;

    public RelicSettingsButton(final AbstractRelic relic, final List<IUIElement> elements) {
    	this(relic, 450.0f, 625.0f, 100.0f, 100.0f, elements);
    }
    public RelicSettingsButton(final AbstractRelic relic, final List<IUIElement> elements, final List<ReplayRelicSetting> settings) {
    	this(relic, elements);
    	this.settings = settings;
    }
    
    public RelicSettingsButton(final AbstractRelic relic, final float x, final float y, final float width, final float height, final List<IUIElement> elements) {
        this(relic.img, relic.outlineImg, x, y, width, height, elements);
        this.relic = relic;
        this.w *= 1.5f;
        this.h *= 1.5f;
        if (!UnlockTracker.isRelicSeen(relic.relicId)) {
        	this.rendColor = Color.BLACK;
		}
    }
    public RelicSettingsButton(final Texture image, final Texture outline, final float x, final float y, final float width, final float height, final List<IUIElement> elements, final List<ReplayRelicSetting> settings) {
    	this(image, outline, x, y, width, height, elements);
    	this.settings = settings;
    }
    public RelicSettingsButton(final Texture image, final Texture outline, final float x, final float y, final float width, final float height, final List<IUIElement> elements) {
    	this.relic = null;
        this.image = image;
        this.outline = outline;
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
        this.elements = elements;
        this.settings = new ArrayList<ReplayRelicSetting>();
        this.isSelected = false;
        this.hitbox = new Hitbox((float)this.x * Settings.scale, (float)this.y * Settings.scale, (float)this.w * Settings.scale, (float)this.h * Settings.scale);
        this.resetButton = new ModButton(800, 550, ReplayTheSpireMod.settingsPanel, (me) -> {
        	this.resetAll();
        });
        this.rendColor = Color.WHITE;
    }
    
    public void render(final SpriteBatch sb) {
    	if (this.outline != null) {
            sb.setColor(new Color(0.0f, 0.0f, 0.0f, 1.0f));
            sb.draw(this.outline, this.x * Settings.scale, this.y * Settings.scale, this.w * Settings.scale, this.h * Settings.scale);
        }
        sb.setColor(this.rendColor);
        sb.draw(this.image, this.x * Settings.scale, this.y * Settings.scale, this.w * Settings.scale, this.h * Settings.scale);
        this.hitbox.render(sb);
        if (this.isSelected) {
        	for (IUIElement element : this.elements) {
        		element.render(sb);
        	}
        	this.resetButton.render(sb);
        }
    }
    
    public void update() {
        this.hitbox.update();
        if (this.hitbox.hovered && InputHelper.justClickedLeft) {
            CardCrawlGame.sound.play("UI_CLICK_1");
            if (this.isSelected) {
            	this.isSelected = false;
            } else {
            	if (RelicSettingsButton.activeButton != null) {
                	RelicSettingsButton.activeButton.isSelected = false;
                }
                RelicSettingsButton.activeButton = this;
                this.isSelected = true;
            }
        }
        if (this.isSelected) {
        	for (IUIElement element : this.elements) {
        		element.update();
        	}
        	this.resetButton.update();
        }
    }
    
    public void resetAll() {
    	for (ReplayRelicSetting element : this.settings) {
    		element.ResetToDefault();
    	}
    }
    
    public int renderLayer() {
        return 1;
    }
    
    public int updateOrder() {
        return 1;
    }
    
    public void addSetting(ReplayRelicSetting setting) {
    	float ly = ReplayTheSpireMod.setting_start_y;
    	float lx = ReplayTheSpireMod.setting_start_x;
    	for (ReplayRelicSetting ls : this.settings) {
    		ly -= ls.elementHeight;
    		if (ly <= 0f) {
    			ly = ReplayTheSpireMod.setting_start_y + (lx >= 600.0f ? 80.0f : 0f);
    			lx += 300.0f;
    		}
    	}
    	settings.add(setting);
		elements.addAll(setting.GenerateElements(lx, ly));
    }
}
