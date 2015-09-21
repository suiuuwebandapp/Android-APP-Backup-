package com.minglang.suiuu.interfaces;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.ViewTreeObserver;

import com.minglang.suiuu.utils.L;

/**
 * Created by Administrator on 2015/9/11.
 * <p/>
 * 复写{@linkandroid.content.DialogInterface}
 * 防止一个内存泄漏问题
 */
public final class DetachableListener implements DialogInterface.OnClickListener {

    private DialogInterface.OnClickListener detachableListener;

    private DetachableListener(DialogInterface.OnClickListener delegateOrNull) {
        this.detachableListener = delegateOrNull;
    }

    public static DetachableListener build(DialogInterface.OnClickListener dialogInterface) {
        return new DetachableListener(dialogInterface);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (detachableListener != null) {
            detachableListener.onClick(dialog, which);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void clearOnDetach(Dialog dialog) {
        dialog.getWindow().getDecorView().getViewTreeObserver()
                .addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
                    @Override
                    public void onWindowAttached() {
                        L.i(getClass().getSimpleName(), "onWindowAttached");
                    }

                    @Override
                    public void onWindowDetached() {
                        L.i(getClass().getSimpleName(), "onWindowDetached");
                        detachableListener = null;
                    }
                });
    }

}