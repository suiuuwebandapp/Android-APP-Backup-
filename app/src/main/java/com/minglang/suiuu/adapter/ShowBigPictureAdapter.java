package com.minglang.suiuu.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/8/25 18:26
 * 修改人：Administrator
 * 修改时间：2015/8/25 18:26
 * 修改备注：
 */

public class ShowBigPictureAdapter extends PagerAdapter {
    private List<TextView> list;
    private List<String> titleList;
     public ShowBigPictureAdapter(List<TextView> list,List<String> titleList) {
        this.list = list;
         this.titleList = titleList;

    }
    @Override
    /**
     * 返回页面的个数
     */
    public int getCount() {
        return list.size();
    }

    @Override
    /**
     * 获得指定位置上的view
     * container 就是viewPager自身
     * position 是指定的位置
     */
    public Object instantiateItem(ViewGroup container, int position) {
        //System.out.println("instantiateItem  position:"+position);

        //给container添加一个指定位置上的view对象,container其实就是viewPager自身
      View view =   list.get(position);

        return view;
    }


    @Override
    /**
     * 判断指定的的view和object是否有关联关系
     * view 某一位置上的显示的页面
     * object 某一位置上返回的object 就是instantiateItem返回的object
     */
    public boolean isViewFromObject(View view, Object object) {
//			if(view == object){
//				return true;
//			}else{
//				return false;
//			}

        return view == object;
    }

    @Override
    /**
     * 销毁指定位置上的view
     *
     * object 就是instantiateItem 返回的object
     */
    public void destroyItem(ViewGroup container, int position, Object object) {
        //System.out.println("destroyItem  position:"+position);

        //下面这句如果不注掉，会抛异常
        //super.destroyItem(container, position, object);

        container.removeView((View)object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}

