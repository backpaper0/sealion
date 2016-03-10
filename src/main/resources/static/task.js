"use strict";
$(function() {
    $('.sl-change-status').on('click', function() {
        superagent
            .post(location.pathname + ':status')
            .type('form')
            .send({ status: $(this).text(), csrfToken: $('#csrfToken').val() })
            .end(function() {
                location.reload();
            })
    });
    
    $('.sl-set-milestone').on('click', function() {
        superagent
            .post(location.pathname + ':milestone')
            .type('form')
            .send({ milestone: $(this).attr('data-sl-milestone'), csrfToken: $('#csrfToken').val() })
            .end(function() {
                location.reload();
            })
    });
    
    $('.sl-set-assignee').on('click', function() {
        superagent
            .post(location.pathname + ':assignee')
            .type('form')
            .send({ account: $(this).attr('data-sl-account'), csrfToken: $('#csrfToken').val() })
            .end(function() {
                location.reload();
            })
    });
});

