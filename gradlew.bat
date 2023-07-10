package com.googol.imagecompress.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import com.googol.filepicker.DividerGridItemDecoration;
import com.googol.imagecompress.R;
import com.googol.imagecompress.adapter.SucessResultAdapter;
import com.googol.imagecompress.base.BaseActivityy;
import com.googol.imagecompress.databinding.ActivityProcessResultBinding;
import com.googol.imagecompress.entity.ResultModel;
import com.googol.imagecompress.utills.Constants;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class SucessResultActivity extends BaseActivityy {
    ActivityProcessResultBinding binding;
    String compressionType = "";
    ArrayList<ResultModel> resultModelArrayList = new ArrayList<>();

    @Override // com.googol.imagecompress.base.BaseActivityy
    public void setOnClick() {
    }
    
    @Override // com.googol.imagecompress.base.BaseActivityy
    public void setBinding() {
        this.binding = (ActivityProcessResultBinding) DataBindingUtil.setContentView(this, R.layout.activity_process_result);
    }

    @Override // com.googol.imagecompress.base.BaseActivityy
    public void init() {
        this.binding.share.setImageResource(R.drawable.ic_share_black_24dp);
        setToolbar(true);
        this.compressionType = getIntent().getStringExtra(Constants.COMPRESSION_TYPE);
        if (this.compressionType.equalsIgnoreCase(Constants.RESIZE)) {
            setToolbarText(getString(R.string.resizeResult));
        } else {
            setToolbarText(getString(R.string.compressionResult));
        }
        this.resultModelArrayList = getIntent().getParcelableArrayListExtra(Constants.SELECTED_LIST);
        this.binding.resultList.setLayoutManager(new GridLayoutManager(this, 4));
        this.binding.resultList.addItemDecoration(new DividerGridItemDecoration(this));
        this.binding.resultList.setAdapter(new SucessResultAdapter(this.compressionType, this.resultModelArrayList, this));
    }

    public void onClick(View view) {
        Uri uri;
        if (view.getId(