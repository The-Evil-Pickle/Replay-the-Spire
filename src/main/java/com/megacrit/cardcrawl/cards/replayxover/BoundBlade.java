package com.megacrit.cardcrawl.cards.replayxover;

import mysticmod.cards.*;
import mysticmod.patches.MysticTags;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.colorless.GhostFetch;
import com.megacrit.cardcrawl.cards.colorless.Shiv;
import com.megacrit.cardcrawl.actions.*;
import mysticmod.powers.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;

import basemod.helpers.CardTags;

import com.megacrit.cardcrawl.core.*;

public class BoundBlade extends AbstractMysticCard
{
    public static final String ID = "m_BoundBlade";
    public static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    
    public BoundBlade() {
        super(ID, BoundBlade.NAME, "mysticmod/images/cards/diviningblow.png", 0, BoundBlade.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.NONE);
        this.crystalBallToggle = false;
        this.tags.add(MysticTags.IS_CANTRIP);
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractCard shiv = new Shiv();
    	if (this.upgraded) {
    		shiv.upgrade();
    	}
    	AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(shiv, 1));
    	AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
        if (!p.hasPower("mysticmod:SpellsPlayedPower") || (p.hasPower("mysticmod:SpellsPlayedPower") && p.getPower("mysticmod:SpellsPlayedPower").amount <= 2)) {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new SpellsPlayed((AbstractCreature)p, 1), 1));
        }
    }

    @Override
    public boolean isSpell() {
        if (AbstractDungeon.player == null || !AbstractDungeon.player.hasPower("mysticmod:SpellsPlayedPower") || AbstractDungeon.player.getPower("mysticmod:SpellsPlayedPower").amount <= 2) {
            this.tags.add(MysticTags.IS_SPELL);
            return true;
        }
        this.tags.remove(MysticTags.IS_SPELL);
        return super.isSpell();
    }
    
    public AbstractCard makeCopy() {
        return new BoundBlade();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = GhostFetch.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = BoundBlade.cardStrings.NAME;
        DESCRIPTION = BoundBlade.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = BoundBlade.cardStrings.UPGRADE_DESCRIPTION;
    }
}
