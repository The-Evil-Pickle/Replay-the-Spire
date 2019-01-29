package com.megacrit.cardcrawl.mod.replay.cards.blue;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import basemod.abstracts.*;

public class ReroutePower extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Reroute Power";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    
    public ReroutePower() {
        super(ID, ReroutePower.NAME, "cards/replay/replayBetaSkill.png", ReroutePower.COST, ReroutePower.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.NONE);
		this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }
	
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	if (p.hasPower(StrengthPower.POWER_ID)) {
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FocusPower(p, p.getPower(StrengthPower.POWER_ID).amount), p.getPower(StrengthPower.POWER_ID).amount));
    		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, StrengthPower.POWER_ID, p.getPower(StrengthPower.POWER_ID).amount));
    	}
    	if (p.hasPower(FocusPower.POWER_ID)) {
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, p.getPower(FocusPower.POWER_ID).amount), p.getPower(FocusPower.POWER_ID).amount));
    		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, FocusPower.POWER_ID, p.getPower(FocusPower.POWER_ID).amount));
    	}
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new ReroutePower();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = ReroutePower.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.retain = true;
            AlwaysRetainField.alwaysRetain.set(this, true);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = ReroutePower.cardStrings.NAME;
        DESCRIPTION = ReroutePower.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ReroutePower.cardStrings.UPGRADE_DESCRIPTION;
    }
}
