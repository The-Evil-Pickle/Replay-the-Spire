package com.megacrit.cardcrawl.mod.replay.cards.red;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.evacipated.cardcrawl.mod.stslib.variables.RefundVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardColor;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.characters.*;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;

public class ManyHands extends CustomCard
{
    public static final String ID = "Replay:Many Hands";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = -1;
    
    public ManyHands() {
        super(ID, ManyHands.NAME, "cards/replay/replayBetaSkill.png", -1, ManyHands.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.RARE, CardTarget.SELF);
        ExhaustiveVariable.setBaseValue(this, 2);
        this.cardsToPreview = new UndeathsTouch();
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ManyHandsAction(AbstractDungeon.player, this.freeToPlayOnce, this.energyOnUse));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new ManyHands();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            RefundVariable.upgrade(this, 1);
            ExhaustiveVariable.upgrade(this, 1);
            this.rawDescription = ManyHands.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = ManyHands.cardStrings.NAME;
        DESCRIPTION = ManyHands.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ManyHands.cardStrings.UPGRADE_DESCRIPTION;
    }
}
