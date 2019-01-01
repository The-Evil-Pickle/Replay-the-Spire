package com.megacrit.cardcrawl.mod.replay.actions.common;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.unlock.*;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.*;
import gluttonmod.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import basemod.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;

public class EchoToDrawAction extends AbstractGameAction
{
    private static final float DURATION_PER_CARD = 0.35f;
    private AbstractCard c;
    private static final float PADDING;
    private int discount;
    
    public EchoToDrawAction(final AbstractCard card) {
        this(card, 1, 0);
    }
    
    public EchoToDrawAction(final AbstractCard card, final int amount) {
        this(card, amount, 0);
    }
    
    public EchoToDrawAction(final AbstractCard card, final int amount, final int discount) {
        UnlockTracker.markCardAsSeen(card.cardID);
        this.amount = amount;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = DURATION_PER_CARD;
        this.c = card;
        this.discount = discount;
    }
    
    private AbstractCard echoCard() {
        final AbstractCard card = this.c.makeStatEquivalentCopy();
        card.name = "Echo: " + card.name;
        card.exhaust = true;
        card.isEthereal = true;
        AlwaysRetainField.alwaysRetain.set(card, false);
        card.retain = false;
        if (card.cost >= 0 && this.discount > 0) {
            card.updateCost(-1 * this.discount);
        }
        if (card instanceof AbstractGluttonCard) {
            ((AbstractGluttonCard)card).onEchoed();
        }
        return card;
    }
    
    public void update() {
        if (this.amount == 0) {
            this.isDone = true;
            return;
        }
        this.addToDiscard();
        if (this.amount > 0) {
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.8f));
        }
        this.isDone = true;
    }
    
    private void addToDiscard() {
        switch (this.amount) {
            case 0: {
                break;
            }
            case 1: {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(this.echoCard(), Settings.WIDTH / 2.0f + (EchoToDrawAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f, true, true));
                break;
            }
            case 2: {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(this.echoCard(), Settings.WIDTH * 0.5f - (EchoToDrawAction.PADDING + AbstractCard.IMG_WIDTH * 0.5f), Settings.HEIGHT * 0.5f, true, true));
                AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(this.echoCard(), Settings.WIDTH * 0.5f + (EchoToDrawAction.PADDING + AbstractCard.IMG_WIDTH * 0.5f), Settings.HEIGHT * 0.5f, true, true));
                break;
            }
            case 3: {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(this.echoCard(), Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.5f, true, true));
                AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(this.echoCard(), Settings.WIDTH * 0.5f - (EchoToDrawAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5f, true, true));
                AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(this.echoCard(), Settings.WIDTH * 0.5f + (EchoToDrawAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5f, true, true));
                break;
            }
            default: {
                for (int i = 0; i < this.amount; ++i) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(this.echoCard(), MathUtils.random(Settings.WIDTH * 0.2f, Settings.WIDTH * 0.8f), MathUtils.random(Settings.HEIGHT * 0.3f, Settings.HEIGHT * 0.7f), true, true));
                }
                break;
            }
        }
    }
    
    static {
        PADDING = 25.0f * Settings.scale;
    }
}
