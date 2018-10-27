package com.megacrit.cardcrawl.mod.replay.cards.red;

import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.relics.IronCore;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import replayTheSpire.ReplayTheSpireMod;

import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.*;

public class IC_FireWall extends AbstractCard
{
    public static final String ID = "Firewall";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 2;
    private static final int DEFENSE_GAINED = 12;
    private static final int FLAME_DAMAGE = 4;
    
    public IC_FireWall() {
        super("Firewall", IC_FireWall.NAME, null, "red/skill/flameBarrier", 2, IC_FireWall.DESCRIPTION, CardType.SKILL, (AbstractDungeon.player == null) ? AbstractCard.CardColor.COLORLESS : ((AbstractDungeon.player instanceof Defect) ? AbstractCard.CardColor.RED : AbstractCard.CardColor.BLUE), (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(IronCore.ID)) ? CardRarity.RARE : CardRarity.SPECIAL, CardTarget.SELF);
        this.baseBlock = 10;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.5f));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FireWallPower(p, this.magicNumber), this.magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new IC_FireWall();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Firewall");
        NAME = IC_FireWall.cardStrings.NAME;
        DESCRIPTION = IC_FireWall.cardStrings.DESCRIPTION;
    }
}
