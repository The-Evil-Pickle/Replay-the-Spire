package com.megacrit.cardcrawl.mod.replay.cards.replayxover.black;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;

public class DarkDeal extends CustomCard implements StartupCard
{
    public static final String ID = "Replay:Dark Deal";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = -2;
    
    public DarkDeal() {
        super(ID, NAME, "cards/replay/replayBetaSkill.png", COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.NONE);
        this.isEthereal = true;
        this.tags.add(CardTags.HEALING);//let's not generate this in combat, that would be silly
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	//does fuck all lol
    }
    @Override
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
        return false;
    }
    @Override
    public AbstractCard makeCopy() {
        return new DarkDeal();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            GraveField.grave.set(this, true);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

	@Override
	public boolean atBattleStartPreDraw() {
		final AbstractCard c = infinitespire.helpers.CardHelper.getRandomBlackCard().makeCopy();
		if (this.upgraded) {
			c.upgrade();
		}
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, 1, true, true));
		return true;
	}
}
