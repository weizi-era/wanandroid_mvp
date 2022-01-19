package com.zjw.wanandroid_mvp.ui.main.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jess.arms.di.component.AppComponent;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.adapter.TodoAdapter;
import com.zjw.wanandroid_mvp.base.BaseActivity;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.bean.TodoBean;
import com.zjw.wanandroid_mvp.contract.todo.TodoListContract;
import com.zjw.wanandroid_mvp.di.component.main.todo.DaggerTodoListComponent;
import com.zjw.wanandroid_mvp.di.module.main.todo.TodoListModule;
import com.zjw.wanandroid_mvp.event.AddEvent;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.presenter.main.todo.TodoListPresenter;
import com.zjw.wanandroid_mvp.utils.RecyclerUtil;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.callback.EmptyCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;

public class TodoActivity extends BaseActivity<TodoListPresenter> implements TodoListContract.ITodoListView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipeRecyclerview)
    SwipeRecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;

    LoadService loadService;

    private int initPage = 1;
    private int currentPage = initPage;
    private TodoAdapter adapter;
    private Dialog dialog;

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerTodoListComponent.builder()
                .appComponent(appComponent)
                .todoListModule(new TodoListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_todo;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbar.setTitle("我的待办");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadService = LoadSir.getDefault().register(refreshLayout, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                loadService.showCallback(LoadingCallback.class);
                currentPage = initPage;
                mPresenter.getTodoList(currentPage);
            }
        });

        Utils.setLoadingColor(loadService);
        loadService.showCallback(LoadingCallback.class);

        initAdapter();

        // 初始化refreshLayout
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = initPage;
                mPresenter.getTodoList(currentPage);
            }
        });

        // 初始化recyclerview
        RecyclerUtil.initRecyclerView(this, recyclerView, new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getTodoList(currentPage);
            }
        });

        mPresenter.getTodoList(currentPage);
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TodoAdapter(R.layout.item_todo);
        recyclerView.setAdapter(adapter);

        adapter.addChildClickViewIds(R.id.todo_sandian);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                TodoBean bean = (TodoBean) adapter.getItem(position);
                Intent intent = new Intent(TodoActivity.this, AddTodoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", bean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                TodoBean bean = (TodoBean) adapter.getItem(position);

                buildDialog(bean);

                dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.delete_todo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.deleteTodo(bean.getId(), position);
                        dialog.dismiss();
                    }
                });

                if (bean.getStatus() != 1) {
                    dialog.findViewById(R.id.complete_todo).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mPresenter.completeTodo(bean.getId(), 1, position);
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_square, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                startActivity(new Intent(TodoActivity.this, AddTodoActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void showTodoList(BasePageBean<List<TodoBean>> bean) {
        refreshLayout.setRefreshing(false);
        if (currentPage == initPage && bean.getDatas().size() == 0) {
            loadService.showCallback(EmptyCallback.class);
        } else if (currentPage == initPage) {
            loadService.showSuccess();
            adapter.setNewInstance(bean.getDatas());
        } else {
            loadService.showSuccess();
            adapter.addData(bean.getDatas());
        }

        currentPage ++;
        if (bean.getPageCount() >= currentPage) {
            recyclerView.loadMoreFinish(false, true);
        } else {
            // 没有更多数据
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.loadMoreFinish(false, false);
                }
            }, 200);
        }
    }

    @Override
    public void deleteTodo(int position) {
        if (adapter.getData().size() > 1) {
            adapter.removeAt(position);
        } else {
            loadService.showCallback(EmptyCallback.class);
        }

    }

    @Override
    public void completeTodo(int position, TodoBean bean) {
        adapter.setData(position, bean);
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddTodoEvent(AddEvent event) {
        if (event.getCode() == Constant.TODO_CODE) {
            loadService.showCallback(LoadingCallback.class);
            adapter.setNewInstance(null);
            mPresenter.getTodoList(initPage);
        }
    }

    private void buildDialog(TodoBean bean) {
        if (dialog == null) {
            dialog = new Dialog(TodoActivity.this, R.style.DialogTheme);
        }
        //2、设置布局
        View view = View.inflate(TodoActivity.this, R.layout.bottom_dialog_layout,null);
        if (bean.getStatus() == 1) {
            view.findViewById(R.id.complete_todo).setVisibility(View.GONE);
        }
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
}