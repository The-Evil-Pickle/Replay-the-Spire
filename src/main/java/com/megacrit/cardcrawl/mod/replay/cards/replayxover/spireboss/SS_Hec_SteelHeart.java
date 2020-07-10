package com.megacrit.cardcrawl.mod.replay.cards.replayxover.spireboss;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.ReplayGainShieldingAction;
import com.megacrit.cardcrawl.mod.replay.powers.replayxover.SteelHeartPower;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import expansioncontent.expansionContentMod;
import expansioncontent.cards.AbstractExpansionCard;
import replayTheSpire.replayxover.downfallbs;
import replayTheSpire.replayxover.downfallen;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class SS_Hec_SteelHeart extends AbstractExpansionCard
{
    public static final String ID = "Replay:SS_hec_2";
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final CardStrings cardStrings;
    private static final int COST = 3;
    
    public SS_Hec_SteelHeart() {
        super(ID, "replay/ss_hec_heart", COST, AbstractCard.CardType.POWER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        this.tags.add(downfallen.STUDY_HEC);
        this.tags.add(expansionContentMod.STUDY);
        this.magicNumber = this.baseMagicNumber = 10;
        this.block = this.baseBlock = 5;
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	this.addToBot(new ReplayGainShieldingAction(p, p, this.block));
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.magicNumber), this.magicNumber));
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SteelHeartPower(p, this.magicNumber/2, 1), this.magicNumber/2));
    }
    
    public AbstractCard makeCopy() {
        return new SS_Hec_SteelHeart();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(4);
            this.upgradeBlock(2);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SS_Hec_SteelHeart.cardStrings.NAME;
        DESCRIPTION = SS_Hec_SteelHeart.cardStrings.DESCRIPTION;
    }
}
