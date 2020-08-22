package com.megacrit.cardcrawl.mod.replay.cards.blue;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
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
import replayTheSpire.patches.CardFieldStuff;

public class CalculationTraining extends CustomCard implements StartupCard
{
    public static final String ID = "ReplayTheSpireMod:Calculation Training";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    
    public CalculationTraining() {
        super(ID, NAME, "cards/replay/calculation_training.png", COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
		this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        ExhaustiveVariable.setBaseValue(this, 2);
        this.tags.add(CardFieldStuff.CHAOS_NEGATIVE_MAGIC);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FocusPower(p, 1), 1));
    	if (!this.upgraded) {
    		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Dazed(), this.magicNumber));
    	}
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
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = CalculationTraining.cardStrings.NAME;
        DESCRIPTION = CalculationTraining.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = CalculationTraining.cardStrings.UPGRADE_DESCRIPTION;
    }

	@Override
	public boolean atBattleStartPreDraw() {
		if (this.upgraded) {
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Dazed(), this.magicNumber));
		}
		return this.upgraded;
	}
}
