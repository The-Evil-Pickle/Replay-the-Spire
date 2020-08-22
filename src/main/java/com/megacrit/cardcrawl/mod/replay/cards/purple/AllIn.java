package com.megacrit.cardcrawl.mod.replay.cards.purple;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.MightPower;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.mod.replay.stances.GuardStance;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.*;

public class AllIn extends CustomCard
{
    public static final String ID = "Replay:AllIn";
    private static final CardStrings cardStrings;
    
    public AllIn() {
        super(ID, AllIn.cardStrings.NAME, "cards/replay/replayBetaSkill.png", 1, AllIn.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.RARE, CardTarget.SELF);
        this.cardsToPreview = new AllOut();
        this.exhaust = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	this.addToBot(new ChangeStanceAction("Wrath"));
    	this.addToBot(new MakeTempCardInHandAction(new AllOut()));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = AllIn.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new AllIn();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
