package com.megacrit.cardcrawl.mod.replay.cards.replayxover.marisa;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.defect.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.mod.replay.relics.IronCore;
import com.megacrit.cardcrawl.mod.replay.relics.M_SpellCore;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import ThMod.ThMod;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
//import com.megacrit.cardcrawl.cards.CardColor;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import basemod.abstracts.*;
import replayTheSpire.ReplayTheSpireMod;

public class SparkCircuitry extends CustomCard
{
    public static final String ID = "Replay:Spark Circuitry";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 1;
    
    public SparkCircuitry() {
        super(ID, SparkCircuitry.NAME, "cards/replay/replayBetaSkill.png", SparkCircuitry.COST, SparkCircuitry.DESCRIPTION, CardType.SKILL, (AbstractDungeon.player == null) ? AbstractCard.CardColor.COLORLESS : CardColor.BLUE, (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(M_SpellCore.ID)) ? CardRarity.COMMON : CardRarity.SPECIAL, CardTarget.SELF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 2;
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
        this.exhaust = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (ThMod.Amplified(this, 1)) {
        	for (int i = 0; i < this.magicNumber; i++) {
        		AbstractDungeon.actionManager.addToBottom(new ChannelAction(new CrystalOrb()));
        	}
        }
        for (int i = 0; i < this.magicNumber; i++) {
        	AbstractDungeon.actionManager.addToBottom(new ChannelAction(new ManaSparkOrb()));
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new SparkCircuitry();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SparkCircuitry.cardStrings.NAME;
        DESCRIPTION = SparkCircuitry.cardStrings.DESCRIPTION;
    }
}
