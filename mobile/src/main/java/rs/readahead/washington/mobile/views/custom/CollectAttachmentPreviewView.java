package rs.readahead.washington.mobile.views.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.hzontal.tella_vault.VaultFile;
import com.hzontal.utils.MediaFile;

import rs.readahead.washington.mobile.R;
import rs.readahead.washington.mobile.databinding.CollectAttachemntPreviewViewBinding;
import rs.readahead.washington.mobile.media.MediaFileHandler;
import rs.readahead.washington.mobile.media.VaultFileUrlLoader;
import rs.readahead.washington.mobile.mvp.contract.ICollectAttachmentMediaFilePresenterContract;
import rs.readahead.washington.mobile.mvp.presenter.CollectAttachmentMediaFilePresenter;
import rs.readahead.washington.mobile.presentation.entity.VaultFileLoaderModel;
import rs.readahead.washington.mobile.util.FileUtil;
import rs.readahead.washington.mobile.views.activity.AudioPlayActivity;
import rs.readahead.washington.mobile.views.activity.PhotoViewerActivity;
import rs.readahead.washington.mobile.views.activity.VideoViewerActivity;
import rs.readahead.washington.mobile.views.collect.widgets.QuestionWidget;

public class CollectAttachmentPreviewView extends LinearLayout implements ICollectAttachmentMediaFilePresenterContract.IView {
    private VaultFile vaultFile;
    private final CollectAttachmentMediaFilePresenter presenter;
    private final RequestManager.ImageModelRequest<VaultFileLoaderModel> glide;
    private final CollectAttachemntPreviewViewBinding binding;

    public CollectAttachmentPreviewView(Context context) {
        this(context, null);
    }

    public CollectAttachmentPreviewView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollectAttachmentPreviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        binding = CollectAttachemntPreviewViewBinding.inflate(LayoutInflater.from(context), this, true);

        MediaFileHandler mediaFileHandler = new MediaFileHandler();
        VaultFileUrlLoader glideLoader = new VaultFileUrlLoader(getContext().getApplicationContext(), mediaFileHandler);

        glide = Glide.with(getContext()).using(glideLoader);
        presenter = new CollectAttachmentMediaFilePresenter(this);
    }

    @Override
    public void onDetachedFromWindow() {
        presenter.destroy();
        super.onDetachedFromWindow();
    }

    public void showPreview(String filename) {
        presenter.getMediaFile(filename);
    }

    @Override
    public void onGetMediaFileSuccess(VaultFile vaultFile) {
        this.vaultFile = vaultFile;

        if (MediaFile.INSTANCE.isVideoFileType(vaultFile.mimeType)) {
            binding.thumbView.setId(QuestionWidget.newUniqueId());
            binding.thumbView.setOnClickListener(v -> showVideoViewerActivity());

            showMediaFileInfo();
            loadThumbnail();
        } else if (MediaFile.INSTANCE.isAudioFileType(vaultFile.mimeType)) {
            binding.thumbView.setImageResource(R.drawable.ic_baseline_headset_white_24);
            binding.thumbView.setOnClickListener(v -> showAudioPlayActivity());

            showMediaFileInfo();
        } else if (MediaFile.INSTANCE.isImageFileType(vaultFile.mimeType)) {
            binding.thumbView.setId(QuestionWidget.newUniqueId());
            binding.thumbView.setOnClickListener(v -> showPhotoViewerActivity());

            loadThumbnail();
            showMediaFileInfo();
        }
    }

    @Override
    public void onGetMediaFileStart() {
    }

    @Override
    public void onGetMediaFileEnd() {
    }

    @Override
    public void onGetMediaFileError(Throwable error) {
        binding.thumbView.setImageResource(R.drawable.ic_error); // todo: handle this with new layout
        Toast.makeText(getContext(), getResources().getText(R.string.collect_form_toast_fail_load_attachment), Toast.LENGTH_LONG).show();
    }

    private void loadThumbnail() {
        glide.load(new VaultFileLoaderModel(vaultFile, VaultFileLoaderModel.LoadType.THUMBNAIL))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.thumbView);
    }

    private void showMediaFileInfo() {
        binding.fileName.setText(vaultFile.name);
        binding.fileSize.setText(FileUtil.getFileSizeString(vaultFile.size));
    }

    private void showVideoViewerActivity() {
        if (vaultFile == null) {
            return;
        }

        try {
            Activity activity = (Activity) getContext();
            activity.startActivity(new Intent(getContext(), VideoViewerActivity.class)
                    .putExtra(VideoViewerActivity.VIEW_VIDEO, vaultFile)
                    .putExtra(VideoViewerActivity.NO_ACTIONS, true));
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
        }
    }

    private void showPhotoViewerActivity() {
        if (vaultFile == null) {
            return;
        }

        try {
            Activity activity = (Activity) getContext();
            activity.startActivity(new Intent(getContext(), PhotoViewerActivity.class)
                    .putExtra(PhotoViewerActivity.VIEW_PHOTO, vaultFile)
                    .putExtra(PhotoViewerActivity.NO_ACTIONS, true));
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
        }
    }

    private void showAudioPlayActivity() {
        if (vaultFile == null) {
            return;
        }

        try {
            Activity activity = (Activity) getContext();
            activity.startActivity(new Intent(getContext(), AudioPlayActivity.class)
                    .putExtra(AudioPlayActivity.PLAY_MEDIA_FILE, vaultFile)
                    .putExtra(AudioPlayActivity.NO_ACTIONS, true));
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
        }
    }
}
