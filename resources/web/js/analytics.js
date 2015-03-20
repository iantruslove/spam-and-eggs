$(function () {
  var navEvent = function (navTarget) {
    ga('send', 'event', 'main-nav', 'click', navTarget);
  };
  var linkEvent = function (linkTarget) {
    ga('send', 'event', 'link', 'click', linkTarget);
  }

  $(".instrument").click(function (ev) {
    linkEvent(ev.target.href);
  });

  $(".analytics-page-nav").click(function (ev) {
    navEvent(ev.target.hash);
  });
});
