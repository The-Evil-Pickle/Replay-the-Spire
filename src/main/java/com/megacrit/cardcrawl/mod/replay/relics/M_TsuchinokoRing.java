package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.green.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.WristBlade;

import ThMod.powers.Marisa.ChargeUpPower;

public class M_TsuchinokoRing extends M_MistRelic
{
    public static final String ID = "m_TsuchinokoRing";
    
    public M_TsuchinokoRing() {
        super(ID, "snakeRing.png", LandingSound.MAGICAL, ThMod.patches.AbstractCardEnum.MARISA_COLOR, CardColor.GREEN);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 2));
    }
    @Override
	public void onUseCard(final AbstractCard card, final UseCardAction action) {
        final AbstractPlayer p = AbstractDungeon.player;
        final Boolean available = (card.type == AbstractCard.CardType.SKILL || card.costForTurn == 0 || card.freeToPlayOnce);
        int div = 8;
        if (p.hasRelic("SimpleLauncher")) {
            div = 6;
        }
        if (available) {
            this.flash();
            //ThMod.logger.info("MiniHakkero : Applying ChargeUpPower for using card : " + card.cardID);
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ChargeUpPower(AbstractDungeon.player, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }
	
    @Override
    public AbstractRelic makeCopy() {
        return new M_TsuchinokoRing();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new AThousandCuts());
        tmpPool.add(new Accuracy());
        tmpPool.add(new Adrenaline());
        tmpPool.add(new Backstab());
        tmpPool.add(new BladeDance());
        tmpPool.add(new BulletTime());
        tmpPool.add(new Burst());
        tmpPool.add(new CalculatedGamble());
        tmpPool.add(new Choke());
        tmpPool.add(new CloakAndDagger());
        tmpPool.add(new DaggerSpray());
        tmpPool.add(new DaggerThrow());
        tmpPool.add(new Deflect());
        tmpPool.add(new Distraction());
        tmpPool.add(new EndlessAgony());
        tmpPool.add(new EscapePlan());
        tmpPool.add(new Finisher());
        tmpPool.add(new Flechettes());
        tmpPool.add(new FlyingKnee());
        tmpPool.add(new InfiniteBlades());
        tmpPool.add(new MasterfulStab());
        tmpPool.add(new Nightmare());
        tmpPool.add(new Outmaneuver());
        tmpPool.add(new PhantasmalKiller());
        tmpPool.add(new Prepared());
        tmpPool.add(new RiddleWithHoles());
        tmpPool.add(new Setup());
        tmpPool.add(new Skewer());
        tmpPool.add(new Slice());
        tmpPool.add(new StormOfSteel());
        tmpPool.add(new ToolsOfTheTrade());
        tmpPool.add(new WellLaidPlans());
		return tmpPool;
	}
}
