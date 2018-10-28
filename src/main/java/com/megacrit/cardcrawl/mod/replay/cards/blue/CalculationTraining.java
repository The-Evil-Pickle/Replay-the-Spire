package com.megacrit.cardcrawl.mod.replay.cards.blue;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardColor;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

import basemod.abstracts.CustomCard;

public class CalculationTraining extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Calculation Training";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 0;
    
    public CalculationTraining() {
        super(ID, NAME, "cards/replay/replayBetaSkill.png", COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
		this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        ExhaustiveVariable.setBaseValue(this, 2);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new CalculationTraining();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            ExhaustiveVariable.upgrade(this, 1);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = CalculationTraining.cardStrings.NAME;
        DESCRIPTION = CalculationTraining.cardStrings.DESCRIPTION;
    }
}
