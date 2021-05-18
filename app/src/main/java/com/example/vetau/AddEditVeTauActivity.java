package com.example.vetau;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditVeTauActivity extends AppCompatActivity {
    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;

    private EditText edt_ga_di;
    private EditText edt_ga_den;
    private EditText edt_gia_ve;
    private EditText edt_loai_ve;
    private Button buttonSave;
    private Button buttonCancel;

    private Vetau vetau;
    private boolean needRefresh;
    private int mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add_ve_tau);

        edt_ga_den=findViewById(R.id.edt_ga_den);
        edt_ga_di=findViewById(R.id.edt_ga_di);
        edt_gia_ve=findViewById(R.id.edt_gia_ve);
        edt_loai_ve=findViewById(R.id.edt_loai_ve);
        buttonSave =findViewById(R.id.button_save);
        buttonCancel =findViewById(R.id.button_cancel);

        buttonSave.setOnClickListener(v -> buttonSaveClicked());
        buttonCancel.setOnClickListener(v -> buttonCancelClicked());


        Intent intent = this.getIntent();
        this.vetau = (Vetau) intent.getSerializableExtra("vetau");
        if(vetau== null)  {
            this.mode = MODE_CREATE;
        } else  {
            this.mode = MODE_EDIT;
            this.edt_ga_den.setText(vetau.getGaDen());
            this.edt_ga_di.setText(vetau.getGaDi());
            this.edt_gia_ve.setText(""+vetau.getDonGia());
            this.edt_loai_ve.setText(""+vetau.getLoaiVe());

        }
    }

    private void buttonCancelClicked() {
        this.onBackPressed();
    }

    private void buttonSaveClicked() {
        MyDatabaseHelper db = new MyDatabaseHelper(this);

        String gaden =  edt_ga_den.getText().toString();
        String gadi =  edt_ga_di.getText().toString();
        //if you don't input giave  --> giave=0-->you must input it  again.
        int giatien =  edt_gia_ve.getText().toString().equals("")?0:Integer.parseInt(edt_gia_ve.getText().toString());
        //if you don't input loaive  --> loaive=0--> you must input it again.
        int loaive =  edt_loai_ve.getText().toString().equals("")?0:Integer.parseInt( edt_loai_ve.getText().toString());

        if(gaden.equals("") || gadi.equals("")||giatien==0||loaive==0) {
            Toast.makeText(getApplicationContext(),
                    "Please enter data again ", Toast.LENGTH_LONG).show();
            return;
        }

        if(mode == MODE_CREATE ) {
            this.vetau= new Vetau(gaden,gadi, giatien,loaive);
            db.addVetau(vetau);
        } else  {
            this.vetau.setGaDi(gadi);
            this.vetau.setGaDen(gaden);
            this.vetau.setDonGia(giatien);
            this.vetau.setLoaiVe(loaive);
            db.updateVeTau(vetau);
        }

        this.needRefresh = true;

        // Back to MainActivity.
        this.onBackPressed();
    }
    @Override
    public void finish() {

        // Create Intent
        Intent data = new Intent();

        // Request MainActivity refresh its ListView (or not).
        data.putExtra("DoneedRefresh?", needRefresh);

        // Set Result
        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }
}
