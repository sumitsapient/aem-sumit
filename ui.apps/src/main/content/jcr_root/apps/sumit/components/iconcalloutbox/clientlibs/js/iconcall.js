/* global jQuery, Coral */
(function($, Coral) {
    "use strict";

    var registry = $(window).adaptTo("foundation-registry");

    registry.register("foundation.validation.validator", {
        selector: "[data-validation=icon-text]",
        validate: function(element) {
            let text = $(element);
            let value=text.val();
            let size = value.length;
            if(size > 175) {
               console.log(value.length);
               return "Maximum Character Allowed is - 175. You have entered "+size+" Characters!";
            }

        }
    });

   
})(jQuery, Coral);