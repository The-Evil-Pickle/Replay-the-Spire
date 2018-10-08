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
import basemod.abstracts.*;

public class ReplayBrewmasterCard extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Brewmster";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 3;
    
    public ReplayBrewmasterCard() {
        super(ID, ReplayBrewmasterCard.NAME, "cards/replay/replayBetaPower.png", ReplayBrewmasterCard.COST, ReplayBrewmasterCard.DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF);
        this.isEthereal = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ReplayBrewPower(p, 1), 1));
		
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new ReplayBrewmasterCard();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isEthereal = false;
            this.rawDescription = ReplayBrewmasterCard.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = ReplayBrewmasterCard.cardStrings.NAME;
        DESCRIPTION = ReplayBrewmasterCard.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ReplayBrewmasterCard.cardStrings.UPGRADE_DESCRIPTION;
    }
}
