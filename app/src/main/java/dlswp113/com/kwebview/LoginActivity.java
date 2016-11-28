package dlswp113.com.kwebview;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import android.util.Log;

/**
 * Created by USER on 2016-09-19.
 */
public class LoginActivity extends AppBarActivity {

    private LoginFragment1 frag1;
    private LoginFragment2 frag2;
    private LoginFragment3 frag3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        frag1 = new LoginFragment1();
        frag2 = new LoginFragment2();
        frag3 = new LoginFragment3();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, frag1).commit();

        TabLayout tabs = (TabLayout)findViewById(R.id.tabs);

        tabs.addTab(tabs.newTab().setText("현 기수"));
        tabs.addTab(tabs.newTab().setText("전 기수"));
        tabs.addTab(tabs.newTab().setText("관리자"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("LoginActivity", "선택된 탭 : "+position );

                Fragment selected = null;

                if(position==0)
                {
                    selected = frag1;
                }
                else if(position==1)
                {
                    selected = frag2;
                }
                else if (position==2)
                {
                    selected = frag3;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
