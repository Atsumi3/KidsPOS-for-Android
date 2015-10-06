package info.nukoneko.cuc.kidspos.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Bind;
import info.nukoneko.cuc.kidspos.R;
import info.nukoneko.cuc.kidspos.adapter.ItemListAdapter;
import info.nukoneko.cuc.kidspos.common.CommonActivity;
import info.nukoneko.cuc.kidspos.model.ItemObject;
import info.nukoneko.cuc.kidspos.navigation.NavigationView;
import info.nukoneko.cuc.kidspos.util.KPLogger;
import info.nukoneko.cuc.kidspos.util.KPToast;
import info.nukoneko.cuc.kidspos.util.SQLiteManager;

/**
 * created at 2015/06/13.
 */
public class TopPageActivity extends CommonActivity {
    @Bind(R.id.tool_bar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.navigation_view) NavigationView mNavigation;
    @Bind(R.id.item_list) ItemListView mItemListView;
    @Bind(R.id.price) TextView priceView;

    ActionBarDrawerToggle mDrawerToggle;

    Integer sumPrice = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);

        mDrawerToggle = getDrawerToggle();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onInputBarcode(String barcode) {
        ItemObject item = SQLiteManager.getItem(barcode);
        if(item == null) {
            KPToast.showToast("登録されていない商品です");
        }else{
            this.mItemListView.getAdapter().add(item);

            this.sumPrice += item.price;
            this.priceView.setText(String.valueOf(this.sumPrice));
        }
    }

    private ActionBarDrawerToggle getDrawerToggle(){
        return new ActionBarDrawerToggle(
                        this,
                        mDrawerLayout,
                        R.string.navigation_open,
                        R.string.navigation_close);
    }
}
