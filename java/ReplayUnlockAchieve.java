package ReplayTheSpireMod;

import java.nio.charset.StandardCharsets;

import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.blue.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.vfx.*;

import basemod.*;
import basemod.helpers.*;
import basemod.interfaces.*;
import java.lang.reflect.*;
import java.io.*;

import com.megacrit.cardcrawl.relics.*;
import java.util.*;

public class ReplayUnlockAchieve {
	
	public String achievementId;
	public String name;
	public String desc;
	public String relicId;
	public boolean isUnlocked;
	
	
	public ReplayUnlockAchieve(String id, String name, String desc, String relicId) {
		this(id, name, desc, relicId, false);
	}
	
	public ReplayUnlockAchieve(String id, String name, String desc, String relicId, boolean isUnlocked) {
		this.achievementId = id;
		this.name = name;
		this.desc = desc;
		this.relicId = relicId;
		this.isUnlocked = isUnlocked;
	}
	
	
}