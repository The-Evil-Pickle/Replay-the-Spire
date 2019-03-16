package replayTheSpire.replayxover.archetypeAPI;

import java.util.ArrayList;

import archetypeAPI.archetypes.abstractArchetype;

public class SilentWeakSet extends abstractArchetype {
    public static ArrayList<String> weakArchetypeFiles = new ArrayList<>();

    public SilentWeakSet() {
        super(weakArchetypeFiles);
    }
}