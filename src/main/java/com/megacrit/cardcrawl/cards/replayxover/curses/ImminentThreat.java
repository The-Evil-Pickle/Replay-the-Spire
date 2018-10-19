package com.megacrit.cardcrawl.cards.replayxover.curses;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import chronomuncher.actions.TransformCardPermanently;
import chronomuncher.cards.AbstractSwitchCard;

public class ImminentThreat extends AbstractSwitchCard {
	public static final String ID = "ReplayTheSpireMod:Imminent Threat";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    public static final int DAMAGEAMT = 2;
    public ImminentThreat() {
        super(ID, NAME, "cards/replay/betaCurse.png", COST, DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.SELF, VengefulThreat.class);
        this.isInnate = true;
        GraveField.grave.set(this, false);
        this.exhaust = true;
        this.baseMagicNumber = DAMAGEAMT;
        this.magicNumber = this.baseMagicNumber;
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, this.magicNumber, DamageInfo.DamageType.THORNS)));
        AbstractDungeon.actionManager.addToBottom(new TransformCardPermanently(p, this, new VengefulThreat()));
    }
    
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
        return this.cardPlayable(m) && this.hasEnoughEnergy();
    }
    
    public AbstractCard makeCopy() {
        return new ImminentThreat();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

	@Override
	public void upgrade() {}
}
