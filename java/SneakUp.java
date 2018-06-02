package com.megacrit.cardcrawl.cards.green;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.core.*;
import basemod.*;
import basemod.abstracts.*;

public class SneakUp extends CustomCard
{
    public static final String ID = "Sneak Up";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    
    public SneakUp() {
        super("Sneak Up", SneakUp.NAME, "cards/replay/sneakUp.png", SneakUp.COST, SneakUp.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        this.isEthereal = true;
        this.isInnate = true;
		
		this.baseMagicNumber = 1;
		this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
		if (!this.upgraded) {
			AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, 1, false));
		}
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = SneakUp.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new SneakUp();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Sneak Up");
        NAME = SneakUp.cardStrings.NAME;
        DESCRIPTION = SneakUp.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = SneakUp.cardStrings.UPGRADE_DESCRIPTION;
    }
}
