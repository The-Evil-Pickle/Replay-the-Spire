package com.megacrit.cardcrawl.events.thecity;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.colorless.*;
import java.util.*;
import com.megacrit.cardcrawl.events.*;
import org.apache.logging.log4j.*;
import com.megacrit.cardcrawl.core.*;

public class MapScoutEvent extends AbstractImageEvent
{
    private static final Logger logger;
    public static final String ID = "ReplayMapScout";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final int GOLD_COST = 30;
    private static final String INTRO_BODY;
    private static final String ACCEPT_BODY;
    private static final String REJECT_BODY;
    private int screenNum;
    
    public MapScoutEvent() {
        super(MapScoutEvent.NAME, MapScoutEvent.INTRO_BODY, "images/events/addict.jpg");
        this.screenNum = 0;
        if (AbstractDungeon.player.gold >= GOLD_COST) {
            this.imageEventText.setDialogOption(Addict.OPTIONS[0] + 85 + Addict.OPTIONS[1], AbstractDungeon.player.gold < 85);
        }
        else {
            this.imageEventText.setDialogOption(Addict.OPTIONS[2] + 85 + Addict.OPTIONS[3], AbstractDungeon.player.gold < 85);
        }
        this.imageEventText.setDialogOption(Addict.OPTIONS[6]);
    }
    
    @Override
    protected void buttonEffect(final int buttonPressed) {
        switch (this.screenNum) {
            case 0: {
                switch (buttonPressed) {
                    case 2: {
                        this.logMetric("Ignored");
                        this.imageEventText.updateBodyText(Addict.REJECT_BODY);
                        this.imageEventText.updateDialogOption(0, Addict.OPTIONS[7]);
                        this.imageEventText.clearRemainingOptions();
                        break;
                    }
                    case 0: {
                        this.imageEventText.updateBodyText(Addict.ACCEPT_BODY);
                        if (AbstractDungeon.player.gold >= 85) {
                            this.logMetric("Obtained Relic");
                            AbstractDungeon.player.loseGold(85);
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier()));
                            this.imageEventText.updateDialogOption(0, Addict.OPTIONS[7]);
                            this.imageEventText.clearRemainingOptions();
                            break;
                        }
                        break;
                    }
                    case 1: {
                        this.imageEventText.updateBodyText(Addict.ACCEPT_BODY);
                        this.logMetric("Gave JAX");
                        final Iterator<AbstractCard> i = AbstractDungeon.player.masterDeck.group.iterator();
                        while (i.hasNext()) {
                            final AbstractCard e = i.next();
                            if (e instanceof JAX) {
                                i.remove();
                            }
                        }
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier()));
                        this.imageEventText.updateDialogOption(0, Addict.OPTIONS[7]);
                        this.imageEventText.clearRemainingOptions();
                        break;
                    }
                    default: {
                        Addict.logger.info("ERROR: Unhandled case " + buttonPressed);
                        break;
                    }
                }
                this.screenNum = 1;
                break;
            }
            case 1: {
                this.openMap();
                break;
            }
        }
    }
    
    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("ReplayMapScout");
        NAME = MapScoutEvent.eventStrings.NAME;
        DESCRIPTIONS = MapScoutEvent.eventStrings.DESCRIPTIONS;
        OPTIONS = MapScoutEvent.eventStrings.OPTIONS;
        INTRO_BODY = MapScoutEvent.DESCRIPTIONS[0];
        ACCEPT_BODY = MapScoutEvent.DESCRIPTIONS[1];
        REJECT_BODY = MapScoutEvent.DESCRIPTIONS[2];
    }
}
