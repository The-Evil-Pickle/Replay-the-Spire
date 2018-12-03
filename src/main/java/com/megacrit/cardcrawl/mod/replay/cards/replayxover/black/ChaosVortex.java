package com.megacrit.cardcrawl.mod.replay.cards.replayxover.black;

import infinitespire.abstracts.*;
import tobyspowerhouse.powers.TPH_ConfusionPower;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.common.ReplayGainShieldingAction;
import com.megacrit.cardcrawl.mod.replay.actions.unique.MultiExhumeAction;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.ChaosPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.core.*;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ChaosVortex extends BlackCard
{
    private static final String ID = "ReplayTheSpireMod:Chaos Vortex";
    private static final CardStrings cardStrings;
    private static final String NAME;
    private static final int COST = 0;
    private static final String DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION;
    
    public ChaosVortex() {
        super(ID, NAME, "cards/replay/replayBetaPowerDark.png", COST, DESCRIPTION, AbstractCard.CardType.POWER, AbstractCard.CardTarget.SELF);
        this.baseBlock = 10;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }
    
    public AbstractCard makeCopy() {
        return new ChaosVortex();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(5);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ReplayGainShieldingAction(p, p, this.block));
        if (this.upgraded) {
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, this.magicNumber), this.magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ChaosPower(p, this.magicNumber), this.magicNumber));
        for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
        	if (!mon.isDeadOrEscaped()) {
        		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new TPH_ConfusionPower(m)));
        	}
        }
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = ChaosVortex.cardStrings.NAME;
        DESCRIPTION = ChaosVortex.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ChaosVortex.cardStrings.UPGRADE_DESCRIPTION;
    }
}
