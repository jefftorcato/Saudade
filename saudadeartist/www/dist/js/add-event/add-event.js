(function ($) {
    "use strict"; // Start of use strict

    //let getCategories = fetchData("",ADMIN_PRIVATE_API+"/getCategories", "get");

    /*getCategories.then(data =>{
        data.forEach(element => {
            $("#inputCategory").append('<option value="' + element.id +'">'+ element.name +'</option>');
        });
    });*/

    $(document).ready(function () {
        db.collection("eventCategory")
            .onSnapshot(function (querySnapshot) {
                querySnapshot.forEach(function (doc) {
                    // doc.data() is never undefined for query doc snapshots
                    //console.log(doc.id, " => ", doc.data().name);
                    $("#inputCategory").append('<option value="' + doc.data().name + '">' + doc.data().name + '</option>');
                });
            })
    })


    $('#form-addEvent').submit(function (event) {
        event.preventDefault();
        overlayOn();
        var $form1 = $(this);
        // Let's select and cache all the fields
        var $inputs = $form1.find("input, select, button, textarea");
        $inputs.prop("disabled", true);

        let form_data = {
            artist: uid,
            avgRating: 0.0,
            category: $('#inputCategory').val(),
            city: $('#inputEventlocation').val(),
            name: $('#inputEventname').val(),
            numRatings: 0,
            ticketCount: parseInt($('#inputEventticket').val()),
            photo: ""
        }

        console.log(form_data)
        let eventEntry = db.collection("event").add(form_data)
            .then(function (docRef) {
                console.log("Document written with ID: ", docRef.id);
                uploadImage(docRef.id)
            })
            .catch(function (error) {
                console.error("Error adding document: ", error);
            });

        function uploadImage(docRef) {
            const selectedFile = document.getElementById('inputEventposter').files[0];

            // Upload file and metadata to the object 'images/image-name.ext'
            var uploadTask = storageRef.child('images/' + docRef).put(selectedFile);
            // Listen for state changes, errors, and completion of the upload.
            uploadTask.on(firebase.storage.TaskEvent.STATE_CHANGED, // or 'state_changed'
                function (snapshot) {
                    // Get task progress, including the number of bytes uploaded and the total number of bytes to be uploaded
                    var progress = Math.round((snapshot.bytesTransferred / snapshot.totalBytes) * 100);
                    //console.log('Upload is ' + progress + '% done');
                    document.getElementById('upload-msg').textContent = progress + '%'
                    switch (snapshot.state) {
                        case firebase.storage.TaskState.PAUSED: // or 'paused'
                            //console.log('Upload is paused');
                            break;
                        case firebase.storage.TaskState.RUNNING: // or 'running'
                            //console.log('Upload is running');
                            break;
                    }
                },
                function (error) {

                    // A full list of error codes is available at
                    // https://firebase.google.com/docs/storage/web/handle-errors
                    switch (error.code) {
                        case 'storage/unauthorized':
                            // User doesn't have permission to access the object
                            break;

                        case 'storage/canceled':
                            // User canceled the upload
                            break;

                        case 'storage/unknown':
                            // Unknown error occurred, inspect error.serverResponse
                            break;
                    }
                },
                function () {
                    // Upload completed successfully, now we can get the download URL
                    uploadTask.snapshot.ref.getDownloadURL().then(function (downloadURL) {
                        console.log('File available at', downloadURL);
                        form_data.photo = downloadURL;
                        db.collection("event").doc(docRef).set({
                                photo: form_data.photo
                            }, { merge: true })
                            .then(function () {
                                $inputs.prop("disabled", false);
                                $('#form-addEvent')[0].reset();
                                overlayOff();
                            })
                            .catch(function (error) {
                                console.error("Error adding document: ", error);
                            });
                    });
                });
        }
    })

})(jQuery); // End of use strict