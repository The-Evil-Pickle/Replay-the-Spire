package com.megacrit.cardcrawl.mod.replay.cards.replayxover.spireboss;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;

import slimebound.powers.PotencyPower;

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

public class SS_Fish_CaptainsOrders extends AbstractExpansionCard
{
    public static final String ID = "Replay:SS_fish_4";
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
    
    public SS_Fish_CaptainsOrders() {
        super(ID, "replay/ss_fish_orders", COST, AbstractCard.CardType.POWER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        this.tags.add(downfallen.STUDY_PONDFISH);
        this.tags.add(expansionContentMod.STUDY);
        this.magicNumber = this.baseMagicNumber = 1;
        this.block = this.baseBlock = 10;
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        if (!p.hasPower(StrengthPower.POWER_ID) || p.getPower(StrengthPower.POWER_ID).amount < 1) {
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
        }
        if (!p.hasPower(PotencyPower.POWER_ID) || p.getPower(PotencyPower.POWER_ID).amount < 1) {
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PotencyPower(p, p, 1), 1));
        }
    }
    
    public AbstractCard makeCopy() {
        return new SS_Fish_CaptainsOrders();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(5);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SS_Fish_CaptainsOrders.cardStrings.NAME;
        DESCRIPTION = SS_Fish_CaptainsOrders.cardStrings.DESCRIPTION;
    }
}
