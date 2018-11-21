package com.megacrit.cardcrawl.mod.replay.cards.replayxover;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.orbs.CrystalOrb;
import com.megacrit.cardcrawl.mod.replay.orbs.GlassOrb;
import com.megacrit.cardcrawl.mod.replay.orbs.HellFireOrb;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardColor;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import basemod.abstracts.*;
import conspire.orbs.Water;

public class DualPolarity extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Dual Polarity";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    
    public DualPolarity() {
        super(ID, DualPolarity.NAME, "cards/replay/replayBetaSkill.png", DualPolarity.COST, DualPolarity.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
    }
	
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	final ChooseAction choice = new ChooseAction((AbstractCard)this, m, EXTENDED_DESCRIPTION[0]);
    	choice.add(EXTENDED_DESCRIPTION[1], EXTENDED_DESCRIPTION[2], () -> {
    		for (int i=0; i<this.magicNumber; i++) {AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Lightning()));}
    		for (int i=0; i<this.magicNumber; i++) {AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Dark()));}
            return;
        });
    	choice.add(EXTENDED_DESCRIPTION[3], EXTENDED_DESCRIPTION[4], () -> {
    		for (int i=0; i<this.magicNumber; i++) {AbstractDungeon.actionManager.addToBottom(new ChannelAction(new CrystalOrb()));}
    		for (int i=0; i<this.magicNumber; i++) {AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Frost()));}
            return;
        });
    	choice.add(EXTENDED_DESCRIPTION[5], EXTENDED_DESCRIPTION[6], () -> {
    		for (int i=0; i<this.magicNumber; i++) {AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Water()));}
    		for (int i=0; i<this.magicNumber; i++) {AbstractDungeon.actionManager.addToBottom(new ChannelAction(new HellFireOrb()));}
            return;
        });
    	choice.add(EXTENDED_DESCRIPTION[7], EXTENDED_DESCRIPTION[8], () -> {
    		for (int i=0; i<this.magicNumber; i++) {AbstractDungeon.actionManager.addToBottom(new ChannelAction(new GlassOrb()));}
    		for (int i=0; i<this.magicNumber; i++) {AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Plasma()));}
            return;
        });
    	AbstractDungeon.actionManager.addToBottom(choice);
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new DualPolarity();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = DualPolarity.cardStrings.NAME;
        DESCRIPTION = DualPolarity.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = DualPolarity.cardStrings.EXTENDED_DESCRIPTION;
    }
}
