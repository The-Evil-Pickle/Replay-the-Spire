package com.megacrit.cardcrawl.mod.replay.cards.red;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;

import basemod.abstracts.CustomCard;

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
    private static final int BLOCK_UP = 3;
    private static final int EXHAUSTIVE_AMT = 2;
    private static final int EXHAUSTIVE_UP = 0;
    
    public ReplayStacked() {
        super(ID, ReplayStacked.NAME, "cards/replay/phantomShield.png", COST, ReplayStacked.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
        ExhaustiveVariable.setBaseValue(this, EXHAUSTIVE_AMT);
        this.baseBlock = BLOCK_AMT;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
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
            //ExhaustiveVariable.upgrade(this, EXHAUSTIVE_UP);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = ReplayStacked.cardStrings.NAME;
        DESCRIPTION = ReplayStacked.cardStrings.DESCRIPTION;
    }
}
