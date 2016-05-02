package guojing.com.form;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button1, button2, button3, button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form1);
        button1 = (Button) findViewById(R.id.save1);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToform2();
            }
        });



    }

    private void jumpToform2() {
        setContentView(R.layout.form2);
        button2 = (Button) findViewById(R.id.save2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToform3();
            }
        });
    }

    private void jumpToform3() {
        setContentView(R.layout.form3);
        button3 = (Button) findViewById(R.id.save3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToform4();
            }
        });
    }

    private void jumpToform4() {
        setContentView(R.layout.form4);
        button4 = (Button) findViewById(R.id.save4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"信息录入完成",Toast.LENGTH_LONG).show();
            }
        });
    }
}
