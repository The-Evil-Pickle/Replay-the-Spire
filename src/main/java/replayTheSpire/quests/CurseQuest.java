package replayTheSpire.quests;

import infinitespire.abstracts.*;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.*;
import infinitespire.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;

import infinitespire.helpers.*;
import infinitespire.helpers.CardHelper;
import infinitespire.quests.PickUpCardQuest;

public class CurseQuest extends PickUpCardQuest
{
    private static final Color COLOR;
    
    public CurseQuest() {
        super();
        this.id = CurseQuest.class.getName();
        this.color = COLOR;
        this.maxSteps = 3;
        this.type =  QuestType.GREEN;
        this.rarity = QuestRarity.RARE;
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
        return "Pick up " + this.maxSteps + " Curses.";
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
