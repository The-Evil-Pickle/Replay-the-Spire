package com.megacrit.cardcrawl.mod.replay.cards.replayxover.spireboss;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.AgingPower;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;

import expansioncontent.expansionContentMod;
import expansioncontent.cards.AbstractExpansionCard;
import replayTheSpire.patches.CardFieldStuff;
import replayTheSpire.replayxover.downfallbs;
import replayTheSpire.replayxover.downfallen;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class SS_Forest_LostForever extends AbstractExpansionCard
{
    public static final String ID = "Replay:SS_forest_5";
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final CardStrings cardStrings;
    private static final int COST = 3;
    
    public SS_Forest_LostForever() {
        super(ID, "replay/ss_forest_skill", COST, AbstractCard.CardType.SKILL, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
        this.tags.add(downfallen.STUDY_FOREST);
        this.tags.add(expansionContentMod.STUDY);
        this.tags.add(CardFieldStuff.CHAOS_NEGATIVE_MAGIC);
        this.magicNumber = this.baseMagicNumber = 15;
        this.exhaust = true;
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new AgingPower(m, this.magicNumber), this.magicNumber));
    }
    
    public AbstractCard makeCopy() {
        return new SS_Forest_LostForever();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-3);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SS_Forest_LostForever.cardStrings.NAME;
        DESCRIPTION = SS_Forest_LostForever.cardStrings.DESCRIPTION;
    }
}
