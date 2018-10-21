package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.abstracts.CustomCard;
import replayTheSpire.variables.Exhaustive;

public class MuscleTraining extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Muscle Training";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 0;
    
    public MuscleTraining() {
        super(ID, NAME, "cards/replay/replayBetaSkill.png", COST, DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
		this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        Exhaustive.setBaseValue(this, 2);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
    	Exhaustive.increment(this);
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new MuscleTraining();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            Exhaustive.upgrade(this, 1);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = MuscleTraining.cardStrings.NAME;
        DESCRIPTION = MuscleTraining.cardStrings.DESCRIPTION;
    }
}
