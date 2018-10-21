package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import basemod.abstracts.CustomCard;
import replayTheSpire.patches.CardFieldStuff;
import replayTheSpire.variables.Exhaustive;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class SolidLightProjector extends CustomCard
{
    public static final String ID = "ReplaySolidLightProjector";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    
    public SolidLightProjector() {
        super(ID, SolidLightProjector.NAME, "cards/replay/replayBetaSkill.png", COST, SolidLightProjector.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 2;
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardFieldStuff.CHAOS_NEGATIVE_MAGIC);
        Exhaustive.setBaseValue(this, 2);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ReplayGainShieldingAction(p, p, this.block * (AbstractDungeon.player.discardPile.size() / this.magicNumber)));
        Exhaustive.increment(this);
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new SolidLightProjector();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.rawDescription = SolidLightProjector.DESCRIPTION;
        this.rawDescription += EXTENDED_DESCRIPTION[0] + (this.block * (AbstractDungeon.player.discardPile.size() / this.magicNumber)) + EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-2);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SolidLightProjector.cardStrings.NAME;
        DESCRIPTION = SolidLightProjector.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = SolidLightProjector.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = SolidLightProjector.cardStrings.EXTENDED_DESCRIPTION;
    }
}
