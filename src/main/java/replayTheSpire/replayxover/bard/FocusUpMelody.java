package replayTheSpire.replayxover.bard;

import com.evacipated.cardcrawl.mod.bard.melodies.AbstractMelody;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;

import basemod.abstracts.CustomCard;

public class FocusUpMelody extends AbstractMelody
{
    public static final String ID = "Replay:FocusUpMelody";
    
    public FocusUpMelody() {
        super(FocusUpMelody.ID, AbstractCard.CardTarget.SELF);
        this.type = AbstractCard.CardType.POWER;
    }
    
    @Override
    protected CustomCard.RegionName getRegionName() {
        return new CustomCard.RegionName("blue/power/defragment");
    }
    
    @Override
    public void play() {
        this.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, 1), 1));
    }
    
    @Override
    public AbstractMelody makeCopy() {
        return new FocusUpMelody();
    }
}