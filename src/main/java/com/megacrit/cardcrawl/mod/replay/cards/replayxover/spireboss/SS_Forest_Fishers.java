package com.megacrit.cardcrawl.mod.replay.cards.replayxover.spireboss;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.ChooseAction;
import com.megacrit.cardcrawl.mod.replay.actions.unique.ShuffleNonStatusAction;
import com.megacrit.cardcrawl.mod.replay.actions.unique.ShuffleRareAction;
import com.megacrit.cardcrawl.mod.replay.powers.PondfishDrowning;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.RP_GiryaPower;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.RP_HourglassPower;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.RP_KunaiPower;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.RP_LetterOpenerPower;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.RP_OrichalcumPower;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.RP_ThreadPower;

import slimebound.*;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;

import basemod.abstracts.CustomCard;
import replayTheSpire.replayxover.slimeboundbs;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class SS_Forest_Fishers extends CustomCard
{
    public static final String ID = "Replay:SS_forest_6";
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final CardStrings cardStrings;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final int COST = 0;
    
    public SS_Forest_Fishers() {
        super(ID, SS_Forest_Fishers.NAME, "cards/replay/ss_forest_fish.png", COST, SS_Forest_Fishers.DESCRIPTION, AbstractCard.CardType.POWER, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.SELF);
        this.tags.add(slimeboundbs.STUDY_FOREST);
        this.tags.add(SlimeboundMod.STUDY);
        this.magicNumber = this.baseMagicNumber = 2;
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	final ChooseAction choice = new ChooseAction(this, m, EXTENDED_DESCRIPTION[0]);
    	choice.add(EXTENDED_DESCRIPTION[1], EXTENDED_DESCRIPTION[2], () -> {
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
            return;
        });
    	choice.add(EXTENDED_DESCRIPTION[3], EXTENDED_DESCRIPTION[4], () -> {
    		AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, 10));
            return;
        });
    	AbstractDungeon.actionManager.addToBottom(choice);
    }
    
    public AbstractCard makeCopy() {
        return new SS_Forest_Fishers();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SS_Forest_Fishers.cardStrings.NAME;
        DESCRIPTION = SS_Forest_Fishers.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = SS_Forest_Fishers.cardStrings.EXTENDED_DESCRIPTION;
    }
}
