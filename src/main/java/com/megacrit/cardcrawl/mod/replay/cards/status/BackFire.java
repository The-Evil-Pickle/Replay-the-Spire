package com.megacrit.cardcrawl.mod.replay.cards.status;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardColor;
import com.megacrit.cardcrawl.cards.CardQueueItem;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class BackFire extends AbstractCard
{
    public static final String ID = "BackFire";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = -2;
    public int UPG_DAMAGE;
    private int actualBurnDamage;
    
    public BackFire() {
        super(ID, NAME, "status/burn", "status/burn", -2, DESCRIPTION, CardType.STATUS, CardColor.COLORLESS, CardRarity.COMMON, CardTarget.SELF);
        this.actualBurnDamage = 4;
        this.UPG_DAMAGE = 6;
        this.baseDamage = 4;
        this.exhaust = true;
    }
    
    @Override
    public void calculateCardDamage(final AbstractMonster mo) {
        final AbstractPlayer player = AbstractDungeon.player;
        this.isDamageModified = false;
            float tmp = this.baseDamage;
            for (final AbstractPower p : player.powers) {
                tmp = p.atDamageGive(tmp, this.damageTypeForTurn);
            }
            for (final AbstractPower p : player.powers) {
                tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn);
            }
            if (tmp < 0.0f) {
                tmp = 0.0f;
            }
            if (this.baseDamage != MathUtils.floor(tmp)) {
                this.isDamageModified = true;
            }
            this.damage = MathUtils.floor(tmp);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (!this.dontTriggerOnUseCard && p.hasRelic("Medical Kit")) {
            this.useMedicalKit(p);
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, this.actualBurnDamage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
        }
    }
    
    @Override
    public void triggerWhenDrawn() {
        if (this.upgraded) {
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VulnerablePower(AbstractDungeon.player, 1, true), 1));
        }
    }
    
    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new BackFire();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.actualBurnDamage = this.UPG_DAMAGE;
            this.rawDescription = Burn.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BackFire");
        NAME = BackFire.cardStrings.NAME;
        DESCRIPTION = BackFire.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = BackFire.cardStrings.UPGRADE_DESCRIPTION;
    }
}