package com.rdstudio.kantinpos;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rdstudio.kantinpos.dataroom.Setoran;
import com.rdstudio.kantinpos.model.SetoranModel;

import java.text.MessageFormat;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HitungFragment extends Fragment {

    private Button btn_tambah_penyetor, btn_masukkan_sisa_1, btn_masukkan_sisa_2, btn_masukkan_sisa_3;
    private SetoranModel setoranModel;
    private String[] array_penyetor;
    List<Setoran> setoranList;
    private TextView barang1, barang2, barang3, tv_hpp_1, tv_hpp_2, tv_hpp_3, tv_jumlah_1, tv_jumlah_2, tv_jumlah_3, et_nama_setoran_1, et_nama_setoran_2, et_nama_setoran_3;
    private TextView terjual1, terjual2, terjual3, bayar_penyetor1, bayar_penyetor2, bayar_penyetor3;
    private TextView tv1, tv2;
    private LinearLayout ll2, ll3;
    int posisi = 0;


    public HitungFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hitung, container, false);
        btn_tambah_penyetor = view.findViewById(R.id.btn_tambah_penyetor);
        barang1 = view.findViewById(R.id.barang1);
        barang2 = view.findViewById(R.id.barang2);
        barang3 = view.findViewById(R.id.barang3);
        tv_hpp_1 = view.findViewById(R.id.tv_hpp_1);
        tv_hpp_2 = view.findViewById(R.id.tv_hpp_2);
        tv_hpp_3 = view.findViewById(R.id.tv_hpp_3);
        tv_jumlah_1 = view.findViewById(R.id.tv_jumlah_1);
        tv_jumlah_2 = view.findViewById(R.id.tv_jumlah_2);
        tv_jumlah_3 = view.findViewById(R.id.tv_jumlah_3);
        et_nama_setoran_1 = view.findViewById(R.id.et_nama_setoran_1);
        et_nama_setoran_2 = view.findViewById(R.id.et_nama_setoran_2);
        et_nama_setoran_3 = view.findViewById(R.id.et_nama_setoran_3);
        btn_masukkan_sisa_1 = view.findViewById(R.id.btn_masukkan_sisa_1);
        btn_masukkan_sisa_2 = view.findViewById(R.id.btn_masukkan_sisa_2);
        btn_masukkan_sisa_3 = view.findViewById(R.id.btn_masukkan_sisa_3);
        bayar_penyetor1 = view.findViewById(R.id.bayar_penyetor1);
        bayar_penyetor2 = view.findViewById(R.id.bayar_penyetor2);
        bayar_penyetor3 = view.findViewById(R.id.bayar_penyetor3);
        terjual1 = view.findViewById(R.id.terjual1);
        terjual2 = view.findViewById(R.id.terjual2);
        terjual3 = view.findViewById(R.id.terjual3);
        ll2 = view.findViewById(R.id.ll2);
        ll3 = view.findViewById(R.id.ll3);
        btn_masukkan_sisa_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sisa((Button) v);
            }
        });
        btn_masukkan_sisa_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sisa((Button) v);
            }
        });
        btn_masukkan_sisa_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sisa((Button) v);
            }
        });

        btn_tambah_penyetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialogTambahSetoran((AppCompatButton) v);
            }
        });
        setoranModel = new ViewModelProvider(this).get(SetoranModel.class);
        setoranModel.getsetoran().observe(this, new Observer<List<Setoran>>() {
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
        // Inflate the layout for this fragment
        return view;
    }


    private void initDialogTambahSetoran(final AppCompatButton btn) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setSingleChoiceItems(array_penyetor, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                posisi = i;
                btn.setTextColor(Color.BLACK);
                btn.setText(array_penyetor[i]);
                barang1.setText(setoranList.get(i).getBarang1());
                et_nama_setoran_1.setText(setoranList.get(i).getBarang1());
                tv_hpp_1.setText(MessageFormat.format("{0}", setoranList.get(i).getHarga_beli1()));
                tv_jumlah_1.setText(MessageFormat.format("{0}", setoranList.get(i).getJumlah1()));
                if (!(setoranList.get(i).getBarang2() == null)) {
                    if (!(setoranList.get(i).getBarang2().isEmpty())) {
                        barang2.setText(setoranList.get(i).getBarang2().toString());
                        et_nama_setoran_2.setText(setoranList.get(i).getBarang2());
                        tv_hpp_2.setText(MessageFormat.format("{0}", setoranList.get(i).getHarga_beli2()));
                        tv_jumlah_2.setText(MessageFormat.format("{0}", setoranList.get(i).getJumlah2()));
                        ll2.setVisibility(View.VISIBLE);
                    }else {
                        barang2.setText("");
                        et_nama_setoran_2.setText("");
                        tv_hpp_2.setText("");
                        tv_jumlah_2.setText("0");
                        ll2.setVisibility(View.GONE);
                    }
                } else {
                    barang2.setText("");
                    et_nama_setoran_2.setText("");
                    tv_hpp_2.setText("");
                    tv_jumlah_2.setText("0");
                    ll2.setVisibility(View.GONE);
                }
                if (!(setoranList.get(i).getBarang3() == null)) {
                    if (!(setoranList.get(i).getBarang3().isEmpty())) {
                        barang3.setText(setoranList.get(i).getBarang3().toString());
                        et_nama_setoran_3.setText(setoranList.get(i).getBarang3());
                        tv_hpp_3.setText(MessageFormat.format("{0}", setoranList.get(i).getHarga_beli3()));
                        tv_jumlah_3.setText(MessageFormat.format("{0}", setoranList.get(i).getJumlah3()));
                        ll3.setVisibility(View.VISIBLE);
                    }else {
                        barang3.setText("");
                        et_nama_setoran_3.setText("");
                        tv_hpp_3.setText("");
                        tv_jumlah_3.setText("0");
                        ll3.setVisibility(View.GONE);
                    }
                } else {
                    barang3.setText("");
                    et_nama_setoran_3.setText("");
                    tv_hpp_3.setText("");
                    tv_jumlah_3.setText("0");
                    ll3.setVisibility(View.GONE);
                }
            }
        });
        builder.show();
    }

    private void sisa(Button btn) {
        int max = 0, harga = 0;
        if (!btn_tambah_penyetor.getText().toString().isEmpty()) {
            switch (btn.getId()) {
                case R.id.btn_masukkan_sisa_1:
                    max = setoranList.get(posisi).getJumlah1();
                    tv1 = terjual1;
                    tv2 = bayar_penyetor1;
                    harga = setoranList.get(posisi).getHarga_beli1();
                    break;
                case R.id.btn_masukkan_sisa_2:
                    max = setoranList.get(posisi).getJumlah2();
                    tv1 = terjual2;
                    tv2 = bayar_penyetor2;
                    harga = setoranList.get(posisi).getHarga_beli2();
                    break;
                case R.id.btn_masukkan_sisa_3:
                    max = setoranList.get(posisi).getJumlah3();
                    tv1 = terjual3;
                    tv2 = bayar_penyetor3;
                    harga = setoranList.get(posisi).getHarga_beli3();
                    break;
            }
            String[] ints = new String[max + 1];
            for (int i = 0; i < max; i++) {
                ints[i] = String.valueOf(i);
                if (i == max - 1) {
                    ints[i + 1] = String.valueOf(i + 1);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setCancelable(true);
                    int finalMax = max;
                    int finalHarga = harga;
                    builder.setSingleChoiceItems(ints, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            btn.setTextColor(Color.BLACK);
                            btn.setText(ints[i]);
                            tv1.setText(MessageFormat.format("{0}", finalMax - i));
                            tv2.setText(MessageFormat.format("Rp. {0}", (finalMax - i) * finalHarga));
                        }
                    });
                    builder.show();

                }
            }
        }

    }

}
