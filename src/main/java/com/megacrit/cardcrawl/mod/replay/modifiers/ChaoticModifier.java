package com.megacrit.cardcrawl.mod.replay.modifiers;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RunModStrings;

public class ChaoticModifier extends AbstractDailyMod
{
    public static final String ID = "replay:Chaotic";
    private static final RunModStrings modStrings = CardCrawlGame.languagePack.getRunModString(ID);
    public static final String NAME = modStrings.NAME;
    public static final String DESC = modStrings.DESCRIPTION;

    public ChaoticModifier() {
        super(ID, NAME, DESC, null, true);
        this.img = ImageMaster.loadImage("images/relics/cursedBlood.png");
    }
}