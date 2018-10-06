package com.megacrit.cardcrawl.cards.replayxover.black;

import infinitespire.abstracts.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.BlackTransmuteAction;

public class DarkTransmutation extends BlackCard
{
    private static final String ID = "ReplayTheSpireMod:Dark Transmutation";
    private static final CardStrings cardStrings;
    private static final String NAME;
    private static final int COST = -1;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    
    public DarkTransmutation() {
        super(ID, NAME, "cards/replay/replayBetaSkillDark.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardTarget.SELF);
        this.purgeOnUse = true;
    }
    
    public AbstractCard makeCopy() {
        return new DarkTransmutation();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = DarkTransmutation.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        AbstractDungeon.actionManager.addToBottom(new BlackTransmuteAction(p, this.upgraded, this.freeToPlayOnce, this.energyOnUse));
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = DarkTransmutation.cardStrings.NAME;
        DESCRIPTION = DarkTransmutation.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = DarkTransmutation.cardStrings.UPGRADE_DESCRIPTION;
    }
}
