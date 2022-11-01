package com.megacrit.cardcrawl.mod.replay.cards.purple;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;

public class AllOut extends CustomCard
{
    public static final String ID = "Replay:AllOut";
    private static final CardStrings cardStrings;
    
    public AllOut() {
        super(ID, AllOut.cardStrings.NAME, "cards/replay/all_out.png", 0, AllOut.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseDamage = 0;
        this.exhaust = true;
        this.selfRetain = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.baseDamage = p.currentBlock;
        this.calculateCardDamage(m);
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToBot(new LoseBlockAction(p, p, p.currentBlock));
        this.rawDescription = AllOut.cardStrings.DESCRIPTION;
        this.initializeDescription();
    }
    
    @Override
    public void applyPowers() {
        this.baseDamage = AbstractDungeon.player.currentBlock;
        super.applyPowers();
        this.rawDescription = AllOut.cardStrings.DESCRIPTION;
        this.rawDescription += AllOut.cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }
    
    @Override
    public void onMoveToDiscard() {
        this.rawDescription = AllOut.cardStrings.DESCRIPTION;
        this.initializeDescription();
    }
    
    @Override
    public void calculateCardDamage(final AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = AllOut.cardStrings.DESCRIPTION;
        this.rawDescription += AllOut.cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }
    
    @Override
    public boolean canUpgrade() {
    	return false;
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
        return new AllOut();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
