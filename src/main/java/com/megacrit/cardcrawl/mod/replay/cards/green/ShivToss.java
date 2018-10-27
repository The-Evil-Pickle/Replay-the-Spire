package com.megacrit.cardcrawl.mod.replay.cards.green;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.ShivTossAction;
import com.megacrit.cardcrawl.mod.replay.powers.TheWorksPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import replayTheSpire.variables.Exhaustive;

public class ShivToss extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Shiv Toss";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
	private static final int COST = 0;
    
    public ShivToss() {
        super(ShivToss.ID, ShivToss.NAME, "cards/replay/replayBetaSkill.png", COST, ShivToss.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        Exhaustive.setBaseValue(this, 2);
        if (this.upgraded)
			this.retain = true;
    }

    @Override
    public void applyPowers() {
		super.applyPowers();
		if (this.upgraded)
			this.retain = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ShivTossAction(this.magicNumber));
    	Exhaustive.increment(this);
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