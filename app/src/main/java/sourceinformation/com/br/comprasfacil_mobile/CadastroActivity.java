package sourceinformation.com.br.comprasfacil_mobile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import sourceinformation.com.br.comprasfacil_mobile.CONF.FireBase;
import sourceinformation.com.br.comprasfacil_mobile.Entidades.Usuarios;
import sourceinformation.com.br.comprasfacil_mobile.Helpers.BaseCustom;
import sourceinformation.com.br.comprasfacil_mobile.Helpers.Referencias;

import static android.R.id.home;

public class CadastroActivity extends AppCompatActivity {


    private EditText editTextEmailCadastro;
    private EditText editTextSenhaCadastro;
    private EditText editTextConfirSenhaCadastro;
    private EditText editTextNomeCadastro;
    private EditText editTextSobreNomeCadastro;
    private TextView textViewNaotemcadastro;
    private Button buttonFazerCadastro;
    private Usuarios usuarios;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTextEmailCadastro = (EditText) findViewById(R.id.editTextEmailCadastro);
        editTextSenhaCadastro = (EditText) findViewById(R.id.editTextSenhaCadastro);
        editTextNomeCadastro = (EditText) findViewById(R.id.editTextNomeCadastro);
        editTextConfirSenhaCadastro = (EditText)findViewById(R.id.editTextConfirSenhaCadastro);
        editTextSobreNomeCadastro =(EditText) findViewById(R.id.editTextSobreNomeCadastro);

        buttonFazerCadastro = (Button) findViewById(R.id.buttonFazerCadastro);

        buttonFazerCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextSenhaCadastro.getText().toString().equals(editTextConfirSenhaCadastro.getText().toString())){
                    usuarios = new Usuarios();
                    usuarios.setNome(editTextNomeCadastro.getText().toString());
                    usuarios.setEmail(editTextEmailCadastro.getText().toString());
                    usuarios.setSenha(editTextSenhaCadastro.getText().toString());
                    usuarios.setSobrenome(editTextSobreNomeCadastro.getText().toString());
                    cadastraUsuario();

                }else{

                    Toast.makeText(CadastroActivity.this, "As senha estão divergentes", Toast.LENGTH_LONG).show();
                }
            }
        });
                ActionBar ab = getSupportActionBar();
                 // Enable the Up button
               ab.setDisplayHomeAsUpEnabled(true);


    }
      private void cadastraUsuario(){
         autenticacao = FireBase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuarios.getEmail(),usuarios.getSenha()).addOnCompleteListener( new OnCompleteListener<AuthResult>() {

        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {

            if(task.isSuccessful()){



                Toast.makeText(CadastroActivity.this, "Cadastro Efetuado com Sucesso!", Toast.LENGTH_SHORT).show();

                String identificadorUsuario = BaseCustom.codificarBase(usuarios.getEmail());
                FirebaseUser usuarioFirebase = task.getResult().getUser();
                usuarios.setId(identificadorUsuario);
                usuarios.salvar();

                Referencias referencias = new Referencias(CadastroActivity.this);
                referencias.salvarUsuariosPreferencias(identificadorUsuario, usuarios.getNome());


                abrirTelaPrincipal();
            }else {
                String erroExcecao = "";

                try {
                    throw task.getException();
                } catch (FirebaseAuthWeakPasswordException e) {
                    erroExcecao = "Digite um senha mais forte contendo no minimo 8 caracteres de letras e números";
                } catch (FirebaseAuthInvalidCredentialsException e ){
                    erroExcecao = "O e-mail digitado e inválido, por favor digite um novo e-mail";
                } catch(FirebaseAuthUserCollisionException e){
                    erroExcecao = "O e-mail que digitado já esta cadastrado ";
                } catch(Exception e){
                erroExcecao = " Erro ao Efetuar Cadastro!!!";
                e.printStackTrace();
             }
             Toast.makeText(CadastroActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
            }
        }
    });


      }

      public void abrirTelaPrincipal() {

          Intent abrirtela = new Intent(CadastroActivity.this, LoginUserActivity.class);
          startActivity(abrirtela);
          finish();

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
