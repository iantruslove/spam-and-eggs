(function ($) {

  var onMoreContactsClick,
      onMoreContactsLoaded,
      spamApp = window.spamApp || {},
      contactTemplate;

  // Load templates
  $.get('/templates/contact.hbs', function (data) {
    Handlebars.registerPartial('templates/contact', data);
    contactTemplate = Handlebars.compile(data);
  });

  onMoreContactsLoaded = function (data, status, jqXHR) {
    data.addresses.forEach(function (val, idx) {
      $("#people-container").append(
        $(contactTemplate(val))
      );
    });
    spamApp._resize();
  };

  onMoreContactsClick = function (ev) {
    $.ajax({
      url: "/api/email-addresses?n=5",
      success: onMoreContactsLoaded
    });
  };

  $(function () {
    $("#button-get-more-contacts").click(onMoreContactsClick);
  });
})(jQuery);
