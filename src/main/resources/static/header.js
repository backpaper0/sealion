"use strict";
$(function() {
    AJS.$('#sl-signout-dialog-open').on('click', function() {
        AJS.dialog2("#sl-signout-dialog").show();
    });
    AJS.$("#sl-signout-dialog-close").click(function(e) {
        e.preventDefault();
        AJS.dialog2("#sl-signout-dialog").hide();
    });
});

