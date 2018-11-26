package com.megacrit.cardcrawl.mod.replay.cards.replayxover.marisa;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;

import ThMod.ThMod;
import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.core.*;

public class LightBash extends CustomCard
{
    public static final String ID = "Replay:Light Bash";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    
    public LightBash() {
        super(ID, LightBash.NAME, "cards/replay/bash.png", 1, LightBash.DESCRIPTION, CardType.ATTACK, (AbstractDungeon.player == null) ? AbstractCard.CardColor.COLORLESS : CardColor.RED, CardRarity.BASIC, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (ThMod.Amplified(this, 1)) {
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
        }
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeMagicNumber(1);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new LightBash();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = LightBash.cardStrings.NAME;
        DESCRIPTION = LightBash.cardStrings.DESCRIPTION;
    }
}
