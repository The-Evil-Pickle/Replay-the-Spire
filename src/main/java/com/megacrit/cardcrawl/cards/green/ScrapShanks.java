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

public class ScrapShanks extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Scrap Shanks";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    
    public ScrapShanks() {
        super(ID, ScrapShanks.NAME, "cards/replay/replayBetaPower.png", 1, ScrapShanks.DESCRIPTION, CardType.POWER, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	int stackAmount = this.magicNumber;
    	if (this.upgraded) {
    		stackAmount *= -1;
    	}
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ScrapShanksPower(p, stackAmount), stackAmount));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new ScrapShanks();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = ScrapShanks.cardStrings.NAME;
        DESCRIPTION = ScrapShanks.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ScrapShanks.cardStrings.UPGRADE_DESCRIPTION;
    }
}
