package tech.yobi.c3;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class ConfigurationActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_configuration, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * Fragment for setting the id
     */
    public static class SetIdFragment extends Fragment {

        public SetIdFragment() {
        }

        /**
         * Returns a new instance of this fragment
         */
        public static SetIdFragment newInstance() {
            SetIdFragment fragment = new SetIdFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_set_id, container, false);
            return rootView;
        }
    }

    /**
     * Fragment for setting name and location
     */
    public static class SetNameLocationFragment extends Fragment {

        public SetNameLocationFragment() {
        }

        /**
         * Returns a new instance of this fragment
         */
        public static SetNameLocationFragment newInstance() {
            SetNameLocationFragment fragment = new SetNameLocationFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_set_name_location, container, false);
            return rootView;
        }
    }

    /**
     * Fragment for setting the carrier
     */
    public static class SetCarrierFragment extends Fragment {

        public SetCarrierFragment() {
        }

        /**
         * Returns a new instance of this fragment
         */
        public static SetCarrierFragment newInstance() {
            SetCarrierFragment fragment = new SetCarrierFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_set_carrier, container, false);
            return rootView;
        }
    }

    /**
     * Fragment for setting the number
     */
    public static class SetNumberFragment extends Fragment {

        public SetNumberFragment() {
        }

        /**
         * Returns a new instance of this fragment
         */
        public static SetNumberFragment newInstance() {
            SetNumberFragment fragment = new SetNumberFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_set_number, container, false);
            return rootView;
        }
    }

    /**
     * Fragment for confirming details
     */
    public static class ConfirmDetailsFragment extends Fragment {

        public ConfirmDetailsFragment() {
        }

        /**
         * Returns a new instance of this fragment
         */
        public static ConfirmDetailsFragment newInstance() {
            ConfirmDetailsFragment fragment = new ConfirmDetailsFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_confirm_details, container, false);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            Fragment resultFragment = null;
//            return PlaceholderFragment.newInstance(position + 1);
            switch (position) {
                case 0:
                    resultFragment = SetIdFragment.newInstance();
                    break;
                case 1:
                    resultFragment = SetNameLocationFragment.newInstance();
                    break;
                case 2:
                    resultFragment = SetCarrierFragment.newInstance();
                    break;
                case 3:
                    resultFragment = SetNumberFragment.newInstance();
                    break;
                case 4:
                    resultFragment = ConfirmDetailsFragment.newInstance();
                    break;
                default:
                    Log.e("Fragment error", "Fragment not defined for position " + position);
                    resultFragment = null;

            }
            return resultFragment;
        }

        @Override
        public int getCount() {
            // Show total number of pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            CharSequence result = null;
            switch (position) {
                case 0:
                    result = "Set ID";
                    break;
                case 1:
                    result = "Set Name and Location";
                    break;
                case 2:
                    result = "Best Carrier";
                    break;
                case 3:
                    result = "Set Number";
                    break;
                case 4:
                    result = "Confirm Details";
                    break;
            }
            return result;
        }
    }
}
