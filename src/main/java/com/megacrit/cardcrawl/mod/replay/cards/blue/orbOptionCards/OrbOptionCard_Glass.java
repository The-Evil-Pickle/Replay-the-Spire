package com.megacrit.cardcrawl.mod.replay.cards.blue.orbOptionCards;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardColor;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.defect.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.blue.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;

public class OrbOptionCard_Glass extends CustomCard
{
    public static final String ID = "OrbOptionCard_Glass";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 0;
    
    public OrbOptionCard_Glass() {
        super("OrbOptionCard_Glass", OrbOptionCard_Glass.NAME, "cards/replay/replayBetaSkill.png", OrbOptionCard_Glass.COST, OrbOptionCard_Glass.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(this.magicNumber));
    	for (int i=0; i < this.magicNumber; i++) {
    		AbstractDungeon.actionManager.addToBottom(new ChannelAction(new HellFireOrb()));
    	}
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new OrbOptionCard_Glass();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("OrbOptionCard_Glass");
        NAME = OrbOptionCard_Glass.cardStrings.NAME;
        DESCRIPTION = OrbOptionCard_Glass.cardStrings.DESCRIPTION;
    }
}