package com.megacrit.cardcrawl.cards.replayxover.curses;

import com.megacrit.cardcrawl.localization.*;
import beaked.*;
import com.megacrit.cardcrawl.cards.*;
import beaked.patches.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import beaked.actions.*;
import beaked.cards.AbstractWitherCard;

import com.megacrit.cardcrawl.core.*;

public class CompoundingHeadache extends AbstractWitherCard
{
    public static final String ID = "ReplayTheSpireMod:Compounding Headache";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final int COST = 0;
    public static final int BASE_AMT = 1;
    
    public CompoundingHeadache() {
        super(ID, CompoundingHeadache.NAME, "cards/replay/betaCurse.png", COST, CompoundingHeadache.DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.SELF);
        this.exhaust = true;
        this.baseMisc = BASE_AMT;
        this.misc = this.baseMisc;
        this.witherEffect = EXTENDED_DESCRIPTION[0];
        this.witherAmount = -1;
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, this.misc));
        AbstractDungeon.actionManager.addToBottom(new WitherAction(this));
    }
    
    public AbstractCard makeCopy() {
        return new CompoundingHeadache();
    }
    
    public void upgrade() {}

    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
        return this.cardPlayable(m) && this.hasEnoughEnergy();
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = CompoundingHeadache.cardStrings.NAME;
        DESCRIPTION = CompoundingHeadache.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = CompoundingHeadache.cardStrings.EXTENDED_DESCRIPTION;
    }
}
