package com.megacrit.cardcrawl.screens.select;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.vfx.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.unlock.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import de.robojumper.ststwitch.*;
import basemod.*;

public class GenericRelicSelectScreen
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private boolean isDone;
    public ArrayList<AbstractRelic> relics;
    public MenuCancelButton cancelButton;
    private static final String SELECT_MSG;
    private Texture smokeImg;
    private float shineTimer;
    private static final float SHINE_INTERAL = 0.1f;
    private static final float SLOT_1_X;
    private static final float SLOT_1_Y;
    private static final float SLOT_2_X;
    private static final float SLOT_2_Y;
    private static final float SLOT_3_X;
    private static final float SLOT_3_Y;
    boolean isVoting;
    boolean mayVote;
    
    public GenericRelicSelectScreen() {
        this.isDone = false;
        this.relics = new ArrayList<AbstractRelic>();
        this.cancelButton = new MenuCancelButton();
        this.shineTimer = 0.0f;
        this.isVoting = false;
        this.mayVote = false;
    }
    
    public void update() {
		/*
        this.shineTimer -= Gdx.graphics.getDeltaTime();
        if (this.shineTimer < 0.0f) {
            this.shineTimer = 0.1f;
            AbstractDungeon.topLevelEffects.add(new BossChestShineEffect());
            AbstractDungeon.topLevelEffects.add(new BossChestShineEffect(MathUtils.random(0.0f, Settings.WIDTH), MathUtils.random(0.0f, Settings.HEIGHT - 128.0f * Settings.scale)));
        }
		*/
        for (final AbstractRelic r : this.relics) {
            r.update();
            if (r.isObtained) {
                this.relicObtainLogic(r);
            }
        }
        if (this.isDone) {
            this.isDone = false;
            //this.mayVote = false;
            //this.updateVote();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            this.relics.clear();
			//AbstractDungeon.genericScreenOverlayReset();
			AbstractDungeon.dynamicBanner.hide();
        }
        this.updateCancelButton();
    }
    
    private void relicObtainLogic(final AbstractRelic r) {
        final HashMap<String, Object> choice = new HashMap<String, Object>();
        final ArrayList<String> notPicked = new ArrayList<String>();
        choice.put("picked", r.relicId);
        final AbstractRoom curRoom = (AbstractRoom)AbstractDungeon.getCurrRoom();
        //curRoom.choseRelic = true;
        for (final AbstractRelic otherRelics : this.relics) {
            if (otherRelics != r) {
                notPicked.add(otherRelics.relicId);
            }
        }
        choice.put("not_picked", notPicked);
        //CardCrawlGame.metricData.boss_relics.add(choice);
        this.isDone = true;
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 99999.0f;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        if (r.relicId.equals("Black Blood") || r.relicId.equals("Ring of the Serpent")) {
            r.instantObtain(AbstractDungeon.player, 0, false);
            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25f;
        }
    }
    
    private void relicSkipLogic() {
		/*
        if (AbstractDungeon.getCurrRoom() instanceof TreasureRoomBoss && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.BOSS_REWARD) {
            final TreasureRoomBoss r = (TreasureRoomBoss)AbstractDungeon.getCurrRoom();
            r.chest.close();
        }
		*/
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25f;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        //this.mayVote = false;
        //this.updateVote();
    }
    
    private void updateCancelButton() {
        this.cancelButton.update();
        if (this.cancelButton.hb.clicked) {
            this.cancelButton.hb.clicked = false;
            this.relicSkipLogic();
        }
    }
    
    public void noPick() {
        final ArrayList<String> notPicked = new ArrayList<String>();
        final HashMap<String, Object> choice = new HashMap<String, Object>();
        for (final AbstractRelic otherRelics : this.relics) {
            notPicked.add(otherRelics.relicId);
        }
        choice.put("not_picked", notPicked);
        //CardCrawlGame.metricData.boss_relics.add(choice);
    }
    
    public void render(final SpriteBatch sb) {
        for (final AbstractGameEffect e : AbstractDungeon.effectList) {
            e.render(sb);
        }
        this.cancelButton.render(sb);
        //((TreasureRoomBoss)AbstractDungeon.getCurrRoom()).chest.render(sb);
        AbstractDungeon.player.render(sb);
        sb.setColor(Color.WHITE);
        sb.draw(this.smokeImg, 470.0f * Settings.scale, AbstractDungeon.floorY - 58.0f * Settings.scale, this.smokeImg.getWidth() * Settings.scale, this.smokeImg.getHeight() * Settings.scale);
        for (final AbstractRelic r : this.relics) {
            //r.render(sb);
			final FloatyEffect f_effect = (FloatyEffect)ReflectionHacks.getPrivate((Object)r, (Class)AbstractRelic.class, "f_effect");
			final float rotation = (float)ReflectionHacks.getPrivate((Object)r, (Class)AbstractRelic.class, "rotation");
			if (!r.isObtained) {
				sb.setColor(Color.WHITE);
				sb.draw(r.img, r.currentX - 64.0f + f_effect.x, r.currentY - 64.0f + f_effect.y, 64.0f, 64.0f, 128.0f, 128.0f, r.scale, r.scale, rotation, 0, 0, 128, 128, false, false);
			}
			else {
				sb.setColor(Color.WHITE);
				sb.draw(r.img, r.currentX - 64.0f, r.currentY - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, r.scale, r.scale, rotation, 0, 0, 128, 128, false, false);
				r.renderCounter(sb);
			}
        }
		/*
        if (AbstractDungeon.topPanel.twitch.isPresent()) {
            this.renderTwitchVotes(sb);
        }
		*/
    }
    /*
    private void renderTwitchVotes(final SpriteBatch sb) {
        if (!this.isVoting) {
            return;
        }
        if (this.getVoter().isPresent()) {
            final TwitchVoter twitchVoter = this.getVoter().get();
            final TwitchVoteOption[] options = twitchVoter.getOptions();
            final int sum = Arrays.stream(options).map(c -> c.voteCount).reduce(0, Integer::sum);
            for (int i = 0; i < this.relics.size(); ++i) {
                String s = "#" + (i + 1) + ": " + options[i + 1].voteCount;
                if (sum > 0) {
                    s = s + " (" + options[i + 1].voteCount * 100 / sum + "%)";
                }
                switch (i) {
                    case 0: {
                        FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, s, GenericRelicSelectScreen.SLOT_1_X, GenericRelicSelectScreen.SLOT_1_Y - 75.0f * Settings.scale, Color.WHITE.cpy());
                        break;
                    }
                    case 1: {
                        FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, s, GenericRelicSelectScreen.SLOT_2_X, GenericRelicSelectScreen.SLOT_2_Y - 75.0f * Settings.scale, Color.WHITE.cpy());
                        break;
                    }
                    case 2: {
                        FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, s, GenericRelicSelectScreen.SLOT_3_X, GenericRelicSelectScreen.SLOT_3_Y - 75.0f * Settings.scale, Color.WHITE.cpy());
                        break;
                    }
                }
            }
            String s2 = "#0: " + options[0].voteCount;
            if (sum > 0) {
                s2 = s2 + " (" + options[0].voteCount * 100 / sum + "%)";
            }
            FontHelper.renderFont(sb, FontHelper.panelNameFont, s2, 20.0f, 256.0f * Settings.scale, Color.WHITE.cpy());
            FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, GenericRelicSelectScreen.TEXT[4] + twitchVoter.getSecondsRemaining() + GenericRelicSelectScreen.TEXT[5], Settings.WIDTH / 2.0f, 192.0f * Settings.scale, Color.WHITE.cpy());
        }
    }
    */
    public void reopen() {
        this.refresh();
        this.cancelButton.show(GenericRelicSelectScreen.TEXT[3]);
        AbstractDungeon.dynamicBanner.appearInstantly(800.0f * Settings.scale, GenericRelicSelectScreen.SELECT_MSG);
        //AbstractDungeon.screen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.showBlackScreen();
    }
    
    public void open(final ArrayList<AbstractRelic> chosenRelics) {
        this.refresh();
        this.relics.clear();
        this.cancelButton.show(GenericRelicSelectScreen.TEXT[3]);
        AbstractDungeon.dynamicBanner.appear(800.0f * Settings.scale, GenericRelicSelectScreen.SELECT_MSG);
        this.smokeImg = ImageMaster.loadImage("images/ui/bossRelicScreenOverlay.png");
        AbstractDungeon.isScreenUp = true;
        //AbstractDungeon.screen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.showBlackScreen();
        final AbstractRelic r = chosenRelics.get(0);
        r.spawn(GenericRelicSelectScreen.SLOT_1_X, GenericRelicSelectScreen.SLOT_1_Y);
        r.hitbox.move(r.currentX, r.currentY);
        this.relics.add(r);
        final AbstractRelic r2 = chosenRelics.get(1);
        r2.spawn(GenericRelicSelectScreen.SLOT_2_X, GenericRelicSelectScreen.SLOT_2_Y);
        r2.hitbox.move(r2.currentX, r2.currentY);
        this.relics.add(r2);
        final AbstractRelic r3 = chosenRelics.get(2);
        r3.spawn(GenericRelicSelectScreen.SLOT_3_X, GenericRelicSelectScreen.SLOT_3_Y);
        r3.hitbox.move(r3.currentX, r3.currentY);
        this.relics.add(r3);
        for (final AbstractRelic r4 : this.relics) {
            UnlockTracker.markRelicAsSeen(r4.relicId);
        }
        //this.mayVote = true;
        //this.updateVote();
    }
    
    public void refresh() {
        this.isDone = false;
        this.cancelButton = new MenuCancelButton();
        this.shineTimer = 0.0f;
    }
    
    public void hide() {
        AbstractDungeon.dynamicBanner.hide();
        AbstractDungeon.overlayMenu.cancelButton.hide();
    }
    /*
    private Optional<TwitchVoter> getVoter() {
        return TwitchPanel.getDefaultVoter();
    }
	
    private void updateVote() {
        if (this.getVoter().isPresent()) {
            final TwitchVoter twitchVoter = this.getVoter().get();
            if (this.mayVote && twitchVoter.isVotingConnected() && !this.isVoting) {
                System.out.println("Publishing Boss Relic Vote");
                this.isVoting = twitchVoter.initiateSimpleNumberVote(Stream.concat((Stream<?>)Stream.of((T)"skip"), this.relics.stream().map((Function<? super Object, ?>)AbstractRelic::toString)).toArray(String[]::new), this::completeVoting);
            }
            else if (this.isVoting && (!this.mayVote || !twitchVoter.isVotingConnected())) {
                twitchVoter.endVoting(true);
                this.isVoting = false;
            }
        }
    }
    
    public void completeVoting(final int option) {
        if (!this.isVoting) {
            return;
        }
        this.isVoting = false;
        if (this.getVoter().isPresent()) {
            final TwitchVoter twitchVoter = this.getVoter().get();
            AbstractDungeon.topPanel.twitch.ifPresent(twitchPanel -> twitchPanel.connection.sendMessage("Voting on relic ended... chose " + twitchVoter.getOptions()[option].displayName));
        }
        while (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.BOSS_REWARD) {
            AbstractDungeon.closeCurrentScreen();
        }
        if (option == 0) {
            this.relicSkipLogic();
        }
        else if (option < this.relics.size() + 1) {
            final AbstractRelic r = this.relics.get(option - 1);
            if (!r.relicId.equals("Black Blood") && !r.relicId.equals("Ring of the Serpent")) {
                r.obtain();
            }
            r.isObtained = true;
        }
    }
    */
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("BossRelicSelectScreen");
        TEXT = GenericRelicSelectScreen.uiStrings.TEXT;
        SELECT_MSG = GenericRelicSelectScreen.TEXT[2];
        SLOT_1_X = 964.0f * Settings.scale;
        SLOT_1_Y = 700.0f * Settings.scale;
        SLOT_2_X = 844.0f * Settings.scale;
        SLOT_2_Y = 560.0f * Settings.scale;
        SLOT_3_X = 1084.0f * Settings.scale;
        SLOT_3_Y = 560.0f * Settings.scale;
		/*
        TwitchVoter.registerListener(new TwitchVoteListener() {
            @Override
            public void onTwitchAvailable() {
                AbstractDungeon.bossRelicScreen.updateVote();
            }
            
            @Override
            public void onTwitchUnavailable() {
                AbstractDungeon.bossRelicScreen.updateVote();
            }
        });
		*/
    }
}
