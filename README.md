# fmall  
电商网站    
技术栈:     
前端: thymleaf、jQuery  
后端: SpringBoot、MyBatis、fastdfs、redis、elasticsearch    
数据库: MySQL  
使用nginx做反向代理    

用户模块  
http://user.fmall.com:8080/  
fmall-user-service用户服务的service层8070  
fmall-user-web用户服务的web层8080  

后台管理模块  
http://manage.fmall.com:8081/  
fmall-manage-service用户服务的service层8071  
fmall-manage-web用户服务的web层8081  

商品详情模块  
http://item.fmall.com:8082/  
fmall-item-service前台的商品详情服务 8072  
fmall-item-web前台的商品详情展示 8082  

搜索模块  
http://search.fmall.com:8083/index (商城首页)   
http://search.fmall.com:8083/list  
fmall-search-web 搜索服务的前台 8083  
fmall-search-service 搜索服务的后台 8073  

购物车模块  
http://cart.fmall.com:8084/  
fmall-cart-web 购物车的前台 8084  
fmall-cart-service 购物车的后台 8074  

用户认证中心模块  
http://passport.fmall.com:8085/  
fmall-passport-web 用户认证中心 8085  
fmall-user-service 用户服务的service层8070  

订单模块  
http://order.fmall.com:8086/  
fmall-order-web 订单 8086  
fmall-order-service 订单服务 8076  

支付模块  
http://payment.fmall.com:8087/  
fmall-payment 8087  