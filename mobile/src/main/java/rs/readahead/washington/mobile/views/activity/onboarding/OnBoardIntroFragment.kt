package rs.readahead.washington.mobile.views.activity.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import rs.readahead.washington.mobile.R
import rs.readahead.washington.mobile.views.base_ui.BaseFragment
import rs.readahead.washington.mobile.views.settings.OnFragmentSelected

class OnBoardIntroFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.onboard_intro_fragment_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    override fun onResume() {
        super.onResume()
        (activity as OnBoardActivityInterface).enableSwipe(
            isSwipeable = false, isTabLayoutVisible = false
        )
        (activity as OnBoardActivityInterface).showButtons(
            isNextButtonVisible = false, isBackButtonVisible = false
        )
    }

    override fun initView(view: View) {
        (activity as OnBoardActivityInterface).hideProgress()

        val enterCodeButton = view.findViewById<TextView>(R.id.sheet_two_btn)
        enterCodeButton.setOnClickListener {
            (activity as OnBoardActivityInterface).enterCustomizationCode()
        }

        val startBtn = view.findViewById<TextView>(R.id.startBtn)
        startBtn.setOnClickListener {
            (activity as OnBoardingActivity).onNextPressed()

        }
    }
}