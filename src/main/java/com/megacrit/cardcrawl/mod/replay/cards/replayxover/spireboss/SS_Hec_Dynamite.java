package com.megacrit.cardcrawl.mod.replay.cards.replayxover.spireboss;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.replayxover.TheDynamitePower;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import expansioncontent.expansionContentMod;
import expansioncontent.cards.AbstractExpansionCard;
import replayTheSpire.patches.CardFieldStuff;
import replayTheSpire.replayxover.downfallbs;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class SS_Hec_Dynamite extends AbstractExpansionCard
{
    public static final String ID = "Replay:SS_hec_4";
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
    
    public SS_Hec_Dynamite() {
        super(ID, "replay/ss_hec_dynamite", COST, AbstractCard.CardType.ATTACK, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY);
      //  this.tags.add(downfallen.STUDY_HEC);
        this.tags.add(expansionContentMod.STUDY);
        this.tags.add(CardFieldStuff.CHAOS_NEGATIVE_MAGIC);
        this.isMultiDamage = true;
        this.magicNumber = this.baseMagicNumber = 3;
        this.damage = this.baseDamage = 30;
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TheDynamitePower(p, this.magicNumber, this), this.magicNumber));
    }
    
    public AbstractCard makeCopy() {
        return new SS_Hec_Dynamite();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(10);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SS_Hec_Dynamite.cardStrings.NAME;
        DESCRIPTION = SS_Hec_Dynamite.cardStrings.DESCRIPTION;
    }
}
