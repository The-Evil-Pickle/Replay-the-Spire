package com.megacrit.cardcrawl.mod.replay.cards.purple;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.MightPower;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.mod.replay.stances.GuardStance;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.*;

public class AtTheReady extends CustomCard
{
    public static final String ID = "Replay:AtTheReady";
    private static final CardStrings cardStrings;
    
    public AtTheReady() {
        super(ID, AtTheReady.cardStrings.NAME, "cards/replay/at_the_ready.png", 1, AtTheReady.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.COMMON, CardTarget.SELF);
        this.exhaust = true;
        this.selfRetain = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	this.addToBot(new ChangeStanceAction(new GuardStance()));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new AtTheReady();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
