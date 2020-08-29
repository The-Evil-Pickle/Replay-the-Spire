package com.megacrit.cardcrawl.mod.replay.cards.purple;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.ScryToZeroAction;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;

public class ManipulateFuture extends CustomCard
{
    public static final String ID = "Replay:ManipulateFuture";
    private static final CardStrings cardStrings;
    
    public ManipulateFuture() {
        super(ID, ManipulateFuture.cardStrings.NAME, "cards/replay/replayBetaSkill.png", 0, ManipulateFuture.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ScryToZeroAction(this.magicNumber));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new ManipulateFuture();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
