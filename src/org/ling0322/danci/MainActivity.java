package org.ling0322.danci;

import android.content.*;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;

public class MainActivity 
    extends FragmentActivity 
    implements ViewPager.OnPageChangeListener { 
    /** Called when the activity is first created. */
    
    private ViewPager mPager = null;
    private TabPageIndicator mIndicator = null;
    private FragmentAdapter mAdapter;
    
    public FragmentAdapter getFragmentAdapter() {
    	return mAdapter;
    }
    
    public void onCreate(Bundle savedInstanceState) {
        //
        // DON'T use savedInstanceState to initialize the activity, since it may cause 
        // some exceptions
        //
        super.onCreate(new Bundle());
        setContentView(R.layout.main_tabs);
        
        Config.mainInstance = this;
        
        mAdapter = new FragmentAdapter(getSupportFragmentManager());
        
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(1);
        
        mIndicator = (TabPageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.setOnPageChangeListener(this);
        mIndicator.setCurrentItem(1);
        
        Log.d("lia", "main on_create");
    }
    
    //
    // menu
    //
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "选项").setIcon(R.drawable.setting_menu);
        menu.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "退出").setIcon(R.drawable.exit);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case Menu.FIRST:
            Intent it = new Intent(this, LiaPreferencesActivity.class);
            startActivity(it);
            break;
        case Menu.FIRST + 1:
            System.exit(0);
            break;
        }
        return false;
    }
    
    //
    //
    //
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //
        // Here intercept the BACK_KEY press event to
        // prevent unwanted exit of this app when users press BACK_KEY
        //
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	int cntFragIndex = mPager.getCurrentItem();
        	if (true == mAdapter.getItem(cntFragIndex).onBackKey()) 
        		return true;
        	
            mPager.setCurrentItem(1, true);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    
    }
    
    @Override
    public void onResume() {
        super.onResume();
        Log.d("lia", "main resume");
        EditText et = (EditText)findViewById(R.id.editText1);
        if (et != null) 
            et.clearFocus();
    }

    public void closeIME() {
        EditText et = (EditText)findViewById(R.id.editText1);
        
        if (et == null)
            return ;
        
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        Log.d("lia", "close input method");
    }
    
    public void openIME(View view) {
        EditText et = (EditText)findViewById(R.id.editText1);
        if (et == null)
            return ;
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        imm.showSoftInput(view, 0);
        Log.d("lia", "open input method");
    }
    
    public void onPageScrollStateChanged(int arg0) {
        //
        // close input method when switch fragments
        //
        // closeIME();
    }

    public void onPageScrolled(int arg0, float arg1, int arg2) {
    	// Log.d("danci", "onPageScrolled");
    }

    public void onPageSelected(int arg0) {
    	CustomFragment fragment = mAdapter.getItem(arg0);
    	fragment.onPageSelected();
    }
}