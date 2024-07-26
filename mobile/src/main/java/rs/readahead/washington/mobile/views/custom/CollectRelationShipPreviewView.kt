package rs.readahead.washington.mobile.views.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import rs.readahead.washington.mobile.R
import rs.readahead.washington.mobile.databinding.CollectRelationshipPreviewViewBinding
import rs.readahead.washington.mobile.presentation.uwazi.UwaziRelationShipEntity

class CollectRelationShipPreviewView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: CollectRelationshipPreviewViewBinding
    private var relationShip: UwaziRelationShipEntity? = null

    init {
        binding =
            CollectRelationshipPreviewViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setRelationShip(relationShip: UwaziRelationShipEntity?) {
        this.relationShip = relationShip
        showMediaFileInfo()
    }

    private fun showMediaFileInfo() {
        if (relationShip != null)
        relationShip?.let {
            binding.fileName.text = it.label
            binding.thumbView.setImageResource(R.drawable.relation_ship_icon)
        }
    }
}