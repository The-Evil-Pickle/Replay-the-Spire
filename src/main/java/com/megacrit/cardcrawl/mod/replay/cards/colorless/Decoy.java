package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class Decoy extends CustomCard
{
    public static final String ID = "Replay:Decoy";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final int COST = -2;
    
    public Decoy() {
        super(ID, Decoy.NAME, "cards/replay/replayBetaSkill", COST, Decoy.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseBlock = 5;
        this.block = this.baseBlock;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, this.block), this.block));
    }
    
    @Override
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
        this.cantUseMessage = Decoy.EXTENDED_DESCRIPTION[0];
        return false;
    }
    
    @Override
    public void triggerOnManualDiscard() {
        this.use(AbstractDungeon.player, AbstractDungeon.getRandomMonster());
    }
    @Override
    public void triggerOnExhaust() {
        this.use(AbstractDungeon.player, AbstractDungeon.getRandomMonster());
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new Decoy();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = Decoy.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = Decoy.cardStrings.NAME;
        DESCRIPTION = Decoy.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = Decoy.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = Decoy.cardStrings.EXTENDED_DESCRIPTION;
    }
}
