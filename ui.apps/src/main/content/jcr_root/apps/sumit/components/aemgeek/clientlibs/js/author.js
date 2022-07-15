/* global jQuery, Coral */
(function($, Coral) {
    "use strict";

    console.log(" --------CUSTOM DIALOG CLIENTLIBS LOADED FOR AEMGEEK COMPONENT------- ");

    var registry = $(window).adaptTo("foundation-registry");

    //Validator required for multifield max and min items
    registry.register("foundation.validation.validator", {
        selector: "[data-validation=geeks-multifield]",
        validate: function(element) {
            var el = $(element);
            let max=el.data("max-items");
            let min=el.data("min-items");
            let items=el.children("coral-multifield-item").length;
            let domitems=el.children("coral-multifield-item");
            console.log("{} : {} :{} ",max,min,items);
            if(items>max){
              /* Use below line if you don't want to add item in multifield more than max limit */
              domitems.last().remove();
              return "You can add maximum "+max+" books. You are trying to add "+items+"th book."
            }
            if(items<min){
                return "You have to add minimum "+min+" books."
            }
        }
    });

    registry.register("foundation.validation.validator", {
        selector: "[data-validation=authorname]",
        validate: function(element) {
            let el = $(element);
            let pattern=/[0-9a-z]/;
            let value=el.val();
            if(pattern.test(value)){
               return "Only UpperCase Allowed";
            }

        }
    });

   
})(jQuery, Coral);