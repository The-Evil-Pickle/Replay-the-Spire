package replayTheSpire.quests;

import infinitespire.abstracts.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.*;
import infinitespire.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.cards.*;
import infinitespire.helpers.*;
import infinitespire.helpers.CardHelper;
import infinitespire.quests.PickUpCardQuest;

public class CurseQuest extends Quest
{
    private static final Color COLOR;
    
    public CurseQuest() {
        super(CurseQuest.class.getName(), CurseQuest.COLOR, 1, QuestType.GREEN, QuestRarity.RARE);
    }
    
    @Override
    public void giveReward() {
    	final ArrayList<AbstractCard> randomBlackCards = CardHelper.getBlackRewardCards();
        AbstractDungeon.cardRewardScreen.open(randomBlackCards, null, "Select a Card.");
    }
    
    @Override
    public Texture getTexture() {
        Texture texture = InfiniteSpire.getTexture("img/infinitespire/ui/questLog/questIcons/card-skill.png");
        return texture;
    }
    
    @Override
    public Quest createNew() {
        return this;
    }
    
    @Override
    public String getRewardString() {
        return "Choose a Black card to obtain.";
    }
    
    @Override
    public String getTitle() {
        return "Pick up a Curse.";
    }
    
    @Override
    public Quest getCopy() {
        return new CurseQuest();
    }
    
    public boolean isCard(final AbstractCard card) {
        return (card.type == AbstractCard.CardType.CURSE || card.rarity == AbstractCard.CardRarity.CURSE);
    }
    
    static {
        COLOR = new Color(0.25f, 0.25f, 0.25f, 1.0f);
    }
}
