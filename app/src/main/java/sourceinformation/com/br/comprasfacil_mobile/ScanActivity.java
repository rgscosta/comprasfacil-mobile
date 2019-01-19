package sourceinformation.com.br.comprasfacil_mobile;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static android.R.id.home;
import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class ScanActivity extends AppCompatActivity {

    Button buttonScan;
    TextView textViewScanCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        final Activity activity = this;

        buttonScan = (Button) findViewById(R.id.buttonScan);
        textViewScanCode = (TextView)findViewById(R.id.textViewScanCode);
          /*Para adicionar esse IntentIntegrator e necessario colocar no Gradle
          implementation 'com.journeyapps:zxing-android-embedded:2.3.0@aar'
          implementation 'com.journeyapps:zxing-android-legacy:2.3.0@aar'
          implementation 'com.journeyapps:zxing-android-integration:2.3.0@aar'
          implementation 'com.google.zxing:core:3.2.0'
          e Sincronizar para funcionar*/



        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Camera Scan");
                integrator.setCameraId(0);
                integrator.initiateScan();
            }
        });
    }
    //Sempre fora do Oncreate
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            textViewScanCode.setText(result.getContents());
        } else {
            alertar("Scan Cancelado");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void alertar (String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

    }
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}


