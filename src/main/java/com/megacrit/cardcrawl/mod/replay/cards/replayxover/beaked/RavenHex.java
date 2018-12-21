package com.megacrit.cardcrawl.mod.replay.cards.replayxover.beaked;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

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
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int LANGUID_AMT = 6;
    private static final int LANGUID_UPAMT = 0;
    
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
        if (this.upgraded) {
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new DecrepitPower(m, this.misc, false), this.misc));
        }
    }
    
    public AbstractCard makeCopy() {
        return new RavenHex();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMisc(LANGUID_UPAMT);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = RavenHex.cardStrings.NAME;
        DESCRIPTION = RavenHex.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = RavenHex.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = RavenHex.cardStrings.EXTENDED_DESCRIPTION;
    }
}
