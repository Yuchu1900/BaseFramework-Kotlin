package com.cmh.base.view;

import android.app.Fragment;
import android.os.Bundle;

import com.cmh.base.R;

public abstract class BaseFragmentActivity extends BaseActivity{

    Fragment mFragment;

    @Override
    public int getLayoutId(){
        return R.layout.base_activity_fragment_container;
    }

    @Override
    public void afterOnCreate(Bundle bundle) {
        super.afterOnCreate(bundle);
        if (bundle == null) {
            mFragment=buildFragment();
            if(mFragment!=null){
                Bundle actBundle = getIntent().getExtras();
                if(actBundle==null){
                    actBundle=new Bundle();
                }
                mFragment.setArguments(addExtras(actBundle));
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,mFragment)
                        .commit();
            }
        }
    }

    //按返回键时，判断是否Activity底下的fragment要处理，否则直接关闭activity
    public void onBackPressed(){
        if(mFragment==null){
            super.onBackPressed();
        }else if(mFragment instanceof BaseFragment.OnBackPressedListener){
            if(!((BaseFragment.OnBackPressedListener) mFragment).onFragmentBackPressed()){
                super.onBackPressed();
            }
        }else{
            super.onBackPressed();
        }
    }

    public abstract Fragment buildFragment();//在activity下加入fragment
    public abstract Bundle addExtras(Bundle bundle);//activity传递数据给底下的fragment
}
