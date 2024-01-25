package rs.readahead.washington.mobile.views.fragment.reports.di

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import javax.inject.Inject

class NavControllerProvider (private val fragment: Fragment) {

    val navController: NavController
        get() = NavHostFragment.findNavController(fragment)
}
