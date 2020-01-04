package com.rdstudio.kantinpos;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rdstudio.kantinpos.dataroom.Setoran;
import com.rdstudio.kantinpos.model.SetoranModel;
import com.rdstudio.kantinpos.utils.Tools;

import java.util.List;
import java.util.Objects;

public class TambahkanSetoran extends AppCompatActivity {

    private View parent_view;
    private String[] array_penyetor;
    private SetoranModel setoranModel;
    List<Setoran> setoranList;
    EditText et_jenis_setoran_1, et_jenis_setoran_2, et_jenis_setoran_3, et_jumlah_setoran_1, et_jumlah_setoran_2, et_jumlah_setoran_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahkan_setoran);
        parent_view = findViewById(R.id.parent_view);
        et_jenis_setoran_1 = findViewById(R.id.et_jenis_setoran_1);
        et_jenis_setoran_2 = findViewById(R.id.et_jenis_setoran_2);
        et_jenis_setoran_3 = findViewById(R.id.et_jenis_setoran_3);
        et_jumlah_setoran_1 = findViewById(R.id.et_jumlah_setoran_1);
        et_jumlah_setoran_2 = findViewById(R.id.et_jumlah_setoran_2);
        et_jumlah_setoran_3 = findViewById(R.id.et_jumlah_setoran_3);
        setoranModel = new ViewModelProvider(this).get(SetoranModel.class);
        setoranModel.getmAll().observe(this, new Observer<List<Setoran>>() {
            @Override
            public void onChanged(List<Setoran> setorans) {
                String[] strings = new String[setorans.size()];
                for (int i = 0; i < setorans.size(); i++) {
                    strings[i] = setorans.get(i).getNama();
                    if (i == setorans.size() - 1) {
                        array_penyetor = strings;
                        setoranList = setorans;
                    }
                }
            }
        });

//        array_penyetor = getResources().getStringArray(R.array.data_penyetor);
        initToolbar();
        initContent();
    }


    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        Objects.requireNonNull(toolbar.getNavigationIcon())
                .setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
        Tools.setSystemBarLight(this);
    }

    // Top menu simpan
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_simpan, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.grey_60));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            // Dialog menu simpan
            showConfirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    // Confirm dialog close and tambah setoran
    private void showConfirmDialog() {
/*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selesai & Tambah Lagi?");
        builder.setMessage(R.string.confirm_setoran);
        builder.setPositiveButton(R.string.ya, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Snackbar.make(parent_view, "Tambahkan data setoran lagi", Snackbar.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.tidak, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
*/
        setor();
        finish();
    }

    void setor() {
        Button etTambahPenyetor = findViewById(R.id.btn_tambah_penyetor);
        int i1, i2 = 0, i3 = 0;
        if ("".contentEquals(et_jumlah_setoran_1.getText())) {
            Toast.makeText(this, "Isi Jumlah Setoran", Toast.LENGTH_SHORT).show();
        } else {
            i1 = Integer.parseInt(et_jumlah_setoran_1.getText().toString());
            if ( !et_jenis_setoran_2.getText().toString().equals("")) {
                i2 = Integer.parseInt(et_jumlah_setoran_2.getText().toString());
            }
            if ( !et_jenis_setoran_3.getText().toString().equals("")) {
                i3 = Integer.parseInt(et_jumlah_setoran_3.getText().toString());
            }
            String nama = etTambahPenyetor.getText().toString();
            setoranModel.setor(nama, i1, i2, i3);
            Toast.makeText(this, "Setoran Ditambahkan", Toast.LENGTH_SHORT).show();
        }


    }

    private void initContent() {

        Button etTambahPenyetor = findViewById(R.id.btn_tambah_penyetor);
        etTambahPenyetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == R.id.btn_tambah_penyetor) {
                    initDialogTambahSetoran((AppCompatButton) view);
                }
            }
        });

    }

    private void initDialogTambahSetoran(final AppCompatButton btn) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setSingleChoiceItems(array_penyetor, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                btn.setTextColor(Color.BLACK);
                btn.setText(array_penyetor[i]);
                et_jenis_setoran_1.setText(setoranList.get(i).getBarang1().toString());
                if (!(setoranList.get(i).getBarang2() == null)) {
                    et_jenis_setoran_2.setText(setoranList.get(i).getBarang2().toString());
                }
                if (!(setoranList.get(i).getBarang3() == null)) {
                    et_jenis_setoran_3.setText(setoranList.get(i).getBarang3().toString());
                }
                et_jumlah_setoran_1.setText("");
                et_jumlah_setoran_2.setText("");
                et_jumlah_setoran_3.setText("");


            }
        });
        builder.show();
    }


}
