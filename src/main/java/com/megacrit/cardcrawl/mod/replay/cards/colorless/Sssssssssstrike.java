package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import basemod.abstracts.*;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;

public class Sssssssssstrike extends CustomCard
{
    public static final String ID = "Replay:Sssssssssstrike";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = -1;
    
    public Sssssssssstrike() {
        super(ID, Sssssssssstrike.NAME, "cards/replay/replayBetaAttack.png", -1, Sssssssssstrike.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
        SneckoField.snecko.set(this, true);
        this.tags.add(AbstractCard.CardTags.STRIKE);
        this.baseDamage = 4;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	for (int i=0; i <= this.cost; i++) {
    		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    	}
    	AbstractDungeon.actionManager.addToBottom(new ReplayRefundAction(this, this.magicNumber));
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
        }
    }
    
    public AbstractCard makeCopy() {
        return new Sssssssssstrike();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = Sssssssssstrike.cardStrings.NAME;
        DESCRIPTION = Sssssssssstrike.cardStrings.DESCRIPTION;
    }
}
