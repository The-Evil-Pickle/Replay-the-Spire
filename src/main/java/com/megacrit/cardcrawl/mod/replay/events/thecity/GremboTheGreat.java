package com.megacrit.cardcrawl.mod.replay.events.thecity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.mod.replay.cards.colorless.GrembosGang;
import com.megacrit.cardcrawl.mod.replay.relics.GremlinFood;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import replayTheSpire.ReplayTheSpireMod;

public class GremboTheGreat extends AbstractImageEvent
{
    public static final String ID = "Grembo";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String DIALOG_1;	
    private static final String DIALOG_2;
    private CurScreen screen;
    private AbstractCard card;
    private AbstractRelic relic;
    private int goldCost;
	private boolean upgradeOption;
    
    public GremboTheGreat() {
        super(GremboTheGreat.NAME, GremboTheGreat.DIALOG_1, "images/events/grembo.png");
        this.screen = CurScreen.INTRO;
        this.card = new GrembosGang();
        this.relic = new GremlinFood();
        this.goldCost = 250;
        this.imageEventText.setDialogOption(GremboTheGreat.OPTIONS[0]);
    }
    
    protected void buttonEffect(final int buttonPressed) {
        switch (this.screen) {
            case INTRO: {
                this.screen = CurScreen.CHOICE;
                this.imageEventText.updateBodyText(DIALOG_2);
                this.imageEventText.clearAllDialogs();
                if (AbstractDungeon.player.gold >= this.goldCost) {
                	this.imageEventText.setDialogOption(OPTIONS[2] + this.goldCost + OPTIONS[3] + OPTIONS[4], this.card.makeStatEquivalentCopy());
                }
                else {
                    this.imageEventText.setDialogOption(OPTIONS[1] + this.goldCost + OPTIONS[3] + "]", true);
                }
                if (AbstractDungeon.player.hasRelic(this.relic.relicId)) {
                	this.imageEventText.setDialogOption(OPTIONS[2] + this.relic.name + OPTIONS[4], this.card.makeStatEquivalentCopy());
                }
                else {
                    this.imageEventText.setDialogOption(OPTIONS[1] + this.relic.name + "]", true);
                }
                this.imageEventText.setDialogOption(OPTIONS[5]);
                break;
            }
            case CHOICE: {
                this.screen = CurScreen.RESULT;
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[0]);
                switch (buttonPressed) {
                    case 0: {
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        UnlockTracker.markCardAsSeen(this.card.cardID);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.card, Settings.WIDTH / 2, Settings.HEIGHT / 2));
                        AbstractDungeon.player.loseGold(this.goldCost);
                        break;
                    }
                    case 1: {
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        UnlockTracker.markCardAsSeen(this.card.cardID);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.card, Settings.WIDTH / 2, Settings.HEIGHT / 2));
                        ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_loseRelic(GremlinFood.ID);
                        break;
                    }
                    case 2: {
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        break;
                    }
                }
                break;
            }
            default: {
                this.openMap();
                break;
            }
        }
    }
    
    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Grembo");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
    }
    
    private enum CurScreen
    {
        INTRO, 
        CHOICE, 
        RESULT;
    }
}