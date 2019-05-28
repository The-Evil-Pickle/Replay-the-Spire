package replayTheSpire.replayxover.bard;

import com.evacipated.cardcrawl.mod.bard.melodies.AbstractMelody;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.orbs.Frost;

import basemod.abstracts.CustomCard;

public class FrostMelody extends AbstractMelody
{
    public static final String ID = "Replay:FrostMelody";
    
    public FrostMelody() {
        super(FrostMelody.ID, AbstractCard.CardTarget.SELF);
        this.type = AbstractCard.CardType.SKILL;
    }
    
    @Override
    protected CustomCard.RegionName getRegionName() {
        return new CustomCard.RegionName("blue/skill/chill");
    }
    
    @Override
    public void play() {
        this.addToBottom(new ChannelAction(new Frost()));
    }
    
    @Override
    public AbstractMelody makeCopy() {
        return new FrostMelody();
    }
}