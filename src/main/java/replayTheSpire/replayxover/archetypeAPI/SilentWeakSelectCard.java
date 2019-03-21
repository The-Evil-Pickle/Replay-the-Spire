package replayTheSpire.replayxover.archetypeAPI;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import archetypeAPI.cards.AbstractArchetypeCard;
import archetypeAPI.patches.ArchetypeCardTags;

public class SilentWeakSelectCard extends AbstractArchetypeCard {
    // == Basic String/Type Declaration ==
    
    public static final String ID = "Replay:SilentWeakSelectCard";
    public static final String IMG = "cards/replay/heelHook.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;

    public static final CardColor COLOR = CardColor.GREEN;
    public static final CardType TYPE = CardType.ATTACK;
    
    // ==/ Basic String/Type Declaration /==

    public SilentWeakSelectCard() {
        super(ID, NAME, IMG, DESCRIPTION, TYPE, COLOR);
        if (Loader.isModLoaded("archetypeapi")) { // Make sure to check for the API before adding a tag from it
             tags.add(ArchetypeCardTags.SINGLE); // Explanation of tags is just below
        }
    }

    @Override
    public void archetypeEffect() { // This is the important necessary bit that adds your archetype.
    	SilentWeakSet coolSilent = new SilentWeakSet(); // Simply create a new instance of the archetype class you made in step 1.
    }

    @Override
    public String getTooltipName() {     // The Abstract archetype card sets a custom tooltip by default.
        return null;  // You can override that directly if you want multiple ones, or, 
    }                                    // or simply pass the name and description in these two methods.
                                         // You can also return null and it will set a default description.
    @Override
    public String getTooltipDesc() {
        return null;
    }
}