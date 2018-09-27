package com.megacrit.cardcrawl.events.thecity;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.EventStrings;

public class ReplayMapScoutEvent  extends AbstractImageEvent
{
	public static String bannedBoss = "none lol";
    public static final String ID = "ReplayMapScout";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String DIALOG_1;	
    private static final String DIALOG_2;
    private CurScreen screen;
    int goldCost;
    private ArrayList<String> bossIDs;
    
    public ReplayMapScoutEvent() {
        super(NAME, DIALOG_1, "images/events/stuck.png");
        this.goldCost = 60;
        if (AbstractDungeon.ascensionLevel >= 15) {
        	this.goldCost += 20;
        }
        this.bossIDs = new ArrayList<String>();
        this.bossIDs.add("Awakened One");
        this.bossIDs.add("Time Eater");
        this.bossIDs.add("Donu and Deca");
        if (AbstractDungeon.player.gold >= this.goldCost) {
            this.imageEventText.setDialogOption(OPTIONS[2] + this.goldCost + OPTIONS[1] + OPTIONS[3]);
            this.imageEventText.setDialogOption(OPTIONS[2] + this.goldCost + OPTIONS[1] + OPTIONS[4]);
            this.imageEventText.setDialogOption(OPTIONS[2] + this.goldCost + OPTIONS[1] + OPTIONS[5]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[0] + this.goldCost + OPTIONS[1] + "]", true);
            this.imageEventText.setDialogOption(OPTIONS[0] + this.goldCost + OPTIONS[1] + "]", true);
            this.imageEventText.setDialogOption(OPTIONS[0] + this.goldCost + OPTIONS[1] + "]", true);
        }
        this.imageEventText.setDialogOption(OPTIONS[6]);
    }
    
    protected void buttonEffect(final int buttonPressed) {
        switch (this.screen) {
            case INTRO: {
                this.screen = CurScreen.CHOICE;
                if (buttonPressed >= this.bossIDs.size()) {
                    this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                } else {
                	bannedBoss = this.bossIDs.get(buttonPressed);
                    this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                    AbstractDungeon.player.loseGold(this.goldCost);
                }
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[7]);
                break;
            }
            default: {
                this.openMap();
                break;
            }
        }
    }
    
    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
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