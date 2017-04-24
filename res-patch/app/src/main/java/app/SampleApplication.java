package app;

import android.app.Application;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import fastdex.patch.TinkerResourcePatcher;

/**
 * Created by tong on 17/4/24.
 */
public class SampleApplication extends Application {
    private static final String TAG = SampleApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            File resourceFile = new File(getFilesDir(),"resources.apk");
            copyAssets("resources.apk",resourceFile);
            TinkerResourcePatcher.isResourceCanPatch(this);
            TinkerResourcePatcher.monkeyPatchExistingResources(this,resourceFile.getAbsolutePath());

            byte[] bytes = loadAssets("xq.txt");
            Log.d(TAG,"bytes: " + new String(bytes));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private byte[] loadAssets(String assetName) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            InputStream is = getAssets().open(assetName);
            int len = -1;
            byte[] buffer = new byte[1024];

            while ((len = is.read(buffer)) != -1) {
                os.write(buffer,0,len);
            }
            is.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }

    private void copyAssets(String assetName, File resourceFile) {
        try {
            InputStream is = getAssets().open(assetName);
            int len = -1;
            byte[] buffer = new byte[1024];

            FileOutputStream fos = new FileOutputStream(resourceFile);
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer,0,len);
            }
            is.close();
            fos.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
