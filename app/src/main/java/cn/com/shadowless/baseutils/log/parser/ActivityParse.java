package cn.com.shadowless.baseutils.log.parser;

import android.app.Activity;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

import cn.com.shadowless.baseutils.log.Parser;
import cn.com.shadowless.baseutils.log.utils.ObjectUtil;


class ActivityParse implements Parser<Activity> {
    @NonNull
    @Override
    public Class<Activity> parseClassType() {
        return Activity.class;
    }

    @Override
    public String parseString(@NonNull Activity activity) {
        Field[] fields = activity.getClass().getDeclaredFields();
        StringBuilder builder = new StringBuilder(activity.getClass().getName());
        builder.append(" {");
        builder.append(LINE_SEPARATOR);
        for (Field field : fields) {
            field.setAccessible(true);
            if ("org.aspectj.lang.JoinPoint$StaticPart".equals(field.getType().getName())) {
                continue;
            }
            if (field.getName().equals("$change") || field.getName().equalsIgnoreCase("this$0")) {
                continue;
            }
            try {
                Object fieldValue = field.get(activity);
                builder.append(field.getName()).append("=>")
                        .append(ObjectUtil.objectToString(fieldValue)).append(LINE_SEPARATOR);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        builder.append("}");
        return builder.toString();
    }
}
