package com.megacrit.cardcrawl.mod.replay.cards.green;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.ShivTossAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;

public class ShivToss extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Shiv Toss";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
	private static final int COST = 0;
    
    public ShivToss() {
        super(ShivToss.ID, ShivToss.NAME, "cards/replay/shiv_toss.png", COST, ShivToss.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        ExhaustiveVariable.setBaseValue(this, 2);
        if (this.upgraded)
        	AlwaysRetainField.alwaysRetain.set(this, true);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ShivTossAction(this.magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new ShivToss();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.retain = true;
            AlwaysRetainField.alwaysRetain.set(this, true);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ShivToss.ID);
        NAME = ShivToss.cardStrings.NAME;
        DESCRIPTION = ShivToss.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ShivToss.cardStrings.UPGRADE_DESCRIPTION;
    }
}