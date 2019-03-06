package com.megacrit.cardcrawl.mod.replay.cards.replayxover.black;

import infinitespire.abstracts.*;
import slimebound.cards.StudyTheSpire;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.common.ReplayGainShieldingAction;
import com.megacrit.cardcrawl.mod.replay.actions.unique.MultiExhumeAction;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.ChaosPower;
import com.megacrit.cardcrawl.mod.replay.powers.DelayedAddCardPower;
import com.megacrit.cardcrawl.mod.replay.powers.TPH_ConfusionPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.core.*;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class BookOfNames extends BlackCard
{
    private static final String ID = "ReplayTheSpireMod:Book of Names";
    private static final CardStrings cardStrings;
    private static final String NAME;
    private static final int COST = 1;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    
    public BookOfNames() {
        super(ID, NAME, "cards/replay/studyspire.png", COST, DESCRIPTION, AbstractCard.CardType.POWER, AbstractCard.CardTarget.SELF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }
    
    public AbstractCard makeCopy() {
        return new BookOfNames();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	int slotGain = this.magicNumber - p.maxOrbs;
        if (slotGain > 0) {
            AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(slotGain));
        }
        AbstractCard c = CardLibrary.getCard(StudyTheSpire.ID).makeCopy();
        c.magicNumber = c.baseMagicNumber = this.magicNumber + (this.upgraded ? 1 : 0);
        c.use(p, m);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DelayedAddCardPower(p, this.magicNumber, this)));
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = BookOfNames.cardStrings.NAME;
        DESCRIPTION = BookOfNames.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = BookOfNames.cardStrings.UPGRADE_DESCRIPTION;
    }
}
