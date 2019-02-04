package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import basemod.abstracts.*;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.*;
import com.evacipated.cardcrawl.mod.stslib.variables.RefundVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;

public class Sssssssssshield extends CustomCard
{
    public static final String ID = "Replay:Sssssssssshield";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    
    public Sssssssssshield() {
        super(ID, Sssssssssshield.NAME, "cards/replay/replayBetaSkill.png", COST, Sssssssssshield.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        SneckoField.snecko.set(this, true);
        this.baseBlock = 6;
        this.exhaust = true;
        //RefundVariable.setBaseValue(this, 1);
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    	for (int i=0; i < this.cost; i++) {
    		AbstractDungeon.actionManager.addToBottom(new ReplayGainShieldingAction(p, p, this.block));
    	}
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBlock(2);
            RefundVariable.upgrade(this, 1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    public AbstractCard makeCopy() {
        return new Sssssssssshield();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = Sssssssssshield.cardStrings.NAME;
        DESCRIPTION = Sssssssssshield.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = Sssssssssshield.cardStrings.UPGRADE_DESCRIPTION;
    }
}
