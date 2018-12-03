package com.megacrit.cardcrawl.mod.replay.cards.replayxover.construct;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.cards.*;
import constructmod.patches.*;
import basemod.helpers.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.ReflectionPower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import constructmod.*;
import constructmod.cards.AbstractCycleCard;

import com.megacrit.cardcrawl.core.*;

public class MirrorSystem extends AbstractCycleCard
{
    public static final String ID = "ReplayTheSpireMod:Mirror System";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 1;
    
    public MirrorSystem() {
        super(MirrorSystem.ID, MirrorSystem.NAME, "cards/replay/replayBetaSkill.png", COST, MirrorSystem.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, 1);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.overheat = 5;
    }
    
    @Override
    public boolean canCycle() {
        return super.canCycle() && (!AbstractDungeon.player.hasPower("Dexterity") || AbstractDungeon.player.getPower("Dexterity").amount < 1);
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ReflectionPower(p, this.magicNumber), this.magicNumber));
    }
    
    public AbstractCard makeCopy() {
        return new MirrorSystem();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeOverheat(5);
        }
        else if (this.canUpgrade()) {
            this.megaUpgradeName();
            this.upgradeMagicNumber(1);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(MirrorSystem.ID);
        NAME = MirrorSystem.cardStrings.NAME;
        DESCRIPTION = MirrorSystem.cardStrings.DESCRIPTION;
    }
}
