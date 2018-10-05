package replayTheSpire.quests;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import infinitespire.InfiniteSpire;
import infinitespire.abstracts.Quest;
import infinitespire.abstracts.Quest.QuestRarity;
import infinitespire.abstracts.Quest.QuestType;
import infinitespire.helpers.QuestHelper;
import replayTheSpire.ReplayTheSpireMod;

public class PortalQuest extends Quest{
    private static final Color COLOR;
    public int gold;

	public PortalQuest() {
		super(PortalQuest.class.getName(), PortalQuest.COLOR, 1, QuestType.BLUE, (ReplayTheSpireMod.SETTING_ROOMS_PORTAL.value <= 20) ? QuestRarity.SPECIAL : ((ReplayTheSpireMod.SETTING_ROOMS_BONFIRE.value < 75) ? QuestRarity.RARE : (QuestRarity.COMMON)));
	}
	
    @Override
    public Texture getTexture() {
        Texture texture = InfiniteSpire.getTexture("images/ui/replay/quest/portal.png");
        return texture;
    }

	@Override
	public Quest createNew() {
        this.gold = QuestHelper.makeRandomCost(75);
		return this;
	}

	@Override
	public Quest getCopy() {
		return new PortalQuest();
	}

	@Override
	public String getRewardString() {
		return "Gain " + this.gold + " Gold.";
	}

	@Override
	public String getTitle() {
		return "Visit a Portal room.";
	}

	@Override
	public void giveReward() {
		CardCrawlGame.sound.play("GOLD_GAIN");
        AbstractDungeon.player.gainGold(this.gold);
	}

    static {
        COLOR = new Color(0.25f, 0.25f, 1.0f, 1.0f);
    }
}
