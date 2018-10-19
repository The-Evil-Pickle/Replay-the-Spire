package replayTheSpire.variables;

import basemod.abstracts.*;
import com.megacrit.cardcrawl.cards.*;

public class MagicMinusOne extends DynamicVariable
{
    public String key() {
        return "replay:M-1";
    }
    
    public boolean isModified(final AbstractCard card) {
        return card.isMagicNumberModified;
    }
    
    public int value(final AbstractCard card) {
        return card.magicNumber - 1;
    }
    
    public int baseValue(final AbstractCard card) {
        return card.baseMagicNumber - 1;
    }
    
    public boolean upgraded(final AbstractCard card) {
        return card.upgradedMagicNumber;
    }
}
