package com.megacrit.cardcrawl.mod.replay.cards.red;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardColor;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.characters.*;

import basemod.abstracts.CustomCard;
import replayTheSpire.variables.Exhaustive;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;

public class ReplayStacked extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Stacked";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMT = 11;
    private static final int BLOCK_UP = 2;
    private static final int EXHAUSTIVE_AMT = 2;
    private static final int EXHAUSTIVE_UP = 1;
    
    public ReplayStacked() {
        super(ID, ReplayStacked.NAME, "cards/replay/replayBetaSkill.png", COST, ReplayStacked.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
        //this.baseMagicNumber = EXHAUSTIVE;
        //this.magicNumber = this.baseMagicNumber;
        Exhaustive.setBaseValue(this, EXHAUSTIVE_AMT);
        this.baseBlock = BLOCK_AMT;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        Exhaustive.increment(this);
        /*--this.baseMagicNumber;
        --this.magicNumber;
        this.initializeDescription();
        if (this.magicNumber <= 0) {
        	this.exhaust = true;
        }*/
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new ReplayStacked();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(BLOCK_UP);
            Exhaustive.upgrade(this, EXHAUSTIVE_UP);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = ReplayStacked.cardStrings.NAME;
        DESCRIPTION = ReplayStacked.cardStrings.DESCRIPTION;
    }
}
