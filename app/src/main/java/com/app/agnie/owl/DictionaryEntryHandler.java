package com.app.agnie.owl;

import com.app.agnie.owl.Util.DictionaryEntry;

/**
 * Created by agnie on 2/21/2017.
 */

public interface DictionaryEntryHandler {

    public DictionaryEntry getDictionaryEntry(int index);

    public int getEntriesCount();

    public boolean isEmpty();

}
