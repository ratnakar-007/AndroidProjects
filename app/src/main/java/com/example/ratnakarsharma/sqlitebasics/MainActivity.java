package com.example.ratnakarsharma.sqlitebasics;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ratnakarsharma.sqlitebasics.dbPackage.TodoDbHelper;
import com.example.ratnakarsharma.sqlitebasics.dbPackage.tables.TodoTable;
import com.example.ratnakarsharma.sqlitebasics.model.Todo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Todo> todoList;
    EditText etNewTask, etId;
    Button btnAddTask, btnUpdate;
    RecyclerView rvTodoList;
    TodoListAdapter tlAdapter;
    SQLiteDatabase todoDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNewTask = (EditText) findViewById(R.id.etNewTask);
        etId = (EditText) findViewById(R.id.etId);
        btnAddTask = (Button) findViewById(R.id.btnAddTask);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        rvTodoList = (RecyclerView) findViewById(R.id.rvTodoList);

        todoDb = (new TodoDbHelper(this)).getWritableDatabase();

        todoList = TodoTable.getAllTodos(todoDb);

        tlAdapter = new TodoListAdapter();
        rvTodoList.setLayoutManager(new LinearLayoutManager(this));
        rvTodoList.setAdapter(tlAdapter);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add text from etNewTask to database
                Todo newTodo = new Todo(
                        etId.getText().toString(),
                        etNewTask.getText().toString(),
                        true
                );
                TodoTable.addNewTodo(todoDb, newTodo);
                updateTodo();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todo updateTodo = new Todo(
                        etId.getText().toString(),
                        etNewTask.getText().toString(),
                        true
                );
                boolean bool = TodoTable.updateTodo(todoDb, updateTodo);
                if(bool){
                    Toast.makeText(MainActivity.this, "UPDATE SUCCess", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(MainActivity.this, "UPDATE FAILURE", Toast.LENGTH_SHORT).show();
                updateTodo();
            }
        });

    }

    void updateTodo () {
        todoList = TodoTable.getAllTodos(todoDb);
        tlAdapter.notifyDataSetChanged();
    }


    public class TodoCardHolder extends RecyclerView.ViewHolder {

        TextView tvTaskName, tvComplete, tvID;

        public TodoCardHolder(View itemView) {
            super(itemView);

            tvTaskName = (TextView) itemView.findViewById(R.id.tvTaskName);
            tvComplete = (TextView) itemView.findViewById(R.id.tvComplete);
            tvID = (TextView) itemView.findViewById(R.id.tvID);
        }
    }

    public class TodoListAdapter extends RecyclerView.Adapter<TodoCardHolder> {

        @Override
        public TodoCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View rootView = getLayoutInflater().inflate(R.layout.list_item_todo, parent, false);

            TodoCardHolder tcHolder = new TodoCardHolder(rootView);

            return tcHolder;
        }

        @Override
        public void onBindViewHolder(TodoCardHolder holder, int position) {

            holder.tvTaskName.setText(todoList.get(position).getTask());
            holder.tvID.setText(todoList.get(position).getId());
            if (todoList.get(position).isDone()) {
                holder.tvComplete.setText("Complete");
            }

        }

        @Override
        public int getItemCount() {
            return todoList.size();
        }
    }
}