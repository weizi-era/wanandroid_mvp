package com.zjw.wanandroid_mvp.ui.main.todo;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IPresenter;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.base.BaseActivity;
import com.zjw.wanandroid_mvp.bean.TodoBean;
import com.zjw.wanandroid_mvp.contract.todo.AddTodoContract;
import com.zjw.wanandroid_mvp.di.component.main.todo.DaggerAddTodoComponent;
import com.zjw.wanandroid_mvp.di.module.main.todo.AddTodoModule;
import com.zjw.wanandroid_mvp.event.AddEvent;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.presenter.main.todo.AddTodoPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import butterknife.BindView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class AddTodoActivity extends BaseActivity<AddTodoPresenter> implements AddTodoContract.IAddTodoView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.todo_title)
    EditText todo_title;
    @BindView(R.id.todo_content)
    EditText todo_content;
    @BindView(R.id.todo_complete_date)
    TextView complete_date;
    @BindView(R.id.todo_type)
    TextView todo_type;
    @BindView(R.id.todo_priority)
    TextView todo_priority;
    @BindView(R.id.submit)
    Button submit;

    private Dialog dialog;
    private DatePickerDialog dateDialog;
    private int year;
    private int month;
    private int day;

    private int typeTag;
    private int priorityTag;
    private Intent intent;

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerAddTodoComponent.builder()
                .appComponent(appComponent)
                .addTodoModule(new AddTodoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_todo;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        intent = getIntent();
        TodoBean bean = (TodoBean) intent.getSerializableExtra("data");
        if (bean != null) {
            toolbar.setTitle("修改待办");
            todo_title.setText(bean.getTitle());
            todo_content.setText(bean.getContent());
            complete_date.setText(bean.getDateStr());
            todo_type.setText(getType(bean.getType()));
            todo_priority.setText(getPriority(bean.getPriority()));
        } else {
            toolbar.setTitle("添加待办");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSystemTime();

        complete_date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (dateDialog == null) {
                    dateDialog = new DatePickerDialog(AddTodoActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String mMonth = String.format("%02d", month + 1);
                            complete_date.setText(year + "-" + mMonth + "-" + dayOfMonth);
                            dateDialog.dismiss();
                        }
                    }, year, month, day);
                }
                dateDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dateDialog.show();
            }
        });

        todo_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildDialog(R.layout.type_dialog_layout);

                dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.work).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        todo_type.setText("工作");
                        typeTag = 1;
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.life).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        todo_type.setText("生活");
                        typeTag = 2;
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.fun).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        todo_type.setText("娱乐");
                        typeTag = 3;
                        dialog.dismiss();
                    }
                });
            }

        });

        todo_priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buildDialog(R.layout.priority_dialog_layout);

                dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.important).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        todo_priority.setText("重要");
                        priorityTag = 1;
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.general).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        todo_priority.setText("一般");
                        priorityTag = 2;
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.tips).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        todo_priority.setText("提示");
                        priorityTag = 3;
                        dialog.dismiss();
                    }
                });
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean != null) {
                    mPresenter.updateTodo(bean.getId(), todo_title.getText().toString(), todo_content.getText().toString(), complete_date.getText().toString(),
                            typeTag == 0 ? bean.getType() : typeTag, priorityTag == 0 ? bean.getPriority() : priorityTag);
                } else {
                    mPresenter.addTodo(todo_title.getText().toString(), todo_content.getText().toString(), complete_date.getText().toString(),
                            typeTag, priorityTag);
                }
            }
        });
    }

    private void buildDialog(@LayoutRes int resource) {
        if (dialog == null) {
            dialog = new Dialog(AddTodoActivity.this, R.style.DialogTheme);
        }
        //2、设置布局
        View view = View.inflate(AddTodoActivity.this, resource,null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void getSystemTime() {
        Calendar calendar = Calendar.getInstance();//取得当前时间的年月日 时分秒
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void addTodo() {
        new AddEvent(Constant.TODO_CODE).post();
        finish();
    }

    @Override
    public void updateTodo() {
        new AddEvent(Constant.TODO_CODE).post();
        finish();
    }

    private String getType(int type) {

        switch (type) {
            case 1:
                return "工作";
            case 2:
                return "生活";
            case 3:
                return "娱乐";
        }

        return null;
    }

    private String getPriority(int priority) {

        switch (priority) {
            case 1:
                return "重要";
            case 2:
                return "一般";
            case 3:
                return "提示";
        }

        return null;
    }
}