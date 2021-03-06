package com.example.wiuh.activity.board.memo;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.wiuh.R;
import com.example.wiuh.WifiInfo;
import com.example.wiuh.model.Memo;
import com.example.wiuh.util.FirebaseUtil;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AddMemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//actionbar에 toolbar대입
        Objects.requireNonNull(getSupportActionBar()).setTitle("메모작성");
        //toolbar에 뒤로가기 생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.submit_memo_btn).setOnClickListener(v -> addMemo());
    }

    private void addMemo() {
        FirebaseUser curUser = FirebaseUtil.getCurUser();
        String SSID = WifiInfo.getSSID();
        String MAC = WifiInfo.getMAC();

        String title = ((EditText) findViewById(R.id.add_title_memo)).getText().toString();
        String body = ((EditText) findViewById(R.id.add_content_memo)).getText().toString();

        Memo memo = new Memo(curUser.getUid(), title, curUser.getDisplayName(), body);
        FirebaseUtil.getMemoRef().push().setValue(memo);
        FirebaseUtil.getWifiRef().child(MAC).setValue(SSID);

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}