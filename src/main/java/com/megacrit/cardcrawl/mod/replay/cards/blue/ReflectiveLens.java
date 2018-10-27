package com.megacrit.cardcrawl.mod.replay.cards.blue;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.defect.*;
import com.megacrit.cardcrawl.mod.replay.actions.unique.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardColor;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;

import basemod.abstracts.*;

public class ReflectiveLens extends CustomCard
{
    public static final String ID = "Reflective Lens";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    
    public ReflectiveLens() {
        super(ReflectiveLens.ID, ReflectiveLens.NAME, "cards/replay/mirrorShield.png", ReflectiveLens.COST, ReflectiveLens.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
		this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new GlassOrb()));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ReflectiveLensPower(p, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new CrystalOrbUpdateAction());
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new ReflectiveLens();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
			//this.upgradeMagicNumber(2);
			this.upgradeBaseCost(ReflectiveLens.COST - 1);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ReflectiveLens.ID);
        NAME = ReflectiveLens.cardStrings.NAME;
        DESCRIPTION = ReflectiveLens.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ReflectiveLens.cardStrings.UPGRADE_DESCRIPTION;
    }
}
