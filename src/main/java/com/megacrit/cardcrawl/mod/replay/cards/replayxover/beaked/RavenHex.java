package com.megacrit.cardcrawl.mod.replay.cards.replayxover.beaked;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import beaked.*;
import beaked.patches.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import beaked.actions.*;
import beaked.cards.AbstractWitherCard;

import com.megacrit.cardcrawl.core.*;

public class RavenHex extends AbstractWitherCard
{
    public static final String ID = "ReplayTheSpireMod:RavenHex";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int LANGUID_AMT = 4;
    private static final int LANGUID_UPAMT = 2;
    
    public RavenHex() {
        super(ID, RavenHex.NAME, "cards/replay/replayBetaSkill.png", COST, RavenHex.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.BEAKED_YELLOW, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
        final int n = LANGUID_AMT;
        this.misc = n;
        this.baseMisc = n;
        this.witherEffect = EXTENDED_DESCRIPTION[0];
        this.witherAmount = 1;
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new WitherAction(this));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new LanguidPower(m, this.misc, false), this.misc));
    }
    
    public AbstractCard makeCopy() {
        return new RavenHex();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMisc(LANGUID_UPAMT);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = RavenHex.cardStrings.NAME;
        DESCRIPTION = RavenHex.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = RavenHex.cardStrings.EXTENDED_DESCRIPTION;
    }
}
