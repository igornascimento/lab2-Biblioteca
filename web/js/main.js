/**
* Declare some global variables
*/
var BASE_URL = 'http://localhost:8080/lab2-Biblioteca/api/',
	searchKeyWord = null,
	responseElement = $('article#response');


/**
 * Iterates over the books and authors arrays to mount the html 
 */
function mountBookResults(data) {
	var html;
	html = '<h1>Exibindo resultados para "'+ searchKeyWord +'":</h1>';
	html += '<div class="row"><div class="card-deck">';
	// getting the books
	for (var i=0; i<data.length; i++) {
		// getting the authors
		var authors = data[i].author,
			printAuthors,
			sep;
		for (var j=0; j<authors.length; j++) {
			printAuthors = sep + authors[j].lastName + ', ' + authors[j].firstName;
			sep = '; ';
		}
		if (i % 3 == 0) {
			html += '</div></div><span class="clear"></span><div class="row"><div class="card-deck">';
		}
		html += '<div class="card flex-md-row">' +
					'<div class="card-body">' +
						'<h5 class="card-title">' + data[i].title + '</h5>' +
						'<h6 class="card-subtitle">'+ data[i].year +' - '+ printAuthors +'</h6>' +
						'<p class="card-text">'+ data[i].shortDescription +'</p>' +
					'</div>' +
					'<img src="'+ data[i].img +'">' +
				'</div>';
	}
	html += '</div></div>';
	return html;
}


$(document).ready(function() {
	/**
	 * Books search
	 */
	$('#quick-search').submit(function(ev) {
		searchKeyWord = $('#quick-search-input').val();
		$('#overlay').show();

		$.ajax({
			url: BASE_URL + 'books/search/',
			method: 'GET',
			data: {title: searchKeyWord},
			success: function(data) {
				html = mountBookResults(data);
			},
			error: function() {
				html = '<h1>Oops!</h1><p>Não foram encontrados resultados para a sua pesquisa.</p>';
			}
		})
		.done(function() {
			$('#overlay').hide();
			responseElement.html(html);
		});
		ev.preventDefault();
	});
});