package com.megacrit.cardcrawl.mod.replay.cards.replayxover.spireboss;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;

import expansioncontent.expansionContentMod;
import expansioncontent.cards.AbstractExpansionCard;
import replayTheSpire.replayxover.downfallbs;
import replayTheSpire.replayxover.downfallen;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.core.*;

public class SS_Fish_LivingLantern extends AbstractExpansionCard
{
    public static final String ID = "Replay:SS_fish_2";
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
    
    public SS_Fish_LivingLantern() {
        super(ID, "replay/ss_fish_light", COST, AbstractCard.CardType.SKILL, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        this.tags.add(downfallen.STUDY_PONDFISH);
        this.tags.add(expansionContentMod.STUDY);
        this.magicNumber = this.baseMagicNumber = 8;
        //ExhaustiveVariable.setBaseValue(this, 2);
        this.exhaust = true;
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
        if (p.currentHealth < p.maxHealth/2) {
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
        }
    }
    
    public AbstractCard makeCopy() {
        return new SS_Fish_LivingLantern();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(6);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SS_Fish_LivingLantern.cardStrings.NAME;
        DESCRIPTION = SS_Fish_LivingLantern.cardStrings.DESCRIPTION;
    }
}
