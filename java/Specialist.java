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

public class Specialist extends AbstractCard
{
    public static final String ID = "Specialist";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int POOL = 1;
	private static final int COST = 0;
    private static final int DMG = 3;
    private static final int UPG_DMG = 1;
    
    public Specialist() {
        super("Specialist", Specialist.NAME, null, "status/beta", Specialist.COST, Specialist.DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF, 1);
        this.baseMagicNumber = Specialist.DMG;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpecialistPower(p, this.magicNumber), this.magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new Specialist();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(Specialist.UPG_DMG);
            this.isInnate = true;
            this.rawDescription = Specialist.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Specialist");
        NAME = Specialist.cardStrings.NAME;
        DESCRIPTION = Specialist.cardStrings.DESCRIPTION;
		UPGRADE_DESCRIPTION = Specialist.cardStrings.UPGRADE_DESCRIPTION;
    }
}
