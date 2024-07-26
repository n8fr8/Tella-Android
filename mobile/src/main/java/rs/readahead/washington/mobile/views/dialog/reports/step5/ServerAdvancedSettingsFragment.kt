package rs.readahead.washington.mobile.views.dialog.reports.step5

import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import rs.readahead.washington.mobile.databinding.FragmentReportServerAdvancedSettingsBinding
import rs.readahead.washington.mobile.domain.entity.reports.TellaReportServer
import rs.readahead.washington.mobile.views.base_ui.BaseBindingFragment
import rs.readahead.washington.mobile.views.dialog.ID_KEY
import rs.readahead.washington.mobile.views.dialog.OBJECT_KEY

@AndroidEntryPoint
class ServerAdvancedSettingsFragment :
    BaseBindingFragment<FragmentReportServerAdvancedSettingsBinding>(
        FragmentReportServerAdvancedSettingsBinding::inflate
    ) {
    private lateinit var serverReports: TellaReportServer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListeners()
    }

    private fun initView() {
        arguments?.getString(OBJECT_KEY)?.let {
            serverReports = Gson().fromJson(it, TellaReportServer::class.java)
        }
    }

    private fun initListeners() {
        binding.backBtn.setOnClickListener {
            baseActivity.onBackPressed()
        }
        binding.nextBtn.setOnClickListener {
            bundle.putSerializable(ID_KEY, copyFields(serverReports).id)
            bundle.putString(OBJECT_KEY, Gson().toJson(copyFields(serverReports)))
            navManager().navigateFromServerAdvancedSettingsFragmentToSuccessfulSetServerFragment()
        }

        binding.backgroundUploadSwitch.mSwitch.setOnCheckedChangeListener { _, isChecked: Boolean ->
            serverReports.isActivatedBackgroundUpload = isChecked
        }

        binding.shareVerificationSwitch.mSwitch.setOnCheckedChangeListener { _, isChecked: Boolean ->
            serverReports.isActivatedMetadata = isChecked
        }

    }
    private fun copyFields(server: TellaReportServer): TellaReportServer {
        server.url = serverReports.url
        server.username = serverReports.username
        server.password = serverReports.password
        server.name = serverReports.name
        server.isActivatedMetadata = serverReports.isActivatedMetadata
        server.isActivatedBackgroundUpload = server.isActivatedBackgroundUpload
        return server
    }

}