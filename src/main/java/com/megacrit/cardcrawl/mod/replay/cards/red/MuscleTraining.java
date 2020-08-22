package com.megacrit.cardcrawl.mod.replay.cards.red;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.abstracts.CustomCard;
import replayTheSpire.patches.CardFieldStuff;

public class MuscleTraining extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Muscle Training";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    
    public MuscleTraining() {
        super(ID, NAME, "cards/replay/weightTraining.png", COST, DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
		this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        ExhaustiveVariable.setBaseValue(this, 2);
        this.tags.add(CardFieldStuff.CHAOS_NEGATIVE_MAGIC);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
    	if (this.upgraded) {
    		AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, this.magicNumber, DamageInfo.DamageType.THORNS)));
    	} else {
    		AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, this.magicNumber));
    	}
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new MuscleTraining();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            ExhaustiveVariable.upgrade(this, 1);
            initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = MuscleTraining.cardStrings.NAME;
        DESCRIPTION = MuscleTraining.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = MuscleTraining.cardStrings.UPGRADE_DESCRIPTION;
    }
}
