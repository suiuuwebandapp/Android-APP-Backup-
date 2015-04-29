package com.minglang.suiuu.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CheckImgBean {
	private static CheckImgBean checkImgBean = null;
	
	/**
	 * 用于记录图片的选择数量
	 */
	public int mCount=0;
	
	
	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	private static List<String> SelectedImage=null;
	
	
	/**
	 * 记录用户取消选择的图片
	 */
	private static List<Integer> ReMoveId=null;
	
	private CheckImgBean() {

	}

	public static CheckImgBean getImgBean() {
		if (checkImgBean == null) {
			checkImgBean = new CheckImgBean();
		}
		return checkImgBean;
	}
	
	
	
	public List<String> getListImg(){
		if(SelectedImage==null){
			SelectedImage=new LinkedList<String>();
		}
		return SelectedImage;
	}
	
	/**
	 * 记录用户取消选择的图片的集合
	 * @return
	 */
	public List<Integer> getReMoveList(){
		if(ReMoveId==null){
			ReMoveId=new ArrayList<Integer>();
		}
		return ReMoveId;
	}

	public static void setReMoveId(List<Integer> reMoveId) {
		ReMoveId = reMoveId;
	}

	
}
