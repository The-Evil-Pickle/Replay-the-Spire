package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
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
import basemod.helpers.TooltipInfo;

public class AwakenedRitual extends CustomCard
{
    public static final String ID = "Crow Ritual";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public ArrayList<TooltipInfo> tips;
    
    public AwakenedRitual() {
        super("Crow Ritual", AwakenedRitual.NAME, "cards/replay/crow_ritual.png", 0, AwakenedRitual.DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF);
		this.baseDamage = 42;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.tips = new ArrayList<TooltipInfo>();
        this.cardsToPreview = new RitualComponent();
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AwakenedRitualPower(p)));
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new RitualComponent(), this.magicNumber, true, false));
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new RitualComponent(), this.magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new AwakenedRitual();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    /*@Override
    public List<TooltipInfo> getCustomTooltips() {
    	this.tips.clear();
        this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1]));
        return this.tips;
    }*/
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Crow Ritual");
        NAME = AwakenedRitual.cardStrings.NAME;
        DESCRIPTION = AwakenedRitual.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = AwakenedRitual.cardStrings.EXTENDED_DESCRIPTION;
    }
}
