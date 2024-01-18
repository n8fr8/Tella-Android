package com.hzontal.tella_locking_ui.ui.pattern

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.hzontal.tella_locking_ui.FINISH_ACTIVITY_REQUEST_CODE
import com.hzontal.tella_locking_ui.R
import com.hzontal.tella_locking_ui.patternlock.PatternView
import com.hzontal.tella_locking_ui.patternlock.SetPatternActivity
import timber.log.Timber

class PatternSetActivity : SetPatternActivity() {
    @SuppressLint("StringFormatInvalid")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMessageText.text = getString(R.string.UnlockPattern_PatternTooShort, minPatternSize)
        mTopImageView.background = ContextCompat.getDrawable(this,R.drawable.pattern_draw_bg)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onConfirmed() {
        super.onConfirmed()
        Timber.d("** We've finished first MainKey saving - now we need to proceed with application **")
    }

    override fun getMinPatternSize(): Int {
        return 6
    }

    override fun onPatternDetected(newPattern: MutableList<PatternView.Cell>) {
        super.onPatternDetected(newPattern)
        when (mStage) {
             Stage.Confirm,Stage.ConfirmWrong -> {
                if (newPattern == mPattern) {
                    updateStage(Stage.ConfirmCorrect);
                } else {
                    updateStage(Stage.ConfirmWrong);
                }
            }
            else -> {}
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FINISH_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) finish()
    }

}