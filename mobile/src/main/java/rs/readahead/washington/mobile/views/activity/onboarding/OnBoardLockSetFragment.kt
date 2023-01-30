package rs.readahead.washington.mobile.views.activity.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import rs.readahead.washington.mobile.R
import rs.readahead.washington.mobile.databinding.OnboardLockSetFragmentBinding
import rs.readahead.washington.mobile.util.hide
import rs.readahead.washington.mobile.views.base_ui.BaseFragment

class OnBoardLockSetFragment : BaseFragment() {

    private lateinit var binding: OnboardLockSetFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OnboardLockSetFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    override fun initView(view: View) {
        (activity as OnBoardActivityInterface).setCurrentIndicator(3)
        (activity as OnBoardingActivity).hideViewpager()
        (activity as OnBoardingActivity).showProgress()
        with(binding) {
            nextBtn.setOnClickListener {
                activity.addFragment(
                    this@OnBoardLockSetFragment,
                    OnBoardAllDoneFragment(),
                    R.id.rootOnboard
                )
            }

            backBtn.hide()
        }
    }
}