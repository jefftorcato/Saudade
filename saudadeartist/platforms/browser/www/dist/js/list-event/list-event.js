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
                            '<img src="'+doc.data().pathToImage+'" class="card-img eventPoster" alt="">',
                        '</div>',
                        '<div class="col-md-8">',
                            '<div class="card-body">',
                                '<h5 class="card-title eventName">'+doc.data().eventName+'</h5>',
                                '<p class="card-text eventLocation">'+doc.data().eventLocation+'</p>',
                                '<p class="card-text eventGroup">'+ +'</p>',
                                '<p class="card-text eventTickets">'+doc.data().ticketCount+'</p>',
                                '<p class="card-text eventTicketsSold">'+ +'</p>',
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