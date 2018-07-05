package com.megacrit.cardcrawl.cards.colorless;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import basemod.*;
import basemod.abstracts.*;

public class AwakenedRitual extends CustomCard
{
    public static final String ID = "Crow Ritual";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    
    public AwakenedRitual() {
        super("Crow Ritual", AwakenedRitual.NAME, "cards/replay/crow_ritual.png", 0, AwakenedRitual.DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF, 1);
		this.baseDamage = 42;
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AwakenedRitualPower(p)));
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new RitualComponent(), this.magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new AwakenedRitual();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Crow Ritual");
        NAME = AwakenedRitual.cardStrings.NAME;
        DESCRIPTION = AwakenedRitual.cardStrings.DESCRIPTION;
    }
}
