package com.megacrit.cardcrawl.mod.replay.cards.purple;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.cards.*;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.core.*;

public class Cycle extends CustomCard
{
    public static final String ID = "Replay:Cycle";
    private static final CardStrings cardStrings;
    
    public Cycle() {
        super(ID, Cycle.cardStrings.NAME, "cards/replay/replayBetaSkill.png", 0, Cycle.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = 1;
        ExhaustiveVariable.setBaseValue(this, 3);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	this.addToBot(new DrawCardAction(p, this.magicNumber));
    }
    
    @Override
    public void triggerOnScry() {
        this.addToBot(new DiscardToHandAction(this));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            ExhaustiveVariable.upgrade(this, 2);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new Cycle();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
