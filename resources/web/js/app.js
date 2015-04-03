(function ($) {

  var onMoreContactsClick,
      onMoreContactsLoaded,
      spamApp = window.spamApp || {},
      contactTemplate,
      preventingDefault;

  // Load templates
  $.get('/templates/contact.hbs', function (data) {
    Handlebars.registerPartial('templates/contact', data);
    contactTemplate = Handlebars.compile(data);
  });

  $.get('/templates/email.hbs', function (data) {
    Handlebars.registerPartial('templates/email', data);
    emailTemplate = Handlebars.compile(data);
  });

  // Set up Handlebars rendering
  Handlebars.registerHelper('breaklines', function(text) {
    text = Handlebars.Utils.escapeExpression(text);
    text = text.replace(/(\r\n|\n|\r)/gm, '<br>');
    return new Handlebars.SafeString(text);
  });

  onMoreContactsLoaded = function (data, status, jqXHR) {
    data.addresses.forEach(function (val, idx) {
      $("#people-container").append(
        $(contactTemplate(val))
      );
    });
    spamApp._resize();
  };

  onMoreEmailsLoaded = function (data, status, jqXHR) {
    $("#email-container").html(
      $(emailTemplate(data.emails[0]))
    );
    spamApp._resize();
  };

  onMoreContactsClick = function (ev) {
    $.ajax({
      url: "/api/email-addresses?n=5",
      success: onMoreContactsLoaded
    });
  };

  onRefreshEmailClick = function (ev) {
    $.ajax({
      url: "/api/emails",
      success: onMoreEmailsLoaded
    });
  };

  preventingDefault = function (handler) {
    return function (ev) {
      handler(ev);
      ev.preventDefault();
    }
  };

  var renderApi = function () {
    var converter = new Showdown.converter({ extensions: ['twitter'] });
    $.ajax({
      url: "/doc/API.md",
      success: function (data) {
        $("#api-markdown").html(
          converter.makeHtml(
            data.
              // Remove the top-level headline
              replace(/^# API.*$/gm, "").
              // Downgrade every heading by one level
              replace(/^#/gm, "##")
          )
        );
        spamApp._resize();
      }
    });
  };

  $(function () {
    $("#button-get-more-contacts").click(onMoreContactsClick);
    $("#refresh-email").click(preventingDefault(onRefreshEmailClick));
    renderApi();
  });
})(jQuery);
