package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import basemod.abstracts.*;

public class BasicCrystalCard extends CustomCard
{
    public static final String ID = "BasicCrystalCard";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    
    public BasicCrystalCard() {
        super("BasicCrystalCard", BasicCrystalCard.NAME, "cards/replay/replayBetaSkill.png", BasicCrystalCard.COST, BasicCrystalCard.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new CrystalOrb()));
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(AbstractOrb.getRandomOrb(true)));
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new BasicCrystalCard();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.showEvokeOrbCount = 2;
			this.rawDescription = BasicCrystalCard.UPGRADE_DESCRIPTION;
			this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BasicCrystalCard");
        NAME = BasicCrystalCard.cardStrings.NAME;
        DESCRIPTION = BasicCrystalCard.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = BasicCrystalCard.cardStrings.UPGRADE_DESCRIPTION;
    }
}
