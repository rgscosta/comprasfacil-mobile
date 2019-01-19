package sourceinformation.com.br.comprasfacil_mobile;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.R.id.home;

public class EscolhaActivity extends AppCompatActivity {

   ImageButton imageButtonCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        imageButtonCode = (ImageButton)findViewById(R.id.imageButtonCode);

        imageButtonCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EscolhaActivity.this, ScanActivity.class);
                startActivity(it);
            }
        });


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
