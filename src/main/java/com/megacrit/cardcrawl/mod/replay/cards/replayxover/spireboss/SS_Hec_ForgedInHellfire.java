package com.megacrit.cardcrawl.mod.replay.cards.replayxover.spireboss;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.ForgedInHellfireAltPower;

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

public class SS_Hec_ForgedInHellfire extends AbstractExpansionCard
{
    public static final String ID = "Replay:SS_hec_1";
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
    
    public SS_Hec_ForgedInHellfire() {
        super(ID, "replay/ss_hec_hellfire", COST, AbstractCard.CardType.POWER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        this.tags.add(downfallen.STUDY_HEC);
        this.tags.add(expansionContentMod.STUDY);
        this.magicNumber = this.baseMagicNumber = 3;
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, this.magicNumber-1), this.magicNumber-1));
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ForgedInHellfireAltPower(p, this.magicNumber), this.magicNumber));
    }
    
    public AbstractCard makeCopy() {
        return new SS_Hec_ForgedInHellfire();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SS_Hec_ForgedInHellfire.cardStrings.NAME;
        DESCRIPTION = SS_Hec_ForgedInHellfire.cardStrings.DESCRIPTION;
    }
}
