(function ($) {
  'use strict';

  var CFM;
  const MASTER = "master";
  const CFM_EDITOR_SEL = ".content-fragment-editor";

  $(window).load(function () {
    if (window.Dam !== undefined) {
      CFM = window.Dam.CFM;
      console.log(CFM);
      getOpenAIContent();
    }
  });

  function getOpenAIContent() {
    const summaryBtns = $('.aisummary');
    const keywordBtns = $('.aikey');

    if (summaryBtns.length !== 0) {
      summaryBtns.on('click', generateSummaryResponse);
    }

    if (keywordBtns.length !== 0) {
      keywordBtns.on('click', generateKeywordResponse);
    }
  }

  function generateKeywordResponse(e) {
    e.preventDefault();
    $("#keyspin").addClass("loader");
    console.log("Keyword Button Clicked");

    const prompt = $(".cfm-multieditor-richtext-editor").text() || $("#sumit").find("p").text();
    const servletUrl = `/bin/chat?prompt=${encodeURIComponent(prompt)}&keyword=true`;
    console.log(servletUrl);

    $.ajax({
      url: servletUrl,
      success: function (response) {
        console.log(response);
        $("#keyspin").removeClass("loader");
        $("input[name=key]").val(response);
      }
    });
  }

  function generateSummaryResponse(e) {
    e.preventDefault();
    $("#spin").addClass("loader");
    console.log("Summary Button Clicked");

    const prompt = $("input[name=prompt]").val();
    const style = $("input[name=style]").val();
    const servletUrl = `/bin/chat?prompt=${encodeURIComponent(prompt)}&style=${encodeURIComponent(style)}`;
    console.log(servletUrl);

    $.ajax({
      url: servletUrl,
      success: function (response) {
        console.log(response);
        $("#spin").removeClass("loader");
        $(".cfm-multieditor-richtext-editor").text(response);
      }
    });
  }
}(jQuery));
