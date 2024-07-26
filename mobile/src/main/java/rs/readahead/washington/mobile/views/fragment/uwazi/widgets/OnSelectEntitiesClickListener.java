package rs.readahead.washington.mobile.views.fragment.uwazi.widgets;

import java.util.List;

import rs.readahead.washington.mobile.domain.entity.uwazi.NestedSelectValue;
import rs.readahead.washington.mobile.presentation.uwazi.UwaziRelationShipEntity;
import rs.readahead.washington.mobile.views.fragment.uwazi.entry.UwaziEntryPrompt;

public interface OnSelectEntitiesClickListener {
    void onSelectEntitiesClicked(UwaziEntryPrompt formEntryPrompt, List<UwaziRelationShipEntity> entities);
}
