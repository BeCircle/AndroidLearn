package cn.codeyourlife.chattest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter msgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMsg();
        inputText = (EditText) findViewById(R.id.user_input);
        send = (Button) findViewById(R.id.btn_send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.chat_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);

        msgAdapter = new MsgAdapter(this.msgList);
        msgRecyclerView.setAdapter(msgAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if (!"".equals(content)){
                    Msg msg = new Msg(content, Msg.TYPE_SEND);
                    msgList.add(msg);
                    // 当有新消息，刷新RecyclerView的显示
                    msgAdapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    inputText.setText("");
                }
            }
        });
    }

    private void initMsg(){
        this.msgList.add(new Msg("你好", Msg.TYPE_RECEIVED));
        this.msgList.add(new Msg("我一点都不好", Msg.TYPE_SEND));
        this.msgList.add(new Msg("你好哥胜多负少是单方事故第三个是十多个", Msg.TYPE_RECEIVED));
    }
}