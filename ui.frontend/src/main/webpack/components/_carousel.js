$(document).ready(function () {
  var deskTop =
    '<div class="flex-items">'+
    '<img src="/content/dam/caap-learner/caap_carousel/techsupport-blue.svg">'+
    '<a class="card-title-text"></a>'+
    '<div class="card-sub-text"></div>'+
    '<a class="continue-link">Continue Reading</a>'+
    '</div>';

  var mobileMark = '<li class="c"> <div class="row">  </div> </li>';  

  function processMobileMarkup(data) {
    var result = data;
    $.each(result, function (index) {
      var art = result[index];
      var $mobElem = $($.parseHTML(mobileMark));
      $mobElem.find(".row").text(art.articleTitle);
      
      if (index <= 6) {
          $(".mobile")
          .append($mobElem);   
      }
    });
  }

  function processDeskMarkup(data) {
    var result = data;
    console.log(result);

    $.each(result, function (index) {
      var art = result[index];
      var $deskElem = $($.parseHTML(deskTop));
      $deskElem.find(".card-title-text").text(art.articleTitle);
      $deskElem.find(".card-title-text").attr("href", art.articleURL);
      $deskElem.find(".card-sub-text").text(art.articleDesc);
      $deskElem
        .find(".continue-link")
        .attr("href", art.articleURL);

      if (index <= 2){
          $("#carouselIndicators1")
          .find(".carousel-inner .row")
          .append($deskElem);
          
          
      }
      else if (index > 2 && index < 6)
        $("#carouselIndicators2")
        .find(".carousel-inner .row")
        .append($deskElem);
        
    });
  }

var url = [
  {
      "id": "c308fe-1562-562e-45db-13b820a1810",
      "articleTitle": "Beans Salad",
      "articleURL": "https://images.unsplash.com/photo-1511690656952-34342bb7c2f2?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTN8fGZvb2R8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60",
      "articleDesc": "This salad acts best as a side for your favorite spaghetti recipes"
  },
  {
      "id": "4edfe48-a3eb-2b55-ab0-422b807a8f3",
      "articleTitle": "Fruits Salad",
      "articleURL": "https://images.unsplash.com/photo-1570197571499-166b36435e9f?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTY4fHxmb29kfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60",
      "articleDesc": "This salad acts best as a side for your favorite spaghetti recipes"
  },
  {
      "id": "d1dc3e7-4ab0-f38-b750-48cca602143",
      "articleTitle": "Bluebbery and Banana",
      "articleURL": "https://images.unsplash.com/photo-1484723091739-30a097e8f929?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTF8fGZvb2R8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60",
      "articleDesc": "Blueberries can help heart health, bone strength, skin health, blood pressure."
  },
  {
      "id": "836352f-0a1e-3e74-dff-d1214c7ad24",
      "articleTitle": "Veggies",
      "articleURL": "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mjd8fGZvb2R8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=60",
      "articleDesc": "A diet rich in vegetables and fruits can lower blood pressure, reduce the risk of heart."
  },
  {
      "id": "83b4dd0-af-2f00-e47b-354862d81b8",
      "articleTitle": "Eggs and Spinach",
      "articleURL": "https://images.unsplash.com/photo-1482049016688-2d3e1b311543?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=653&q=80",
      "articleDesc": "Rich in iron, spinach boosts your strength and metabolism. "
  }
];

   $.getJSON(
    "http://localhost:3002/caap",
    function (data) {
      var dialog = $('.dialog').val();
      var dialogObj = JSON.parse(dialog);
      console.log("Dialog data",dialogObj);
      console.log("API data",data);
      var merged = $.extend( true, data, dialogObj );
      console.log("Merged data",merged);
      processDeskMarkup(merged);
      processMobileMarkup(merged);
    }
  );
  
    var slideCount =  $(".slider ul li").length;
    var slideWidth =  $(".slider ul li").width();
    var slideHeight =  $(".slider ul li").height();
    var slideUlWidth =  slideCount * slideWidth;
    
    $(".slider").css({"max-width":slideWidth, "height": slideHeight});
    $(".slider ul").css({"width":slideUlWidth, "margin-left": - slideWidth });
    $(".slider ul li:last-child").prependTo($(".slider ul"));
    
    function moveLeft() {
      $(".slider ul").stop().animate({
        left: + slideWidth
      },700, function() {
        $(".slider ul li:last-child").prependTo($(".slider ul"));
        $(".slider ul").css("left","");
      });
    }
    
    function moveRight() {
      $(".slider ul").stop().animate({
        left: - slideWidth
      },700, function() {
        $(".slider ul li:first-child").appendTo($(".slider ul"));
        $(".slider ul").css("left","");
      });
    }
    
    
    $(".next").on("click",function(){
      moveRight();
    });
    
    $(".prev").on("click",function(){
      moveLeft();
    });

  var slideCount1 =  $(".slidermobile ul li").length;
  var slideWidth1 =  $(".slidermobile ul li").width();
  var slideHeight1 =  $(".slidermobile ul li").height();
  var slideUlWidth1 =  slideCount1 * slideWidth1;
  
  $(".slidermobile").css({"max-width":slideWidth1, "height": slideHeight1});
  $(".slidermobile ul").css({"width":slideUlWidth1, "margin-left": - slideWidth1 });
  $(".slidermobile ul li:last-child").prependTo($(".slidermobile ul"));
  
  function moveLeft1() {
    $(".slidermobile ul").stop().animate({
      left: + slideWidth1
    },700, function() {
      $(".slidermobile ul li:last-child").prependTo($(".slidermobile ul"));
      $(".slidermobile ul").css("left","");
    });
  }
  
  function moveRight1() {
    $(".slidermobile ul").stop().animate({
      left: - slideWidth1
    },700, function() {
      $(".slidermobile ul li:first-child").appendTo($(".slidermobile ul"));
      $(".slidermobile ul").css("left","");
    });
  }
  
  
  $(".next1").on("click",function(){
    moveRight1();
  });
  
  $(".prev1").on("click",function(){
    moveLeft1();
  });
    
    
  });
