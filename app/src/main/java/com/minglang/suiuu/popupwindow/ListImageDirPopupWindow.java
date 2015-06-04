package com.minglang.suiuu.popupwindow;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.BaseHolderAdapter;
import com.minglang.suiuu.entity.ImageFolder;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

public class ListImageDirPopupWindow extends
        BasePopupWindowForListView<ImageFolder> {
    private ListView mListDir;

    public ListImageDirPopupWindow(int width, int height, List<ImageFolder> data, View convertView) {
        super(convertView, width, height, true, data);
    }

    @Override
    public void initViews() {
        mListDir = (ListView) findViewById(R.id.id_list_dir);
        mListDir.setAdapter(new BaseHolderAdapter<ImageFolder>(context, mData, R.layout.item_image_dir_pop) {
            @Override
            public void convert(ViewHolder helper, ImageFolder item, long position) {
                helper.setText(R.id.id_dir_item_name, item.getName());
                helper.setImageByUrl(R.id.id_dir_item_image,
                        item.getFirstImagePath());
                helper.setText(R.id.id_dir_item_count, item.getCount() + "张");
            }
        });
    }

    public interface OnImageDirSelected {
        void selected(ImageFolder folder);
    }

    private OnImageDirSelected mImageDirSelected;

    public void setOnImageDirSelected(OnImageDirSelected mImageDirSelected) {
        this.mImageDirSelected = mImageDirSelected;
    }

    @Override
    public void initEvents() {
        mListDir.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (mImageDirSelected != null) {
                    mImageDirSelected.selected(mData.get(position));
                }
            }
        });
    }

    @Override
    public void init() {

    }

    @Override
    protected void beforeInitWeNeedSomeParams(Object... params) {

    }

}
