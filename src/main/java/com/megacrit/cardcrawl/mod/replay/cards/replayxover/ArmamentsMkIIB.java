package com.megacrit.cardcrawl.mod.replay.cards.replayxover;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.replayxover.MistArmamentsBAction;
import com.megacrit.cardcrawl.mod.replay.relics.M_RuneBlood;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.dungeons.*;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class ArmamentsMkIIB extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Armaments MK-II-B";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    
    public ArmamentsMkIIB() {
        super(ID, ArmamentsMkIIB.NAME, "cards/replay/armaments.png", 1, ArmamentsMkIIB.DESCRIPTION, CardType.SKILL, (AbstractDungeon.player == null) ? CardColor.COLORLESS : CardColor.RED, (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(M_RuneBlood.ID)) ? CardRarity.UNCOMMON : CardRarity.SPECIAL, CardTarget.SELF);
        this.baseBlock = 5;
        ExhaustiveVariable.setBaseValue(this, 3);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new MistArmamentsBAction(this.upgraded));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new ArmamentsMkIIB();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = ArmamentsMkIIB.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = ArmamentsMkIIB.cardStrings.NAME;
        DESCRIPTION = ArmamentsMkIIB.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ArmamentsMkIIB.cardStrings.UPGRADE_DESCRIPTION;
    }
}
