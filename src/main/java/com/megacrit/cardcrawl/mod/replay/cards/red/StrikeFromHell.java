package com.megacrit.cardcrawl.mod.replay.cards.red;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardColor;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;

public class StrikeFromHell extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Strike From Hell";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 8;
    private static final int DMG_INC = 4;
    
    public StrikeFromHell() {
        super(StrikeFromHell.ID, StrikeFromHell.NAME, "cards/replay/strikeFromHell.png", StrikeFromHell.COST, StrikeFromHell.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ENEMY);
        this.tags.add(AbstractCard.CardTags.STRIKE);
        this.baseDamage = StrikeFromHell.ATTACK_DMG;
        this.baseMagicNumber = StrikeFromHell.DMG_INC;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void triggerOnExhaust() {
    	AbstractDungeon.actionManager.addToTop(new UnExhaustAction(this));
    	AbstractDungeon.actionManager.addToTop(new ModifyDamageAction(this.uuid, this.magicNumber));
    	if (this.upgraded) {
    		this.freeToPlayOnce = true;
    	}
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new StrikeFromHell();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeDamage(1);
            //this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(StrikeFromHell.ID);
        NAME = StrikeFromHell.cardStrings.NAME;
        DESCRIPTION = StrikeFromHell.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = StrikeFromHell.cardStrings.UPGRADE_DESCRIPTION;
    }
}
