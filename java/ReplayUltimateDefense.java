package com.megacrit.cardcrawl.cards.colorless;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import basemod.abstracts.*;

public class ReplayUltimateDefense extends CustomCard
{
    public static final String ID = "ReplayUltimateDefense";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 4;
    
    public ReplayUltimateDefense() {
        super("ReplayUltimateDefense", ReplayUltimateDefense.NAME, "cards/replay/replayBetaPower.png", ReplayUltimateDefense.COST, ReplayUltimateDefense.DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
		this.baseBlock = 5;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
		if (this.upgraded) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
		}
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, 5), 5));
		AbstractDungeon.actionManager.addToBottom(new ReplayGainShieldingAction(p, p, this.block));
		
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new ReplayUltimateDefense();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.upgradeBlock(3);
            this.rawDescription = ReplayUltimateDefense.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ReplayUltimateDefense");
        NAME = ReplayUltimateDefense.cardStrings.NAME;
        DESCRIPTION = ReplayUltimateDefense.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ReplayUltimateDefense.cardStrings.UPGRADE_DESCRIPTION;
    }
}
