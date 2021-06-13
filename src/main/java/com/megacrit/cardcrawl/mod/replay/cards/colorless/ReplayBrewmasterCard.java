package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardColor;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
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
        super(ID, ReplayBrewmasterCard.NAME, "cards/replay/brewmaster.png", ReplayBrewmasterCard.COST, ReplayBrewmasterCard.DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF);
        this.tags.add(CardTags.HEALING);
        //this.isEthereal = true;
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
            this.upgradeBaseCost(2);
            //this.isEthereal = false;
            //this.rawDescription = ReplayBrewmasterCard.UPGRADE_DESCRIPTION;
            //this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = ReplayBrewmasterCard.cardStrings.NAME;
        DESCRIPTION = ReplayBrewmasterCard.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ReplayBrewmasterCard.cardStrings.UPGRADE_DESCRIPTION;
    }
}
