
function showMessage(r) {
    var x = document.getElementsByClassName('messagediv');
        if (x[r].style.display === "none") {
            x[r].style.display = "block";
        } else {
            x[r].style.display = "none";
        }


}

    document.addEventListener('click', function () {
        for (r = 0; r < document.getElementsByClassName('mail').length; r++){
        document.getElementsByClassName('mail')[r]
            .addEventListener('click', showMessage(r));
        }
    });

