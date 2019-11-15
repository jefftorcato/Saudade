function routeMenulinks(id) {

    var link;

    switch (id) {
        case "link-dashboard":
            link = "./pages/dashboard/dashboard.html";
            break;
        case "link-addEvent":
            link = "./pages/add-event/add-event.html";
            break;
        case "link-eventList":
            link = "./pages/list-event/list-event.html";
        default:
            break;
    }

    return link;
}

function overlayOn() {
    document.getElementById("overlay").style.display = "flex";
  }
  
  function overlayOff() {
    document.getElementById("overlay").style.display = "none";
  }

$(".nav-link").on("click", function () {
    var nav_id = $(this);
    console.log(nav_id[0].id);

    var link = routeMenulinks(nav_id[0].id);
    $('#sidebar').toggleClass('active');
    $('#sidebarCollapse').toggleClass('active');
    $("#BaseDiv").load(link);
});

$(document).ready(function () {
    //$('#display-name').text(displayName)

    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
        $(this).toggleClass('active');
    });

    $('#signout').on('click', function () {
        firebase.auth().signOut().then(function () {
            // Sign-out successful.
        }).catch(function (error) {
            console.error(error)
        });
    });
});