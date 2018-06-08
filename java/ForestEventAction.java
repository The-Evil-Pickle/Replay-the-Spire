package ReplayTheSpireMod.monsters.fadingForest;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class ForestEventAction extends AbstractGameAction
{
	
	private boolean hasBuilt;
	public static FadingForestBoss forest;
	public static String title;
	public static String body;
	public static String[] options;
	public static ArrayList<HitBox> hbs;
	
	
	
    public ForestEventAction(final String title, final String body, final String[] options) {
        this.setValues(null, null, 0);
        this.actionType = ActionType.WAIT;
		this.hasBuilt = false;
    }
    
    @Override
    public void update() {
		if (!this.hasBuilt) {
			//build text and buttons
			this.hasBuilt = true;
		}
        if () {
			this.isDone = true;
		}
    }
}
