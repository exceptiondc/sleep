   $(function () {
       var $sleepLogo = $('.sleep-logo');
       if(!$sleepLogo.attr('href')){
           $sleepLogo.attr('href', '/web/index.html')
       }
       $sleepLogo.on('click', function () {
           localStorage.setItem('langType', 'ch')
       });

       setNavActive();
       function setNavActive() {
           var nav = $('.sleep-nav-list').find('li');
           $('.sleep-nav-list').find('.active').removeClass('active');
           if(new RegExp('page/news/news-').test(location.href)){
               nav.eq(2).addClass('active');
               return;
           }
           for(var i = 0; i < nav.length - 2; i++){
               var $nav = $(nav[i]);
               var href = $nav.find('a').attr('href');
               if(href){
                   if(new RegExp(href).test(location.href)){
                       $nav.addClass('active');
                       break;
                   }
               }
           }
       }
       var lang = $('.sleep-nav-list').find('.sleep-lang').find('a');
       var langType = localStorage.getItem('langType');
       if(langType === 'en'){
           lang.eq(0).attr('style', 'color: #ccc;');
           lang.eq(1).attr('style', 'color: #333;');
           $('[data-en]').each(function (index, ele) {
               ele.textContent = ele.getAttribute('data-en');
           });
           lang.eq(1).on('click', function () {
               localStorage.setItem('langType', 'ch');
               location.reload();
           });

           // nav strange logic start
           var navItem = $('.sleep-nav-list').find('li');
           navItem.eq(0).hide();
           navItem.eq(1).hide();
           navItem.eq(2).hide();
           navItem.eq(3).hide();
           navItem.eq(4).hide();
           navItem.eq(5).find('span').hide();
           lang.eq(1).attr('href', '/web/index.html');
           // nav strange logic end
       } else {
            // nav strange logic
           lang.eq(0).attr('href', '/web/page/AboutUs.html');
           // nav strange logic end

           lang.eq(0).on('click', function () {
               localStorage.setItem('langType', 'en');
               location.reload();
           });
       }
       $('body').show();
       $(window).scroll(function(){
           if($(window).scrollTop()>150){  //距顶部多少像素时，出现返回顶部按钮
               $('#back_to_top').fadeIn();
           }
           else{
               $('#back_to_top').hide();
           }
       });
       $('#back_to_top').click(function(){
           $('html,body').animate({'scrollTop':0},1000); //返回顶部动画 数值越小时间越短
       });

       $("#back_to_top").hover(function(){
           $("#back_to_top").attr("src", "./images/top-hover.png");
       },function(){
           $("#back_to_top").attr("src","./images/top.png");
       });

       $("#footer-sina").hover(function(){
           $("#footer-sina").attr("src", "./images/footer-sina2.png");
       },function(){
           $("#footer-sina").attr("src","./images/footer-sina.png");
       });

       $("#footer-wechat").hover(function(){
           $("#footer-wechat").attr("src", "./images/footer-wechat2.png");
       },function(){
           $("#footer-wechat").attr("src","./images/footer-wechat.png");
       });
   });