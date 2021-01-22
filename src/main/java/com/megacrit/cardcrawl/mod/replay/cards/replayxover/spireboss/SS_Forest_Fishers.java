package com.megacrit.cardcrawl.mod.replay.cards.replayxover.spireboss;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.ChooseAction;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;

import expansioncontent.expansionContentMod;
import expansioncontent.cards.AbstractExpansionCard;
import replayTheSpire.replayxover.downfallbs;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.core.*;

public class SS_Forest_Fishers extends AbstractExpansionCard
{
    public static final String ID = "Replay:SS_forest_6";
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final CardStrings cardStrings;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    
    public SS_Forest_Fishers() {
        super(ID, "replay/ss_forest_fish", COST, AbstractCard.CardType.POWER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
       // this.tags.add(downfallen.STUDY_FOREST);
        this.tags.add(expansionContentMod.STUDY);
        this.magicNumber = this.baseMagicNumber = 4;
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	final ChooseAction choice = new ChooseAction(this, m, EXTENDED_DESCRIPTION[0]);
    	choice.add(EXTENDED_DESCRIPTION[1], EXTENDED_DESCRIPTION[2], () -> {
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
            return;
        });
    	choice.add(EXTENDED_DESCRIPTION[3], EXTENDED_DESCRIPTION[4], () -> {
    		AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber * 2));
            return;
        });
    	AbstractDungeon.actionManager.addToBottom(choice);
    }
    
    public AbstractCard makeCopy() {
        return new SS_Forest_Fishers();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SS_Forest_Fishers.cardStrings.NAME;
        DESCRIPTION = SS_Forest_Fishers.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = SS_Forest_Fishers.cardStrings.EXTENDED_DESCRIPTION;
    }
}
