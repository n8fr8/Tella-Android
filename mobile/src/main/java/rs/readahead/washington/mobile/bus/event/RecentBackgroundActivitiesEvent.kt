package rs.readahead.washington.mobile.bus.event

import rs.readahead.washington.mobile.bus.IEvent
import rs.readahead.washington.mobile.domain.entity.background_activity.BackgroundActivityModel
import rs.readahead.washington.mobile.domain.entity.background_activity.BackgroundActivityStatus

class RecentBackgroundActivitiesEvent(
    backgroundActivityModels: List<BackgroundActivityModel>?
) : IEvent {

    private var backgroundActivityModels: MutableList<BackgroundActivityModel> =
        backgroundActivityModels?.toMutableList() ?: mutableListOf()

    init {
        removeFinishedItems()
    }

    fun getBackgroundActivityModels(): List<BackgroundActivityModel> {
        return backgroundActivityModels
    }

    // Method to remove finished items from the list
    private fun removeFinishedItems() {
        backgroundActivityModels = backgroundActivityModels.filter {
            it.status != BackgroundActivityStatus.COMPLETED
        }.toMutableList()
    }

    // Method to check if the list is not empty
    fun hasItems(): Boolean {
        return backgroundActivityModels.isNotEmpty()
    }

    fun size(): Int {
        return backgroundActivityModels.size
    }
}
