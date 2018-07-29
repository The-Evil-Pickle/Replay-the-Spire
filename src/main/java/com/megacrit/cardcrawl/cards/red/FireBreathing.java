package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class FireBreathing extends AbstractCard
{
    public static final String ID = "Fire Breathing";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 1;
    
    public FireBreathing() {
        super("Fire Breathing", FireBreathing.NAME, "red/power/fireBreathing", "red/power/fire_breathing", COST, FireBreathing.DESCRIPTION, CardType.POWER, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
  	    this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FireBreathingPower(p, this.magicNumber), this.magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new FireBreathing();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Fire Breathing");
        NAME = FireBreathing.cardStrings.NAME;
        DESCRIPTION = FireBreathing.cardStrings.DESCRIPTION;
    }
}
