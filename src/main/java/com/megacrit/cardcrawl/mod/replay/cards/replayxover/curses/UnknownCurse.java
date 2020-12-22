package com.megacrit.cardcrawl.mod.replay.cards.replayxover.curses;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.AbstractUnknownCard;

import static sneckomod.SneckoMod.getModID;
import static sneckomod.SneckoMod.makeCardPath;

import java.util.ArrayList;
import java.util.function.Predicate;

public class UnknownCurse extends AbstractUnknownCard {
    public final static String ID = "Replay:UnknownCurse";

    public UnknownCurse() {
        super(ID, "cards/replay/betaCurse.png", CardType.CURSE, CardRarity.CURSE);
        this.color = CardColor.CURSE;
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.color == CardColor.CURSE;
    }
    
    @Override
    public void upgrade() {}
    
    @Override
    public boolean canUpgrade() {
    	return false;
    }
    
    @Override
    public void replaceUnknown(Predicate<AbstractCard> funkyPredicate) {
        AbstractPlayer p = AbstractDungeon.player;
        boolean validCard;

        ArrayList<String> tmp = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (!c.isSeen)
                UnlockTracker.markCardAsSeen(c.cardID);
            AbstractCard q = c.makeCopy();
            validCard = !c.hasTag(CardTags.STARTER_STRIKE) && !c.hasTag(CardTags.STARTER_DEFEND) && c.type != CardType.STATUS && c.rarity != CardRarity.SPECIAL && c.color != AbstractDungeon.player.getCardColor() && !c.hasTag(SneckoMod.BANNEDFORSNECKO);
            if (this.upgraded) {
                if (!c.canUpgrade()) validCard = false;
                if (validCard) q.upgrade();
            }
            if (funkyPredicate.test(q)) {
                if (validCard) tmp.add(c.cardID);
            }
        }

        AbstractCard cUnknown;
        if (tmp.size() > 0) {
            cUnknown = CardLibrary.cards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1))).makeStatEquivalentCopy();
        } else {
            cUnknown = new com.megacrit.cardcrawl.cards.colorless.Madness();
        }

        if (this.upgraded) cUnknown.upgrade();
        if (cUnknown != null) {
            p.hand.removeCard(this);
            p.drawPile.removeCard(this);
            if (cUnknown.cardsToPreview == null)
                cUnknown.cardsToPreview = this.makeStatEquivalentCopy();
            AbstractDungeon.player.drawPile.addToRandomSpot(cUnknown);
        }
    }

    @Override
    public void replaceUnknownFromHand(Predicate<AbstractCard> funkyPredicate) {
        AbstractPlayer p = AbstractDungeon.player;
        boolean validCard;

        ArrayList<String> tmp = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (!c.isSeen)
                UnlockTracker.markCardAsSeen(c.cardID);
            AbstractCard q = c.makeCopy();
            validCard = !c.hasTag(SneckoMod.BANNEDFORSNECKO) && !c.hasTag(CardTags.HEALING) && !c.hasTag(CardTags.STARTER_STRIKE) && !c.hasTag(CardTags.STARTER_DEFEND) && c.type != CardType.STATUS && c.rarity != CardRarity.SPECIAL && c.color != AbstractDungeon.player.getCardColor();
            if (this.upgraded) {
                if (!c.canUpgrade()) validCard = false;
                if (validCard) q.upgrade();
            }
            if (funkyPredicate.test(q)) {
                if (validCard) tmp.add(c.cardID);
            }
        }

        AbstractCard cUnknown;
        if (tmp.size() > 0) {
            cUnknown = CardLibrary.cards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1))).makeStatEquivalentCopy();
        } else {
            cUnknown = new com.megacrit.cardcrawl.cards.colorless.Madness();
        }

        if (this.upgraded) cUnknown.upgrade();
        if (cUnknown != null) {
            p.limbo.removeCard(this);
            p.hand.removeCard(this);
            p.drawPile.removeCard(this);
            if (cUnknown.cardsToPreview == null)
                cUnknown.cardsToPreview = this.makeStatEquivalentCopy();
            AbstractDungeon.player.hand.addToTop(cUnknown);
        }
    }
}
