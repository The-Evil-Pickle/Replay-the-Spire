package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.unique.LimbFromLimbAction;
import com.megacrit.cardcrawl.actions.unique.UnExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import replayTheSpire.patches.CardFieldStuff;

public class LimbFromLimb extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Limb From Limb";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 3;
    private static final int ATTACK_DMG = 24;
    private static final int DMG_UP = 4;
    private static final int THRESHOLD = 30;
    private static final int THRESHOLD_UP = 2;
    
    public LimbFromLimb() {
        super(ID, NAME, "cards/replay/replayBetaAttack.png", COST, DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = THRESHOLD;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardFieldStuff.CHAOS_NEGATIVE_MAGIC);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new LimbFromLimbAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), this));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new LimbFromLimb();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(DMG_UP);
            this.upgradeMagicNumber(THRESHOLD_UP);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
