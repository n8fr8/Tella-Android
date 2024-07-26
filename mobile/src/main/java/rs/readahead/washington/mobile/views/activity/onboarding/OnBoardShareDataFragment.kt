package rs.readahead.washington.mobile.views.activity.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import rs.readahead.washington.mobile.R
import rs.readahead.washington.mobile.views.base_ui.BaseFragment

class OnBoardShareDataFragment : BaseFragment() {

    private lateinit var connectBtn: TextView
    private lateinit var continueBtn: TextView
    private lateinit var backBtn: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.onboard_share_data_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
    }

    override fun initView(view: View) {
        (baseActivity as OnBoardActivityInterface).initProgress(2)
        (baseActivity as OnBoardActivityInterface).setCurrentIndicator(0)

        connectBtn = view.findViewById(R.id.startBtn)
        connectBtn.setOnClickListener {
            (baseActivity as OnBoardActivityInterface).showChooseServerTypeDialog()
        }
        backBtn = view.findViewById(R.id.back_btn)
        backBtn.setOnClickListener {
            baseActivity.onBackPressed()
        }
        continueBtn = view.findViewById(R.id.sheet_two_btn)
        continueBtn.setOnClickListener {
            baseActivity.addFragment(
                this,
                OnBoardHideOptionFragment(),
                R.id.rootOnboard
            )
        }
    }
}