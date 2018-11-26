package com.megacrit.cardcrawl.mod.replay.cards.replayxover.beaked;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import beaked.*;
import beaked.patches.*;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.evacipated.cardcrawl.mod.stslib.variables.RefundVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import beaked.actions.*;
import beaked.cards.AbstractWitherCard;

import com.megacrit.cardcrawl.core.*;

public class OwlGaze extends AbstractWitherCard
{
    public static final String ID = "ReplayTheSpireMod:Owlgaze";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final int COST = 3;
    
    public OwlGaze() {
        super(ID, OwlGaze.NAME, "cards/replay/replayBetaSkill.png", COST, OwlGaze.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.BEAKED_YELLOW, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.NONE);
        this.baseMisc = 4;
        this.misc = this.baseMisc;
        this.witherEffect = EXTENDED_DESCRIPTION[0];
        this.witherAmount = 1;
        RefundVariable.setBaseValue(this, this.misc-1);
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new WitherAction(this));
        if (this.upgraded) {
        	for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
        		if (!mon.isDeadOrEscaped()) {
        			AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(mon, p));
        		}
        	}
        } else {
        	AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(AbstractDungeon.getRandomMonster(), p));
        }
    }
    @Override
    public void applyPowers() {
    	RefundVariable.setBaseValue(this, this.misc-1);
    	super.applyPowers();
    }
    
    public AbstractCard makeCopy() {
        return new OwlGaze();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.target = AbstractCard.CardTarget.ALL_ENEMY;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = OwlGaze.cardStrings.NAME;
        DESCRIPTION = OwlGaze.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = OwlGaze.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = OwlGaze.cardStrings.EXTENDED_DESCRIPTION;
    }
}
