package com.megacrit.cardcrawl.cards.green;

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

public class FluidMovement extends CustomCard
{
    public static final String ID = "Fluid Movement";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    
    public FluidMovement() {
        super("Fluid Movement", FluidMovement.NAME, "cards/replay/fluidMovement.png", 1, FluidMovement.DESCRIPTION, CardType.POWER, CardColor.GREEN, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RetainSomeBlockPower(p, this.magicNumber), this.magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new FluidMovement();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(3);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Fluid Movement");
        NAME = FluidMovement.cardStrings.NAME;
        DESCRIPTION = FluidMovement.cardStrings.DESCRIPTION;
    }
}
