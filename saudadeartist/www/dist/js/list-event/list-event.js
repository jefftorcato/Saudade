(function ($) {
    "use strict";

    db.collection("event").where("artist", "==", uid)
    .onSnapshot(function(querySnapshot) {
        
        querySnapshot.forEach(function(doc) {
            console.log(doc.data())
            let card_html = [
                '<div class="card mb-3" style="max-width: 540px;">',
                    '<div class="row no-gutters">',
                        '<div class="col-md-4">',
                            '<img src="'+doc.data().photo+'" class="card-img eventPoster" alt="">',
                        '</div>',
                        '<div class="col-md-8">',
                            '<div class="card-body">',
                                '<div class="col-md-12">',
                                    '<h5 class="card-title eventName">'+doc.data().name+'</h5>',
                                    '<div class="row">',
                                        '<div class="col-md-5">',
                                            '<p>'+"Event City:"+'</p>',
                                        '</div>',
                                        '<div class="col-md-7">',
                                            '<p class="card-text eventLocation">'+doc.data().city+'</p>',
                                        '</div>',                                       
                                    '</div>',
                                    '<div class="row">',
                                        '<div class="col-md-5">',
                                            '<p>'+"Event Category:"+'</p>',
                                        '</div>',
                                        '<div class="col-md-7">',
                                            '<p class="card-text eventGroup">'+doc.data().category +'</p>',
                                        '</div>',                                       
                                    '</div>',
                                    '<div class="row">',
                                        '<div class="col-md-5">',
                                            '<p>'+"Event Tickets:"+'</p>',
                                        '</div>',
                                        '<div class="col-md-7">',
                                            '<p class="card-text eventTickets">'+doc.data().ticketCount+'</p>',
                                        '</div>',
                                    '</div>',
                                    '<div class="row">',
                                        '<div class="col-md-5">',
                                            '<p>'+"Tickets Sold:"+'</p>',
                                        '</div>',
                                        '<div class="col-md-7">',
                                            '<p class="card-text eventTicketsSold">'+"None" +'</p>',
                                        '</div>',
                                    '</div>',
                                    '<div class="row">',
                                        '<div class="col-md-5">',
                                            '<p>'+"Event City:"+'</p>',
                                        '</div>',
                                        '<div class="col-md-7">',
                                            '<p class="card-text eventTickets">'+doc.data().numRatings+'</p>',
                                        '</div>',
                                    '</div>',
                                '</div>',
                            '</div>',
                        '</div>',
                    '</div>',
                '</div>'
            ].join("\n")
            console.log(card_html)
            $(".cardList").append(card_html);
        });
    });
})(jQuery);