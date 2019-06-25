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
    private static final int REWARD_AMOUNT = 1;
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
	public Quest getCopy() {
		return new PortalQuest();
	}

	@Override
	public String getRewardString() {
		//return this.voidShardStrings.TEXT[2] + REWARD_AMOUNT + this.voidShardStrings.TEXT[4];
		return "Gain " + this.gold + " gold."; 
	}

	@Override
	public String getTitle() {
		return "Visit a Portal room.";
	}

	@Override
	public void giveReward() {
		CardCrawlGame.sound.play("GOLD_GAIN");
		AbstractDungeon.player.gainGold(this.gold);
		//InfiniteSpire.gainVoidShards(REWARD_AMOUNT);
	}

	@Override
	public Quest createNew() {
		this.gold = QuestHelper.makeRandomCost(75);
		return this;
	}
	
    static {
        COLOR = new Color(0.25f, 0.25f, 1.0f, 1.0f);
    }

}
