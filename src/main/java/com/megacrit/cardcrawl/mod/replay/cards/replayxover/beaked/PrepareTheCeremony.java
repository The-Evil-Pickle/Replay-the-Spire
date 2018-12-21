package com.megacrit.cardcrawl.mod.replay.cards.replayxover.beaked;

import basemod.abstracts.*;
import basemod.helpers.TooltipInfo;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.cards.colorless.RitualComponent;

import beaked.*;
import beaked.patches.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;

import java.util.ArrayList;
import java.util.List;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import beaked.powers.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class PrepareTheCeremony extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Prepare the Ceremony";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final int COST = 1;
    public static final int DAMAGE = 2;
    public static final int DRAW = 2;
    public ArrayList<TooltipInfo> tips;
    
    public PrepareTheCeremony() {
        super(ID, PrepareTheCeremony.NAME, "cards/replay/replayBetaSkill.png", COST, PrepareTheCeremony.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.BEAKED_YELLOW, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        this.baseMagicNumber = DRAW;
        this.magicNumber = this.baseMagicNumber;
        this.tips = new ArrayList<TooltipInfo>();
        ExhaustiveVariable.setBaseValue(this, 2);
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new RitualComponent(), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindmillPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
    	this.tips.clear();
        this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1]));
        return this.tips;
    }
    public AbstractCard makeCopy() {
        return new PrepareTheCeremony();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeMagicNumber(1);
            ExhaustiveVariable.upgrade(this, 1);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = PrepareTheCeremony.cardStrings.NAME;
        DESCRIPTION = PrepareTheCeremony.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = PrepareTheCeremony.cardStrings.EXTENDED_DESCRIPTION;
    }
}
