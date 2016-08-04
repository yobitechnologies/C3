package tech.yobi.c3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final HomeActivity current = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button startInstallation = (Button) findViewById(R.id.installation_activity_button);
        assert startInstallation != null;
        startInstallation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                attemptLogin();
                Intent intent = new Intent(current, InstallationActivity.class);
                startActivity(intent);
            }
        });

        Button startConfiguration = (Button) findViewById(R.id.configuration_activity_button);
        assert startConfiguration != null;
        startConfiguration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                attemptLogin();
                Intent intent = new Intent(current, ConfigurationActivity.class);
                startActivity(intent);
            }
        });
    }
}
