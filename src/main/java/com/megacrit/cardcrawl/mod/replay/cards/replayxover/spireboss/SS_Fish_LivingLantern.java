package com.megacrit.cardcrawl.mod.replay.cards.replayxover.spireboss;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.PondfishDrowning;

import slimebound.*;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;

import basemod.abstracts.CustomCard;
import replayTheSpire.replayxover.slimeboundbs;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class SS_Fish_LivingLantern extends CustomCard
{
    public static final String ID = "Replay:SS_fish_2";
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
    
    public SS_Fish_LivingLantern() {
        super(ID, SS_Fish_LivingLantern.NAME, "cards/replay/ss_fish_light.png", COST, SS_Fish_LivingLantern.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.SELF);
        this.tags.add(slimeboundbs.STUDY_PONDFISH);
        this.tags.add(SlimeboundMod.STUDY);
        this.magicNumber = this.baseMagicNumber = 7;
        ExhaustiveVariable.setBaseValue(this, 2);
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
        if (p.currentHealth < p.maxHealth/2) {
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
        }
    }
    
    public AbstractCard makeCopy() {
        return new SS_Fish_LivingLantern();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(5);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SS_Fish_LivingLantern.cardStrings.NAME;
        DESCRIPTION = SS_Fish_LivingLantern.cardStrings.DESCRIPTION;
    }
}
