package com.megacrit.cardcrawl.mod.replay.cards.green;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.DiscardByTypeAction;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.evacipated.cardcrawl.mod.stslib.variables.RefundVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardColor;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
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
    private static final int COST = 3;
    
    public SneakUp() {
        super("Sneak Up", SneakUp.NAME, "cards/replay/sneakUp.png", SneakUp.COST, SneakUp.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        this.isEthereal = true;
        this.isInnate = true;
		RefundVariable.setBaseValue(this, 2);
		this.baseMagicNumber = 1;
		this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
		//if (!this.upgraded) {
			AbstractDungeon.actionManager.addToBottom(new DiscardByTypeAction(p, AbstractCard.CardType.ATTACK, -1));
		//} else {
		//	
		//}
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(this.cost - 1);
            RefundVariable.upgrade(this, -1);
            //this.rawDescription = SneakUp.UPGRADE_DESCRIPTION;
            //this.initializeDescription();
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
