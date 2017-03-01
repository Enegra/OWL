package com.app.agnie.owl.Util;

import java.util.ArrayList;

/**
 * Created by agnie on 2/21/2017.
 */

public interface DictionaryEntryHandler {

    public DictionaryEntry getDictionaryEntry(int index);

    public int getEntriesCount();

    public boolean isEmpty();

    public int getImageID(int index);

    public ArrayList<DictionaryEntry> getDictionaryEntries();

}
