package com.megacrit.cardcrawl.cards.green;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import basemod.abstracts.CustomCard;
import replayTheSpire.variables.Exhaustive;

public class SpeedTraining extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Speed Training";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 0;
    
    public SpeedTraining() {
        super(ID, NAME, "cards/replay/replayBetaSkill.png", COST, DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
		this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        Exhaustive.setBaseValue(this, 2);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
    	Exhaustive.increment(this);
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new SpeedTraining();
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
        NAME = SpeedTraining.cardStrings.NAME;
        DESCRIPTION = SpeedTraining.cardStrings.DESCRIPTION;
    }
}
