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
import basemod.*;
import basemod.abstracts.*;

public class Specialist extends CustomCard
{
    public static final String ID = "Specialist";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION;
    private static final int POOL = 1;
	private static final int COST = 0;
    private static final int DMG = 2;
    private static final int UPG_DMG = 1;
    
    public Specialist() {
        super("Specialist", Specialist.NAME, "cards/replay/specialist.png", Specialist.COST, Specialist.DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = Specialist.DMG;
        this.magicNumber = this.baseMagicNumber;
		this.buildDescription();
    }
    
	private void buildDescription() {
		this.rawDescription = "";
		if (AbstractDungeon.player == null || AbstractDungeon.player.masterMaxOrbs > 0) {
			this.rawDescription = this.EXTENDED_DESCRIPTION[0];
		}
		if (this.upgraded) {
			this.rawDescription += this.UPGRADE_DESCRIPTION;
		} else {
			this.rawDescription += this.DESCRIPTION;
		}
		this.initializeDescription();
	}
	
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
		if (p.maxOrbs > 0 || p.masterMaxOrbs > 0) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FocusPower(p, -1), -1));
		}
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpecialistPower(p, this.magicNumber), this.magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new Specialist();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeMagicNumber(Specialist.UPG_DMG);
            this.isInnate = true;
            this.buildDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Specialist");
        NAME = Specialist.cardStrings.NAME;
        DESCRIPTION = Specialist.cardStrings.DESCRIPTION;
		UPGRADE_DESCRIPTION = Specialist.cardStrings.UPGRADE_DESCRIPTION;
		EXTENDED_DESCRIPTION = Specialist.cardStrings.EXTENDED_DESCRIPTION;
    }
}
