package com.megacrit.cardcrawl.events.thebottom;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.core.*;
import java.util.*;

public class Stuck extends AbstractImageEvent
{
    public static final String ID = "Stuck";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String DIALOG_1;	
    private static final String DIALOG_2;
    private CurScreen screen;
    private AbstractCard card;
	private boolean upgradeOption;
    
    public Stuck() {
        super(Stuck.NAME, Stuck.DIALOG_1, "images/events/stuck.png");
        this.screen = CurScreen.INTRO;
        this.setCards();
        this.imageEventText.setDialogOption(Stuck.OPTIONS[0]);
    }
    
    private void setCards() {
        final ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
        for (final AbstractCard abstractCard : AbstractDungeon.player.masterDeck.group) {
            if (abstractCard.canUpgrade()) {
                list.add(abstractCard);
            }
        }
        Collections.shuffle(list, new Random(AbstractDungeon.miscRng.randomLong()));
		if (!list.isEmpty()) {
			this.card = list.get(0);
			this.upgradeOption = true;
		}
		else {
			upgradeOption = false;
			final ArrayList<AbstractCard> listB = new ArrayList<AbstractCard>();
			for (final AbstractCard abstractCard : AbstractDungeon.player.masterDeck.group) {
				listB.add(abstractCard);
			}
			Collections.shuffle(listB, new Random(AbstractDungeon.miscRng.randomLong()));
			this.card = listB.get(0);
		}
    }
    
    protected void buttonEffect(final int buttonPressed) {
        switch (this.screen) {
            case INTRO: {
                this.screen = CurScreen.CHOICE;
                this.imageEventText.updateBodyText(Stuck.DIALOG_2);
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(Stuck.OPTIONS[1] + FontHelper.colorString(this.card.name, "r"), this.card.makeStatEquivalentCopy());
                if (this.upgradeOption) {
					AbstractCard c = this.card.makeStatEquivalentCopy();
					c.upgrade();
                    this.imageEventText.setDialogOption(Stuck.OPTIONS[2] + FontHelper.colorString(this.card.name, "g"), c);
                }
                else {
                    this.imageEventText.setDialogOption(Stuck.OPTIONS[3], true);
                }
                break;
            }
            case CHOICE: {
                this.screen = CurScreen.RESULT;
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(Stuck.OPTIONS[4]);
                switch (buttonPressed) {
                    case 0: {
                        this.imageEventText.updateBodyText(Stuck.DESCRIPTIONS[2]);
                        AbstractDungeon.effectList.add(new PurgeCardEffect(this.card));
                        AbstractDungeon.player.masterDeck.removeCard(this.card);
                        break;
                    }
                    case 1: {
                        this.imageEventText.updateBodyText(Stuck.DESCRIPTIONS[3]);
						this.card.upgrade();
						AbstractDungeon.player.bottledCardUpgradeCheck(this.card);
                        AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(this.card.makeStatEquivalentCopy()));
						AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
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
        eventStrings = CardCrawlGame.languagePack.getEventString("Stuck");
        NAME = Stuck.eventStrings.NAME;
        DESCRIPTIONS = Stuck.eventStrings.DESCRIPTIONS;
        OPTIONS = Stuck.eventStrings.OPTIONS;
        DIALOG_1 = Stuck.DESCRIPTIONS[0];
        DIALOG_2 = Stuck.DESCRIPTIONS[1];
    }
    
    private enum CurScreen
    {
        INTRO, 
        CHOICE, 
        RESULT;
    }
}