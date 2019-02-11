package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import basemod.BaseMod;
import replayTheSpire.patches.DiscoverPatch;

public class DiscoverPerminantAction extends AbstractGameAction
{
    private boolean retrieveCard;
    private CardType cardType;
    private CardRarity cardRarity;
    private CardColor cardColor;

    public DiscoverPerminantAction() {
        this(null, null, null);
    }
    public DiscoverPerminantAction(final CardColor color) {
        this(null, null, color);
    }
    public DiscoverPerminantAction(final CardColor color, final CardRarity rarity) {
        this(null, rarity, color);
    }
    
    public DiscoverPerminantAction(final CardType type, final CardRarity rarity, final CardColor color) {
        this.retrieveCard = false;
        this.cardType = type;
        this.cardRarity = rarity;
        this.cardColor = color;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.cardType = type;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
        	DiscoverPatch.lookingForColor = this.cardColor;
        	DiscoverPatch.lookingForRarity = this.cardRarity;
        	DiscoverPatch.lookingForType = this.cardType;
            AbstractDungeon.cardRewardScreen.discoveryOpen();
            this.tickDuration();
            return;
        }
        if (!this.retrieveCard) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                final AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                disCard.current_x = -1000.0f * Settings.scale;
                if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                }
                else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                }
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(AbstractDungeon.cardRewardScreen.discoveryCard.makeCopy(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            this.retrieveCard = true;
        }
        this.tickDuration();
    }
}
