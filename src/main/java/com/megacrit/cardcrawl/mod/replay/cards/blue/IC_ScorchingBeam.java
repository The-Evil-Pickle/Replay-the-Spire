package com.megacrit.cardcrawl.mod.replay.cards.blue;

import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.defect.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.mod.replay.relics.IronCore;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.*;

import basemod.abstracts.*;
import replayTheSpire.ReplayTheSpireMod;

public class IC_ScorchingBeam extends CustomCard
{
    public static final String ID = "Scorching Beam";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 2;
    
    public IC_ScorchingBeam() {
        super("Scorching Beam", IC_ScorchingBeam.NAME, "cards/replay/scorching_beam.png", IC_ScorchingBeam.COST, IC_ScorchingBeam.DESCRIPTION, CardType.ATTACK, (AbstractDungeon.player == null) ? AbstractCard.CardColor.COLORLESS : ((AbstractDungeon.player instanceof Defect) ? AbstractCard.CardColor.RED : AbstractCard.CardColor.BLUE), (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(IronCore.ID)) ? CardRarity.UNCOMMON : CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        this.baseDamage = 8;
        this.isMultiDamage = true;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1f));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
		for (int i=0; i<this.magicNumber; i++) {		
			final AbstractOrb orb = new HellFireOrb();
			AbstractDungeon.actionManager.addToBottom(new ChannelAction(orb));	
		}
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new IC_ScorchingBeam();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
            this.upgradeMagicNumber(1);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Scorching Beam");
        NAME = IC_ScorchingBeam.cardStrings.NAME;
        DESCRIPTION = IC_ScorchingBeam.cardStrings.DESCRIPTION;
    }
}
