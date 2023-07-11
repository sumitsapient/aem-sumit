(function ($) {
  'use strict';

  const CFM_EDITOR_SEL = ".content-fragment-editor";

  $(window).load(function () {
      if (window.location.pathname.includes('/content/dam/sumit') && window.Dam !== undefined) {
        getOpenAIContent();
      }
    });

  function getOpenAIContent() {
    const summaryRTE = ['description', 'summary'];

    $('.cfm-multieditor ').each(function (index) {
      $(this).find('.cfm-multieditor-richtext-editor').addClass('item-' + summaryRTE[index]);
      $(this).find('.cfm-multieditor-richtext-container').addClass('container-' + summaryRTE[index]);
    });

    var wait = new Coral.Wait();
    $(wait).css({
      'margin-top': '5px',
      'margin-left': '8px'
    });

        const wordsCountFieldWrapper = $('input[name="wordsCount"]').closest('div.coral-Form-fieldwrapper');
        const descriptionFieldWrapper = $('input[name="description"]').closest('div.coral-Form-fieldwrapper');
        const summaryFieldWrapper = $('input[name="summary"]').closest('div.coral-Form-fieldwrapper');
        const keywordsFieldWrapper = $('input[name="keywords"]').closest('div.coral-Form-fieldwrapper');
        const describeImageFieldWrapper = $('input[name="describeImage"]').closest('div.coral-Form-fieldwrapper');
        const summaryTypeWrapper = $('input[name="summaryType"]').closest('div.coral-Form-fieldwrapper');
        const imagePathField = $('input[name="imagePath"]');

        wordsCountFieldWrapper.addClass("short-warmth");
    if ($("input[name=summaryType]").val() === 'custom') {
        wordsCountFieldWrapper.removeClass("short-warmth");
    }

    descriptionFieldWrapper.addClass("short-warmth");
    summaryFieldWrapper.addClass("short-warmth");
    keywordsFieldWrapper.addClass("short-warmth");
    describeImageFieldWrapper.addClass("short-warmth");
    imagePathField.addClass("short-warmth");

    const button = new Coral.Button().set({
                                                variant: 'secondary',
                                                innerText: 'AI Fetch'
                                              });

    const aiKeyBtn = new Coral.Button().set({
      variant: 'secondary',
      innerText: 'AI Keywords',
      id: 'key'
    });
    const aiSumBtn = new Coral.Button().set({
      variant: 'secondary',
      innerText: 'AI Summary'
    });
    const aiImgBtn = new Coral.Button().set({
      variant: 'secondary',
      innerText: 'AI Image'
    });

    let buttonStyle = {'border-width': '1px',
                       'font-size': '12px',
                       'margin-top': '12px'
                       }

    $(aiKeyBtn).css(buttonStyle);
    $(aiImgBtn).css(buttonStyle);
    $(aiSumBtn).css(buttonStyle);

    descriptionFieldWrapper.append(aiKeyBtn);
    descriptionFieldWrapper.append(aiSumBtn);
    descriptionFieldWrapper.append(aiImgBtn);

    summaryTypeWrapper.append(button);
    $(button).css(buttonStyle).click((event) => {
      event.preventDefault();
      button.set({ hidden: true });
      wait.set({ hidden: false });
      let type = $("input[name=summaryType]").val();
      let count = $("input[name=wordsCount]").val();
      var messageArray = [];
      $('input[name="message"]').each(function () {
        var value = $(this).val();
        messageArray.push(value);
      });
      console.log('Message:', messageArray);
      console.log('Type:', type);
      console.log('Count:', count);
      const body = {
        message: messageArray,
        count: count,
        type: type
      };
      console.log('Body:', body);
      const servletUrl = `/bin/openai?payload=${JSON.stringify(body)}`;

      $.ajax({
        url: servletUrl,
        success: function (response) {
          wait.set({ hidden: true });
          button.set({ hidden: false });
          descriptionFieldWrapper.removeClass("short-warmth");
          $(".item-description").html("<p>" + response + "</p>");
          $(".container-description").addClass("cfm-multieditor-current-selected");
          console.log(response);
        }
      });
      summaryTypeWrapper.append(wait);
    });

    $('coral-select[name="summaryType"]').on('change', function () {
      var selectedValue = $(this).val();
      console.log('Selected value:', selectedValue);
      if (selectedValue === 'custom') {
        wordsCountFieldWrapper.removeClass("short-warmth");
      } else {
        wordsCountFieldWrapper.addClass("short-warmth");
      }
    });

    $(aiKeyBtn).click((event) => {
      event.preventDefault();
      aiKeyBtn.set({ innerText: 'Loading Keywords...' });
      $(aiKeyBtn).css({ 'background-color': '#6e6e6e', 'color': '#ffffff' });//change color to gray
      var aiDesc = $('.item-description p').text();
      const servletUrl = `/bin/openai?keyword=${encodeURIComponent(aiDesc)}`;
      $.ajax({
        url: servletUrl,
        success: function (response) {
          aiKeyBtn.set({ innerText: 'AI Keywords' });
          $(aiKeyBtn).css({ 'background-color': '', 'color': '#6e6e6e' });
          $('input[name="keywords"]').val(response);
          keywordsFieldWrapper.removeClass("short-warmth");
          console.log("Keywords:", response);
        }
      });
    });

    $(aiSumBtn).click((event) => {
      event.preventDefault();
      aiSumBtn.set({ innerText: 'Loading Summary...' });
      $(aiSumBtn).css({ 'background-color': '#6e6e6e', 'color': '#ffffff' });//change color to gray
      var aiDesc = $('.item-description p').text();
      const servletUrl = `/bin/openai?summary=${encodeURIComponent(aiDesc)}`;
      $.ajax({
        url: servletUrl,
        success: function (response) {
          aiSumBtn.set({ innerText: 'AI Summary' });
          $(aiSumBtn).css({ 'background-color': '', 'color': '#6e6e6e' });
          summaryFieldWrapper.removeClass("short-warmth");
          $(".item-summary").html("<p>" + response + "</p>");
          $(".container-summary").addClass("cfm-multieditor-current-selected");
          console.log("Summary:", response);
        }
      });
    });

    $(aiImgBtn).click((event) => {
      event.preventDefault();
      aiImgBtn.set({ disabled: true });
      //getImage();
      const searchImageBtn = { variant: 'secondary', innerText: 'Generate' };
      const buttonImg = new Coral.Button().set(searchImageBtn);
      describeImageFieldWrapper.removeClass("short-warmth");
      describeImageFieldWrapper.append(buttonImg);
      $(buttonImg).css({
        'border-width': '1px',
        'font-size': '12px',
        'margin-top': '14px'
      }).click((event) => {
        event.preventDefault();
        buttonImg.set({ innerText: 'Generating Image...' });
        $(buttonImg).css({ 'background-color': '#6e6e6e', 'color': '#ffffff' });
        const imageDef = $('input[name="describeImage"]').val();
        let data = { "prompt": imageDef, "operationname": "Generate" };
        let servletUrl = "/bin/image";
        // Send a request to the servlet
        let xhr = new XMLHttpRequest();
        xhr.open("POST", servletUrl, true);
        xhr.setRequestHeader('Content-type', 'application/json');
        xhr.onreadystatechange = function () {
          if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            console.log(xhr.responseText);

            var imgElement = $('<img>');
            imgElement.attr('src', xhr.responseText);
            imgElement.css({
              'border-radius': '5px',
              'height': '300px',
              'display': 'flex',
              'margin': 'auto'
            });

            var closeButton = $('<button>', {
              id: 'closeButton',
              text: 'x',
              class: 'btnClose'
            });

            var imageContainer = $('<div>', {
              id: 'imageContainer',
              class: 'imgContainer'
            }).append(closeButton, imgElement);

            $('input[name="imagePath"]').val(xhr.responseText);
            describeImageFieldWrapper.after(imageContainer);
            buttonImg.set({ innerText: 'Generate' });
            $(buttonImg).css({ 'background-color': '', 'color': '#6e6e6e' });

            $('#closeButton').click(function (event) {
              event.preventDefault();
              $('#imageContainer').remove();
            });
          }
        };
        xhr.send(JSON.stringify(data));
      });
    });
  }
}(jQuery));
