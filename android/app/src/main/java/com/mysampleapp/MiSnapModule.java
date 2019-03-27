package com.mysampleapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.miteksystems.misnap.analyzer.MiSnapAnalyzerResult;
import com.miteksystems.misnap.misnapworkflow.MiSnapWorkflowActivity;
import com.miteksystems.misnap.misnapworkflow_UX2.params.WorkflowApi;
import com.miteksystems.misnap.params.CameraApi;
import com.miteksystems.misnap.params.MiSnapApi;
import com.miteksystems.misnap.params.ScienceApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class MiSnapModule extends ReactContextBaseJavaModule {

    private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
    private static final String E_PICKER_CANCELLED = "E_PICKER_CANCELLED";
    private static final String E_FAILED_TO_SHOW_PICKER = "E_FAILED_TO_SHOW_PICKER";
    private static final String E_NO_IMAGE_DATA_FOUND = "E_NO_IMAGE_DATA_FOUND";

    private Promise mPickerPromise;

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {

        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            if (MiSnapApi.RESULT_PICTURE_CODE == requestCode) {
                if (RESULT_OK == resultCode) {
                    if (mPickerPromise != null) {
                        if (data != null) {
                            Bundle extras = data.getExtras();
                            String miSnapResultCode = extras.getString(MiSnapApi.RESULT_CODE);

                            switch (miSnapResultCode) {
                                // MiSnap check capture
                                case MiSnapApi.RESULT_SUCCESS_VIDEO:
                                case MiSnapApi.RESULT_SUCCESS_STILL:

                                    // Image returned successfully
                                    byte[] sImage = data.getByteArrayExtra(MiSnapApi.RESULT_PICTURE_DATA);

                                    mPickerPromise.resolve(sImage);

                                    break;
                            }
                        } else {
                            // Image canceled, stop
                            Toast.makeText(getReactApplicationContext(), "MiSnap canceled", Toast.LENGTH_SHORT).show();
                        }

                        mPickerPromise = null;

                    }

                } else if (RESULT_CANCELED == resultCode) {
                    // Camera not working or not available, stop
                    Toast.makeText(getReactApplicationContext(), "Operation canceled!!!", Toast.LENGTH_SHORT).show();
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        String miSnapResultCode = extras.getString(MiSnapApi.RESULT_CODE);
                        if (!miSnapResultCode.isEmpty()) {
                            Toast.makeText(getReactApplicationContext(), "Shutdown reason: " + miSnapResultCode, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    };

    public MiSnapModule(ReactApplicationContext reactContext) {
        super(reactContext);

        // Add the listener for `onActivityResult`
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    @Override
    public String getName() {
        return "MiSnapModule";
    }

    @ReactMethod
    private void startMiSnapWorkflow(final Promise promise) {

        String docType = MiSnapApi.PARAMETER_DOCTYPE_CHECK_FRONT;

        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
            return;
        }

        // Store the promise to resolve/reject when picker returns data
        mPickerPromise = promise;

        JSONObject misnapParams = new JSONObject();
        try {
            misnapParams.put(MiSnapApi.MiSnapDocumentType, docType);
            if (docType.equals(MiSnapApi.PARAMETER_DOCTYPE_CHECK_FRONT)) {
                misnapParams.put(ScienceApi.MiSnapGeoRegion, ScienceApi.GEO_REGION_GLOBAL);
            }

            // Here you can override optional API parameter defaults
            misnapParams.put(CameraApi.MiSnapAllowScreenshots, 1);
            // e.g. misnapParams.put(MiSnapApi.AppVersion, "1.0");
            // Workflow parameters are now put into the same JSONObject as MiSnap parameters
            misnapParams.put(WorkflowApi.MiSnapTrackGlare, "1");
            misnapParams.put(CameraApi.MiSnapFocusMode, CameraApi.PARAMETER_FOCUS_MODE_HYBRID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intentMiSnap = new Intent(currentActivity, MiSnapWorkflowActivity.class);
        intentMiSnap.putExtra(MiSnapApi.JOB_SETTINGS, misnapParams.toString());
        currentActivity.startActivityForResult(intentMiSnap, MiSnapApi.RESULT_PICTURE_CODE);
    }


}
