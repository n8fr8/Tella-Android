package rs.readahead.washington.mobile.views.dialog.reports.step3

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.hzontal.shared_ui.bottomsheet.KeyboardUtil
import org.hzontal.shared_ui.utils.DialogUtils
import rs.readahead.washington.mobile.MyApplication
import rs.readahead.washington.mobile.R
import rs.readahead.washington.mobile.databinding.FragmentLoginReportsScreenBinding
import rs.readahead.washington.mobile.domain.entity.reports.TellaReportServer
import rs.readahead.washington.mobile.util.KeyboardLiveData
import rs.readahead.washington.mobile.views.base_ui.BaseBindingFragment
import rs.readahead.washington.mobile.views.dialog.OBJECT_KEY
import rs.readahead.washington.mobile.views.dialog.reports.ReportsConnectFlowViewModel
import rs.readahead.washington.mobile.views.dialog.reports.edit.EDIT_MODE_KEY

internal const val OBJECT_SLUG = "os"

@AndroidEntryPoint
class LoginReportsFragment :
    BaseBindingFragment<FragmentLoginReportsScreenBinding>(FragmentLoginReportsScreenBinding::inflate) {
    private var validated = true
    private lateinit var serverReports: TellaReportServer
    private val viewModel by viewModels<ReportsConnectFlowViewModel>()
    private var projectSlug = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListeners()
        initObservers()

    }

    private fun initListeners() {
        binding.loginButton.setOnClickListener {
            if (!MyApplication.isConnectedToInternet(baseActivity)) {
                DialogUtils.showBottomMessage(
                    baseActivity,
                    getString(R.string.settings_docu_error_no_internet),
                    true
                )
            } else {
                validate()
                if (validated) {
                    viewModel.checkServer(copyFields(TellaReportServer(0)), projectSlug)
                }
            }
        }
        binding.backBtn.setOnClickListener { baseActivity.onBackPressed() }
    }

    private fun initObservers() {

        viewModel.error.observe(baseActivity) {
            binding.passwordLayout.error =
                getString(R.string.settings_docu_error_wrong_credentials)
        }
        viewModel.authenticationSuccess.observe(baseActivity) { server ->
            KeyboardUtil.hideKeyboard(baseActivity, binding.root)
            bundle.putString(OBJECT_KEY, Gson().toJson(server))
            bundle.putBoolean(EDIT_MODE_KEY, false)
            navManager().navigateFromLoginToReportsScreenToEditTellaServerFragment()
        }
        viewModel.progress.observe(baseActivity) {
            binding.progressBar.isVisible = it
        }
    }

    private fun validate() {
        validated = true
        validateRequired(binding.username, binding.usernameLayout)
        validateRequired(binding.password, binding.passwordLayout)
    }

    private fun validateRequired(field: EditText?, layout: TextInputLayout?) {
        layout?.error = null
        if (TextUtils.isEmpty(field!!.text.toString())) {
            layout?.error = getString(R.string.settings_text_empty_field)
            validated = false
        }
    }

    private fun copyFields(server: TellaReportServer): TellaReportServer {
        server.url = serverReports.url
        server.username = binding.username.text.toString().trim(' ')
        server.password = binding.password.text.toString()
        server.name = serverReports.name
        serverReports = server
        return server
    }

    private fun initView() {
        arguments?.getString(OBJECT_KEY)?.let {
            serverReports = Gson().fromJson(it, TellaReportServer::class.java)
        }
        arguments?.getString(OBJECT_SLUG)?.let {
            projectSlug = it
        }

        if (!serverReports.username.isNullOrEmpty() && !serverReports.password.isNullOrEmpty()) {
            binding.username.setText(serverReports.username)
            binding.password.setText(serverReports.password)
        }
        KeyboardLiveData(binding.root).observe(viewLifecycleOwner) {
            binding.backBtn.isVisible = !it.first
        }
    }
}