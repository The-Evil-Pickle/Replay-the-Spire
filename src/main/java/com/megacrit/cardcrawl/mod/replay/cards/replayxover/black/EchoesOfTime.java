package com.megacrit.cardcrawl.mod.replay.cards.replayxover.black;

import infinitespire.abstracts.*;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.MultiExhumeAction;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.core.*;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;

public class EchoesOfTime extends BlackCard
{
    private static final String ID = "ReplayTheSpireMod:Echoes of Time";
    private static final CardStrings cardStrings;
    private static final String NAME;
    private static final int COST = 1;
    private static final String DESCRIPTION;
    
    public EchoesOfTime() {
        super(ID, NAME, "cards/replay/replayBetaSkillDark.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardTarget.SELF);
        this.purgeOnUse = true;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        GraveField.grave.set(this, true);
    }
    
    public AbstractCard makeCopy() {
        return new EchoesOfTime();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(5);
        }
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MultiExhumeAction(this.magicNumber));
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = EchoesOfTime.cardStrings.NAME;
        DESCRIPTION = EchoesOfTime.cardStrings.DESCRIPTION;
    }
}
