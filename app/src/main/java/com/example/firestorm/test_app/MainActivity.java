package com.example.firestorm.test_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
/* Greeting to anyone viewing this .. I hereby claim  I am an amature who is doing the project just
for educational purpose . as you will see there are a lot of bad practises in my code . anyway .
please feel free to EDIT . Making it better .. and also . I searched forums . Stack . Youtube .since
iam a ranked amatuer .i dont have that much talent to write all these alone ..  I am someone who
never had a Coding teacher :).
 */


public class MainActivity extends AppCompatActivity {

    EditText inputText,password;
    TextView outputText;
    String outputstring;
    String AES="AES";
    Button encbtn ,decbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText=(EditText)findViewById(R.id.inputText);
        outputText=(TextView)findViewById(R.id.textView);
        password=(EditText)findViewById(R.id.password);
        encbtn=(Button)findViewById(R.id.encode);
        decbtn=(Button)findViewById(R.id.decode);
        Button  BT1 =(Button)findViewById(R.id.button);

     BT1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

             newactivity();
         }
     });



    encbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            try {
                outputstring=encrypt(inputText.getText().toString(),password.getText().toString());
                outputText.setText(outputstring);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    });
    decbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            try {
                outputstring = decrypt(outputstring,password.getText().toString());
                outputText.setText(outputstring);


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this,"Wrong Password",Toast.LENGTH_LONG).show();

            }

        }
    });

    }

         private String encrypt(String Data,String password) throws Exception{
            SecretKeySpec key =generateKey(password);
             Cipher c = Cipher.getInstance(AES);
             c.init(Cipher.ENCRYPT_MODE,key);
             byte[] encval=c.doFinal(Data.getBytes());
             String encryptedValue = Base64.encodeToString(encval,Base64.DEFAULT);
            return encryptedValue;


   }

        private String decrypt(String outputstring,String password)throws Exception{
            SecretKeySpec key =generateKey(password);
            Cipher c = Cipher.getInstance(AES);
            c.init(Cipher.DECRYPT_MODE,key);
            byte[] decodeValue=Base64.decode(outputstring,Base64.DEFAULT);
            byte[] decValue=c.doFinal(decodeValue);
            String decryptedvalue = new String (decValue);
            return decryptedvalue;
        }

        private SecretKeySpec generateKey(String password) throws Exception{

            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte [] bytes =password.getBytes("UTF-8");
            digest.update(bytes,0,bytes.length);
            byte [] key =digest.digest();
            SecretKeySpec secretKeySpec=new SecretKeySpec(key,AES);
            return secretKeySpec;

        }

        public void newactivity(){

            Intent next =new Intent(MainActivity.this,Secondary.class);
            startActivity(next);
            Toast.makeText(this,"Work in progress",Toast.LENGTH_LONG).show();

        }


}
