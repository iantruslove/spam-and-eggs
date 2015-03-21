(function ($) {

  var onMoreContactsClick,
      onMoreContactsLoaded,
      spamApp = window.spamApp || {};

  onMoreContactsLoaded = function (data, status, jqXHR) {
    data.addresses.forEach(function (val, idx) {
      $("#people-container").append( $('<p> <span class="fa fa-user"></span> ' +
                                       val["first-name"] + ' ' + val["last-name"] +
                                       '<br> <span class="fa fa-envelope-o"></span> ' +
                                       val["email-address"].replace(/</g,"&lt;").replace(/>/g,"&gt;") +
                                       '</p>')
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
