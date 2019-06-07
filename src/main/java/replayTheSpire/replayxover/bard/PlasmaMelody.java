package replayTheSpire.replayxover.bard;

import com.evacipated.cardcrawl.mod.bard.melodies.AbstractMelody;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.orbs.Plasma;

import basemod.abstracts.CustomCard;

public class PlasmaMelody extends AbstractMelody
{
    public static final String ID = "Replay:PlasmaMelody";
    
    public PlasmaMelody() {
        super(PlasmaMelody.ID, AbstractCard.CardTarget.SELF);
        this.type = AbstractCard.CardType.SKILL;
    }
    
    @Override
    protected CustomCard.RegionName getRegionName() {
        return new CustomCard.RegionName("blue/skill/fusion");
    }
    
    @Override
    public void play() {
        this.addToBottom(new ChannelAction(new Plasma()));
    }
    
    @Override
    public AbstractMelody makeCopy() {
        return new PlasmaMelody();
    }
}