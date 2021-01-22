package com.megacrit.cardcrawl.mod.replay.cards.replayxover.spireboss;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;

import expansioncontent.expansionContentMod;
import expansioncontent.cards.AbstractExpansionCard;
import replayTheSpire.replayxover.downfallbs;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class SS_Forest_ManyPaths extends AbstractExpansionCard
{
    public static final String ID = "Replay:SS_forest_1";
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
    
    public SS_Forest_ManyPaths() {
        super(ID, "replay/ss_forest_skill", COST, AbstractCard.CardType.SKILL, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
      //  this.tags.add(downfallen.STUDY_FOREST);
        this.tags.add(expansionContentMod.STUDY);
        this.magicNumber = this.baseMagicNumber = 1;
        ExhaustiveVariable.setBaseValue(this, 3);
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, this.magicNumber, false));
        AbstractDungeon.actionManager.addToBottom(new FetchAction(p.drawPile, this.magicNumber+1));
    }
    
    public AbstractCard makeCopy() {
        return new SS_Forest_ManyPaths();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SS_Forest_ManyPaths.cardStrings.NAME;
        DESCRIPTION = SS_Forest_ManyPaths.cardStrings.DESCRIPTION;
    }
}
