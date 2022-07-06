
package cn.com.shadowless.baseutils.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.ComponentName;
import android.content.Context;

import androidx.annotation.NonNull;


/**
 * The type Basic device admin receiver.
 *
 * @author sHadowLess
 */
public class BasicDeviceAdminReceiver extends DeviceAdminReceiver {

    /**
     * Gets component name.
     *
     * @param context the context
     * @return the component name
     */
    public static ComponentName getComponentName(@NonNull Context context) {
        return new ComponentName(context.getApplicationContext(), BasicDeviceAdminReceiver.class);
    }

}
