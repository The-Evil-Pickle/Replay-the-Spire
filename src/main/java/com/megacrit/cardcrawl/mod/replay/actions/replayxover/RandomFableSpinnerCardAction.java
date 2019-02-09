package com.megacrit.cardcrawl.mod.replay.actions.replayxover;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.helpers.*;

import replayTheSpire.replayxover.slimeboundbs;

import com.megacrit.cardcrawl.cards.*;
import slimebound.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import java.util.*;

public class RandomFableSpinnerCardAction extends AbstractGameAction
{
    public boolean upgradeCard;
    
    public RandomFableSpinnerCardAction(final boolean upgraded) {
        this.upgradeCard = upgraded;
    }
    
    public void update() {
        final ArrayList<String> tmp = new ArrayList<String>();
        for (final Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
            if (c.getValue().hasTag(slimeboundbs.STUDY_FOREST)) {
                tmp.add(c.getKey());
            }
        }
        final AbstractCard cStudy = CardLibrary.cards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1)));
        if (this.upgradeCard) {
            cStudy.upgrade();
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cStudy));
        this.isDone = true;
    }
}
