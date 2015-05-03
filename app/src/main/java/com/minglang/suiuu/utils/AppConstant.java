package com.minglang.suiuu.utils;

public class AppConstant {
	/***
	 * 4.4以下(也就是kitkat以下)的版本
	 */
	public static final int KITKAT_LESS = 0;
	/***
	 * 4.4以上(也就是kitkat以上)的版本,当然也包括最新出的5.0棒棒糖
	 */
	public static final int KITKAT_ABOVE = 1;
	
	/***
	 * 裁剪图片成功后返回
	 */
	public static final int INTENT_CROP = 2;

    /**
     * 保存在suiuu_content对象中的图片
     */
    public static final String img_from_suiuu_content = "http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content";
}
