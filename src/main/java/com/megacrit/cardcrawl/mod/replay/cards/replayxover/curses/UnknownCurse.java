package com.megacrit.cardcrawl.mod.replay.cards.replayxover.curses;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

    public static ArrayList<String> unknownCurseReplacements = new ArrayList();

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.type == CardType.CURSE;
    }

    @Override
    public ArrayList<String> myList() {
        return unknownCurseReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        //TODO - Overbanner art
        return SneckoMod.overBanner1;
    }
}
