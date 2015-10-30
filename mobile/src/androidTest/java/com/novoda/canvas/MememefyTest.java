package com.novoda.canvas;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.novoda.canvas.mememefy.CameraSourcePreview;
import com.novoda.canvas.mememefy.FaceGraphic;
import com.novoda.canvas.mememefy.GraphicOverlay;
import com.novoda.canvas.base.NovodaActivityTest;

import java.io.IOException;

public class MememefyTest extends NovodaActivityTest {

    private CameraSource mCameraSource = null;

    private CameraSourcePreview mPreview;
    private GraphicOverlay mGraphicOverlay;
    private TextView mTextViewStatus;

    private Activity mActivity;

    @Override
    public void startTestFor(Activity activity) {
        final ViewGroup root = getParent();

        LayoutInflater inflater =
                (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View v = inflater.inflate(R.layout.androidify, root, true);
        mPreview = (CameraSourcePreview) v.findViewById(R.id.preview);
        mGraphicOverlay = (GraphicOverlay) v.findViewById(R.id.faceOverlay);
        mTextViewStatus = (TextView) v.findViewById(R.id.statusLabel);

        mActivity = activity;

        boolean hasCam
                = activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);

        if(!hasCam) {
            mTextViewStatus.setText("No front camera available.");
            return;
        }

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.M){
            mTextViewStatus.setText("Does not run on Marshmallow :-/");
            return;
        }

        createCameraSource(640, 360);
        startCameraSource();
    }

    private void createCameraSource(int width, int height) {
        FaceDetector detector = new FaceDetector.Builder(mActivity)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        detector.setProcessor(
                new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory())
                        .build());

        if (!detector.isOperational()) {
            mTextViewStatus.setText("FaceDetection not present.\nDownloading library...");
            return;
        }

        mCameraSource = new CameraSource.Builder(mActivity, detector)
                .setRequestedPreviewSize(width, height)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(30.0f)
                .build();
    }

    private void startCameraSource() {

        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mActivity);
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(mActivity, code, 101);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                //Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    private class GraphicFaceTrackerFactory implements MultiProcessor.Factory<Face> {
        @Override
        public Tracker<Face> create(Face face) {
            return new GraphicFaceTracker(mGraphicOverlay);
        }
    }

    /**
     * Face tracker for each detected individual. This maintains a face graphic within the app's
     * associated face overlay.
     */
    private class GraphicFaceTracker extends Tracker<Face> {
        private GraphicOverlay mOverlay;
        private FaceGraphic mFaceGraphic;

        GraphicFaceTracker(GraphicOverlay overlay) {
            mOverlay = overlay;
            mFaceGraphic = new FaceGraphic(overlay);
        }

        /**
         * Start tracking the detected face instance within the face overlay.
         */
        @Override
        public void onNewItem(int faceId, Face item) {
            mFaceGraphic.setId(faceId);
        }

        /**
         * Update the position/characteristics of the face within the overlay.
         */
        @Override
        public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
            mOverlay.add(mFaceGraphic);
            mFaceGraphic.updateFace(face);
        }

        /**
         * Hide the graphic when the corresponding face was not detected.  This can happen for
         * intermediate frames temporarily (e.g., if the face was momentarily blocked from
         * view).
         */
        @Override
        public void onMissing(FaceDetector.Detections<Face> detectionResults) {
            mOverlay.remove(mFaceGraphic);
        }

        /**
         * Called when the face is assumed to be gone for good. Remove the graphic annotation from
         * the overlay.
         */
        @Override
        public void onDone() {
            mOverlay.remove(mFaceGraphic);
        }
    }

}
