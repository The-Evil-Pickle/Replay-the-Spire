package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import java.util.*;

public class Baseball extends AbstractRelic
{
    public static final String ID = "Baseball";
    
    public Baseball() {
        super("Baseball", "baseball.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.SOLID);
    }
    
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    public void onEquip() {
        final ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
        for (final AbstractCard abstractCard : AbstractDungeon.player.masterDeck.group) {
            if (abstractCard.cost > 0) {
                list.add(abstractCard);
            }
        }
        Collections.shuffle(list, new Random(AbstractDungeon.miscRng.randomLong()));
        if (!list.isEmpty()) {
            list.get(0).cost = 0;
            list.get(0).costForTurn = 0;
            list.get(0).upgradedCost = true;
            list.get(0).isCostModified = true;
            AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)list.get(0));
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(list.get(0).makeStatEquivalentCopy()));
        }
    }
    
    public AbstractRelic makeCopy() {
        return new Baseball();
    }
}
