package replayTheSpire.quests;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.PotionSlot;

import infinitespire.InfiniteSpire;
import infinitespire.abstracts.Quest;

public class PotionBuyQuest extends Quest{
    private static final Color COLOR;

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
		return "Obtain Potions in non-combat rooms.";
	}

	@Override
	public void giveReward() {
		AbstractDungeon.player.potionSlots += 1;
        AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));	
	}

    static {
        COLOR = new Color(0.25f, 0.25f, 1.0f, 1.0f);
    }
}