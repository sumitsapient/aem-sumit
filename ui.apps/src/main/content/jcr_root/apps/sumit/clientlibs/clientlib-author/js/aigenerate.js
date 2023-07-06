(function ($) {
  'use strict';

  var CFM;
  const MASTER = "master";
  const CFM_EDITOR_SEL = ".content-fragment-editor";

  $(window).load(function () {
    if (window.location.pathname.includes('/content/dam/sumit/yadav/')){
    if (window.Dam !== undefined) {
          CFM = window.Dam.CFM;
          console.log(CFM);
          localStorage.removeItem('activeMarkerButton');
          localStorage.removeItem('activeTypeButton');
          getOpenAIContent();
        }}

  });

  function getOpenAIContent() {
    $("input[name='prompt']").prev("label").text("Enter Prompt *");
    $('.coral-Form-field _coral-Textfield').addClass('heading-text');
    const summaryRTE = ['short-warmth', 'short-aggressive', 'short-formal', 'detail-warmth', 'detail-aggressive', 'detail-formal', 'detail-formal', 'detail-formal'];

    $('.coral-Form-fieldwrapper').each(function (index) {
      $(this).find('.cfm-multieditor  ').addClass(summaryRTE[index - 1]);
      $(this).find('.cfm-multieditor-richtext-editor').addClass('item-' + summaryRTE[index - 1]);
      $(this).find('.cfm-multieditor-richtext-container').addClass('container-' + summaryRTE[index - 1]);
    });

    var wait = new Coral.Wait();

    const typeLabel = document.createElement('label');
    typeLabel.id = 'type';
    typeLabel.className = 'coral-Form-fieldlabel';
    typeLabel.textContent = 'Select Type *';
    $('.coral-Form-fieldwrapper')[0].append(typeLabel);

    const typeBtns = [
      { variant: 'secondary', innerText: 'Short' },
      { variant: 'secondary', innerText: 'Detail' }
    ];

    let currentTypeBtn = null;
    let activeTypeButton = null;

    for (const data of typeBtns) {
      const button = new Coral.Button().set(data);
      $(button).css({
        'border-width': '1px',
        'font-size': '12px'
      }).click((event) => {
        event.preventDefault();

        if (currentTypeBtn) {
          $(currentTypeBtn).css({ 'background-color': '', 'color': '#6e6e6e' }); // Reset previous button color
        }

        $(button).css({ 'background-color': '#6e6e6e', 'color': '#ffffff' }); // Change button color to gray
        currentTypeBtn = button; // Update currentTypeBtn
        activeTypeButton = button.innerText;
        localStorage.setItem('activeTypeButton', activeTypeButton);
        const markerStorageItem = localStorage.getItem('activeMarkerButton');
        const typeStorageItem = localStorage.getItem('activeTypeButton');
        if (markerStorageItem && typeStorageItem) {
          const prompt = $("input[name=prompt]").val();
          $('.coral-Form-fieldwrapper')[0].append(wait);
          generateSummaryResponse(prompt, typeStorageItem, markerStorageItem, wait);
        }
      });

      $('.coral-Form-fieldwrapper')[0].append(button);
    }

    console.log(activeTypeButton);

    const markersLabel = document.createElement('label');
    markersLabel.id = 'type';
    markersLabel.className = 'coral-Form-fieldlabel';
    markersLabel.textContent = 'Select Markers *';
    $('.coral-Form-fieldwrapper')[0].append(markersLabel);

    const markerBtns = [
      { variant: 'secondary', innerText: 'Warmth' },
      { variant: 'secondary', innerText: 'Aggressive' },
      { variant: 'secondary', innerText: 'Formal' }
    ];

    let currentMarkerButton = null;
    let activeMarkerButton = null;

    for (const data of markerBtns) {
      const button = new Coral.Button().set(data);
      $(button).css({
        'border-width': '1px',
        'font-size': '12px'
      }).click((event) => {
        event.preventDefault();

        if (currentMarkerButton) {
          $(currentMarkerButton).css({ 'background-color': '', 'color': '#6e6e6e' }); // Reset previous button color
        }

        $(button).css({ 'background-color': '#6e6e6e', 'color': '#ffffff' }); // Change button color to green
        currentMarkerButton = button; // Update currentMarkerButton
        activeMarkerButton = button.innerText;
        console.log(activeTypeButton);
        console.log(activeMarkerButton);
        localStorage.setItem('activeMarkerButton', activeMarkerButton);
        const markerStorageItem = localStorage.getItem('activeMarkerButton');
        const typeStorageItem = localStorage.getItem('activeTypeButton');
        if (markerStorageItem && typeStorageItem) {
          const prompt = $("input[name=prompt]").val();
          $('.coral-Form-fieldwrapper')[0].append(wait);
          generateSummaryResponse(prompt, typeStorageItem, markerStorageItem, wait);
        }
      });

      $('.coral-Form-fieldwrapper')[0].append(button);
    }
  }

  function generateSummaryResponse(prompt, type, marker, wait) {

    wait.set({ hidden: false });
    const servletUrl = `/bin/chat?prompt=${encodeURIComponent(prompt)}&type=${encodeURIComponent(type)}&marker=${encodeURIComponent(marker)}`;
    console.log(servletUrl);
    $.ajax({
      url: servletUrl,
      success: function (response) {
        if (type == 'Short' && marker == 'Warmth') {
          $('.cfm-multieditor  ').removeClass('short-warmth');
         // $(".item-short-warmth").text(response);
         $(".item-short-warmth").html("<p>" + response + "</p>");
         $(".container-short-warmth").addClass("cfm-multieditor-current-selected");
        }
        if (type == 'Short' && marker == 'Aggressive') {
          $('.cfm-multieditor  ').removeClass('short-aggressive');
          $(".item-short-aggressive").html("<p>" + response + "</p>");
         // $(".item-short-aggressive").text(response);
         $(".container-short-aggressive").addClass("cfm-multieditor-current-selected");
        }
        if (type == 'Short' && marker == 'Formal') {
          $('.cfm-multieditor  ').removeClass('short-formal');
          $(".item-short-formal").html("<p>" + response + "</p>");
         // $(".item-short-formal").text(response);
          $(".container-short-formal").addClass("cfm-multieditor-current-selected");
        }
        if (type == 'Detail' && marker == 'Warmth') {
          $('.cfm-multieditor  ').removeClass('detail-warmth');
          $(".item-detail-warmth").html("<p>" + response + "</p>");
        //  $(".item-detail-warmth").text(response);
        //  $(".container-detail-warmth").addClass("cfm-multieditor-current-selected");
        }
        if (type == 'Detail' && marker == 'Aggressive') {
          $('.cfm-multieditor  ').removeClass('detail-aggressive');
          $(".item-detail-aggressive").html("<p>" + response + "</p>");
         // $(".item-detail-aggressive").text(response);
          $(".container-detail-aggressive").addClass("cfm-multieditor-current-selected");
        }
        if (type == 'Detail' && marker == 'Formal') {
          $('.cfm-multieditor  ').removeClass('detail-formal');
          $(".item-detail-formal").html("<p>" + response + "</p>");
        //  $(".item-detail-formal").text(response);
          $(".container-detail-formal").addClass("cfm-multieditor-current-selected");
        }
        console.log(response);
        wait.set({ hidden: true });
      }
    });
  }
}(jQuery));
