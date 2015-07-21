package com.tapsbook.photobooksdk_android.Base;

/**
 * Created by yaohu on 15/7/20.
 */
public abstract class AbsBaseActivity extends BaseActivity {

    public abstract  void asyncDataSuccess(Object object);

    public abstract  void asyncDataFail(Exception object);
}
