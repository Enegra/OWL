package com.app.agnie.owl.Util;

import java.util.ArrayList;

public interface DictionaryEntryHandler {

    DictionaryEntry getDictionaryEntry(int index);

    int getEntriesCount();

    boolean isEmpty();

    int getImageID(int index);

    ArrayList<DictionaryEntry> getDictionaryEntries();

}
