package com.megacrit.cardcrawl.mod.replay.cards.replayxover.construct;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.replayxover.MistArmamentsAction;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import constructmod.cards.AbstractConstructCard;
import constructmod.cards.Reinforce;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class ArmamentsMkII extends AbstractConstructCard
{
    public static final String ID = "ReplayTheSpireMod:Armaments MK-II";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String M_UPGRADE_DESCRIPTION;
    
    public ArmamentsMkII() {
        super(ID, ArmamentsMkII.NAME, "img/cards/" + Reinforce.ID + ".png", 1, ArmamentsMkII.DESCRIPTION, CardType.SKILL, (AbstractDungeon.player == null) ? CardColor.COLORLESS : CardColor.RED, CardRarity.SPECIAL, CardTarget.SELF, 1);
        this.baseBlock = 5;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new MistArmamentsAction(this.upgraded, this.megaUpgraded));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new ArmamentsMkII();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.name = "Armaments MK-III";
            this.rawDescription = ArmamentsMkII.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
        else if (this.canUpgrade()) {
            this.megaUpgradeName();
            this.name = "Armaments MK-IV";
            this.rawDescription = ArmamentsMkII.M_UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = ArmamentsMkII.cardStrings.NAME;
        DESCRIPTION = ArmamentsMkII.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ArmamentsMkII.cardStrings.UPGRADE_DESCRIPTION;
        M_UPGRADE_DESCRIPTION = ArmamentsMkII.cardStrings.EXTENDED_DESCRIPTION[0];
    }
}
