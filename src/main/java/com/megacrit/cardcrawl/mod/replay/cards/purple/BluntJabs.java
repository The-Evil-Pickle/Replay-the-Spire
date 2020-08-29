package com.megacrit.cardcrawl.mod.replay.cards.purple;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.ReplayGainShieldingAction;
import com.megacrit.cardcrawl.mod.replay.actions.unique.ScryToZeroAction;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.ReflectionPower;

import basemod.abstracts.CustomCard;
import replayTheSpire.ReplayTheSpireMod;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BluntJabs extends CustomCard
{
    public static final String ID = "Replay:BluntJabs";
    private static final CardStrings cardStrings;
    
    public BluntJabs() {
        super(ID, BluntJabs.cardStrings.NAME, "cards/replay/replayBetaAttack.png", 1, BluntJabs.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = 4;
        this.damage = this.baseDamage;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractCard pCard = null;
    	for (AbstractCard c : p.hand.group) {
    		if (c == this) {
    			if (pCard != null) {
    				pCard.retain = true;
    			}
    		} else if (pCard == this) {
    			c.retain = true;
    		}
    		pCard = c;
    	}
    	for (int i=0; i < this.magicNumber; i++) {
    		this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    	}
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new BluntJabs();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
