package com.megacrit.cardcrawl.mod.replay.cards.replayxover.black;

import infinitespire.abstracts.*;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.core.*;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class Haul extends BlackCard
{
    private static final String ID = "ReplayTheSpireMod:Haul";
    private static final CardStrings cardStrings;
    private static final String NAME;
    private static final int COST = 0;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    
    public Haul() {
        super(ID, NAME, "cards/replay/replayBetaSkillDark.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardTarget.SELF);
        ExhaustiveVariable.setBaseValue(this, 2);
    }
    
    public AbstractCard makeCopy() {
        return new Haul();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = Haul.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, p.drawPile.size()));
        if (!this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NoDrawPower(p)));
        }
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = Haul.cardStrings.NAME;
        DESCRIPTION = Haul.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = Haul.cardStrings.UPGRADE_DESCRIPTION;
    }
}
