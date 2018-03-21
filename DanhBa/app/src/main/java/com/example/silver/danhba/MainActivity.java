package com.example.silver.danhba;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.silver.danhba.adapter.ContactAdapter;
import com.example.silver.danhba.mode.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private List<Contact> arrayContact;
    private ContactAdapter adapter;
    private EditText edtName;
    private EditText edtNumber;
    private RadioButton rbtnMale;
    private RadioButton rbtnFemale;
    private Button btnAddContact;
    private ListView lvContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWidget();
        arrayContact=new ArrayList<>();
        adapter=new ContactAdapter(this,R.layout.item_contact_listview,arrayContact);
        lvContact.setAdapter(adapter);
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                showDialogConfirm(position);
            }
        });
    }
    public void setWidget(){
        edtName=(EditText) findViewById(R.id.edt_name);
        edtNumber=(EditText) findViewById(R.id.edt_number);
        rbtnMale=(RadioButton) findViewById(R.id.rbtn_nam);
        rbtnFemale=(RadioButton) findViewById(R.id.rbtn_nu);
        btnAddContact=(Button) findViewById(R.id.btn_them_lien_he);
        lvContact=(ListView) findViewById(R.id.lv_lien_he);

    }
    public void addContact(View view){
        if(view.getId()==R.id.btn_them_lien_he){
            String name= edtName.getText().toString().trim();
            String number=edtNumber.getText().toString().trim();
            boolean isMale=true;
            if(rbtnMale.isChecked()){
                isMale=true;
            }else{
                isMale=false;
            }
            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(number)){
                Toast.makeText(this, "Vui lòng nhập Họ tên hoặc Số điện thoại", Toast.LENGTH_SHORT).show();
            }else{
                Contact contact = new Contact(isMale,name,number);
                arrayContact.add(contact);
            }
            adapter.notifyDataSetChanged();
        }
    }
    public void showDialogConfirm(final int position){
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        Button btnCall=(Button) dialog.findViewById(R.id.btn_call);
        Button btnSendMessage=(Button) dialog.findViewById(R.id.btn_send_message);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intenCall(position);
            }
        });
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentSendMessage(position);
            }
        });
        dialog.show();
    }

    private void intentSendMessage(int position) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:"+arrayContact.get(position).getmNumber()));
        startActivity(intent);
    }

    private void intenCall(int position) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+arrayContact.get(position).getmNumber()));
        startActivity(intent);
    }
}
