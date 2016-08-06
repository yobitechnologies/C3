package tech.yobi.c3;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationActivity extends AppCompatActivity implements LocationListener {

    static ConfigurationManager configurationManager = new ConfigurationManager();
    static LocationManager locationManager;

    static double curr_latitude = -1001;
    static double curr_longitude = -1001;

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    private static final int INITIAL_REQUEST = 1337;

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
        findViewById(R.id.main_content).requestFocus();

        //Requesting permissions
        if (Build.VERSION.SDK_INT >= 23 && PackageManager.PERMISSION_GRANTED != checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
        }

        //getting GPS location from LocationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    2000,   // Interval in milliseconds
                    10, this);
        } catch (SecurityException e) {
            Toast.makeText(getBaseContext(), "Security exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int i, final float v, final int i2) {
            }

            @Override
            public void onPageSelected(final int i) {
                if (i == 4) {
                    ConfirmDetailsFragment fragment = (ConfirmDetailsFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, i);
                    if (fragment != null) {
                        //Confirm details page is visible now: refresh values
//                        Log.e("ConfirmDetailsFragment", "visible");
                        fragment.refreshValues();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(final int i) {
            }
        });
    }


    //LocationManager stuff
    @Override
    public void onLocationChanged(Location location) {
        curr_latitude = location.getLatitude();
        curr_longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps turned off ", Toast.LENGTH_LONG).show();
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
            EditText idText = (EditText) rootView.findViewById(R.id.id_text);

            idText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int id;
                    try {
                        id = Integer.parseInt(s.toString());
                    } catch (Exception e) {
                        id = 0;
                    }

                    configurationManager.setId(id);
                    Log.e("ID Changed", (Integer.valueOf(configurationManager.getId())).toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
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
            final View rootView = inflater.inflate(R.layout.fragment_set_name_location, container, false);

            final Button fromGPSButton = (Button) rootView.findViewById(R.id.get_loc_button);
            final EditText latText = (EditText) rootView.findViewById(R.id.lat_text);
            final EditText lngText = (EditText) rootView.findViewById(R.id.lng_text);
            final EditText nameText = (EditText) rootView.findViewById(R.id.name_text);


            final Geocoder geocoder = new Geocoder(getContext());

            fromGPSButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (curr_longitude < -1000) {
//                        Toast.makeText(getContext(), "Could not get location from GPS", Toast.LENGTH_SHORT).show();
                    } else {
                        latText.setText(Double.toString(curr_latitude));
                        lngText.setText(Double.toString(curr_longitude));
                        configurationManager.setLatitude(curr_latitude);
                        configurationManager.setLongitude(curr_longitude);
                        String name;
                        try {
                            Address location = geocoder.getFromLocation(curr_latitude, curr_longitude, 1).get(0);
                            name = location.getLocality();
                            assert name != null;
                        } catch (Exception e) {
                            name = "";
                            Toast.makeText(getContext(), "Could not get name from coordinates", Toast.LENGTH_LONG).show();
                        }
                        if (name != null) {
                            nameText.setText(name);
                        } else {
                            nameText.setText("");
                        }
                    }

                }
            });

            lngText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    double lng;
                    try {
                        lng = Double.parseDouble(s.toString());
                    } catch (Exception e) {
                        lng = 0;
                    }

                    configurationManager.setLongitude(lng);
                    Log.e("Longitude Changed", (Double.valueOf(configurationManager.getLongitude())).toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            latText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    double lat;
                    try {
                        lat = Double.parseDouble(s.toString());
                    } catch (Exception e) {
                        lat = 0;
                    }

                    configurationManager.setLatitude(lat);
                    Log.e("Latitude Changed", (Double.valueOf(configurationManager.getLatitude())).toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            nameText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String name;
                    try {
                        name = s.toString();
                    } catch (Exception e) {
                        name = "";
                    }

                    configurationManager.setName(name);
                    Log.e("Name Changed", (configurationManager.getName()));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return rootView;
        }
    }

    /**
     * Fragment for setting the carrier
     */
    public static class SetCarrierFragment extends Fragment implements OnItemSelectedListener {

        Spinner serviceProviderSpinner;

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
            serviceProviderSpinner = (Spinner) rootView.findViewById(R.id.service_provider_spinner);
            serviceProviderSpinner.setOnItemSelectedListener(this);

            List<String> providers = new ArrayList<String>();
            providers.add("Airtel");
            providers.add("Aircel");
            providers.add("BSNL");
            providers.add("Idea");
            providers.add("Reliance");
            providers.add("Tata Docomo");
            providers.add("Vodafone");

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, providers);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            serviceProviderSpinner.setAdapter(adapter);

            return rootView;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String serviceProvider = parent.getItemAtPosition(position).toString();
            configurationManager.setCarrier(serviceProvider);
            Log.e("Service Provider", configurationManager.getCarrier());

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            String serviceProvider = parent.getItemAtPosition(0).toString();
            configurationManager.setCarrier(serviceProvider);
            Log.e("Service Provider", configurationManager.getCarrier());
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
            EditText simNumText = (EditText) rootView.findViewById(R.id.sim_num_text);

            simNumText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String sim_number = s.toString();
                    configurationManager.setSimNumber(sim_number);
                    Log.e("Sim Number Changed", (configurationManager.getSimNumber()));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return rootView;
        }
    }

    /**
     * Fragment for confirming details
     */
    public static class ConfirmDetailsFragment extends Fragment {

        TextView confirm_id_text;
        TextView confirm_name_text;
        TextView confirm_lat_text;
        TextView confirm_lng_text;
        TextView confirm_service_provider_text;
        TextView confirm_sim_num_text;
        Button submit_configuration_button;


        public ConfirmDetailsFragment() {
//            Log.e("ConfirmDetailsFragment", "constructor");
        }

        /**
         * Returns a new instance of this fragment
         */
        public static ConfirmDetailsFragment newInstance() {
            ConfirmDetailsFragment fragment = new ConfirmDetailsFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
//            Log.e("ConfirmDetailsFragment", "newInstance");
            return fragment;
        }

        public void refreshValues() {
//            Log.e("ConfirmDetailsFragment", "Refreshvalues");
            if (configurationManager.getId() != 0) {
                confirm_id_text.setText(Integer.toString(configurationManager.getId()));
            } else {
                confirm_id_text.setText("N/A");
            }
            if (configurationManager.getName() != null) {
                confirm_name_text.setText(configurationManager.getName());
            } else {
                confirm_name_text.setText("N/A");
            }
            if (configurationManager.getLatitude() != 0) {
                confirm_lat_text.setText(Double.toString(configurationManager.getLatitude()));
            } else {
                confirm_lat_text.setText("N/A");
            }
            if (configurationManager.getLongitude() != 0) {
                confirm_lng_text.setText(Double.toString(configurationManager.getLongitude()));
            } else {
                confirm_lng_text.setText("N/A");
            }
            if (configurationManager.getCarrier() != null) {
                confirm_service_provider_text.setText(configurationManager.getCarrier());
            } else {
                confirm_service_provider_text.setText("N/A");
            }
            if (configurationManager.getSimNumber() != null) {
                confirm_sim_num_text.setText(configurationManager.getSimNumber());
            } else {
                confirm_sim_num_text.setText("N/A");
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_confirm_details, container, false);

            confirm_id_text = (TextView) rootView.findViewById(R.id.confirm_id_text);
            confirm_name_text = (TextView) rootView.findViewById(R.id.confirm_name_text);
            confirm_lat_text = (TextView) rootView.findViewById(R.id.confirm_lat_text);
            confirm_lng_text = (TextView) rootView.findViewById(R.id.confirm_lng_text);
            confirm_service_provider_text = (TextView) rootView.findViewById(R.id.confirm_service_provider_text);
            confirm_sim_num_text = (TextView) rootView.findViewById(R.id.confirm_sim_num_text);
            submit_configuration_button = (Button) rootView.findViewById(R.id.submit_configuration_button);

            submit_configuration_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Instantiating the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(getContext());

                    // Generating the URL
                    StringBuilder urlBuilder = new StringBuilder();
                    urlBuilder.append(CredentialsManager.CONFIG_SUBMIT_URL);
                    // Adding parameters
                    urlBuilder.append("?");
                    Map<String, String> params = configurationManager.getParams();
                    for (String key : params.keySet()) {
                        urlBuilder.append(key);
                        urlBuilder.append("=");
                        urlBuilder.append(params.get(key));
                        urlBuilder.append("&");
                    }
                    urlBuilder.setLength(urlBuilder.length() - 1);
                    String url = urlBuilder.toString();

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.
                                    Log.e("VOLLEY", "Response is: " + response);
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", "That didn't work:" + error.getMessage());
                            Toast.makeText(getContext(), "Error sending request to server", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            // Adding parameters to request
                            return configurationManager.getParams();
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }
            });

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
