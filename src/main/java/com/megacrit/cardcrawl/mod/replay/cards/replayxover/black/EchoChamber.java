package com.megacrit.cardcrawl.mod.replay.cards.replayxover.black;

import infinitespire.abstracts.*;
import tobyspowerhouse.powers.TPH_ConfusionPower;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.common.ReplayGainShieldingAction;
import com.megacrit.cardcrawl.mod.replay.actions.unique.MultiExhumeAction;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.ChaosPower;
import com.megacrit.cardcrawl.mod.replay.powers.EchoChamberPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.core.*;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class EchoChamber extends BlackCard
{
    private static final String ID = "ReplayTheSpireMod:Echo Chamber";
    private static final CardStrings cardStrings;
    private static final String NAME;
    private static final int COST = 1;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    
    public EchoChamber() {
        super(ID, NAME, "cards/replay/replayBetaPowerDark.png", COST, DESCRIPTION, AbstractCard.CardType.POWER, AbstractCard.CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }
    
    public AbstractCard makeCopy() {
        return new EchoChamber();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EchoChamberPower(p, this.magicNumber, this.upgraded), this.magicNumber));
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = EchoChamber.cardStrings.NAME;
        DESCRIPTION = EchoChamber.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = EchoChamber.cardStrings.UPGRADE_DESCRIPTION;
    }
}
