package com.megacrit.cardcrawl.cards.green;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.colorless.PotOfGreed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import basemod.abstracts.CustomCard;

public class TheWorks extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:The Works";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
	public boolean copyPlayedThisTurn = false;
    
    public TheWorks() {
        super(TheWorks.ID, TheWorks.NAME, "cards/replay/replayBetaSkill.png", 0, TheWorks.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TheWorksPower(p, this.magicNumber), this.magicNumber));
        this.copyPlayedThisTurn = true;
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new TheWorks();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(5);
        }
    }
	@Override
	public void atTurnStart() {
		this.copyPlayedThisTurn = false;
	}
	
    @Override
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
        final boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        if (this.copyPlayedThisTurn) {
			this.cantUseMessage = "Already played.";
            return false;
		}
        return canUse;
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(TheWorks.ID);
        NAME = TheWorks.cardStrings.NAME;
        DESCRIPTION = TheWorks.cardStrings.DESCRIPTION;
    }
}