package com.weatherapp.umbrella.ui.dashboard.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weatherapp.umbrella.R;
import com.weatherapp.umbrella.UmbrellaApplication;
import com.weatherapp.umbrella.callbacks.ScreenRefresher;
import com.weatherapp.umbrella.callbacks.ToolbarDecorator;
import com.weatherapp.umbrella.events.DisplayProgressEvent;
import com.weatherapp.umbrella.events.HideProgressEvent;
import com.weatherapp.umbrella.events.ScreenSwitcherEvent;
import com.weatherapp.umbrella.models.common.ToolBarModel;
import com.weatherapp.umbrella.presenters.dashboard.FetchHourlyInfoPresenter;
import com.weatherapp.umbrella.utils.UiUtility;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.weatherapp.umbrella.utils.Constants.ZIPCODE;

public class MainActivity extends AppCompatActivity implements ToolbarDecorator {

    @BindView(R.id.back)
    ImageView backButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.settings)
    ImageView settings;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.temp)
    TextView temp;
    @BindView(R.id.conditions)
    TextView conditions;
    @BindView(R.id.tempContainer)
    LinearLayout tempContainer;

    @Inject
    EventBus eventBus;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    FetchHourlyInfoPresenter presenter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((UmbrellaApplication) getApplication()).getApplicationComponent().inject(this);
        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.settings)
    public void onSettingsClicked(View v) {
        v.setVisibility(View.GONE);
        backButton.setVisibility(View.VISIBLE);
        presenter.showSettingsPage(this);
    }

    @OnClick(R.id.back)
    public void onBackClicked(View v) {
        getSupportFragmentManager().popBackStack();
    }

    @Subscribe(sticky = true)
    public void onDiaplayProgressReceived(DisplayProgressEvent event) {
        eventBus.removeStickyEvent(event);
        progressDialog = UiUtility.createProgressDialog(this);
    }

    @Subscribe(sticky = true)
    public void onScreenSwitcherEventReceived(ScreenSwitcherEvent event) {
        eventBus.removeStickyEvent(ScreenSwitcherEvent.class);
        if (event == null)
            return;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragmentToDisplay = event.getFragment();
        if (isDuplicateFragment(fragmentToDisplay, fragmentManager)) {
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.container);
            if (currentFragment instanceof ScreenRefresher)
                ((ScreenRefresher) currentFragment).refreshScreen(event.getModel());
            return;
        }
        fragmentTransaction.replace(R.id.container, fragmentToDisplay);
        if (event.isAddToBackstack())
            fragmentTransaction.addToBackStack(fragmentToDisplay.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    private boolean isDuplicateFragment(Fragment fragmentToDisplay, FragmentManager fragmentManager) {
        Fragment fragmentOnTop = fragmentManager.findFragmentById(R.id.container);
        if (fragmentOnTop == null || fragmentToDisplay == null)
            return false;
        if (StringUtils.equalsIgnoreCase(fragmentOnTop.getClass().getCanonicalName(),
                fragmentToDisplay.getClass().getCanonicalName()))
            return true;
        return false;
    }

    @Subscribe(sticky = true)
    public void onHideProgressReceived(HideProgressEvent event) {
        eventBus.removeStickyEvent(event);
        progressDialog.dismiss();
    }

    @Override
    protected void onStart() {
        super.onStart();
        eventBus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        eventBus.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (StringUtils.isEmpty(sharedPreferences.getString(ZIPCODE, "")))
            UiUtility.showChangeZipDialog(this, sharedPreferences, eventBus);
        else
            presenter.queueRequest();
    }

    @Override
    public void decorateToolbar(ToolBarModel model) {
        if (model == null)
            return;
        title.setText(model.getTitle());
        toolbar.setBackgroundColor(model.getColorCode());
        if (model.isDashboardView()) {
            tempContainer.setVisibility(View.VISIBLE);
            temp.setText(model.getTemperature(sharedPreferences));
            conditions.setText(model.getCondition());
            backButton.setVisibility(View.GONE);
            settings.setVisibility(View.VISIBLE);
        } else {
            tempContainer.setVisibility(View.GONE);
        }
    }
}
