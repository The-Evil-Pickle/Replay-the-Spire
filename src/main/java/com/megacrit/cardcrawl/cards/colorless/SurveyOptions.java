package com.megacrit.cardcrawl.cards.colorless;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import basemod.abstracts.*;

public class SurveyOptions extends CustomCard
{
    public static final String ID = "Survey Options";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = -1;
    private static final int BASE_BLOCK = 7;
    
    public SurveyOptions() {
        super("Survey Options", SurveyOptions.NAME, "cards/replay/replayBetaSkill.png", -1, SurveyOptions.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new RefundDrawAction(p, this.magicNumber, this.freeToPlayOnce, this.energyOnUse));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new SurveyOptions();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Survey Options");
        NAME = SurveyOptions.cardStrings.NAME;
        DESCRIPTION = SurveyOptions.cardStrings.DESCRIPTION;
    }
}
