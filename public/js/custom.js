
document.addEventListener('DOMContentReady', function () {
    document.getElementById('delete')
        .addEventListener('click', sendDeleteRequest(document..routes.BooksController.destroy(book.id),));
});

function clickHandler(element) {
    element.
}

function sendDeleteRequest(url , rUrl) {
  $.ajax({
      url: url,
      method: "DELETE",
      success: function (){
        window.location = rUrl;
      },
      error: function() {
          window.location.reload();
      }
  });
}
