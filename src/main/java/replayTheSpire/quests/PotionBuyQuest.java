package replayTheSpire.quests;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.potions.PotionSlot;

import infinitespire.InfiniteSpire;
import infinitespire.abstracts.Quest;

public class PotionBuyQuest extends Quest{
    private static final Color COLOR;
    private static final ArrayList<PowerTip> tip;
    static {
    	tip = new ArrayList<PowerTip>();
    	tip.add(new PowerTip("*", "Obtain potions At Non-Combat Rooms to complete this quest."));
    }
    
	public PotionBuyQuest() {
		super(PotionBuyQuest.class.getName(), PotionBuyQuest.COLOR, 2, QuestType.BLUE, QuestRarity.RARE);
	}
	
    @Override
    public Texture getTexture() {
        Texture texture = InfiniteSpire.getTexture("images/ui/replay/quest/potion.png");
        return texture;
    }
    
	@Override
	public Quest createNew() {
		return this;
	}
	
	@Override
	public Quest getCopy() {
		return new PortalQuest();
	}
	
	@Override
	public String getRewardString() {
		return "Gain a potion slot.";
	}
	
	@Override
	public String getTitle() {
		return "Obtain Potions @NCR*.";
	}
	
	@Override
	public void giveReward() {
		AbstractDungeon.player.potionSlots += 1;
        AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));	
	}
	@Override
    public void render(final SpriteBatch sb) {
        if (this.isHovered) {
        	if (InputHelper.mX < 1400.0f * Settings.scale) {
                TipHelper.queuePowerTips(InputHelper.mX + 50.0f * Settings.scale, InputHelper.mY + 50.0f * Settings.scale, tip);
            }
            else {
                TipHelper.queuePowerTips(InputHelper.mX - 350.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, tip);
            }
        }
    }
    static {
        COLOR = new Color(0.25f, 0.25f, 1.0f, 1.0f);
    }
}