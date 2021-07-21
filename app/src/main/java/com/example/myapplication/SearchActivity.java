package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.valueOf;

public class SearchActivity  extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference idol_list;
    private DatabaseReference album_list;
    private DatabaseReference member_list;

    private boolean isSelected = false;
    private String selectGroup;
    private String selectAlbum;
    private String selectMember;

    private TextView textView_group;
    private TextView textView_album;
    private TextView textView_member;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search); // xml 연결

        textView_group = findViewById(R.id.TextView_group);
        textView_album = findViewById(R.id.TextView_album);
        textView_member = findViewById(R.id.TextView_member);

        mDatabase = FirebaseDatabase.getInstance().getReference(); //데이터를 읽기 위한 DatabaseReference 인스턴스
        idol_list = mDatabase.child("idol");
        idol_list.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String,Object> idol = (Map<String, Object>) snapshot.getValue();
                Log.d("data", String.valueOf(idol.keySet())); // [방탄소년단, 원어스]
                Object [] idol_list = idol.keySet().toArray();
                int len = idol_list.length;
                Log.d("data", String.valueOf(len)); // 2 (방탄, 원어스 2개)
                Log.d("data", idol_list[0].toString()); //방탄소년단

                selectGroup = idol_list[0].toString();
                isSelected = true;
                textView_group.setText(idol_list[0].toString());

                if(isSelected)
                    selectOther(selectGroup);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    public void selectOther(String group){
        album_list=mDatabase.child("idol").child(group).child("album");
        album_list.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList album = (ArrayList)snapshot.getValue();
                Log.d("data", "album 결과 - "+String.valueOf(album));
                Log.d("data", album.get(0).toString()); // {title=persona, version=1}
                Object obj = album.get(0); //첫번째 객체를 저장
                HashMap<String,String> aa = (HashMap)obj; //해시맵으로 변환
                Log.d("data", aa.get("title"));  // keySet에는 version이 있지만 get("version")으로 받지 못함
                Log.d("data", String.valueOf(aa.keySet())); //[title, version]
                Log.d("data", String.valueOf(aa.values())); //[persona, 1]

                Object[] titleAndVersion = aa.values().toArray(); // keySet으로 받지 못하기 때문에 Array로 변경하여
                String al = titleAndVersion[0].toString(); //title
                String ver = titleAndVersion[1].toString(); //version
                Log.d("data",al+" "+ver);
                selectAlbum = al+"("+ver+")";
                textView_album.setText(selectAlbum);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        member_list = mDatabase.child("idol").child(group).child("member");
        member_list.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList member = (ArrayList)snapshot.getValue();
                Log.d("data", "member 결과 - "+String.valueOf(member));
                Log.d("data", member.get(0).toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
