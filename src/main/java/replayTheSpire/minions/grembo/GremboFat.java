package replayTheSpire.minions.grembo;

import kobting.friendlyminions.monsters.*;
import com.megacrit.cardcrawl.monsters.*;
import kobting.friendlyminions.actions.*;
import basemod.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.badlogic.gdx.math.*;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.actions.common.*;
import java.util.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;

public class GremboFat extends AbstractFriendlyMonster
{
	private static final MonsterStrings monsterStrings;
    public static String NAME;
    public static String ID;
    private ArrayList<ChooseActionInfo> moveInfo;
    private boolean hasAttacked;
    private AbstractMonster target;
    
    public GremboFat() {
        super(GremboFat.NAME, GremboFat.ID, 14, (ArrayList)null, -8.0f, 10.0f, 230.0f, 240.0f, "images/monsters/exord/cook.png", -700.0f, 0.0f);
        this.hasAttacked = false;
        this.img = null;
        this.loadAnimation("images/monsters/theBottom/fatGremlin/skeleton.atlas", "images/monsters/theBottom/fatGremlin/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.flipHorizontal = true;
        this.damage.add(new DamageInfo(this, 4));
    }
    
    public void takeTurn() {
    	switch (this.nextMove) {
	        case 1: {
            	final int attackValue = 9;
                int modifiedAttackValue = 0;
            	final ArrayList<AbstractMonster> viableTargets = new ArrayList<AbstractMonster>();
                for (final AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (monster.currentHealth > 0) {
                        viableTargets.add(monster);
                    }
                }
                if (viableTargets.size() > 0) {
                    this.target = viableTargets.get(AbstractDungeon.aiRng.random(viableTargets.size() - 1));
                    modifiedAttackValue = MathUtils.floor(applyPowersProxy(this.target, attackValue, (AbstractMonster)this));
                }
                if (this.target != null) {
                	AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target, new DamageInfo(this.target, modifiedAttackValue), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this, new WeakPower(this.target, 1, true), 1));
                }
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
	            break;
	        }
	        case 99: {
	        	AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.5f, DIALOG[1], false));
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
	        }
    	}
    }
    
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        this.hasAttacked = false;
    }
    
    private static float applyPowersProxy(final AbstractMonster m, final float input, final AbstractMonster minionInstance) {
        float retVal = input;
        for (final AbstractPower p : minionInstance.powers) {
            retVal = p.atDamageGive(retVal, DamageInfo.DamageType.NORMAL);
        }
        for (final AbstractPower p : m.powers) {
            retVal = p.atDamageReceive(retVal, DamageInfo.DamageType.NORMAL);
        }
        for (final AbstractPower p : minionInstance.powers) {
            retVal = p.atDamageFinalGive(retVal, DamageInfo.DamageType.NORMAL);
        }
        for (final AbstractPower p : m.powers) {
            retVal = p.atDamageFinalReceive(retVal, DamageInfo.DamageType.NORMAL);
        }
        return retVal;
    }


    @Override
    protected void getMove(final int num) {
    	if (this.escapeNext || this.hasPower(CowardicePower.POWER_ID) && this.getPower(CowardicePower.POWER_ID).amount <= 0) {
    		this.setMove((byte)99, Intent.ESCAPE);
    		return;
    	}
        this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base);
    }
    
    static {
    	monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinFat");
    	NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
