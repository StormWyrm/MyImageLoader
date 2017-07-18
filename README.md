# MyImageLoader  

## 一、简介：  
这是一个简单的异步图片加载框架。  

默认情况下采用硬盘和内存双缓存方式来对图片进行缓存并对图片进行压缩处理。硬盘缓存使用DiskLruCache,内存缓存采用LruCache,能够有效的提高图片加载的速度。    

该框架解决了ListView，RecyclerView异步加载网络图片混乱的情况，能够在网络请求图片之前加载默认显示图片，并在网络错误时加载默认错误图片。通过三级缓存，二次采样对图片进行压缩，能够有效的减少内存溢出的概率以及图片加载的速度。

## 二、下载：
1. 下载并以Moudle形式倒入Android Studio
2. 
## 三、使用:  
* 使用默认配置进行加载图片：

		ImageLoader.getInstance(Context context)
					.displayImage(ImageView imageView, String imageUrl);

* 修改图片加载框架的配置

        //建议在Application中进行初始化
		ImageLoaderConfig config = new ImageLoaderConfig.Builder(this)
	                .setImageCache()  //修改默认的缓存方案
	                .setImageDownload()//修改默认的图片下载方案
	                .setDefaultDrawable()//修改默认显示图片
	                .setErrorDrawable()//修改加载错误显示图片
	                .build();

	  	 ImageLoader.getInstance(this).init(config);//对ImageLoader进行配置
	    初始化完成后
