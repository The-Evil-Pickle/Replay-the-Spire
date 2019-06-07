package replayTheSpire.replayxover.bard;

import com.evacipated.cardcrawl.mod.bard.melodies.AbstractMelody;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.orbs.Lightning;

import basemod.abstracts.CustomCard;

public class LightningMelody extends AbstractMelody
{
    public static final String ID = "Replay:LightningMelody";
    
    public LightningMelody() {
        super(LightningMelody.ID, AbstractCard.CardTarget.SELF);
        this.type = AbstractCard.CardType.SKILL;
    }
    
    @Override
    protected CustomCard.RegionName getRegionName() {
        return new CustomCard.RegionName("blue/skill/zap");
    }
    
    @Override
    public void play() {
        this.addToBottom(new ChannelAction(new Lightning()));
    }
    
    @Override
    public AbstractMelody makeCopy() {
        return new LightningMelody();
    }
}