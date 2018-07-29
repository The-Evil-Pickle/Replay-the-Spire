package com.megacrit.cardcrawl.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

public class m_SnakeCloak extends AbstractRelic
{
    public static final String ID = "m_SnakeCloak";
    
    public m_SnakeCloak() {
        super(ID, "snakeRing.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
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
    public void onEquip() {
		AbstractDungeon.rareRelicPool.add("Tough Bandages");
		AbstractDungeon.rareRelicPool.add("Tingsha");
        final long startTime = System.currentTimeMillis();
        final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Prepared());
        tmpPool.add(new Acrobatics());
        tmpPool.add(new AllOutAttack());
        tmpPool.add(new CalculatedGamble());
        tmpPool.add(new DaggerThrow());
        tmpPool.add(new MasterfulStab());
        tmpPool.add(new Reflex());
        tmpPool.add(new StormOfSteel());
        tmpPool.add(new Tactician());
        tmpPool.add(new ToolsOfTheTrade());
        tmpPool.add(new UnderhandedStrike());
        tmpPool.add(new Unload());
        for (final AbstractCard c : tmpPool) {
			switch (c.rarity) {
				case COMMON: {
					AbstractDungeon.commonCardPool.addToTop(c);
					AbstractDungeon.srcCommonCardPool.addToBottom(c);
					continue;
				}
				case UNCOMMON: {
					AbstractDungeon.uncommonCardPool.addToTop(c);
					AbstractDungeon.srcUncommonCardPool.addToBottom(c);
					continue;
				}
				case RARE: {
					AbstractDungeon.rareCardPool.addToTop(c);
					AbstractDungeon.srcRareCardPool.addToBottom(c);
					continue;
				}
				default: {
					AbstractDungeon.uncommonCardPool.addToTop(c);
					AbstractDungeon.srcUncommonCardPool.addToBottom(c);
					continue;
				}
			}
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new m_SnakeCloak();
    }
}
