(function ($) {

    'use strict';

    var CFM,
        MASTER = "master",
        CFM_EDITOR_SEL = ".content-fragment-editor";

    $( window ).load(function() {

        if (window.Dam != undefined) {
            CFM = window.Dam.CFM;
            console.log(CFM);
            getOpenAIContent();
        }
    });

    function getOpenAIContent() {
        var btn = document.querySelectorAll('.aisummary');
        if(btn.length !== 0) {
            btn.forEach(generateResponse);
        }

    }

    function generateResponse(selector) {
        selector.addEventListener('click',(e)=>{                     
             e.preventDefault();
			 $( "#spin" ).addClass("loader");
             console.log("Button Clicked");
             var prompt = $("input[name=prompt]").val();
             var style = $("input[name=style]").val();
             var servletUrl = "/bin/chat?prompt=" + encodeURIComponent(prompt)+"&"+"?style="+encodeURIComponent(style);
             console.log(servletUrl);

            // Send a request to the servlet
             var xhr = new XMLHttpRequest();
             xhr.open("GET", servletUrl);
             xhr.onreadystatechange = function () {
             if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
             console.log(xhr.responseText);   
             $( "#spin" ).removeClass("loader");    
             $(".cfm-multieditor-richtext-editor").text(xhr.responseText);   
        }
      };
            xhr.send();

      });
    }
}(jQuery));