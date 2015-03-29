$(function () {
  var navEvent, actionEvent, linkEvent;

  navEvent = function (navTarget) {
    ga('send', 'event', 'main-nav', 'click', navTarget);
  };

  actionEvent = function (context, action) {
    ga('send', 'event', context, action);
  }

  linkEvent = function (linkTarget) {
    ga('send', 'event', 'link', 'click', linkTarget);
  }

  parentArticle = function (el) {
    var parent = $(el).parent()[0];
    if (parent === undefined) {
      console.error("No article parent found.")
      return undefined;
    } else if (parent.tagName === "ARTICLE") {
      return parent;
    } else {
      return parentArticle(parent);
    }
  };

  $("a").click(function (ev) {
    var $target = $(ev.target);
    if ($target.hasClass("no-instrument")) {
      return;
    } else if ($target.attr("data-action")) {
      actionEvent(parentArticle(ev.target).id,
                  $target.attr("data-action"));
    } else if ($target.hasClass("analytics-page-nav")) {
      navEvent(ev.target.hash);
    } else {
      linkEvent(ev.target.href);
    }
  });
});
