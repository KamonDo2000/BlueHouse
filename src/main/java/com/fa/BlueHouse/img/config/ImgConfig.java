package com.fa.BlueHouse.img.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class ImgConfig  implements WebMvcConfigurer {
	 @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        // Cấu hình để truy cập vào ảnh trong thư mục 
		  registry.addResourceHandler("/imagesRequest/**")
          .addResourceLocations("file:///C:/img/request")
          ;
		  registry.addResourceHandler("/imagesRepair/**")
          .addResourceLocations("file:///C:/img/repair");
		  
		  registry.addResourceHandler("/imagesReport/**")
          .addResourceLocations("file:///C:\\img\\report");
		  
		  registry.addResourceHandler("/imagesRepair/**")
          .addResourceLocations("file:///E:/TaiLieu/Mock%20project/repair/img/");
		  registry.addResourceHandler("/imgNotification/**")
			.addResourceLocations("file:///E:/imgNoti/");
		  
		  registry.addResourceHandler("/imgResident/**")
		  .addResourceLocations("file:///C:/img/resident");
		  
		  registry.addResourceHandler("/imgEmployee/**")
		  .addResourceLocations("file:///E:/TaiLieu/Mock%20project/employee/img/");
		  }
	 
}


