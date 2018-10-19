package com.megacrit.cardcrawl.cards.replayxover.beaked;

import com.megacrit.cardcrawl.localization.*;
import beaked.*;
import com.megacrit.cardcrawl.cards.*;
import beaked.patches.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.dungeons.*;
import beaked.actions.*;
import beaked.cards.AbstractWitherCard;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class WingsOfSteel extends AbstractWitherCard
{
    public static final String ID = "ReplayTheSpireMod:Wings of Steel";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int BLOCK_AMT = 6;
    private static final int BLOCK_BONUS = 2;
    private static final int WITHER_MINUS_BLOCK = 2;
    private static final int UPGRADE_PLUS_WITHER = -1;
    
    public WingsOfSteel() {
        super(ID, WingsOfSteel.NAME, "cards/replay/replayBetaSkill.png", COST, WingsOfSteel.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.BEAKED_YELLOW, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        final int n = BLOCK_AMT;
        this.misc = n;
        this.baseMisc = n;
        final int misc = this.misc;
        this.block = misc + BLOCK_BONUS;
        this.baseBlock = misc + BLOCK_BONUS;
        final int n2 = WITHER_MINUS_BLOCK;
        this.magicNumber = n2;
        this.baseMagicNumber = n2;
        this.witherEffect = EXTENDED_DESCRIPTION[0];
        this.linkWitherAmountToMagicNumber = true;
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new WitherAction(this));
        AbstractDungeon.actionManager.addToBottom(new ReplayGainShieldingAction(p, p, this.block));
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.misc), this.misc));
    }
    
    @Override
    public void applyPowers() {
        final int misc = this.misc;
        this.block = misc + BLOCK_BONUS;
        this.baseBlock = misc + BLOCK_BONUS;
        super.applyPowers();
    }
    
    public AbstractCard makeCopy() {
        return new WingsOfSteel();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeMagicNumber(UPGRADE_PLUS_WITHER);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = WingsOfSteel.cardStrings.NAME;
        DESCRIPTION = WingsOfSteel.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = WingsOfSteel.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = WingsOfSteel.cardStrings.EXTENDED_DESCRIPTION;
    }
}
