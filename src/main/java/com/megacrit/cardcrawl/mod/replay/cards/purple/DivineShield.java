package com.megacrit.cardcrawl.mod.replay.cards.purple;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.ReplayGainShieldingAction;
import com.megacrit.cardcrawl.mod.replay.actions.unique.ScryToZeroAction;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.ReflectionPower;

import basemod.abstracts.CustomCard;
import replayTheSpire.ReplayTheSpireMod;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DivineShield extends CustomCard
{
    public static final String ID = "Replay:DivineShield";
    private static final CardStrings cardStrings;
    
    public DivineShield() {
        super(ID, DivineShield.cardStrings.NAME, "cards/replay/replayBetaSkill.png", 1, DivineShield.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = 7;
        this.block = this.baseBlock;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	if (AbstractDungeon.player.currentBlock == 0 && ReplayTheSpireMod.playerShielding < 1) {
            this.addToBot(new ReplayGainShieldingAction(p, p, this.block));
        } else {
        	this.addToBot(new ApplyPowerAction(p, p, new ReflectionPower(p, this.magicNumber), this.magicNumber));
        }
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
            this.upgradeMagicNumber(1);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new DivineShield();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
